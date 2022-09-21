package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hck
 * @since 2021-03-18
 */
@TableName("t_contract")
public class Contract extends BaseEntity {

    @TableId
    private Integer id;
    private Integer seq;
    private Integer companyId;
    private String contractNo;
    private String signTarget;
    private String filePath;
    private Date contractDate;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

}
