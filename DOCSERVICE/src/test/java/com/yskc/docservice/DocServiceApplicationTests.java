package com.yskc.docservice;

import com.aspose.words.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import wordUtil.LicenseLoad;
import org.springframework.cglib.core.internal.Function;

@SpringBootTest
public class DocServiceApplicationTests {

    @Test
    void contextLoads() {
        String dataDir = "C:/Users/Administrator/Desktop/test";
        String SimpleStyledFilePath = dataDir + "/FirstFile.html";
        String testFilePath = dataDir + "/test.html";
        String DocPath = dataDir + "/FirstFile"+System.currentTimeMillis()+".docx";

        LicenseLoad.getLicense();

        try {
            Document doc = new Document();

            DocumentBuilder builder = new DocumentBuilder(doc);

            builder.getPageSetup().setPaperSize(com.aspose.words.PaperSize.A4);
            builder.getPageSetup().setLeftMargin(72);
            builder.getPageSetup().setRightMargin(72);

            //   1、开始插入页眉
            //   将光标移动到页眉位置
            builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
            String imageFileName = "C:/Users/Administrator/Desktop/test/image/test.png";
            builder.insertImage(imageFileName, RelativeHorizontalPosition.MARGIN, 0, RelativeVerticalPosition.TOP_MARGIN, 44, 27, 27, WrapType.THROUGH);
            builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
            //   添加页眉线
            Border borderHeader = builder.getParagraphFormat().getBorders().getBottom();
            borderHeader.setShadow(true);
            borderHeader.setDistanceFromText(22);
            borderHeader.setLineStyle(LineStyle.SINGLE);

            builder.moveToDocumentStart();
            builder.insertHtml(/*readFileContent(SimpleStyledFilePath)*/
            "<P align='right'>Paragraph right</P>" +
                    "<b>Implicit paragraph left</b>" +
                    "<div align='center'>Div center</div>" +
                    "<h1 align='left'>Heading 1 left.</h1>");
            builder.insertBreak(BreakType.PAGE_BREAK);

            builder.insertHtml(/*readFileContent*/(testFilePath));
            // Stream memStream = File.OpenRead("addreess+file.xls");
            // builder.InsertOleObject(memStream, "AcroExch.Document.7"", false, null);
            //生成doc文件
            insertWatermarkText(doc, "我的水印");
            doc.save(DocPath, SaveOptions.createSaveOptions(DocPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加水印方法
     *
     * @param doc           word文件流
     * @param watermarkText 水印内容
     */
    public static void insertWatermarkText(Document doc, String watermarkText) {
        // 居中
        insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
            @Override
            public Object apply(Shape watermark) {
                // Place the watermark in the page center.
                watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
                watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
                watermark.setWrapType(WrapType.NONE);
                watermark.setVerticalAlignment(VerticalAlignment.CENTER);
                watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
                return null;
            }
        });
        // 顶部
        insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
            @Override
            public Object apply(Shape watermark) {
                watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
                watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
                watermark.setWrapType(WrapType.NONE);
                //  我们需要自定义距离顶部的高度
                watermark.setVerticalAlignment(VerticalAlignment.TOP);
                watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
                //  watermark.setTop(120);
                return null;
            }
        });

        //尾部
        insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
            @Override
            public Object apply(Shape watermark) {
                watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
                watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
                watermark.setWrapType(WrapType.NONE);
                // 我们需要自定义距离顶部的高度
                watermark.setVerticalAlignment(VerticalAlignment.BOTTOM);
                watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
                // 设置距离顶部的高度
                //   watermark.setTop(480);

                return null;
            }
        });
    }

    private static void insertWatermarkText(Document doc, String watermarkText,
                                            Function<Shape, Object> watermaskPositionConfigFunc) {

        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        watermark.getTextPath().setText(watermarkText);
        // 这里设置为宋体可以保证在转换为PDF时中文不是乱码.
        watermark.getTextPath().setFontFamily("WeiRuanYaHei");
        //WeiRuanYaHei 宋体

        try {
            // 水印大小
            watermark.setWidth(150);
            watermark.setHeight(30);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 左下到右上
        watermark.setRotation(-20);
//字体RGB颜色
        final String colorStr = "EE8262";
        watermark.getFill().setColor(new java.awt.Color(Integer.parseInt(colorStr, 16)));
        watermark.setStrokeColor(new java.awt.Color(Integer.parseInt(colorStr, 16)));
        watermaskPositionConfigFunc.apply(watermark);
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);
        for (Section sect : doc.getSections()) {
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);

        }

    }

    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect,
                                                  int headerType) {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);
        if (header == null) {
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }
        try {
            header.appendChild(watermarkPara.deepClone(true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
