package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 9:46
 * @Description:
 */
@TableName("innovation_project")
public class InnovationProject extends MsBaseEntity {
    @TableId
    private Integer id;

    private Integer customerId;

    private Integer year;

    private Integer deptId;

    private String types;

    public static InnovationProject build(Integer customerId, Integer year, Integer productType, Integer userId, Date now,Integer deptId) {
        InnovationProject ip = new InnovationProject();
        ip.customerId = customerId;
        ip.year = year;
        ip.types = productType.toString();
        ip.deptId = deptId;
        ip.create(userId, now);
        return ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }


}
