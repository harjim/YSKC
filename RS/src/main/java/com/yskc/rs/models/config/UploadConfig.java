package com.yskc.rs.models.config;

public class UploadConfig {
    private String basicPath;
    private String importPath;
    // private String templatePath;
    private String msImportPath;
    private String resourcePath;
    private String docPath;
    /**
     * 导入最大行数【默认为50000】
     */
    private Integer maxRow = 50000;

    private Boolean generateByPdf7 = false;

    public String getBasicPath() {
        return basicPath;
    }

    public void setBasicPath(String basicPath) {
        this.basicPath = basicPath;
    }

    public String getImportPath() {
        return importPath;
    }

    public void setImportPath(String importPath) {
        this.importPath = importPath;
    }

    public String getMsImportPath() {
        return msImportPath;
    }

    public void setMsImportPath(String msImportPath) {
        this.msImportPath = msImportPath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /*
        上传的各种文档路径,按公司分文件夹
         */
    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public Integer getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(Integer maxRow) {
        this.maxRow = maxRow;
    }

    public Boolean getGenerateByPdf7() {
        return generateByPdf7;
    }

    public void setGenerateByPdf7(Boolean generateByPdf7) {
        this.generateByPdf7 = generateByPdf7;
    }
}
