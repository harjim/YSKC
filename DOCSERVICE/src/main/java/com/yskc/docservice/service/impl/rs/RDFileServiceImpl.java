package com.yskc.docservice.service.impl.rs;

import com.aspose.words.*;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.models.rs.PDocFile;
import com.yskc.docservice.models.rs.company.CompanyMember;
import com.yskc.docservice.service.rd.DataFactory;
import com.yskc.docservice.service.rd.RDDocument;
import com.yskc.docservice.service.rd.doc.DefaultDocument;
import com.yskc.docservice.service.rs.RDFileService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import wordUtil.LicenseLoad;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RDFileServiceImpl implements RDFileService {
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private DocServiceConfig docServiceConfig;
    @Autowired
    Configuration freemarkerConfiguration;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        LicenseLoad.getLicense();
        com.aspose.pdf.License license = new com.aspose.pdf.License();
        String code = "<License>\n"
                + "  <Data>\n"
                + "    <Products>\n"
                + "      <Product>Aspose.Total for Java</Product>\n"
                + "      <Product>Aspose.Words for Java</Product>\n"
                + "    </Products>\n"
                + "    <EditionType>Enterprise</EditionType>\n"
                + "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n"
                + "    <LicenseExpiry>20991231</LicenseExpiry>\n"
                + "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n"
                + "  </Data>\n"
                + "  <Signature>111</Signature>\n"
                + "</License>";
        try {
            license.setLicense(new ByteArrayInputStream(code.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String preview(DocParam docParam) throws OwnerException {
        if (docParam.getpDocFileId().length == 0) {
            throw new OwnerException("?????????????????????!");
        }
        DataFactory dataFactory = this.applicationContext.getBean(DataFactory.class);
        dataFactory.setDocParam(docParam);
        List<PDocFile> docFileList = dataFactory.getDocFileList();
        StringBuilder sb = new StringBuilder();
        for (PDocFile docFile :
                docFileList) {
            RDDocument rdDocument = this.getRDDocument(docFile, docParam, dataFactory, "/static/");
/*            if (addFileName) {
                rdDocument.appendData("ftlDocFileName", docFile.getDocFileName());
            }*/
            sb.append(rdDocument.getHtml());
        }
        return sb.toString();
    }

    @Override
    public void export(DocParam docParam, OutputStream outputStream) throws Exception {
        if (docParam.getpDocFileId().length == 0) {
            throw new OwnerException("?????????????????????!");
        }
        DataFactory dataFactory = this.applicationContext.getBean(DataFactory.class);
        dataFactory.setDocParam(docParam);
        CompanyMember companyInfo = dataFactory.getCompanyInfo();
        List<PDocFile> docFileList = dataFactory.getDocFileList();
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        PageSetup pageSetup = builder.getPageSetup();
        pageSetup.setPaperSize(PaperSize.A4);
        // pageSetup.setTopMargin(90);
        pageSetup.setLeftMargin(54);
        pageSetup.setRightMargin(54);

        // ???????????????
        if (docParam.getHeader()) {
            writeDocHeader(builder, companyInfo.getCompanyLogoPath(), companyInfo.getCompanyName());
        }

        //????????????????????????????????????
        builder.moveToDocumentStart();

        //????????????????????????????????????
        // ???????????????
        Integer pageSize = 0;
        if (docParam.getCover()) {
            builder.getFont().setName("??????");
            String coverHtml = getCoverHtml(dataFactory, "/static/");
            builder.insertHtml(coverHtml);
            builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
            pageSize = 1;

        }
        // ???????????????
        Boolean addCatalogue = docParam.getCatalogue() && docParam.getpDocFileId().length > 1;
        if (addCatalogue) {
            this.writeDocIndex(doc, builder);
            pageSize = doc.getPageCount() - 1;
            builder.getPageSetup().setRestartPageNumbering(true);
        }
        // ???????????????
        boolean isFirst = true;
        for (PDocFile docFile :
                docFileList) {
            if (!isFirst) {
                builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
                builder.getPageSetup().setRestartPageNumbering(false);
            } else {
                isFirst = false;
            }
            RDDocument rdDocument = this.getRDDocument(docFile, docParam, dataFactory, Paths.get(docServiceConfig.getResourcePath(), "/static/").toAbsolutePath().toString() + System.getProperty("file.separator"));
            if (rdDocument.isAttachment()) {
                // ???????????????HTML????????????????????????????????????,???????????????????????????
                List<String> atts = rdDocument.getAttachments();
                // FIXME?????????
                if (!CollectionUtils.isEmpty(atts)) {
                    for (int i = 0; i < atts.size(); i++) {
                        if (i > 0) {
                            builder.insertBreak(BreakType.PAGE_BREAK);
                        }
                        Shape imgShape = builder.insertImage(atts.get(i));
                        imgShape.setName("attachment"); // ??????????????????????????????attachment;
                    }
                }
            } else {
                /*Integer docFileId = docFile.getDocFileId();
                Integer docTemplateId = docFile.getDocTemplateId();
                if (!docFileId.equals(27) && !docFileId.equals(50) && !docFileId.equals(43) && !docTemplateId.equals(103)) {
                    rdDocument.appendData("ftlDocFileName", docFile.getDocFileName());
                }*/
                if (rdDocument.hasDocName()) {
                    rdDocument.appendData("ftlDocFileName", docFile.getDocFileName());
                }
                builder.insertHtml(rdDocument.getHtml());
            }
        }
        // ????????????
        this.handleDocImage(doc, pageSetup);

        // ????????????
        NodeCollection<Table> tables = doc.getChildNodes(NodeType.TABLE, true);
        for (Table table :
                tables) {
            Section section = (Section)table.getAncestor(NodeType.SECTION);
            double pageWidth = section.getPageSetup().getPageWidth() - (section.getPageSetup().getLeftMargin() + section.getPageSetup().getRightMargin());
            // ?????????????????????
            for (Row row :
                    table.getRows()) {
                for (Cell cell :
                        row.getCells()) {
                    if (cell.getCellFormat().getWidth() > 0) {
                        PreferredWidth preferredWidth = cell.getCellFormat().getPreferredWidth();
                        if (preferredWidth.getType() == PreferredWidthType.PERCENT) {
                            double cellWidth = pageWidth * preferredWidth.getValue() / 100;
                            cell.getCellFormat().setWidth(cellWidth);
                            // cell.getCellFormat()
                        } else if (preferredWidth.getType() == PreferredWidthType.POINTS) {
                            cell.getCellFormat().setWidth(preferredWidth.getValue());
                        }
                    }
                }
            }
        }
        /* ????????????????????????
        NodeCollection<Paragraph> paragraphs = doc.getChildNodes(NodeType.PARAGRAPH,true);
        for (Paragraph paragraph:
                paragraphs) {
            paragraph.setParaAttr(1080,false);
            paragraph.setParaAttr(1070,true);
        }
        */

        // ??????
        if (docParam.getPageNum()) {
            this.writeDocFooter(doc, pageSize);
        }
        // ???????????????
        if (addCatalogue) {
            doc.updateFields();
        }
        // 0 ??????PDF, 1 ??????word
        if (docParam.getExportType() != null && docParam.getExportType() == 0) {
            doc.updatePageLayout();
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            doc.save(outputStream, pdfSaveOptions);
        } else {
            doc.save(outputStream, SaveOptions.createSaveOptions(SaveFormat.DOCX));
        }
    }

    /*
    ??????????????????????????????????????????,???????????????????????????????????????????????????????????????.
     */
    private void handleDocImage(Document doc, PageSetup pageSetup) throws Exception {
        NodeCollection shapes = doc.getChildNodes(NodeType.SHAPE, true);
        double contentWidth = pageSetup.getPageWidth() - pageSetup.getLeftMargin() - pageSetup.getRightMargin();
        double contentHeight = pageSetup.getPageHeight() - pageSetup.getTopMargin() - pageSetup.getBottomMargin();
        for (Object shape :
                shapes) {
            Shape s = (Shape)shape;
            if (s.isImage()) {
                double sh = s.getHeight();
                double sw = s.getWidth();
                // ?????????????????????????????????
                if (s.getName().equals("attachment") && sw > sh) {
                    s.setRotation(90);
                    s.setWrapType(WrapType.TOP_BOTTOM);
                    s.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
                    s.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
                    if ((sh / sw) > (contentWidth / contentHeight)) {
                        double realWidth = contentWidth - 10;
                        s.setWidth(sw * realWidth / sh);
                        s.setHeight(realWidth);
                    } else {
                        double realHeight = contentHeight - 10;
                        s.setWidth(realHeight);
                        s.setHeight(sh * realHeight / sw);
                    }
                    s.setTop((s.getWidth() - s.getHeight()) / 2);
                    s.setLeft((s.getHeight() - s.getWidth()) / 2 + contentWidth - s.getHeight());
                } else if (sw > contentWidth) {
                    double realWidth = contentWidth - 10;
                    s.setWidth(realWidth);
                    s.setHeight(sh * realWidth / sw);
                }
            }
        }
    }

    /*
    ??????????????????
     */
    private void writeDocIndex(Document doc, DocumentBuilder builder) throws Exception {
        //?????????????????????
        builder.getCurrentParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        builder.setBold(true);
        builder.getFont().setName("simsun");
        builder.getFont().setSize(16); // ????????????
        builder.writeln("??? ???");
        //????????????????????????
        builder.getParagraphFormat().clearFormatting();
        //????????????
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
        builder.getFont().setName("simsun");
        builder.getFont().setSize(16); // ????????????
        builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
        doc.getStyles().getByStyleIdentifier(StyleIdentifier.TOC_1).getParagraphFormat().setLineSpacing(18); // ??????1.5?????????
    }

    private void writeDocHeader(DocumentBuilder builder, String logoUrl, String companyName) throws Exception {
        // logo????????????36pt
        int maxHeight = 36;
        // ??????????????????
        int brokenHeight = 22;
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        builder.write(companyName);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        if (StringUtils.hasLength(logoUrl)) {
            String logPath = Paths.get(docServiceConfig.getResourcePath(), "/static/images", logoUrl).toString();
            File file = new File(logPath);
            if (file.exists()) {
                Shape hShape = builder.insertImage(logPath);
                hShape.setWrapType(WrapType.THROUGH);
                hShape.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
                hShape.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
                if (hShape.getHeight() > maxHeight) {
                    hShape.setWidth(hShape.getWidth() * maxHeight / hShape.getHeight());
                    hShape.setHeight(maxHeight);
                }
                hShape.setLeft(0);
                hShape.setTop(-brokenHeight - hShape.getHeight() - 1);
            }
        }
        //   ???????????????
        Border borderHeader = builder.getParagraphFormat().getBorders().getBottom();
        borderHeader.setShadow(true);
        borderHeader.setDistanceFromText(4); // ????????????
        borderHeader.setLineStyle(LineStyle.SINGLE);
    }

    private void writeDocFooter(Document doc, Integer pageSize) throws Exception {
        HeaderFooter footer = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
//        doc.getFirstSection().getHeadersFooters().add(footer);
        doc.getSections().get(pageSize).getHeadersFooters().add(footer);
        //????????????
        Paragraph footerpara = new Paragraph(doc);
        footerpara.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
        Run footerparaRun = new Run(doc);
        footerparaRun.getFont().setName("??????");
        footerparaRun.getFont().setSize(9.0);//???5?????????
        footerpara.appendChild(footerparaRun);
        footerpara.appendField(FieldType.FIELD_PAGE, true);//????????????
        footerpara.appendChild(footerparaRun);
        footer.appendChild(footerpara);
    }

    /*
    ?????????????????????context???????????????????????????????????????
     */
    private RDDocument getRDDocument(PDocFile docFile, DocParam docParam, DataFactory dataFactory, String ftlPath) {
        String tplName = docFile.getDocTemplateName();
        //?????????????????????????????????
        if (tplName.contains("_")) {
            tplName = tplName.substring(0, tplName.indexOf("_"));
        }
        String beanName = tplName + "_Doc";

        RDDocument rdDocument;
        if (!this.applicationContext.containsBean(beanName)) {
            rdDocument = new DefaultDocument();
        } else {
            rdDocument = this.applicationContext.getBean(beanName, RDDocument.class);
        }
        rdDocument.init(docParam, docFile, dataFactory, ftlPath, freemarkerConfiguration);

        return rdDocument;
    }

    @Autowired
    EmployeeDao employeeDao;
    private String getCoverHtml(DataFactory dataFactory, String ftlPath) throws OwnerException {
        Template template;
        try (Writer writer = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            template = freemarkerConfiguration.getTemplate("ReportCourse.html");
            Map dataModel = new HashMap<>();
            CompanyMember companyInfo = dataFactory.getCompanyInfo();
            dataModel.put("companyName", companyInfo.getCompanyName());

            Map projectMap = dataFactory.getProject();
            dataModel.put("pname", projectMap.get("pname"));

            ProjectEntity projectInfo = dataFactory.getProjectInfo();
            String durationTime = MessageFormat.format("{0, date,yyyy.MM}~{1, date,yyyy.MM}", projectInfo.getBeginDate(), projectInfo.getEndDate());
            dataModel.put("durationTime" ,durationTime);
            String ename = projectInfo.getMasterENumber();
            if (StringUtils.hasText(ename)) {
                ename = employeeDao.getByNumber(companyInfo.getCompanyId(), ename).getEname();
            }
            ename = ename == null ? "???" : ename;
            dataModel.put("ename", ename);

            dataModel.put("projectRdDept", projectMap.get("projectRdDept"));
            dataModel.put("ftlPath", ftlPath);
            template.process(dataModel, bufferedWriter);
            return writer.toString();
        } catch (TemplateNotFoundException e) {
            logger.error("?????? ReportCourse ?????????!");
            throw new OwnerException("?????????????????????!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
