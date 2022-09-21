package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.service.ExportDocFileService;
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
        Map<String,Object> dataMap=new HashMap<>();
        if(StringUtils.isEmpty(fileData)){
            dataMap.put("list",new ArrayList<>());
        }else {
            dataMap= JsonUtils.jsonToPojo(fileData, Map.class);
        }
        return dataMap;
    }
}
