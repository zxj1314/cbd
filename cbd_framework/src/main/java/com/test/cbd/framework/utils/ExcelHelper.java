package com.test.cbd.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
public class ExcelHelper<T> {
    //构造方法
    public ExcelHelper() {
    }

    Class<T> clazz;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }



    /**
     * 读EXCEL文件，获取信息集合
     * @param filePath：文件所在目录
     * @param name：文件名称
     * @param fieldmap：目标类需要导入的字段
     * @return
     * @Author zxj
     */
    public List<T> getExcelInfo(String filePath,String name,Map fieldmap,MultipartFile Mfile) {
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf = (CommonsMultipartFile) Mfile; //获取本地存储路径

        File file = new File(filePath);
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) file.mkdirs();
        //新建一个文件
        String newFilePath=filePath+name;
        File file1 = new File(newFilePath);
        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            log.error("ReadExcel:" + e);
            e.printStackTrace();
        }
        List<T> dist = new ArrayList<T>();
        try {
            FileInputStream in = new FileInputStream(file1);
            HSSFWorkbook book = new HSSFWorkbook(in);
            // // 得到第一页
            HSSFSheet sheet = book.getSheetAt(0);
            // // 得到第一面的所有行
            Iterator<Row> row = sheet.rowIterator();
            // 获取标题map
            Map titlemap = getTitleMap(row);
            while (row.hasNext()) {
                T tObject = clazz.newInstance();
                // 标题下的第一行
                Row rown = row.next();
                int k = 0;
                // 遍历一行的列
                for (int j = 0; j < rown.getLastCellNum() + 1; j++) {
                    Cell cell = rown.getCell(j);
                    if (cell == null) {
                        k++;
                        continue;
                    }
                    // 得到此列的对应的标题
                    String titleString = (String) titlemap.get(k);
                    // 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
                    if (fieldmap.containsKey(titleString)) {
                        Method setMethod = (Method) fieldmap.get(titleString);
                        setData(tObject, cell, setMethod);
                    }
                    k++;
                }
                dist.add(tObject);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return dist;
    }


    private Map getTitleMap(Iterator<Row> row) {

        Row title = row.next();
        // 得到第一行的所有列
        Iterator<Cell> cellTitle = title.cellIterator();
        // 将标题的文字内容放入到一个map中。
        Map titlemap = new HashMap();
        // 从标题第一列开始
        int i = 0;
        // 循环标题所有的列
        while (cellTitle.hasNext()) {
            Cell cell = cellTitle.next();
            String value = cell.getStringCellValue();
            // 还是把表头trim一下
            value = value.trim();
            titlemap.put(i, value);
            i = i + 1;
        }
        return titlemap;
    }


    private void setData(T tObject, Cell cell, Method setMethod) throws IllegalAccessException, InvocationTargetException {
        // 得到setter方法的参数
        Type[] ts = setMethod.getGenericParameterTypes();
        // 只要一个参数
        String xclass = ts[0].toString();
        // 判断参数类型
        try {
            switch (cell.getCellTypeEnum()) {
                // 数字
                case NUMERIC:
                    if ("class java.lang.String".equals(xclass)) {
                        if ((cell.getNumericCellValue() + "").indexOf(".") > 0) {
                            setMethod.invoke(tObject, (cell.getNumericCellValue() + "").substring(0, (cell.getNumericCellValue() + "").lastIndexOf(".")));
                        }
                    } else if ("class java.lang.Integer".equals(xclass)) {
                        setMethod.invoke(tObject, (int) cell.getNumericCellValue());
                    } else if ("int".equals(xclass)) {
                        setMethod.invoke(tObject, (int) cell.getNumericCellValue());
                    } else if ("class java.lang.Long".equals(xclass)) {
                        Long temp = (long) cell.getNumericCellValue();
                        setMethod.invoke(tObject, temp);
                    }
                    break;
                // 字符串
                case STRING:
                    if ("class java.lang.Integer".equals(xclass)) {
                        setMethod.invoke(tObject, Integer.parseInt(cell.getStringCellValue()));
                    } else if ("class java.lang.String".equals(xclass)) {
                        setMethod.invoke(tObject, cell.getStringCellValue().trim());
                    } else if ("int".equals(xclass)) {
                        int temp = Integer.parseInt(cell.getStringCellValue());
                        setMethod.invoke(tObject, temp);
                    } else if ("class java.lang.Long".equals(xclass)) {
                        Long temp = Long.parseLong(cell.getStringCellValue());
                        setMethod.invoke(tObject, temp);
                    }
                    break;
                // Boolean
                case BOOLEAN:
                    Boolean boolname = true;
                    if ("否".equals(cell.getStringCellValue())) {
                        boolname = false;
                    }
                    setMethod.invoke(tObject, boolname);
                    break;
                // 公式
                case FORMULA:
                    log.info(cell.getCellFormula() + "   ");
                    break;
                // 空值
                case BLANK:
                    log.info(" ");
                    break;
                // 故障
                case ERROR:
                    log.info(" ");
                    break;
                default:
                    log.info("未知类型   ");
                    break;
            }
        } catch (Exception e) {
            // 转换出错
            log.error(e.toString());
            throw e;
        }
    }


    // 得到目标类需要导入的字段
    public Map getFieldMap(String[] fileds) throws NoSuchMethodException {
        Field[] filed = clazz.getDeclaredFields();
        Map fieldmap = new HashMap();
        // 循环读取所有字段
        for (int i = 0; i < filed.length; i++) {
            Field f = filed[i];
            for(int j=0;j<fileds.length;j++){
                String fd=fileds[j];
                if(f.getName().equals(fd)){
                    String setMethodName = "set"
                            + f.getName().substring(0, 1).toUpperCase()
                            + f.getName().substring(1);
                    Method setMethod = clazz.getMethod(setMethodName,
                            new Class[]{f.getType()});
                    fieldmap.put(f.getName(), setMethod);
                }

            } }
        return fieldmap;
    }

    /**
     *
     * @param basePath:绝对路径
     * @param excelPath：相对路径
     * @param content：要导出的内容
     * @param title:标题栏
     * @param sheetName:簿名
     * @param fileName:文件名
     * @return
     * @Author zxj
     */
    public  String export(String basePath,String excelPath,String [][] content,String[] title,String sheetName,String fileName){
        OutputStream os=null;

        try {
            fileName=new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/"+fileName;
            File folderFile=new File(basePath+excelPath+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if(!folderFile.exists()){
                folderFile.mkdir();
            }
            os = new FileOutputStream(new File(basePath+excelPath + fileName));
            HSSFWorkbook wb = getHSSFWorkbook(sheetName, title, content, null);
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            os=null;
        }
        return excelPath+fileName;
    }


    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    private  HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
}