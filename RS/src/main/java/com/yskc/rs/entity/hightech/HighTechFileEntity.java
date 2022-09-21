package com.yskc.rs.entity.hightech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.UserInfo;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 17:10
 * @Description: 高新技术资料文件
 */
@TableName("highTech_file")
public class HighTechFileEntity extends BaseEntity {

    @TableId
    private Integer id;
    private Integer companyId;
    private Integer highTechId;
    /**
     * 证明材料：[1:生产许可证,2:国内知识产权证书,3:检测报告,4:能源体系证书,5:销售发票
     *               6:销售合同,7:用户反馈,8:产品图片]
     */
    private Integer type;
    /**
     * 文件路径[多文件]
     */
    private String filepath;

    private String fileName;



    public static HighTechFileEntity build(UserInfo userInfo, Integer highTechId, Integer type, String filepath, String fileName) {
        HighTechFileEntity entity=new HighTechFileEntity();
        entity.setCompanyId(userInfo.getCompanyId());
        entity.setFileName(fileName);
        entity.setHighTechId(highTechId);
        entity.setType(type);
        entity.setFilepath(filepath);
        entity.create(userInfo.getUserId(),userInfo.getMsUserId(),new Date());
        return entity;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getHighTechId() {
        return highTechId;
    }

    public void setHighTechId(Integer highTechId) {
        this.highTechId = highTechId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
