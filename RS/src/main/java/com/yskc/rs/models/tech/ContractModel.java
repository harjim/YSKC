package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.tech.ContractDetail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/20 10:12
 * @Description:
 */
public class ContractModel implements Serializable {

    private Integer id;
    private Integer seq;

    private String contractNo;

    private String signTarget;

    private String filePath;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractDate;

    private List<ContractDetailModel> detailModels;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getSignTarget() {
        return signTarget;
    }

    public void setSignTarget(String signTarget) {
        this.signTarget = signTarget;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<ContractDetailModel> getDetailModels() {
        return detailModels;
    }

    public void setDetailModels(List<ContractDetailModel> detailModels) {
        this.detailModels = detailModels;
    }
}
