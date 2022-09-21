package com.yskc.docservice.models.rs.rdplan;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.models.rs.aggragation.AggDeeConfigDetailModel;
import com.yskc.docservice.models.rs.excel.TableField;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: doc-service
 * @BelongsPackage: com.yskc.docservice.models.rs.rdemployeeplan
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-11 14:14
 * @Description: 研发人员计划分配表格
 */
public class RdPlanTableField extends TableField {
    private Integer type;
    private String config;
    private Map<Integer, AggDeeConfigDetailModel> configMap;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Map<Integer, AggDeeConfigDetailModel> getConfigMap() {
        if (configMap == null && StringUtils.hasLength(config)) {
            configMap = new HashMap<>();
            Map<String, Map<String, Object>> map = JsonUtils.jsonToPojo(config, Map.class);
            for (String key : map.keySet()) {
                configMap.put(Integer.valueOf(key), BeanUtil.toBean(map.get(key), AggDeeConfigDetailModel.class));
            }
        }
        return configMap;
    }

    public void setConfigMap(Map<Integer, AggDeeConfigDetailModel> configMap) {
        this.configMap = configMap;
    }
}
