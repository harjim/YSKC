package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.StageDao;
import com.yskc.rs.dao.project.ProjectRdEquipmentDao;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.projectequipment.RdEquipmentResultModel;
import com.yskc.rs.service.ExportDocFileService;
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
    private StageDao stageDao;

    public static RdEquipmentSummaryForm rdEquipmentSummaryForm;

    @PostConstruct
    public void init() {
        rdEquipmentSummaryForm = this;
        rdEquipmentSummaryForm.projectRdEquipmentDao = this.projectRdEquipmentDao;
        rdEquipmentSummaryForm.stageDao = this.stageDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        //项目设备年度统计
        StageEntity stage = rdEquipmentSummaryForm.stageDao.getStageInfo(projectEntity.getId(), dataModel.getpDocFileId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null&& stage.getBeginDate()!=null ? stage.getBeginDate() : projectEntity.getBeginDate());

        // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
        List<RdEquipmentResultModel> rdEquipmentModels = rdEquipmentSummaryForm.projectRdEquipmentDao.queryYearList(projectEntity.getCompanyId(), projectEntity.getId(),year);
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