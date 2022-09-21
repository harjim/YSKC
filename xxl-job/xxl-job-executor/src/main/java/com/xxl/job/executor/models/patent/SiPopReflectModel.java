package com.xxl.job.executor.models.patent;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.executor.entity.ms.PatentDataEntity;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-19 09:35
 * @Description: sipop映射
 */
public class SiPopReflectModel extends PatentReflectModel {
    /**
     * {
     * "applicationDocNum": "CN86206370", 专利号
     * publicationDocNum: CN202955642U 公开号
     * "type": "02", 专利类型
     * "title": "顺向牙刷", 专利名称
     * "validate": "03", 专利状态 01 审中，02 有效 03 无效
     * "applicant": "肖来宾", 申请人
     * "inventor": "肖来宾", 发明人
     * "applicationDate": "19860830", 申请日期
     * "publicationDate": "19870930", 公开日期
     * "ipcMain": "A46B13/08", 主分类号
     * }
     */
    private String applicationDocNum;
    private String publicationDocNum;
    private Integer type;
    private String title;
    private Integer validate;
    private String applicant;
    private String inventor;
    private String applicationDate;
    private String publicationDate;
    private String ipcMain;
    private String queryWord;

    public String getApplicationDocNum() {
        return applicationDocNum;
    }

    public void setApplicationDocNum(String applicationDocNum) {
        this.applicationDocNum = applicationDocNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIpcMain() {
        return ipcMain;
    }

    public void setIpcMain(String ipcMain) {
        this.ipcMain = ipcMain;
    }

    public String getQueryWord() {
        return queryWord;
    }

    public String getPublicationDocNum() {
        return publicationDocNum;
    }

    public void setPublicationDocNum(String publicationDocNum) {
        this.publicationDocNum = publicationDocNum;
    }

    @Override
    public PatentDataEntity reflectEntity() {
        String caseStatus = "";
        if (validate != null) {
            if (validate == 2) {
                caseStatus = "有效";
            } else if (validate == 3) {
                caseStatus = "无效";
            } else {
                caseStatus = "审中";
            }
        }
        Date applyDate = null;
        Date publicDate = null;
        if (!StringUtils.isEmpty(publicationDate)) {
            publicDate = DateUtil.parse(publicationDate, DatePattern.PURE_DATE_FORMAT);
        }
        if (!StringUtils.isEmpty(applicationDate)) {
            applyDate = DateUtil.parse(applicationDate, DatePattern.PURE_DATE_FORMAT);
        }
        return PatentDataEntity.build(queryWord, applyDate, applicant, inventor, type, ipcMain, title,
                publicDate, publicationDocNum, applicationDocNum, caseStatus, queryWord, null);
    }

    @Override
    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }
}
