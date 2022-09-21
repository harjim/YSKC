package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/16 10:48
 * description:评审报告导出
 */
@Component
public class TReviewForm implements ExportDocFileService {

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(fileData)) {
            dataMap.put("list", new ArrayList<>());
        } else {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        return dataMap;
    }
}
