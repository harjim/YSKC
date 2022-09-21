package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.rs.models.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/entity/rs
 * @Author: hm
 * @CreateTime: 2022/8/25
 * @Description: RSDB c_name_history表
 */
@TableName("c_name_history")
public class RsNameHistoryEntity extends BaseEntity implements Serializable {

    @TableId
    private Integer id;

    private Integer companyId;
    /*
     * 变更名称
     */
    private String companyName;

    /*
     * 生效时间
     */
    private Date startDate;

    /*
     * 备注
     */
    private String remark;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
