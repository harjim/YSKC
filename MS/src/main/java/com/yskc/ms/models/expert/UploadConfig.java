package com.yskc.ms.models.expert;


public class UploadConfig {

    private String imagePath;
    private String importPath;
    private String importDocList;
    private String resourcePath;
    private String rsBasicPath;
    private String docPath;
    private Boolean generateByPdf7 = false;

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getRsBasicPath() {
        return rsBasicPath;
    }

    public void setRsBasicPath(String rsBasicPath) {
        this.rsBasicPath = rsBasicPath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getImportDocList() {
        return importDocList;
    }

    public void setImportDocList(String importDocList) {
        this.importDocList = importDocList;
    }

    public String getImportPath() {
        return importPath;
    }

    public void setImportPath(String importPath) {
        this.importPath = importPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getGenerateByPdf7() {
        return generateByPdf7;
    }

    public void setGenerateByPdf7(Boolean generateByPdf7) {
        this.generateByPdf7 = generateByPdf7;
    }
}
