package com.yskc.rs.enums;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/6/2 11:09
 * @Description:高品上传文件类型
 */
public enum HighTechFileTypeEnum {

    PRODUCTION_LICENCE("生产许可证",1),

    INTELLECTUAL_PROPERTY_CERTIFICATE("国内知识产权证书",2),

    TEST_REPORT("检测报告",3),

    ENERGY_SYSTEM_CERTIFICATE("能源体系证书",4),

    SALES_INVOICE("销售发票",5),

    SALES_CONTRACT("销售合同",6),

    USER_FEEDBACK("用户反馈",7),

    PRODUCT_PICTURE("产品图片",8),

    OTHER("其他",9);

    private String fileType;

    private Integer type;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    HighTechFileTypeEnum(String fileType, Integer type){
        this.fileType=fileType;
        this.type=type;
    }

    /**
     * 通过type获取费用枚举
     *
     * @param type
     * @return
     */
    public static HighTechFileTypeEnum getCostEnum(int type) {
        for (HighTechFileTypeEnum highTechFileTypeEnum : values()) {
            if (highTechFileTypeEnum.type == type) {
                return highTechFileTypeEnum;
            }
        }
        return null;
    }

    public static Map<Integer,String> getDataMap(){
        Map<Integer,String> map=new HashMap<>();
        for (HighTechFileTypeEnum highTechFileTypeEnum : values()) {
           map.put(highTechFileTypeEnum.getType(),highTechFileTypeEnum.getFileType());
        }
        return map;
    }
}
