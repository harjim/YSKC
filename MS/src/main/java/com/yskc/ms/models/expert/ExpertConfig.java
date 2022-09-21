package com.yskc.ms.models.expert;


public class ExpertConfig {

    private String expertPath;
    private  String  domainName;
    public String getExpertPath() {
        return expertPath;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setExpertPath(String expertPath) {
        this.expertPath = expertPath;
    }
}
