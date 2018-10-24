package com.test.cbd.framework.service;


import com.test.cbd.framework.bean.UserToken;
import com.test.cbd.framework.vo.BaseVO;
import com.test.cbd.framework.vo.PageResult;
import com.test.cbd.framework.vo.QueryParam;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 * 作者：chenjiefeng
 * 日期：2018/5/17
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
public interface BaseBusService<V extends BaseVO> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    V findById(Serializable id);

    /**
     * 根据param条件查询数据
     * @param queryParam
     * @return
     */
    List<V> findBy(QueryParam queryParam);

    /**
     * 根据BaseQuery条件查询分页数据
     * @param queryParam 查询条件
     * @return
     */
    PageResult<V> findPage(QueryParam queryParam);

    int deleteById(UserToken userTokenInfo, Serializable id);

    String insert(UserToken userTokenInfo, V maindata);

    int update(UserToken userTokenInfo, V maindata);


}
