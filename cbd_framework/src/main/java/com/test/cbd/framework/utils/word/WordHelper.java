package com.test.cbd.framework.utils.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WordHelper {


    /**
     * 写入书签内容、图片
     * 注意：适用于word 2007,poi 版本 3.7
     * word文档中用{变量名}这个形式标识要替换的文本
     * 用{{@localhostPicture"+i+"}}这种形式标识要替换的图片
     * @param basePath 绝对路径
     * @param oldFileName word的相对路径
     * @param oldFileName 旧的文件名
     * @param filePath 模板文件路径
     * @param map 指定写入书签参数
     * @return
     * @Author zxj
     */
    public String export(String basePath,String wordPath,String oldFileName,String filePath, Map<String, Object> oldmap){
        String folder=basePath+wordPath;
        try {
            Map<String, Object> map=new HashMap<>();
            for (String key:oldmap.keySet()){
                map.put("${"+key+"}",oldmap.get(key));
            }
            if(StringUtils.isBlank(folder))return "";
            String newFileFolder=folder+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File newFile=new File(newFileFolder);
            if(!newFile.exists()){
                newFile.mkdir();
            }
            String fileName=new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"\\"+oldFileName;
            File outFile=new File(folder+fileName);
            //复制一份模板
            String copyPath=folder+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"\\"+"template.docx";
            File copyFile=new File(copyPath);
            FileCopyUtils.copy(new File(filePath),copyFile);
            //写入文字到doc
            OutputStream outputStream = null;
            outputStream = new FileOutputStream(outFile);
            XWPFDocument doc= WordUtil.generateWord(map,copyPath);
            doc.write(outputStream);
            outputStream.flush();
            outputStream.close();
            doc.close();
            //写入图片到doc
            String picUrl = (String)map.get("${picUrl}");
            String[] urls = picUrl.split(";");
            int size=urls.length;
            FileInputStream inputStream=new FileInputStream(outFile);
            XWPFDocument doc1=new XWPFDocument(inputStream);
            outputStream = new FileOutputStream(outFile);
            XWPFParagraph lastParagraph = doc1.getLastParagraph();
            for(int i=1;i<=size;i++){
                XWPFRun run = lastParagraph.createRun();
                run.setText("{{@localhostPicture"+i+"}}");
                lastParagraph.addRun(run);

            }
            doc1.write(outputStream);
            inputStream.close();
            outputStream.close();
            doc1.close();


           Map<String,Object> datas=new HashMap<String,Object>();
           File imgFile=null;
           FileInputStream imgInputStream=null;
           BufferedImage image=null;
            for(int i=0;i< urls.length;i++){
                String url=urls[i];
                url=basePath+url;
                imgFile=new File(url);
                if(!imgFile.exists()) continue;
                imgInputStream=new FileInputStream(imgFile);
                image= ImageIO.read(imgInputStream);
                if(StringUtils.isNotBlank(url)) {
                    datas.put("localhostPicture" + (i + 1), new PictureRenderData(image.getWidth(), image.getHeight(), url));
                }
            }

            XWPFTemplate template=XWPFTemplate.compile(outFile).render(datas);
            FileOutputStream out1=new FileOutputStream(outFile);
            template.write(out1);
            out1.flush();
            out1.close();
            template.close();
            imgInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return wordPath+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"/"+oldFileName;
    }
}
