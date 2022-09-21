package com.yskc.rs.entity.company;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/2/15 14:17
 * @Description:公司历史名称实体类
 * @author: hsx
 */

@TableName("c_name_history")
public class NameHistoryEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -349908511991304170L;

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
