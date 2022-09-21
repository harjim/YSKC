package com.yskc.rs.models.projectyieldconfig;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.utils.ToolUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-13 10:46
 * @Description: 试制信息汇总
 */
public class ProjectYieldConfigTotalModel {

    private Integer projectId;
    /**
     * 总量/总工时
     */
    private BigDecimal total;
    /**
     * 研发量/研发工时
     */
    private BigDecimal agg;

    /**
     * 同部门，试制日期
     */
    Map<String, List<ProjectYieldConfigInfoModel>> infos;

    public static ProjectYieldConfigTotalModel build(Integer projectId) {
        ProjectYieldConfigTotalModel info = new ProjectYieldConfigTotalModel();
        info.projectId = projectId;
        info.total = info.agg = BigDecimal.ZERO;
        info.infos = new HashMap<>();
        return info;
    }

    public static ProjectYieldConfigTotalModel build(List<ProjectYieldConfigInfoModel> data, Boolean hour) {
        ProjectYieldConfigTotalModel info = new ProjectYieldConfigTotalModel();
        //// TODO: 22/09/19 zdf total取第一条数据的
        info.total = data.get(0).getTotal();
        // 如果是工时，则agg=试制工时，否则agg=试制量

        BigDecimal agg = BigDecimal.ZERO;
        for (ProjectYieldConfigInfoModel item : data) {
            agg = agg.add(hour ? item.getTrialHour() : item.getAgg());
        }
        info.agg = agg;
        return info;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAgg() {
        return agg;
    }

    public BigDecimal getBitAgg(int bit) {
        return agg.setScale(bit, BigDecimal.ROUND_DOWN);
    }

    public void setAgg(BigDecimal agg) {
        this.agg = agg;
    }

    public Map<String, List<ProjectYieldConfigInfoModel>> getInfos() {
        return infos;
    }

    public void setInfos(Map<String, List<ProjectYieldConfigInfoModel>> infos) {
        this.infos = infos;
    }

    public void add(ProjectYieldConfigInfoModel info) {
        this.agg = this.agg.add(info.getAgg());
        ToolUtils.putAndAdd(infos, MessageFormat.format("{0}{1}", info.getDeptName().trim(), DateUtil.format(info.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT)), info);
    }
}
