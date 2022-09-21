package com.yskc.ms.models.newexpert.expert;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/10/9 8:23
 * @Description:
 * @author: hsx
 */
public class UserEnterModel {

    private Integer id;

    private String realname;                //真实姓名

    private Integer gender;                 //性别

    private String nativePlace;             //籍贯

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private Integer idType;                 //证件类型

    private String idNo;                    //证件号码

    private String mobile;

    private String email;

    private Integer eduLevel;               //学历

    private String graduatedSchool;         //毕业院校

    private Integer degree;                 //学位

    private String titles;                  //职称

    private String otherTitles;             //其他职称

    private String researchArea;            //研究领域

    private String industries;              //熟悉行业

    private String unitName;                //工作单位

    private String unitType;                //单位性质

    private String job;                     //工作职务

    private String unitAddressCode;         //工作所在地代码

    private String unitAddress;             //工作所在地详细地址

    private String types;                   //专家类型，可多选

    private Integer transferResult;         //是否愿意转化科技成果

    private Integer govReview;              //是否参加过'政府资助项目'评审

    private String govReviewName;           //是否参加过'政府资助项目'名称  (当govReview=true时,govReviewName应有值)

    private String researchResult;          //研究成果

    private List<UserFilesModel> files;     //文件列表

    private Integer status;                 //审核状态  0进行中 1通过 2驳回 3激活 4提交 5未提交

    private String deptName;                //部门

    private String avatar;                  //头像

    private String referrer;

    private String tags;

    private List<String> tagsArr;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<String> getTagsArr() {
        return tagsArr;
    }

    public void setTagsArr(List<String> tagsArr) {
        this.tagsArr = tagsArr;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public String getIndustries() {
        return industries;
    }

    public void setIndustries(String industries) {
        this.industries = industries;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUnitAddressCode() {
        return unitAddressCode;
    }

    public void setUnitAddressCode(String unitAddressCode) {
        this.unitAddressCode = unitAddressCode;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getTransferResult() {
        return transferResult;
    }

    public void setTransferResult(Integer transferResult) {
        this.transferResult = transferResult;
    }

    public Integer getGovReview() {
        return govReview;
    }

    public void setGovReview(Integer govReview) {
        this.govReview = govReview;
    }

    public String getGovReviewName() {
        return govReviewName;
    }

    public void setGovReviewName(String govReviewName) {
        this.govReviewName = govReviewName;
    }

    public String getResearchResult() {
        return researchResult;
    }

    public void setResearchResult(String researchResult) {
        this.researchResult = researchResult;
    }

    public List<UserFilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<UserFilesModel> files) {
        this.files = files;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOtherTitles() {
        return otherTitles;
    }

    public void setOtherTitles(String otherTitles) {
        this.otherTitles = otherTitles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
}
