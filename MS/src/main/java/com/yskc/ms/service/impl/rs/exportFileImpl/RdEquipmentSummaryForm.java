package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.ProjectRdEquipmentDao;
import com.yskc.ms.dao.rs.RsStageDao;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.RsStageEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.RdEquipmentResultModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 16:13
 * description:项目设备年度统计
 */
@Component
public class RdEquipmentSummaryForm implements ExportDocFileService {

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private RsStageDao stageDao;

    public static RdEquipmentSummaryForm rdEquipmentSummaryForm;

    @PostConstruct
    public void init() {
        rdEquipmentSummaryForm = this;
        rdEquipmentSummaryForm.projectRdEquipmentDao = this.projectRdEquipmentDao;
        rdEquipmentSummaryForm.stageDao = this.stageDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        RsStageEntity stage = rdEquipmentSummaryForm.stageDao.getStageInfo(projectEntity.getId(), dataModel.getpDocFileId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectEntity.getBeginDate());
        //项目设备年度统计
        // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
        List<RdEquipmentResultModel> rdEquipmentModels = rdEquipmentSummaryForm.projectRdEquipmentDao.queryYearList(projectEntity.getCompanyId(), projectEntity.getId(), year);
        Map<String, Object> dataMap;
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        } else {
            dataMap = new HashMap<>();
        }
//        if (dataMap.containsKey("equipmentYear")) {
        if (!CollectionUtils.isEmpty(rdEquipmentModels)) {
            int seq = 0;
            for (RdEquipmentResultModel rde : rdEquipmentModels) {
                seq += 1;
                rde.setNum(seq);
            }
        } else {
            rdEquipmentModels = new ArrayList<>();
        }
//        }
        dataMap.put("equipmentYearList", rdEquipmentModels);
        return dataMap;
    }
}