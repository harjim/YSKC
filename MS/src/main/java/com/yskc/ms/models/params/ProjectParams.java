package com.yskc.ms.models.params;

/**
 * @author zdf
 */
public class ProjectParams extends PageParams {
    private String companyName;
    private String productName;
    private String year;
    private String fullPath;
    private Integer[] tIds;
    private Integer[] fIds;
    private Integer ownId;
    private Integer mId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getOwnId() {
        return ownId;
    }

    public void setOwnId(Integer ownId) {
        this.ownId = ownId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }

    public Integer[] getfIds() {
        return fIds;
    }

    public void setfIds(Integer[] fIds) {
        this.fIds = fIds;
    }
}
