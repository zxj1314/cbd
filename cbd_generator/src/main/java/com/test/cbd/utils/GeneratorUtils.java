package com.test.cbd.utils;

import com.test.cbd.entity.ColumnEntity;
import com.test.cbd.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <br>
 * <b>功能：工具类</b>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
public class GeneratorUtils {

    private static final String TEMPLATE_DIR_NAME = "template";

    public static String getRootPath() {
        URL rootUrl = ClassLoader.getSystemResource("");
        return (new File(rootUrl.getPath())).getPath();
    }

    public static List<File> getTemplates(String classRootPath) {
        File templatesDir = new File(classRootPath + File.separator + TEMPLATE_DIR_NAME );
        return getFiles(templatesDir);
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<ColumnEntity>();
        for (Map<String, String> column : columns) {
            String columnComment = column.get("columnComment");
            if(columnComment != null){
                columnComment = columnComment.replace("\r\n","||");
                columnComment = columnComment.replace("||        ","||");
            }else{
                columnComment = "";
            }

            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(columnComment);
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "Object");
            columnEntity.setAttrType(attrType);

            //Java类型二次转换
            String columnType = column.get("columnType");
            if(columnType != null){
                String attrType2 = config.getString("columnType-"+columnType.toLowerCase());
                if(attrType2 != null){
                    columnEntity.setAttrType(attrType2);
                }
            }

            //Boolean类型，则去掉is开头
            if("Boolean".equals(columnEntity.getAttrType())){
                //去掉is开头
                String tempName = columnToJava(columnEntity.getColumnName().toLowerCase().replace("is_",""));//驼峰+首字母大写
                columnEntity.setAttrName(tempName);//首字母大写
                columnEntity.setAttrname(StringUtils.uncapitalize(tempName));//驼峰+首字母小写
            }

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey"))) {
                columnEntity.setPk(true);
                if (tableEntity.getPkList() == null) {
                    tableEntity.setPkList(new ArrayList());
                }
                tableEntity.getPkList().add(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPkList() == null) {
            tableEntity.setPkList(new ArrayList());
            tableEntity.getPkList().add(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        String comments = tableEntity.getComments();
        if(comments != null){
            comments = comments.replace("\r\n","||");
        }else{
            comments = "";
        }

        //封装模板数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", comments);
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("moduleName", config.getString("moduleName"));
        map.put("moduleNameUrl", config.getString("moduleNameUrl"));
        map.put("pkList", tableEntity.getPkList());
        map.put("pkOne", false);
        if(tableEntity.getPkList().size() ==1){//该表是单主键
            map.put("pkOne", true);
            map.put("pkColumn", tableEntity.getPkList().get(0));
        }
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        String classRootPath = getRootPath();
        List<File> templates = getTemplates(classRootPath);
        for (File template : templates) {
            //渲染模板
            String templatePathOfClass =  template.getPath().replace(classRootPath,"").substring(1);//相对classPath下的路径
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(templatePathOfClass, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, templatePathOfClass, tableEntity.getClassName(), config.getString("package"), config.getString("moduleName"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(File template, String templatePathOfClass, String className, String packageName, String moduleName) {
        String fileName =  template.getName();
        //去掉原名
        //替换包名
        //结果： \java\com\...\...\...\
        String tmp = templatePathOfClass
                        .replace(fileName,"")
                        .replace(TEMPLATE_DIR_NAME, "")
                        .replace("${moduleName}",moduleName)
                        .replace("${className}",className)
                        .replace("${package}",packageName)
                        .replace(".", File.separator);

        if(fileName.toLowerCase().endsWith(".vm")){
            fileName = fileName.substring(0, fileName.length()-3);
        }
        fileName = fileName.replace("${className}",className).replace("${moduleName}",moduleName);//替换占位符
        return moduleName + tmp + fileName;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //获取相对路径
    public static ArrayList<File> getFiles(File dirFile){
        //目标集合fileList
        ArrayList<File> fileList = new ArrayList<File>();
        if (dirFile.exists() && dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            for (File fileIndex : files) {
                //如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    fileList.addAll(getFiles(fileIndex));
                } else {
                    if(fileIndex.getName().endsWith(".vm")) {
                        //如果文件是普通文件，则将文件句柄放入集合中
                        fileList.add(fileIndex);
                    }
                }
            }
        }
        return fileList;
    }

    //获取相对路径
    public static ArrayList<String> getFilesPath(File dirFile){
        //目标集合fileList
        ArrayList<String> fileList = new ArrayList<String>();
        if (dirFile.exists() && dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            for (File fileIndex : files) {
                //如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    fileList.addAll(getFilesPath(fileIndex));
                } else {
                    if(fileIndex.getName().endsWith(".vm")) {
                        //如果文件是普通文件，则将文件句柄放入集合中
                        fileList.add(fileIndex.getPath());
                    }
                }
            }
        }
        return fileList;
    }
}
