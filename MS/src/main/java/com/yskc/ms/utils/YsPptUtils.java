package com.yskc.ms.utils;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.config.Constant;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.xslf.usermodel.*;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ppt帮助类
 * @author huronghua
 */
public class YsPptUtils {
    private static Logger logger = LoggerFactory.getLogger(YsPptUtils.class);

    /**
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    public static String toHtml(String filePath) throws OwnerException {
        if (filePath.endsWith(Constant.EXCEL_PPTX)) {
            return pptxToHtml(filePath);
        } else if (filePath.endsWith(Constant.EXCEL_PPT)) {
            return pptToHtml(filePath);
        }else {
            return "";
        }
    }

    /**
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    public static String pptxToHtml(String filePath) throws OwnerException {
        try {
            Path allPath = Paths.get(filePath);
            String path= allPath.getParent().toString();
            String name=allPath.getFileName().toString();
            String fileName=name.substring(0, name.lastIndexOf("."));
            FileInputStream is = new FileInputStream(filePath);
            XMLSlideShow ppt = new XMLSlideShow(is);
            is.close();
            Dimension pgsize = ppt.getPageSize();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                try {
                    for (XSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                        if (shape instanceof XSLFTextShape) {
                            XSLFTextShape tsh = (XSLFTextShape) shape;
                            for (XSLFTextParagraph p : tsh) {
                                for (XSLFTextRun r : p) {
                                    r.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    graphics.setPaint(Color.white);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                    ppt.getSlides().get(i).draw(graphics);
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                        ImageIO.write(img, "png", out);
                        BASE64Encoder encoder = new BASE64Encoder();
                        String imgData = encoder.encode(out.toByteArray());
                        sb.append("<br>");
                        sb.append("<img src=" + "\"" + "data:image/png;base64," + imgData + "\"" + "/>");
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                } catch (Exception e) {
                    logger.error("ppt转换为html,发生异常,源文件={}", filePath, e);
                    return null;
                }
            }
            String content=sb.toString();
            File f=new File(path +"/"+ fileName+".html");
            FileUtil.writeAsString(f, content);
            ppt.close();
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * @param filePath
     * @return
     */
    public static String pptToHtml(String filePath) {
        String htmlStr = "";
        try {
            Path allPath = Paths.get(filePath);
            String path= allPath.getParent().toString();
            String name=allPath.getFileName().toString();
            String fileName=name.substring(0, name.lastIndexOf("."));
            HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(filePath));
            Dimension pgsize = ppt.getPageSize();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
                    if (shape instanceof HSLFTextShape) {
                        HSLFTextShape tsh = (HSLFTextShape) shape;
                        for (HSLFTextParagraph p : tsh) {
                            for (HSLFTextRun r : p) {
                                r.setFontFamily("宋体");
                            }
                        }
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                ppt.getSlides().get(i).draw(graphics);
                try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    ImageIO.write(img, "png", out);
                    BASE64Encoder encoder = new BASE64Encoder();
                    String imgData = encoder.encode(out.toByteArray());
                    sb.append("<br>");
                    sb.append("<img src=" + "\"" + "data:image/png;base64," + imgData + "\"" + "/>");
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
            htmlStr = sb.toString();
            File f=new File(path +"/"+ fileName+".html");
            FileUtil.writeAsString(f, htmlStr);
            ppt.close();
        } catch (Exception e) {
            logger.error("ppt转换为html,发生异常,源文件={}", filePath, e);
            return null;
        }
        return htmlStr;
    }

    /**
     * *
     * * @param srcImgPath
     * * @param distImgPath
     * * @param width
     * * @param height
     * * @throws IOException
     */
    public static void resizeImage(String srcImgPath, String distImgPath, int width, int height) throws IOException {
        File srcFile = new File(srcImgPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(buffImg, "JPEG", new File(distImgPath));
    }
}
