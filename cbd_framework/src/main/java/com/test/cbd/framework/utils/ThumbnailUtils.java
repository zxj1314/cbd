/**
 * @Project: rcp-java
 * @Copyright: ©2018  广州弘度信息科技有限公司. 版权所有
 */
package com.test.cbd.framework.utils;
/*
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

*//**
 * @author huangjunjie
 * @ClassName ThumbnailUtils
 * @Description //TODO 用一句话描述功能
 * @Date 2018/6/27
 *//*
@Slf4j
public class ThumbnailUtils {


    public static String getVideoThumbnail(String videoPath) throws IOException {

        AppConfig webConfig = SpringHelper.getBean(AppConfig.class);
            Frame frame = null;
            //标识
            int flag = 0;
            FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(webConfig.getBasePath() + videoPath);
            
            int idx = videoPath.lastIndexOf(".");
            String fileName = videoPath.substring(0,idx) + "-thumb" + ".jpg";
            File outPut = new File( webConfig.getBasePath() + fileName);
            fFmpegFrameGrabber.start();
            //获取视频文件信息,总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();

            if (ftp < 5) {
                return null;
            }
            //跳过前4帧
            while (flag < 5) {
                fFmpegFrameGrabber.grabImage();
                flag++;
            }

            //获取第5帧
            frame = fFmpegFrameGrabber.grabImage();
            if (frame != null) {
                ImageIO.write(frameToBufferedImage(frame), "jpg",outPut);
            }
            fFmpegFrameGrabber.stop();
            return fileName;

    }

    private static BufferedImage frameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        BufferedImage outPut = new BufferedImage(300, 200, BufferedImage.TYPE_INT_BGR);
        outPut.getGraphics().drawImage(bufferedImage, 0, 0, 300, 200, null);
        return outPut;
    }


}*/
