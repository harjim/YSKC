package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.dao.rs.ProjectRdEquipmentDao;
import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.RdEquipmentResultModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 17:47
 * description:设备使用记录导出
 */
@Component
public class EquipmentAggForm implements ExportDocFileService {

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    public static EquipmentAggForm equipmentAggForm;

    @PostConstruct
    public void init() {
        equipmentAggForm = this;
        equipmentAggForm.projectRdEquipmentDao = this.projectRdEquipmentDao;
        equipmentAggForm.projectDocFileDao = this.projectDocFileDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        List<RdEquipmentResultModel> equipmentList = new ArrayList<>();
        ProjectDocFileEntity docFile = equipmentAggForm.projectDocFileDao.selectById(dataModel.getpDocFileId());
        if (StringUtils.isEmpty(fileData) && docFile.getMonth() != null) {
            equipmentList = getData(projectEntity, docFile.getMonth());
            dataMap.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFile.getMonth(), "yyyy年MM月"));
        } else if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            String dateStr = (String) dataMap.get("equipmentMonth");
            if (!StringUtils.isEmpty(dateStr)) {
                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
                equipmentList = getData(projectEntity, date);
                dataMap.put("equipmentMonth", dateStr.replace("-", "年") + "月份");
            } else if (docFile.getMonth() != null) {
                equipmentList = getData(projectEntity, docFile.getMonth());
                dataMap.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFile.getMonth(), "yyyy年MM月"));
            }
        }
        dataMap.put("equipmentList", equipmentList);
        return dataMap;
    }

    private List<RdEquipmentResultModel> getData(ProjectEntity projectEntity, Date month) {
        Integer year = cn.hutool.core.date.DateUtil.year(month);
        // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
        List<RdEquipmentResultModel> equipmentList = equipmentAggForm.projectRdEquipmentDao.queryList(projectEntity.getCompanyId(), projectEntity.getId(), month, year);
        if (!CollectionUtils.isEmpty(equipmentList)) {
            int seq = 0;
            for (RdEquipmentResultModel rde : equipmentList) {
                seq += 1;
                rde.setNum(seq);
            }
        }
        return equipmentList;
    }

//    @Override
//    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
//        Map<String, Object> dataMap = new HashMap<>();
//        List<RdEquipmentResultModel> equipmentList = new ArrayList<>();
//        if (!StringUtils.isEmpty(fileData)) {
//            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
//            String dateStr = (String) dataMap.get("equipmentMonth");
//            if (!StringUtils.isEmpty(dateStr)) {
//                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
//                equipmentList = equipmentAggForm.projectRdEquipmentDao.queryList(projectEntity.getCompanyId(), projectEntity.getId(), date);
//                if (!CollectionUtils.isEmpty(equipmentList)) {
//                    int seq = 0;
//                    for (RdEquipmentResultModel rde : equipmentList) {
//                        seq += 1;
//                        rde.setNum(seq);
//                    }
//                }
//                dataMap.put("equipmentMonth", dateStr.replace("-", "年") + "月份");
//            }
//        }
//        dataMap.put("equipmentList", equipmentList);
//        return dataMap;
//    }
}
