package com.test.cbd.framework.bean;

/**
 * 功能：设备类型
 * 作者：chenjiefeng
 * 日期：2018/3/30
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
public enum DeviceEnum {

    /**
     * web
     */
    DEVICE_WEB(0),
    /**
     * app
     */
    DEVICE_APP(1);

    private int type;

    DeviceEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
