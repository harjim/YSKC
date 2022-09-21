package com.yskc.docservice.service.rd.doc;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.MaterialDao;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDao;
import com.yskc.docservice.entity.rs.project.ProjectDocFileEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.material.MaterialPlanModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by hck
 * on 2020/10/21 10:15
 * description: 用料计划表
 */
@Component("MaterialPlanForm_Doc")
@Scope("prototype")
public class MaterialPlanFormDocument extends RDDocument {

    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

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
    private final static int REPAIR_RESERVE_ID=52;

    @Override
    protected Map getDocMap(){
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        Map<String, Object> map = new HashMap<>();
        List<MaterialPlanModel> merials = new ArrayList<>();
        Integer projectId = projectInfo.getId();
        Date month = this.getDocMonth();
        if (month != null) {
            List<Integer> costTypes=new ArrayList<>();
            int type=getType(this.docFile.getDocFileId(),costTypes);
            map.put("materialPlanMonth", cn.hutool.core.date.DateUtil.format(month, "yyyy-MM"));
            merials = getData(projectId, month, DateUtil.getMonthLastDay(month), costTypes, type);
        }
        map.put("workshop", projectInfo.getWorkshop());
        map.put("productLine", projectInfo.getProductLine());
        map.put("processSection", projectInfo.getProcessSection());
        map.put("materials", merials);
        return map;
    }

    private Date getDocMonth() {
        if (this.docFile.getMonth() != null) {
            return this.docFile.getMonth();
        } else if (this.getJsonMap() != null) {
            String dStr = (String) this.getJsonMap().get("month");
            if (StringUtils.hasLength(dStr)) {
                return DateUtil.getDateByString(dStr + "-01 00:00:00");
            }
        }
        return null;
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        List<MaterialPlanModel> merials = new ArrayList<>();
        ProjectDocFileEntity docFile = projectDocFileDao.selectById(this.docFile.getId());
        Integer projectId = project.getId();
        Date month = null;
        if (StringUtils.isEmpty(this.docFile.getData()) && docFile.getMonth() != null) {
            month = docFile.getMonth();
        } else if (!StringUtils.isEmpty(this.docFile.getData())) {
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            String dateStr = (String) map.get("month");
            if (!StringUtils.isEmpty(dateStr)) {
                month = DateUtil.getMonthFirstDay(DateUtil.getDateByString(dateStr + "-01 00:00:00"));
            } else if (docFile.getMonth() != null) {
                month = docFile.getMonth();
            }
        }

        if (month != null) {
            List<Integer> costTypes=new ArrayList<>();
            int type=getType(docFile.getDocFileId(),costTypes);
            map.put("materialPlanMonth", cn.hutool.core.date.DateUtil.format(month, "yyyy-MM"));
            merials = getData(projectId, month, DateUtil.getMonthLastDay(month), costTypes, type);
        }
        map.put("workshop", project.getWorkshop());
        map.put("productLine", project.getProductLine());
        map.put("processSection", project.getProcessSection());
        map.put("materials", merials);
        return map;
    }

    private List<MaterialPlanModel> getData(Integer projectId, Date beginDate, Date endDate, List<Integer> costTypes, Integer type) {
        List<MaterialPlanModel> merials = materialDao.getMaterialPlan(projectId, beginDate, endDate, costTypes, type);
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
     * @param docFileId
     * @param costTypes
     * @return
     */
    public static int getType(Integer docFileId,List<Integer> costTypes){
        int type;
        switch (docFileId) {
            case MATERIAL_RAW_ID:
                costTypes.addAll(CollUtil.newArrayList(20000, 20001, 20002));
                type = 0;
                break;
            case MATERIAL_AUXILIARY_ID:
                costTypes.addAll(CollUtil.newArrayList(20000, 20001, 20002));
                type = 1;
                break;
            case TRIAL_RAW_ID:
                costTypes.addAll(CollUtil.newArrayList(20301, 20303, 20304));
                type = 0;
                break;
            case TRIAL_AUXILIARY_ID:
                costTypes.addAll(CollUtil.newArrayList(20301, 20303, 20304));
                type = 1;
                break;
            case TRIAL_RESERVE_ID:
                costTypes.addAll(CollUtil.newArrayList(20301, 20303, 20304));
                type = 2;
                break;
            case REPAIR_RAW_ID:
                costTypes.add(20601);
                type = 0;
                break;
            case REPAIR_RESERVE_ID:
                costTypes.add(20601);
                type=2;
                break;
            default:
                costTypes.add(-1);
                type=-1;
                break;
        }
        return type;
    }
}
