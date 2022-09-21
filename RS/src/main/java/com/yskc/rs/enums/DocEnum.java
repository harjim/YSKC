package com.yskc.rs.enums;

/**
 * 上传文档类型枚举
 * 对应sys_document表中的fileType
 */
public enum DocEnum {

    /**
     * 创新项目-研发成果
     */
    listType_1001("创新项目-研发成果", 0, 1001),
    /**
     * 创新项目-知识产权
     */
    listType_2001("创新项目-知识产权", 0, 2001),
    /**
     * 创新项目-研发档案
     */
    listType_3001("创新项目-研发档案", 0, 3001),
    /**
     * 创新项目-研发意识
     */
    listType_4001("创新项目-研发意识", 0, 4001),
    /**
     * 创新项目-项目其他财务信息
     */
    listType_5001("创新项目-项目其他财务信息", 0, 5001),
    /**
     * 创新项目-研发技术中心
     */
    listType_6001("创新项目-研发技术中心", 0, 6001),
    /**
     * 创新项目-研发项目
     */
    listType_7001("创新项目-研发项目", 0, 7001),
    /**
     * 基本信息-年度审计报告
     */
    fileType_2("基本信息-年度审计报告", 2, 0),
    /**
     * 基本信息-年度汇算清缴报告
     */
    fileType_3("基本信息-年度汇算清缴报告", 3, 0),
    /**
     * 创新项目-年度项目分析
     */
    fileType_1005("创新项目-年度项目分析", 1005, 0),
    /**
     * 产业投资项目-审计报告
     */
    fileType_2001("产业投资项目-审计报告", 2001, 0),
    /**
     * 产业投资项目-附件列表
     */
    fileType_2002("产业投资项目-附件列表", 2002, 0),
    /**
     * 创新项目-子项目资料
     */
    fileType_3000("创新项目-子项目资料", 3000, 0),
    /**
     * 创新项目-PO单
     */
    fileType_7002("创新项目-PO单", 7002, 0),
    /**
     * 创新项目-BOM表
     */
    fileType_7003("创新项目-BOM表", 7003, 0),
    /**
     * 创新项目-生产日报表
     */
    fileType_7004("创新项目-生产日报表", 7004, 0),
    /**
     * 项目阶段-设计验证
     */
    fileType_8001("项目阶段-规划阶段", 8001, 0),
    /**
     * 项目阶段-试验证
     */
    fileType_8002("项目阶段-试验证", 8002, 0),
    /**
     * 项目阶段-试制
     */
    fileType_8003("项目阶段-试制", 8003, 0);


    private String fileName;

    private int fileType;

    private int listType;

    DocEnum(String fileName, Integer fileType, Integer listType) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.listType = listType;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileType() {
        return fileType;
    }

    public int getListType() {
        return listType;
    }

    /**
     * 通过type获取费用枚举
     *
     * @param fileType
     * @param listType
     * @return
     */
    public static String getCostEnum(int fileType, int listType) {

        for (DocEnum docEnum : values()) {
            if (docEnum.fileType == fileType && docEnum.listType == listType) {
                return docEnum.getFileName();
            }
        }
        return "";
    }
}
