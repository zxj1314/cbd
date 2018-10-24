package com.test.cbd.framework.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.cbd.common.bean.BeanMapper;
import com.test.cbd.common.bean.Reflections;
import com.test.cbd.common.exception.BizRuntimeException;
import com.test.cbd.framework.bean.UserToken;
import com.test.cbd.framework.dao.BaseDAO;
import com.test.cbd.framework.domain.BaseDO;
import com.test.cbd.framework.service.BaseBusService;
import com.test.cbd.framework.util.IdGenerator;
import com.test.cbd.framework.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>service基类<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2017-10-19 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2017<br>
 */
@Slf4j
//@Service(version = "1.0.0")
public abstract class BaseBusServiceImpl<V extends BaseVO, D extends BaseDO, DAO extends BaseDAO<V,D>> implements BaseBusService<V> {

	@Autowired
	protected DAO dao;


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public V findById(Serializable id) {
		return dao.findById(id);
		//return BeanMapper.map(entity, getVoClazz());
	}

	/**
	 * 根据param条件查询数据
	 * @param queryParam
	 * @return
	 */
	public List<V> findBy(QueryParam queryParam) {
		if(queryParam != null){
			if(queryParam.getPageBean() != null && queryParam.getPageBean().getPageSize()>0){//设置了条数限制
				PageHelper.startPage(1, queryParam.getPageBean().getPageSize(), true);
				if(queryParam.getOrderBean() != null){
					String orderStr = getOrderString(queryParam.getOrderBean());
					if(StringUtils.isNotBlank(orderStr)){
						PageHelper.orderBy(orderStr);//排序设置
					}
				}
			}else{
				if(queryParam.getOrderBean() != null){
					PageHelper.startPage(1, 2000, true);//最大返回2000条
					String orderStr = getOrderString(queryParam.getOrderBean());
					if(StringUtils.isNotBlank(orderStr)){
						PageHelper.orderBy(orderStr);//排序设置
					}
				}
			}
		}
		return dao.findBy(queryParam);
	}

	/**
	 * 根据BaseQuery条件查询分页数据
	 * @param queryParam 查询条件
	 * @return
	 */
	public PageResult<V> findPage(QueryParam queryParam) {
		PageBean pageBean = null;
		if(queryParam != null && queryParam.getPageBean() != null) {
			pageBean = queryParam.getPageBean();
		} else {
			pageBean = new PageBean();
		}
		if(pageBean.getPageSize() > 1000){//api分页结果最大1000条
			throw new BizRuntimeException("page size too big");
		}

		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize(), true);//分页设置
		if(queryParam != null && queryParam.getOrderBean() != null){
			String orderStr = getOrderString(queryParam.getOrderBean());
			if(StringUtils.isNotBlank(orderStr)){
				PageHelper.orderBy(orderStr);//排序设置
			}
		}

		List<V> dataList = dao.findBy(queryParam);
		PageInfo<V> pageInfo = new PageInfo(dataList);
		//PageInfo<D> pageInfo = PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize(), true)	.doSelectPageInfo(() -> dao.findBy(queryParam));

		PageResult<V> pageResult = BeanMapper.map(pageInfo, PageResult.class);

		if(pageInfo.getList() != null){
			pageResult.setDataList(pageInfo.getList());
		}
		return pageResult;
	}


	public int deleteById(UserToken userTokenInfo, Serializable id) {
		return dao.deleteById(id);
	}

	/**
	 * 对id为主键的VO对象，会自动生成主键
	 * @param userTokenInfo
	 * @param maindata
	 * @return
	 */
	public String insert(UserToken userTokenInfo, V maindata) {
		D entity = BeanMapper.map(maindata, getDoClazz());
		String pkid = setPkIdValue(entity, maindata);
		int result = dao.insert(entity);
		return pkid;
	}

	public int update(UserToken userTokenInfo, V maindata) {
		D entity = BeanMapper.map(maindata, getDoClazz());
		return dao.update(entity);
	}
	@SuppressWarnings("unchecked")
	protected String getOrderString(OrderBean orderBean) {
		try {
			if (orderBean != null && orderBean.getOrderBy() != null) {
				StringBuffer buf = new StringBuffer(20); 
				//获取注解字段
				Class<D> doEntity = getDoClazz();
				String orderBy = ReflectionUtils.findField(doEntity, orderBean.getOrderBy()).getAnnotation(Column.class).name();
				if (StringUtils.isNotBlank(orderBy)) {
					buf.append(orderBy)
							.append(" ")
							.append(orderBean.getOrder() != null ? orderBean.getOrder() : "asc");

					if (orderBean.getOrderBy2() != null) { 
						String orderBy2 = ReflectionUtils.findField(doEntity, orderBean.getOrderBy2()).getAnnotation(Column.class).name();
						if (StringUtils.isNotBlank(orderBy2)) {
							buf.append(", ")
									.append(orderBy2)
									.append(" ")
									.append(orderBean.getOrder2() != null ? orderBean.getOrder2() : "asc");
						}
					}
				}

				return buf.toString();
			}
			return null;
		} catch(Exception e){
			throw new BizRuntimeException("sql order error");
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<D> getDoClazz() {
		return Reflections.getSuperClassGenricType(getClass(), 1);
	}

	@SuppressWarnings("unchecked")
	protected Class<V> getVoClazz() {
		return Reflections.getSuperClassGenricType(getClass(), 0);
	}

	//设置id的值，只对id设置
	protected String setPkIdValue(D entity, V maindata) {
		try {
			String pkName = "id";
			Field field = Reflections.getField(entity, pkName);
			if(field != null && field.get(entity) == null){
				String pkVal = IdGenerator.nextId();
				//Reflections.setFieldValue(maindata, pkName, pkVal);
				Reflections.setFieldValue(entity, pkName, pkVal);
				return pkVal;
			}
		} catch (Exception e) {
			log.error("设置主键值错误", e);
		}
		return null;
	}


}
