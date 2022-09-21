package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.MaterialDao;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.enums.DocFileDataEnum;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.MaterialPlanModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by hck
 * on 2020/10/21 10:15
 * description:用料计划表
 */
@Component
public class MaterialPlanForm implements ExportDocFileService {

    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    public static MaterialPlanForm materialPlanForm;

    /**
     * 研发物料-原材料
     */
    private final static int MATERIAL_RAW_ID = 35;
    /**
     * 研发物料-辅料
     */
    private final static int MATERIAL_AUXILIARY_ID = 39;
    /**
     * 试制备品备件/工艺装备用料
     */
    private final static int TRIAL_RESERVE_ID = 36;
    /**
     * 试制原材料
     */
    private final static int TRIAL_RAW_ID = 40;
    /**
     * 试制辅料
     */
    private final static int TRIAL_AUXILIARY_ID = 41;
    /**
     * 修理原材料
     */
    private final static int REPAIR_RAW_ID = 49;
    /**
     * 修理备品备件/工艺装备用料
     */
    private final static int REPAIR_RESERVE_ID = 52;

    @PostConstruct
    public void init() {
        materialPlanForm = this;
        materialPlanForm.materialDao = this.materialDao;
        materialPlanForm.projectDocFileDao = this.projectDocFileDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        List<MaterialPlanModel> merials = new ArrayList<>();
        ProjectDocFileEntity docFile = materialPlanForm.projectDocFileDao.selectById(dataModel.getpDocFileId());
        Integer projectId = projectEntity.getId();
        Date month = null;
        if (StringUtils.isEmpty(fileData) && docFile.getMonth() != null) {
            month = docFile.getMonth();
        } else if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            String dateStr = (String) dataMap.get("month");
            if (!StringUtils.isEmpty(dateStr)) {
                month = DateUtil.getMonthFirstDay(DateUtil.getDateByString(dateStr + "-01 00:00:00"));
            } else if (docFile.getMonth() != null) {
                month = docFile.getMonth();
            }
        }
        if (month != null) {
            List<Integer> costTypes = new ArrayList<>();
            int type = getType(docFile.getDocFileId(), costTypes);
            dataMap.put("materialPlanMonth", cn.hutool.core.date.DateUtil.format(month, "yyyy-MM"));
            merials = getData(projectId, month, DateUtil.getMonthLastDay(month), costTypes, type);
        }
        dataMap.put("materials", merials);
        return dataMap;
    }

    private List<MaterialPlanModel> getData(Integer projectId, Date beginDate, Date endDate, List<Integer> costTypes, Integer type) {
        List<MaterialPlanModel> merials = materialPlanForm.materialDao.getMaterialPlanData(projectId, beginDate, endDate, costTypes, type);
        if (!CollectionUtils.isEmpty(merials)) {
            for (MaterialPlanModel model : merials) {
                String acqDateStr = model.getAcqDate() != null ? DateUtil.getTimeStr(model.getAcqDate(), "yyyy-MM-dd") : "";
                model.setAcqDateStr(acqDateStr);
            }
        }
        return merials;
    }

    /**
     * type=0 原材料，type=1 辅料， type=2 备品备件
     *
     * @param docFileId
     * @param costTypes
     * @return
     */
    public static int getType(Integer docFileId, List<Integer> costTypes) {
        int type;
        switch (docFileId) {
            case MATERIAL_RAW_ID:
                Collections.addAll(costTypes,20000, 20001, 20002);
                type = 0;
                break;
            case MATERIAL_AUXILIARY_ID:
                Collections.addAll(costTypes,20000, 20001, 20002);
                type = 1;
                break;
            case TRIAL_RAW_ID:
                Collections.addAll(costTypes,20301, 20303, 20304);
                type = 0;
                break;
            case TRIAL_AUXILIARY_ID:
                Collections.addAll(costTypes,20301, 20303, 20304);
                type = 1;
                break;
            case TRIAL_RESERVE_ID:
                Collections.addAll(costTypes,20301, 20303, 20304);
                type = 2;
                break;
            case REPAIR_RAW_ID:
                costTypes.add(20601);
                type = 0;
                break;
            case REPAIR_RESERVE_ID:
                costTypes.add(20601);
                type = 2;
                break;
            default:
                costTypes.add(-1);
                type = -1;
                break;
        }
        return type;
    }

    /**
     * 获取材料相关文档id map
     * @return
     */
    public static Map<Integer, Integer> getMaterialDocIdTypeMap() {
        Map<Integer, Integer> result = new HashMap<>();
        Integer type = DocFileDataEnum.MATERIAL.getType();
        result.put(MATERIAL_RAW_ID, type);
        result.put(MATERIAL_AUXILIARY_ID, type);
        result.put(TRIAL_RAW_ID, type);
        result.put(TRIAL_AUXILIARY_ID, type);
        result.put(TRIAL_RESERVE_ID, type);
        result.put(REPAIR_RAW_ID, type);
        result.put(REPAIR_RESERVE_ID, type);
        return result;
    }
}
