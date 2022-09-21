package com.yskc.docservice.models.rs.excel;

public class TableFieldItem {
    private String title;
    private boolean required;
    private String defaultTitle;
    private boolean importField;

    public boolean getImportField() {
        return importField;
    }

    public void setImportField(boolean importField) {
        this.importField = importField;
    }

    public String getTitle() {
        return  title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }
}
