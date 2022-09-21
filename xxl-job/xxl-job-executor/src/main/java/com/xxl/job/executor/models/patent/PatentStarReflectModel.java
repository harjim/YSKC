package com.xxl.job.executor.models.patent;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.entity.ms.PatentDataEntity;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-10 09:02
 * @Description: 专利映射model
 */
public class PatentStarReflectModel extends PatentReflectModel {
    /*{
            "StrANX": "7CDA1BAA9HDD9GIF9CGC8EEA7EBA9GEFADIA9EIE9GBA9GDC", // 法律状态
            "StrApNo": "202030246122.5", // 申请号/专利号
            "StrApDate": "2020.05.25", // 申请日期
            "StrInventor": "刘林峰", // 发明人
            "StrApply": "东莞市卓林机器人科技有限公司", // 申请人
            "StrMainIPC": "15-99(12)", //分类号
            "ZhuanLiLeiXing": "3",  // 专利类型
            "StrAnnNo": "306027267S", //公告号
            "StrAnnDate": "2020.09.01", // 公告日期

            "StrTitle": "六关节机器人  // 专利名称
            "StrFtUrl": "http:\/\/45.121.55.212\/bns\/comm\/getimg.aspx?idx=7CDA1BAA9HDD9GIF9CGC8EEA7EBA9GEFADIA9EIE9GBA9GDC&Ty=CNG&ImgTp=wg&pd=2020%e5%b9%b49%e6%9c%881%e6%97%a5",

            "StrShenQingRenDiZhi": "广东省东莞市大岭山镇园山仔西六巷17号401室", // 地址
    }
*/
    @JsonProperty("StrANX")
    private String StrANX;
    @JsonProperty("StrApNo")
    private String StrApNo;
    @JsonProperty("StrApDate")
    private String StrApDate;
    @JsonProperty("StrAnnNo")
    private String StrAnnNo;
    @JsonProperty("StrAnnDate")
    private String StrAnnDate;
    @JsonProperty("StrPubNo")
    private String StrPubNo;
    @JsonProperty("StrPubDate")
    private String StrPubDate;
    @JsonProperty("StrMainIPC")
    private String StrMainIPC;
    @JsonProperty("StrInventor")
    private String StrInventor;
    @JsonProperty("StrApply")
    private String StrApply;
    @JsonProperty("StrTitle")
    private String StrTitle;
    @JsonProperty("ZhuanLiLeiXing")
    private Integer ZhuanLiLeiXing;
    @JsonProperty("StrShenQingRenDiZhi")
    private String StrShenQingRenDiZhi;
    private String caseStatus;
    private String queryWord;

    public String getStrANX() {
        return StrANX;
    }

    public void setStrANX(String strANX) {
        StrANX = strANX;
    }

    public String getStrApNo() {
        return StrApNo;
    }

    public void setStrApNo(String strApNo) {
        StrApNo = strApNo;
    }

    public String getStrApDate() {
        return StrApDate;
    }

    public void setStrApDate(String strApDate) {
        StrApDate = strApDate;
    }

    public String getStrAnnNo() {
        return StrAnnNo;
    }

    public void setStrAnnNo(String strAnnNo) {
        StrAnnNo = strAnnNo;
    }

    public String getStrAnnDate() {
        return StrAnnDate;
    }

    public void setStrAnnDate(String strAnnDate) {
        StrAnnDate = strAnnDate;
    }

    public String getStrMainIPC() {
        return StrMainIPC;
    }

    public void setStrMainIPC(String strMainIPC) {
        StrMainIPC = strMainIPC;
    }

    public String getStrInventor() {
        return StrInventor;
    }

    public void setStrInventor(String strInventor) {
        StrInventor = strInventor;
    }

    public String getStrApply() {
        return StrApply;
    }

    public void setStrApply(String strApply) {
        StrApply = strApply;
    }

    public String getStrTitle() {
        return StrTitle;
    }

    public void setStrTitle(String strTitle) {
        StrTitle = strTitle;
    }

    public Integer getZhuanLiLeiXing() {
        return ZhuanLiLeiXing;
    }

    public void setZhuanLiLeiXing(Integer zhuanLiLeiXing) {
        ZhuanLiLeiXing = zhuanLiLeiXing;
    }

    public String getStrShenQingRenDiZhi() {
        return StrShenQingRenDiZhi;
    }

    public void setStrShenQingRenDiZhi(String strShenQingRenDiZhi) {
        StrShenQingRenDiZhi = strShenQingRenDiZhi;
    }

    @Override
    public PatentDataEntity reflectEntity() {
        return PatentDataEntity.build(StrShenQingRenDiZhi.trim(),!StringUtils.isEmpty(StrApDate)?DateUtil.parse(StrApDate, Constant.DOP_DATE_FORMAT): null,
                StrApply.trim(),StrInventor.trim(),ZhuanLiLeiXing,StrMainIPC.trim(),StrTitle.trim(),getExistDate(),getExistStr(),StrApNo.trim(),
                caseStatus,queryWord,StrANX);
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    @Override
    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getQueryWord() {
        return queryWord;
    }

    @Override
    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }

    public String getStrPubNo() {
        return StrPubNo;
    }

    public void setStrPubNo(String strPubNo) {
        StrPubNo = strPubNo;
    }

    public String getStrPubDate() {
        return StrPubDate;
    }

    public void setStrPubDate(String strPubDate) {
        StrPubDate = strPubDate;
    }

    public Date getExistDate() {
        String realStr = StringUtils.isEmpty(StrAnnDate) ? StrPubDate : StrAnnDate;
        if (!StringUtils.isEmpty(realStr)) {
            if (realStr.trim().length() == 10) {
                return DateUtil.parse(realStr, Constant.DOP_DATE_FORMAT);
            } else {
                return DateUtil.parse(realStr, DatePattern.PURE_DATE_FORMAT);
            }
        }
        return null;
    }

    public String getExistStr() {
        return StringUtils.isEmpty(StrAnnNo.trim()) ? StrPubNo.trim() : StrAnnNo.trim();
    }
}
