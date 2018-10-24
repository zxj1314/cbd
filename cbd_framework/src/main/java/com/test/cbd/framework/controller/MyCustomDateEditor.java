package com.test.cbd.framework.controller;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：
 * 作者：chenjiefeng
 * 日期：2018/7/18
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
public class MyCustomDateEditor extends PropertyEditorSupport {

    private static SimpleDateFormat DF_yMd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat DF_yMd_Hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || !StringUtils.hasText(text)) {
            this.setValue((Object)null);
        } else {
            try{
                this.setValue(new Date(Long.parseLong(text)));
            } catch (NumberFormatException var1) {
                try {
                    if(text.length() <= 10){
                        this.setValue(DF_yMd.parse(text));
                    }else{
                        this.setValue(DF_yMd_Hms.parse(text));
                    }
                } catch (ParseException var2) {
                    throw new IllegalArgumentException("Could not parse date: " + var2.getMessage(), var2);
                }
            }
        }
    }
}
