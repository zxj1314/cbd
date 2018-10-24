package com.test.cbd.framework.util;

/**
 * Twitter_Snowflake （id 生成器）
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截= 得到的值）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * 改造成：1位标识 + 47位相对时间戳 + 4位机器码 + 12位序列 （转成16进制的话，倒数第4个值就是机器码）
 */
public class IdWorker {

    /** 工作机器ID(0~16) */
    private final long workerId;

    /** 机器id所占的位数 */
    private final static long workerIdBits = 4L;
    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
    /** 序列在id中占的位数 */
    private final static long sequenceBits = 12L;
    /** 机器ID向左移序列的位数 */
    private final static long workerIdShift = sequenceBits;
    /** 时间截向左移X位 */
    private final static long timestampLeftShift = sequenceBits + workerIdBits;
    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    public final static long sequenceMask = -1L ^ -1L << sequenceBits;

    /** 开始时间截 (2018-01-01) */
    private final static long twepoch = 1288834974657L;
    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;
    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /**
     * @param workerId 机器号，如果为分布式环境，则workerId必须为不一样的自然数
     */
    public IdWorker(final long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
//                System.out.println("###########" + sequenceMask);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards. Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        long nextId = ((timestamp - twepoch << timestampLeftShift))  | (this.workerId << this.workerIdShift) | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

}
