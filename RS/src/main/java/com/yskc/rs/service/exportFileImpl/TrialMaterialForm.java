package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.MaterialDao;
import com.yskc.rs.dao.ProjectDocFileDao;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by hck
 * on 2020/12/2 9:02
 * description:试制材料计划表
 */
@Component
public class TrialMaterialForm implements ExportDocFileService {

    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    public static TrialMaterialForm trialMaterialForm;

    @PostConstruct
    public void init() {
        trialMaterialForm = this;
        trialMaterialForm.materialDao = this.materialDao;
        trialMaterialForm.projectDocFileDao=this.projectDocFileDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        ProjectDocFileEntity docFile=trialMaterialForm.projectDocFileDao.selectById(dataModel.getpDocFileId());
        List<Integer> keys=Arrays.asList(20301,20303,20304);
        Integer projectId=projectEntity.getId();
        List<MaterialPlanModel> merials = new ArrayList<>();
        if(StringUtils.isEmpty(fileData) && docFile.getMonth()!=null) {
            Date endMonth=DateUtil.getMonthLastDay(docFile.getMonth());
            merials= getData(projectId,docFile.getMonth(),endMonth,keys);
            dataMap.put("trialMaterialMonth", cn.hutool.core.date.DateUtil.format(docFile.getMonth(),"yyyy-MM"));
        }else if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            String dateStr = (String) dataMap.get("month");
            if (!StringUtils.isEmpty(dateStr)) {
                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
                Date beginDate = DateUtil.getMonthFirstDay(date);
                Date endDate = DateUtil.getMonthLastDay(date);
                merials = getData(projectId,beginDate,endDate,keys);
                dataMap.put("trialMaterialMonth",dateStr);
            }else if(docFile.getMonth()!=null) {
                Date endMonth=DateUtil.getMonthLastDay(docFile.getMonth());
                merials= getData(projectId,docFile.getMonth(),endMonth,keys);
                dataMap.put("trialMaterialMonth", cn.hutool.core.date.DateUtil.format(docFile.getMonth(),"yyyy-MM"));
            }
        }
        dataMap.put("materials", merials);
        return dataMap;
    }

    private List<MaterialPlanModel> getData(Integer projectId, Date beginDate,Date endDate,List<Integer> keys){
        List<MaterialPlanModel>  merials = trialMaterialForm.materialDao.getMaterialPlan(projectId, beginDate, endDate,keys,null);
        if (!CollectionUtils.isEmpty(merials)) {
            for (MaterialPlanModel model : merials) {
                String acqDateStr=model.getAcqDate()!=null?DateUtil.getTimeStr(model.getAcqDate(),"yyyy-MM-dd"):"";
                model.setAcqDateStr(acqDateStr);
            }
        }
        return merials;
    }
}
