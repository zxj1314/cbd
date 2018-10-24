package com.test.cbd.common.utils;



import com.test.cbd.common.exception.BizRuntimeException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <br>
 * <b>功能：</b>数字工具类<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
public class NumberUtils {

    /**
     * 浮点类型的格式
     */
    public final static String PATTERN_DEFAULT_DOUBLE = "###0.00";

    /**
     * 金额格式
     */
    public final static String PATTERN_DEFAULT_MONEY = "#,##0.00";

    /**
     * 格式化金额
     *
     * @param money
     * @param pattern 格式
     * @return
     */
    public static String formatMoney(BigDecimal money, String pattern) {
        if (money == null) {
            return null;
        }
        return new DecimalFormat(pattern).format(money);
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String formatMoney(BigDecimal money) {
        return formatMoney(money, "#,##0.00");
    }

    /**
     * 格式化数字
     *
     * @param numeric
     * @param pattern 格式
     * @return
     */
    public static String formatNumeric(Double numeric, String pattern) {
        if (numeric == null) {
            return null;
        }
        return new DecimalFormat(pattern).format(numeric);
    }

    /**
     * 格式化数字，默认格式：###0.00
     *
     * @param numeric
     * @return
     */
    public static String formatNumeric(Double numeric) {
        return formatNumeric(numeric, "###0.00");
    }

    /**
     * 格式化数字
     *
     * @param numeric
     * @param pattern
     * @return
     */
    public static String formatNumeric(Long numeric, String pattern) {
        if (numeric == null) {
            return null;
        }
        return new DecimalFormat(pattern).format(numeric);
    }

    /**
     * 格式化数字
     *
     * @param numeric
     * @param pattern
     * @return
     */
    public static String formatNumeric(Integer numeric, String pattern) {
        if (numeric == null) {
            return null;
        }
        return new DecimalFormat(pattern).format(numeric);
    }

    /**
     * 是否是正整数
     *
     * @param str
     * @return
     */
    public static boolean isNumericPositiveInt(String str) {
        Pattern pattern = Pattern.compile("[1-9][0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 小写金额转大写金额
     *
     * @param money
     * @return
     */
    public static String convertToChineseNumber(BigDecimal money) {
        if (money == null) {
            return "";
        }
        Double number = money.doubleValue();
        StringBuffer chineseNumber = new StringBuffer();
        String[] num = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] unit = {"分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万"};
        String tempNumber = String.valueOf(Math.round((number * 100)));
        int tempNumberLength = tempNumber.length();
        if ("0".equals(tempNumber)) {
            return "零圆整";
        }
        if (tempNumberLength > 15) {
            throw new BizRuntimeException("超出转化范围.");
        }
        boolean preReadZero = true;    //前面的字符是否读零
        for (int i = tempNumberLength; i > 0; i--) {
            if ((tempNumberLength - i + 2) % 4 == 0) {
                //如果在（圆，万，亿，万）位上的四个数都为零,如果标志为未读零，则只读零，如果标志为已读零，则略过这四位
                if (i - 4 >= 0 && "0000".equals(tempNumber.substring(i - 4, i))) {
                    if (!preReadZero) {
                        chineseNumber.insert(0, "零");
                        preReadZero = true;
                    }
                    i -= 3;    //下面还有一个i--
                    continue;
                }
                //如果当前位在（圆，万，亿，万）位上，则设置标志为已读零（即重置读零标志）
                preReadZero = true;
            }
            Integer digit = Integer.parseInt(tempNumber.substring(i - 1, i));
            if (digit == 0) {
                //如果当前位是零并且标志为未读零，则读零，并设置标志为已读零
                if (!preReadZero) {
                    chineseNumber.insert(0, "零");
                    preReadZero = true;
                }
                //如果当前位是零并且在（圆，万，亿，万）位上，则读出（圆，万，亿，万）
                if ((tempNumberLength - i + 2) % 4 == 0) {
                    chineseNumber.insert(0, unit[tempNumberLength - i]);
                }
            }
            //如果当前位不为零，则读出此位，并且设置标志为未读零
            else {
                chineseNumber.insert(0, num[digit] + unit[tempNumberLength - i]);
                preReadZero = false;
            }
        }
        //如果分角两位上的值都为零，则添加一个“整”字
        if (tempNumberLength - 2 >= 0 && "00".equals(tempNumber.substring(tempNumberLength - 2, tempNumberLength))) {
            chineseNumber.append("整");
        }
        return chineseNumber.toString();
    }
}
