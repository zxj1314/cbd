package com.test.cbd.framework.dao;

import com.test.cbd.common.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：注解方式自动生成基本的sql，不用在mybatis mapper文件配置
 * 作者：chenjiefeng
 * 日期：2018/3/22
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2017
 */
@Slf4j 
public class BaseSqlProvider {

    public String insert(Object bean) {
        try{
            long time1 = System.currentTimeMillis();
            Class<?> beanClass = bean.getClass();
            StringBuilder insertSql = new StringBuilder("INSERT INTO ");
            insertSql.append(getTableName(beanClass)).append("(");

            StringBuilder valueSql = new StringBuilder(" value (");
            Object fieldVal = null;
            Field[] beanFields = beanClass.getDeclaredFields();
            for(Field field: beanFields){
                // 判断该成员变量上是不是存在Column类型的注解
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                field.setAccessible(true);//获取私有属性
                fieldVal = field.get(bean);
                if(fieldVal == null){ 
                    if (field.isAnnotationPresent(Id.class)) {//判断是否是主键
                        throw new BizRuntimeException("insert sql pk value can not null");
                    }
                    continue;
                }

                Column column = field.getAnnotation(Column.class);// 获取实例

                insertSql.append(column.name() + ",");
                valueSql.append("#{" + field.getName() + "},");
            }

            valueSql.deleteCharAt(valueSql.length() - 1).append(") ");
            insertSql.deleteCharAt(insertSql.length() - 1).append(") ").append(valueSql.toString());
            log.debug("time:{} ms.........",(System.currentTimeMillis()-time1));
            log.debug(insertSql.toString());
            return insertSql.toString();
        }catch (Exception e){
            throw new BizRuntimeException("sql provider error", e);
        }
    }

    public String batchInsert(Map paramMap) {
        try{
            long time1 = System.currentTimeMillis();
            List<?> list = (List)paramMap.get("list");
            if(list.size() > 10000){
                throw new BizRuntimeException("batch Insert error, too more entities");
            }
            Class<?> beanClass = list.get(0).getClass();
            StringBuilder insertSql = new StringBuilder("INSERT INTO ");
            insertSql.append(getTableName(beanClass)).append("(");
            StringBuilder oneValueSql = new StringBuilder("(");

            Field[] beanFields = beanClass.getDeclaredFields();
            for(Field field: beanFields){
                // 判断该成员变量上是不是存在Column类型的注解
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }

                Column column = field.getAnnotation(Column.class);// 获取实例
                insertSql.append(column.name()).append(",");
                oneValueSql.append("#'{'list[{0}]." + field.getName() + "},");//单引号包括'{'，标记为非正则表达式
            }
            insertSql.deleteCharAt(insertSql.length() - 1).append(") values ");
            oneValueSql.deleteCharAt(oneValueSql.length() - 1).append(")");

            MessageFormat mf = new MessageFormat(oneValueSql.toString());
            for (int i = 0; i < list.size(); i++) {
                insertSql.append(mf.format(new Object[]{i}));
                if (i < list.size() - 1) {
                    insertSql.append(",");
                }
            }

            log.debug("time:{} ms.........",(System.currentTimeMillis()-time1));
            log.debug(insertSql.toString());
            return insertSql.toString();
        }catch (Exception e){
            throw new BizRuntimeException("sql provider error", e);
        }
    }

    public String update(Object bean) {
        try{
            Class<?> beanClass = bean.getClass();
            String tableName = getTableName(beanClass);
            List<Field> idFieldList = new ArrayList();
            StringBuilder updateSql = new StringBuilder();
            updateSql.append(" update ").append(tableName).append(" set ");
            Field[] beanFields = beanClass.getDeclaredFields();
            Object fieldVal = null;
            for(Field field: beanFields){
                // 判断该成员变量上是不是存在Column类型的注解
                if (field.isAnnotationPresent(Id.class)) {
                    idFieldList.add(field);
                    continue;
                }

                // 判断该成员变量上是不是存在Column类型的注解
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                Column column = field.getAnnotation(Column.class);// 获取实例
                if(!column.updatable() ||"create_time".equals(column.name()) ||  "update_time".equals(column.name()) ){//不给更新
                    continue;
                }
                /*
                field.setAccessible(true);//获取私有属性
                fieldVal = field.get(bean);
                if(fieldVal == null){
                    if(!column.updatable() ||"create_time".equals(column.name()) ||  "update_time".equals(column.name()) ){
                        continue;
                    }
                }*/
                updateSql.append(column.name()).append("=#{").append(field.getName()).append("},");
            }
            updateSql.deleteCharAt(updateSql.length() - 1);

            if(idFieldList.size() > 0){
                boolean where = true;
                for(Field field: idFieldList){
                    Column column = field.getAnnotation(Column.class);// 获取实例
                    if(where){
                        where = false;
                        updateSql.append(" where ");
                    }else {
                        updateSql.append(" and "); 
                    }
                    updateSql.append(column.name()).append(" =#{").append(field.getName()).append("}");
                }
                log.debug("updateSql:{} ", updateSql.toString());
                return updateSql.toString();
            }
        }catch (Exception e){
            throw new BizRuntimeException("sql provider error", e);
        }
        throw new BizRuntimeException("sql provider error: update");
    }

    private String getTableName(Class<?> beanClass) {
        Table table = beanClass.getAnnotation(Table.class);
        return table.name();
    }

    //返回bean和其父类的所有属性
    private Field[] getFields(Class<?> beanClass) {
        Field[] beanFields = beanClass.getDeclaredFields();
        Class<?> beanSuperClass = beanClass.getSuperclass();
        Field[] beanSuperFields = beanSuperClass.getDeclaredFields();
        return ArrayUtils.addAll(beanFields, beanSuperFields);
    }

    //代码里面写死sql，一般只是开发时使用
    public String selectSQL(Object bean) {
        try {
            String sql = (String)bean;
            if(!(sql.substring(0,7).toLowerCase().startsWith("select"))){
                throw new BizRuntimeException("sql provider [selectSql] error: no start with [select]");
            }
            return sql;
        }catch (Exception e){
            throw new BizRuntimeException("sql provider [selectSql] error", e);
        }
    }
}
