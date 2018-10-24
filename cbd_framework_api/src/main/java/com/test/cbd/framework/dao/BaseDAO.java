package com.test.cbd.framework.dao;

import com.test.cbd.framework.domain.BaseDO;
import com.test.cbd.framework.vo.BaseVO;
import com.test.cbd.framework.vo.QueryParam;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
public interface BaseDAO<V extends BaseVO, D extends BaseDO> {


	/**
	 * 新增
	 * @param entity
	 * @return 保存数据的记录数
	 */
	@InsertProvider(type = BaseSqlProvider.class, method = "insert")
	//需要预先设置主键的值
	int insert(D entity);

	/**
	 * 批量新增
	 * @param list 实体集合
	 */
	@InsertProvider(type = BaseSqlProvider.class, method = "batchInsert")
	//需要预先设置主键的值
	int batchInsert(@Param("list") List<D> list);

	/**
	 * 更新
	 * @param entity 实体
	 * @return 更新数据的记录数
	 */
	@UpdateProvider(type = BaseSqlProvider.class, method = "update")
	int update(D entity);

	/**
	 * 代码中组装好sql，直接执行。（慎用）
	 * @param sql
	 * @return
	 */
	@SelectProvider(type = BaseSqlProvider.class, method = "selectSQL")
	List<Map> selectSQL(String sql);

	/**
	 * 根据id删除实体
	 * @param id
	 * @return
	 */
	int deleteById(Serializable id);

	/**
	 * 根据id删除实体
	 * @param ids
	 * @return
	 */
	int deleteByIds(@Param("ids") Serializable... ids);

	/**
	 * 根据id获取实体VO
	 * @param id 实体id
	 * @return 实体VO
	 */
	V findById(Serializable id);

	/**
	 * 根据id获取实体DO
	 * @param id 实体id
	 * @return 实体DO
	 */
	D findDoById(Serializable id);

	/**
	 * 根据id获取实体（锁）
	 * @param id 实体id
	 * @return 实体
	 */
	D findByIdForUpdate(Serializable id);

	/**
	 * 根据ids获取实体列表
	 *
	 * @param ids
	 * @return
	 */
	List<V> findByIds(@Param("ids") Serializable... ids);

	/**
	 * 根据param条件查询数据
	 * @param param
	 * @return
	 */
	List<V> findBy(QueryParam param);

}
