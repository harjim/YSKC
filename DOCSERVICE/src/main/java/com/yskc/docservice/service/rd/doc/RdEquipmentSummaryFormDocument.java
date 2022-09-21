package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.StageDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEquipmentDao;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.rdequipment.RdEquipmentResultModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 16:13
 * description:项目设备年度统计
 */
@Component("RdEquipmentSummaryForm_Doc")
@Scope("prototype")
public class RdEquipmentSummaryFormDocument extends RDDocument {

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private StageDao stageDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        //项目设备年度统计
        StageEntity stage = stageDao.getStageInfo(project.getId(), this.docFile.getId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null&& stage.getBeginDate()!=null ? stage.getBeginDate() : project.getBeginDate());

        // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
        List<RdEquipmentResultModel> rdEquipmentModels = projectRdEquipmentDao.queryYearList(project.getCompanyId(), project.getId(),year);
        Map<String, Object> map = new HashMap<>();
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
        map.put("equipmentYearList", rdEquipmentModels);
        return map;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map jsonMap = getJsonMap();
        Map resultMap = new HashMap<>();

        ProjectEntity projectInfo = dataFactory.getProjectInfo();
        //项目设备年度统计
        StageEntity stage = stageDao.getStageInfo(projectInfo.getId(), this.docFile.getId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null&& stage.getBeginDate()!=null ? stage.getBeginDate() : projectInfo.getBeginDate());

        List<RdEquipmentResultModel> rdEquipmentModels = projectRdEquipmentDao.queryYearList(projectInfo.getCompanyId(), projectInfo.getId(), year);
        if (!CollectionUtils.isEmpty(rdEquipmentModels)) {
            int seq = 0;
            for (RdEquipmentResultModel rde : rdEquipmentModels) {
                seq += 1;
                rde.setNum(seq);
            }
        } else {
            rdEquipmentModels = new ArrayList<>();
        }
        resultMap.put("equipmentYearList", rdEquipmentModels);

        return resultMap;
    }
}