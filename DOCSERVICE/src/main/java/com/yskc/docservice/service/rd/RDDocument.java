package com.yskc.docservice.service.rd;

import com.aspose.pdf.devices.JpegDevice;
import com.aspose.pdf.devices.Resolution;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.models.rs.PDocFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RDDocument {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    protected Configuration freemarkerConfiguration;
    protected DataFactory dataFactory;
    protected PDocFile docFile;
    protected DocParam docParam;
    protected String ftlPath;
    protected String uriFilePath;

    protected Map dataMap = new HashMap();

    String tplFile = "_Template.html";
    public void init(DocParam docParam, PDocFile docFile, DataFactory dataFactory, String ftlPath, Configuration freemarkerConfiguration) {
        this.docFile = docFile;
        this.docParam = docParam;
        this.dataFactory = dataFactory;
        this.ftlPath = ftlPath;
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.uriFilePath = Paths.get(dataFactory.docServiceConfig.getResourcePath(), "/static/").toUri().toString();
        // this.tplFile = docFile.getDocTemplateName()+".html";
    }

    public String getHtml() throws OwnerException {
        Template template;
        try (Writer writer = new StringWriter();
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            template = freemarkerConfiguration.getTemplate(tplFile);
            Map dataModel = this.getJsonMap();
            Map projectMap = this.dataFactory.getProject();
            Map projectYearMap = this.dataFactory.getProjectYearInfo(this.getDocYear());
            dataModel.putAll(projectMap);
            if (projectYearMap != null)
                dataModel.putAll(projectYearMap);
            if (this.getStage() != null) {
                dataModel.put("projectPhase", this.getStage().getStageName());
            }
            Map docMap = this.getDocMap();
            if (docMap != null) {
                dataModel.putAll(docMap);
            }
            if (this.dataMap.size() > 0) {
                dataModel.putAll(this.dataMap);
            }

            Map footerMap = this.getFooterMap();
            if (footerMap != null && footerMap.size() > 0) {
                dataModel.putAll(footerMap);
            }
            // 项目阶段
            StageEntity stageEntity = getStage();
            dataModel.put("currentStage", stageEntity.getStageName());

            dataModel.put("tpl", docFile.getDocTemplateName());
            dataModel.put("ftlPath", ftlPath);
            template.process(dataModel, bufferedWriter);
            return writer.toString();
        } catch (TemplateNotFoundException e) {
            logger.error("模板" + tplFile + "不存在!");
            throw new OwnerException("模板文件不存在!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /*
     * 获取当前研发文档附件列表
     */
    public List<String> getAttachments() throws IOException {
        return null;
    }



    private Map<String, Object> jsonMap;

    protected Map getJsonMap() {
        if (this.jsonMap == null) {
            if (this.docFile.getData() != null) {
                this.jsonMap = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
                if (this.jsonMap != null) {
                    String replacePath = "src=\"" + ftlPath;
                    for (String k :
                            this.jsonMap.keySet()) {
                        if (this.jsonMap.get(k) instanceof String) {
                            String v = (String) this.jsonMap.get(k);
                            if (v!=null) {
                                if (v.startsWith("<p>") && v.contains("<img")) {
                                    this.jsonMap.put(k, v.replace("src=\"/static/", replacePath));
                                } else {
                                    this.jsonMap.put(k, v.replace("\n", "<br />"));
                                }
                            }
                        }
                    }
                }
            } else {
                this.jsonMap = new HashMap();
            }
        }
        return this.jsonMap;
    }

    protected Date getDocDate() {
        if (this.docFile.getMonth() != null) {
            return this.docFile.getMonth();
        } else {
            StageEntity stage = this.getStage();
            if (stage != null && stage.getBeginDate() != null) {
                return stage.getBeginDate();
            } else {
                return this.dataFactory.getProjectInfo().getBeginDate();
            }
        }
    }

    protected Integer getDocYear() {
        return this.getDocDate().getYear() + 1900;
    }

    protected StageEntity getStage() {
        return this.dataFactory.getProjectStage().get(this.docFile.getStage());
    }

    protected Map getFooterMap() {
        return this.dataFactory.getFooterMap(this.getDocYear(), this.ftlPath);
    }

    public void appendData(Map map) {
        this.dataMap.putAll(map);
    }

    public void appendData(String key, Object object) {
        this.dataMap.put(key, object);
    }

    protected Map getDocMap(ProjectEntity projectEntity, Integer currentYear) {
        return null;
    }

    protected Map getDocMap() throws IOException {
        return null;
    }

    /**
     * 是否是附件类型
     * @return
     */
    public boolean isAttachment(){
        return false;
    }
    /**
     * 是否显示文档名
     */
    public boolean hasDocName() {
        return true;
    }

    /**
     * pdf 转 img
     * @param fPath img 或 pdf 存储路径
     * @param realPath 真实路径
     * @param uriPath
     * @return imgPaht
     * @throws IOException
     */
    protected String pdfToImg(String fPath, String realPath, String uriPath) throws IOException {
        String imgPath = null;
        if (fPath.endsWith(".pdf")) {
            File pdfFile = new File(realPath);
            if (pdfFile.exists()) {
                File file = new File(pdfFile.getAbsolutePath().replace(".pdf", ""), "1.jpg");
                if (!file.exists()) {
                    file.getParentFile().mkdir();
                    com.aspose.pdf.Document doc1 = new com.aspose.pdf.Document(realPath);
                    Resolution resolution = new Resolution(96);
                    JpegDevice jpegDevice = new JpegDevice(resolution, 100);
                    for (int index = 1; index <= doc1.getPages().size(); index++) {
                        // 输出路径
                        file = new File(file.getParentFile().getAbsolutePath(), index + ".jpg");
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdir();
                        }
                        FileOutputStream fileOs = new FileOutputStream(file);
                        jpegDevice.process(doc1.getPages().get_Item(index), fileOs);
                        fileOs.close();
                        imgPath = file.getAbsolutePath();
                    }
                } else {
                    File[] files = file.getParentFile().listFiles();
                    for (int i = 1; i <= files.length; i++) {
                        imgPath = file.getParentFile().getAbsolutePath() + "/" + i + ".jpg";
                    }
                }

            }
        } else {
            try {
                if (new UrlResource(uriPath).exists()) {
                    imgPath = realPath;
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return imgPath;
    }

}