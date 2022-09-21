package com.yskc.docservice.entity.rs.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.entity.rs.tech.BaseEntity;
import com.yskc.docservice.models.rs.aggragation.AggDeeConfigDetailModel;

import java.util.Date;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-21 11:22
 * @Description: 项目研发归集配置
 */
@TableName("p_rdAgg_config")
public class ProjectRdAggConfig extends BaseEntity {
    @TableId
    private Integer id;

    private Integer type;

    private Integer companyId;

    private String config;
    private Date month;

    public static ProjectRdAggConfig build(Integer companyId,Integer userId,Integer msUserId, Date now,
                                           Map<Integer, AggDeeConfigDetailModel> config, Integer type, Date month) {
        ProjectRdAggConfig configEntity = new ProjectRdAggConfig();
        configEntity.companyId = companyId;
        configEntity.type = type;
        configEntity.config = JsonUtils.objectToJson(config);
        configEntity.month = month;
        configEntity.create(userId,msUserId,now);
        return configEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
