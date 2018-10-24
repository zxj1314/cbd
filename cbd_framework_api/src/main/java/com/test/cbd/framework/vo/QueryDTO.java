package com.test.cbd.framework.vo;

import java.io.Serializable;

/**
 * 功能：web接口分页或者排序等复杂查询时使用
 * 作者：chenjiefeng
 * 日期：2018/3/20
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2017
 */
public class QueryDTO implements Serializable {

    private PageBean pageBean;

    private OrderBean orderBean;


    public QueryDTO() {

    }


    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

}
