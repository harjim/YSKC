package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:07
 * @Description:专利购买列表
 */
@TableName("patent_buying_list")
public class PatentBuyingList extends MsBaseEntity {
    @TableId
    private Integer id;

    private String filePath;//购买附件

    private Integer customerId;

    private Integer demandId;

    private Integer patentSeaId;

    private Integer status;//购买状态

    public PatentBuyingList build(Integer customerId, Integer demandId, Integer patentSeaId, Integer userId, Date date) {
        PatentBuyingList entity = new PatentBuyingList();
        entity.setCustomerId(customerId);
        entity.setDemandId(demandId);
        entity.setPatentSeaId(patentSeaId);
        entity.setStatus(0);
        entity.create(userId, date);
        return entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getPatentSeaId() {
        return patentSeaId;
    }

    public void setPatentSeaId(Integer patentSeaId) {
        this.patentSeaId = patentSeaId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
