package com.yskc.rs.utils;

import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlOptions;
import org.aspectj.util.FileUtil;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.utils
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-27 17:03
 * @Description: word工具类
 */

public class YsWordUtils {
    private static Logger logger = LoggerFactory.getLogger(YsWordUtils.class);
    private static final String PIC_NAME = "pic.png";
    private static final int DEFAULT_WIDTH = 180;
    private static final int DEFAULT_HIGHT = 40;

    /**
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    public static String wordToHtml(String filePath) throws OwnerException {
        if (filePath.endsWith(Constant.EXCEL_DOC)) {
            return docToHtml(filePath);
        } else if (filePath.endsWith(Constant.EXCEL_DOCX)) {
            return docxToHtml(filePath);
        }else {
            return "";
        }
    }

    /**
     *
     * @param sourceFileName
     * @return
     * @throws OwnerException
     */
    public static String docxToHtml(String sourceFileName) throws OwnerException {
        Path allPath = Paths.get(sourceFileName);
        String path= allPath.getParent().toString();
        String name=allPath.getFileName().toString();
        String fileName=name.substring(0, name.lastIndexOf("."));
        String targetFileName = path+"/"+fileName+".html";
        String imagePathStr = path+"/image/";
        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(sourceFileName));
            XHTMLOptions options = XHTMLOptions.create();
            // html中图片的路径
            options.setImageManager(new ImageManager(allPath.toFile(),imagePathStr){
                private Map<String,byte[]> imgMap=new HashMap<>();
                @Override
                public void extract(String imagePath, byte[] imageData) throws IOException {
                   if(!imgMap.containsKey(imagePath)){
                       imgMap.put(imagePath,imageData);
                   }
                }

                @Override
                public String resolve(String uri) {
                    if(imgMap.containsKey(uri)){
                        BASE64Encoder encoder = new BASE64Encoder();
                        String imgData= encoder.encode(imgMap.get(uri));
                        return "data:image/png;base64,"+imgData;
                    }else {
                        return "";
                    }
                }
            });
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
            outputStreamWriter.close();
            return cn.hutool.core.io.FileUtil.readString(targetFileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("",e);
            throw new OwnerException(ErrorEnum.FAIL);
        } catch (FileNotFoundException e) {
            logger.error("",e);
            throw new OwnerException(ErrorEnum.FAIL);
        } catch (IOException e) {
            logger.error("",e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }


    /**
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    public static String docToHtml(String filePath) throws OwnerException {
        try {
            InputStream input = new FileInputStream(filePath);
            Path allPath = Paths.get(filePath);
            String path= allPath.getParent().toString();
            String name=allPath.getFileName().toString();
            String fileName=name.substring(0, name.lastIndexOf("."));
            HWPFDocument wordDocument = new HWPFDocument(input);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder()
                            .newDocument());
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                @Override
                public String savePicture(byte[] content, PictureType pictureType,
                                          String suggestedName, float widthInches, float heightInches) {
                    BASE64Encoder encoder = new BASE64Encoder();
                    String s= encoder.encode(content);
                    return "data:image/png;base64,"+s;
                }
            });
            wordToHtmlConverter.processDocument(wordDocument);
            org.w3c.dom.Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            outStream.close();
            String content = new String(outStream.toByteArray());
            File f=new File(path +"/"+ fileName+".html");
            FileUtil.writeAsString(f, content);
            input.close();
            wordDocument.close();
            return content;
        } catch (Exception e) {
            logger.error("",e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }
    /**
     * 生成word报告
     *
     * @param data        数据map
     * @param templateUrl 模板路径
     * @param action      携带xwpfDocument供其他处理
     * @throws Exception
     */
    public static void generalWordReport(Map<String, Object> data, String templateUrl, Consumer<XWPFDocument> action) throws Exception {
        XWPFDocument xwpfDocument = (new YsParseWord()).parseWord(templateUrl, data); //WordExportUtil.exportWord07(templateUrl, data);
        action.accept(xwpfDocument);
    }

    /**
     * 创建页眉header（带图片流形式）
     *
     * @param doc
     * @param headerStr
     * @param is
     * @throws Exception
     */
    public static void createHeader(XWPFDocument doc, String headerStr, InputStream is) throws Exception {
        //获取header
        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT) ;
        //清除存在的header
        header.clearHeaderFooter();
        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setBorderBottom(Borders.THICK);

        CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
        tabStop.setVal(STTabJc.RIGHT);
        int twipsPerInch = 1440;
        tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

        XWPFRun run = paragraph.createRun();

        /*
         * 根据流生成图片，固定尺寸
         * 添加图片到页眉左边
         * */
        XWPFPicture picture = run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, PIC_NAME, Units.toEMU(DEFAULT_WIDTH), Units.toEMU(DEFAULT_HIGHT));
        String blipID = "";
        //*************
        for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
            blipID = header.getRelationId(picturedata);
        }
        picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
        run.addTab();
        is.close();
        /*
         * 添加字体页眉，公司全称
         * 公司全称在右边
         * */
        run = paragraph.createRun();
        run.setText(headerStr);
        setXWPFRunStyle(run, "新宋体", 10);
    }

    /**
     * 创建页眉header（不带图片）
     *
     * @param doc       文档实例
     * @param headerStr 页眉字体
     * @throws Exception
     */
    public static void createHeader(XWPFDocument doc, String headerStr){
        //获取header
        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT);
        //清除存在的header
        header.clearHeaderFooter();
        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        paragraph.setBorderBottom(Borders.THICK);
        /*
         * 添加字体页眉，公司全称
         * 公司全称在右边
         * */
        XWPFRun run = paragraph.createRun();
        run.setText(headerStr);
        setXWPFRunStyle(run, "新宋体", 10);
    }

    /**
     * 设置页脚的字体样式
     *
     * @param r1 段落元素
     */
    private static void setXWPFRunStyle(XWPFRun r1, String font, int fontSize) {
        r1.setFontSize(fontSize);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }


    /**
     * word文件合并
     *
     * @param wordList
     * @return
     * @throws Exception
     */
    public static XWPFDocument mergeWord(List<XWPFDocument> wordList) throws Exception {
        if (CollectionUtils.isEmpty(wordList)) {
            throw new RuntimeException("待合并的word文档为空");
        }
        List<String> titles=new ArrayList<>();
        for (XWPFDocument document : wordList) {
            int page= document.getHeaderList().size();
            List<XWPFParagraph> paraList = document.getParagraphs();
            XWPFParagraph p = paraList.get(0);//获取到第一个段落,根据需要读取相应段落
            String title=p.getText();
            titles.add(title);
//           //创建段落
//            XWPFParagraph para = document.createParagraph(); //一个XWPFRun代表具有相同属性的一个区域：一段文本
//            XWPFRun run = para.createRun();
//            run.setBold(true); //加粗
//            run.setText("加粗的内容");
        }
        XWPFDocument doc = wordList.get(0);
        // int pageNum=doc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
        int size = wordList.size();
        if (size > 1) {
            doc.createParagraph().setPageBreak(true);
            for (int i = 1; i < size; i++) {
                // 从第二个word开始合并
                XWPFDocument nextPageDoc = wordList.get(i);
                // 最后一页不需要设置分页符
                if (i != (size - 1)) {
                    nextPageDoc.createParagraph().setPageBreak(true);
                }
                appendBody(doc, nextPageDoc);
            }
        }
        return doc;
    }

    private static void appendBody(XWPFDocument src, XWPFDocument append) throws Exception {
        CTBody src1Body = src.getDocument().getBody();
        CTBody src2Body = append.getDocument().getBody();
        List<XWPFPictureData> allPictures = append.getAllPictures();
        // 记录图片合并前及合并后的ID
        Map<String, String> map = new HashMap<>();
        for (XWPFPictureData picture : allPictures) {
            String before = append.getRelationId(picture);
            //将原文档中的图片加入到目标文档中
            String after = src.addPictureData(picture.getData(), Document.PICTURE_TYPE_PNG);
            map.put(before, after);
        }
        appendBody(src1Body, src2Body, map);
    }


    private static void appendBody(CTBody src, CTBody append, Map<String, String> map) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        if (map != null && !map.isEmpty()) {
            //对xml字符串中图片ID进行替换
            for (Map.Entry<String, String> set : map.entrySet()) {
                addPart = addPart.replace(set.getKey(), set.getValue());
            }
        }
        //将两个文档的xml内容进行拼接
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
        src.set(makeBody);
    }

}
