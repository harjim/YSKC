package com.xxl.job.executor.models.patent;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.executor.entity.ms.PatentDataEntity;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-21 15:07
 * @Description: baiten
 */
public class BaiTenReflectModel extends PatentReflectModel {

    /**
     * field_values": {
     * "id": "CN201910385817.8", // 专利号
     * "pn": "CN111615210A", // 公开号
     * "ti": "两步随机接入的方法及终端", // 专利名称
     * "pd": "20200901", // 公开日期
     * "ad": "20190509", // 申请日期
     * "pa": ["维沃移动通信有限公司"], // 申请人
     * "in": ["孙鹏", "陈晓航", "刘昊"], //  发明人
     * "ic1": "H04W74/00", 主分类号
     * "aa": "广东省东莞市长安镇乌沙步步高大道283号", // 地址
     * "type": ["cn_in"], //类型
     * "lsnt": "审中-公开发明", // 法论状态
     * },
     */
    private String id;
    private String pn;
    private String ti;
    private String pd;
    private String ad;
    private String pa;
    private String in;
    private String ic1;
    private String aa;
    private Integer mainType;
    private String lsnt;
    private String queryWord;

    public static PatentDataEntity mapToModelAndReflect(Map<String, Object> map,String queryWord) {
        BaiTenReflectModel baiTenReflectModel = new BaiTenReflectModel();
        baiTenReflectModel.id = map.get("id").toString();
        baiTenReflectModel.pn = map.get("pn").toString();
        baiTenReflectModel.ti = map.get("ti").toString();
        baiTenReflectModel.pd = map.get("pd").toString();
        baiTenReflectModel.ad = map.get("ad").toString();
        Object paObj = map.get("pa");
        if (paObj != null && paObj instanceof ArrayList) {
            baiTenReflectModel.pa = String.join(",", (ArrayList) paObj);
        } else {
            baiTenReflectModel.pa = "";
        }
        Object inObj = map.get("in");
        if (inObj != null && inObj instanceof ArrayList) {
            baiTenReflectModel.in = String.join(",", (ArrayList) inObj);
        } else {
            baiTenReflectModel.in = "";
        }
        baiTenReflectModel.ic1 = map.get("ic1").toString();
        baiTenReflectModel.aa = map.get("aa").toString();
        Object typeObj = map.get("type");
        if (typeObj != null && typeObj instanceof ArrayList) {
            baiTenReflectModel.mainType = TYPE_MAP.getOrDefault(((ArrayList) typeObj).get(0).toString().trim(), 0);
        } else {
            baiTenReflectModel.mainType = 0;
        }
        baiTenReflectModel.queryWord = queryWord;
        baiTenReflectModel.lsnt = map.get("lsnt").toString();
        return baiTenReflectModel.reflectEntity();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }


    public String getIc1() {
        return ic1;
    }

    public void setIc1(String ic1) {
        this.ic1 = ic1;
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public String getLsnt() {
        return lsnt;
    }

    public void setLsnt(String lsnt) {
        this.lsnt = lsnt;
    }

    public String getQueryWord() {
        return queryWord;
    }

    public static Map<String, Integer> getTypeMap() {
        return TYPE_MAP;
    }

    private final static Map<String, Integer> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put("cn_in", 1);
        TYPE_MAP.put("cn_um", 2);
        TYPE_MAP.put("cn_id", 3);
    }

    @Override
    public PatentDataEntity reflectEntity() {
        String caseStatus = "";
        if (!StringUtils.isEmpty(lsnt)) {
            if (lsnt.contains("有权")) {
                caseStatus = "有效";
            } else if (lsnt.contains("无权")) {
                caseStatus = "无效";
            } else if (lsnt.contains("审中")) {
                caseStatus = "审中";
            }
        }
        return PatentDataEntity.build(aa, DateUtil.parse(ad, DatePattern.PURE_DATE_FORMAT), pa, in, mainType,
                ic1, ti, DateUtil.parse(pd, DatePattern.PURE_DATE_FORMAT), pn, id, caseStatus, queryWord, null);
    }
}
