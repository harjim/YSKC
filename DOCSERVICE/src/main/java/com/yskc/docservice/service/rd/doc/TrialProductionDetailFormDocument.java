package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.TrialProdDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.trialprod.StageTrialModel;
import com.yskc.docservice.models.rs.trialprod.StageTrialShowModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/16 11:14
 * description:
 */
@Component("TrialProductionDetailForm_Doc")
@Scope("prototype")
public class TrialProductionDetailFormDocument extends RDDocument {

    @Autowired
    private TrialProdDao trialProdDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isEmpty(this.docFile.getData())){
            map.put("fileList",new ArrayList<>());
        }else {
            map= JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
        }
        List<StageTrialModel> trialModels = trialProdDao.getTrialByProject(project.getId(), project.getCompanyId());
        List<StageTrialShowModel> trialShowModels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trialModels)) {
            for (StageTrialModel model : trialModels) {
                StageTrialShowModel showModel = new StageTrialShowModel();
                showModel.setWorkHours(new BigDecimal(model.getMinute()).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP));
                showModel.setMonth(DateUtil.format(model.getTrialDate(), "yyyy-MM"));
                if (model.getStartTime() != null) {
                    showModel.setStartTime(DateUtil.getTimeStr(model.getStartTime(), "HH:mm"));
                }
                if (model.getEndTime() != null) {
                    showModel.setEndTime(DateUtil.getTimeStr(model.getEndTime(), "HH:mm"));
                }
                if (null != model.getTrialDate()) {
                    String timeStr = DateUtil.getTimeStr(model.getTrialDate(), "yyyy-MM-dd");
                    showModel.setStartDate(timeStr);
                    showModel.setEndDate(timeStr);
                }
                showModel.setStatus("试制");
                showModel.setType("开机");
                trialShowModels.add(showModel);
            }
        }
        map.put("fileList", trialShowModels);
        return map;
    }

    /**
     * 试验试制研发工时列表
     * @return 列表
     */
    private List<StageTrialShowModel> getTrialShowModels() {
        ProjectEntity projectInfo = dataFactory.getProjectInfo();
        List<StageTrialModel> trialModels = trialProdDao.getTrialByProject(projectInfo.getId(), projectInfo.getCompanyId());
        List<StageTrialShowModel> trialShowModels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trialModels)) {
            for (StageTrialModel model : trialModels) {
                StageTrialShowModel showModel = new StageTrialShowModel();
                showModel.setWorkHours(new BigDecimal(model.getMinute()).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP));
                showModel.setMonth(DateUtil.format(model.getTrialDate(), "yyyy-MM"));
                if (model.getStartTime() != null) {
                    showModel.setStartTime(DateUtil.getTimeStr(model.getStartTime(), "HH:mm"));
                }
                if (model.getEndTime() != null) {
                    showModel.setEndTime(DateUtil.getTimeStr(model.getEndTime(), "HH:mm"));
                }
                if (null != model.getTrialDate()) {
                    String timeStr = DateUtil.getTimeStr(model.getTrialDate(), "yyyy-MM-dd");
                    showModel.setStartDate(timeStr);
                    showModel.setEndDate(timeStr);
                }
                showModel.setStatus("试制");
                showModel.setType("开机");
                trialShowModels.add(showModel);
            }
        }
        return trialShowModels;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map resultMap = new HashMap<>();
        resultMap.put("fileList", this.getTrialShowModels());
        return resultMap;
    }
}
