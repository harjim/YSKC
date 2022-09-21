package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.TrialProdDao;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDao;
import com.yskc.docservice.entity.rs.project.ProjectDocFileEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.trialprod.ImportDocTrialModel;
import com.yskc.docservice.models.rs.trialprod.StageTrialModel;
import com.yskc.docservice.service.rd.RDDocument;
import com.yskc.docservice.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by hck
 * on 2020/6/28 15:01
 * description:验证报告
 */
@Component( "DesignReportForm_Doc" )
@Scope( "prototype" )
public class DesignReportFormDocument extends RDDocument {

    @Autowired
    private TrialProdDao trialProdDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    @Override
    protected Map getDocMap() {
        Map<String, Object> map = new HashMap<>();
        Date m = this.getDocMonth();
        List<StageTrialModel> list;
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        if (m != null) {
            list = trialProdDao.chooseGetTrial(projectInfo.getCompanyId(), projectInfo.getId(), m);
        } else {
            list = trialProdDao.queryDocFileTrial(projectInfo.getCompanyId(), Arrays.asList(this.docFile.getId()), projectInfo.getId());
        }

        //2021/6/15  验证报告地点数据来源项目的部门/车间/产线/工艺段
        String place = projectInfo.getDeptName();
        if (place == null) {
            ToolUtils.getProjectPlace(projectInfo);
        }
        List<ImportDocTrialModel> importDocTrialModels = new ArrayList<>();
        boolean hasTrialProduct = false;
        if (!CollectionUtils.isEmpty(list)) {
            String date = "";
            for (int i = 0; i < list.size(); i++) {
                StageTrialModel trialProdSumarryModel = list.get(i);
                ImportDocTrialModel model = new ImportDocTrialModel();
                Date trialDate = trialProdSumarryModel.getTrialDate();
                if (trialDate != null) {
                    date = DateUtil.format(trialDate, "yyyy-MM-dd");
                }
                if (StringUtils.hasLength(trialProdSumarryModel.getTrialProduct())) {
                    hasTrialProduct = true;
                }
                model.setIndex(i + 1);
                model.setTrialProduct(trialProdSumarryModel.getTrialProduct());
                model.setTrialDate(date);
                model.setActualPO(trialProdSumarryModel.getRdYield());
                model.setPlace(place);
                Date startTime = trialProdSumarryModel.getStartTime();
                Date endTime = trialProdSumarryModel.getEndTime();
                model.setUnit(trialProdSumarryModel.getUnit());
                model.setStartTime(startTime != null ? DateUtil.format(startTime, "HH:mm:ss") : null);
                model.setEndTime(endTime != null ? DateUtil.format(endTime, "HH:mm:ss") : null);
                importDocTrialModels.add(model);
            }
        }
        map.put("hasTrialProduct", hasTrialProduct);
        map.put("list", importDocTrialModels);
        return map;
    }

    private Date getDocMonth() {
        if (this.docFile.getMonth() != null) {
            return this.docFile.getMonth();
        } else if (this.getJsonMap() != null) {
            String dStr = (String)this.getJsonMap().get("month");
            if (StringUtils.hasLength(dStr)) {
                return DateUtil.getDateByString(dStr + "-01 00:00:00");
            }
        }
        return null;
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        ProjectDocFileEntity projectDocFile = projectDocFileDao.selectById(this.docFile.getId());
        List<StageTrialModel> list;
        Date month = null;
        if (!StringUtils.isEmpty(this.docFile.getData())) {
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            if (map.containsKey("month")) {
                String dataMonth = map.get("month").toString() + "-01 00:00:00";
                month = DateUtil.parse(dataMonth, "yyyy-MM-dd HH:mm:ss");
            }
        }
        if (projectDocFile.getMonth() != null) {
            list = trialProdDao.chooseGetTrial(project.getCompanyId(), project.getId(), projectDocFile.getMonth());
        } else if (month != null) {
            list = trialProdDao.chooseGetTrial(project.getCompanyId(), project.getId(), month);
        } else {
            list = trialProdDao.queryDocFileTrial(project.getCompanyId(), Arrays.asList(this.docFile.getId()), project.getId());
        }
        //2021/6/15  验证报告地点数据来源项目的部门/车间/产线/工艺段
        String place = ToolUtils.getProjectPlace(project);
        List<ImportDocTrialModel> importDocTrialModels = new ArrayList<>();
        boolean hasTrialProduct = false;
        if (!CollectionUtils.isEmpty(list)) {
            String date = "";
            for (int i = 0; i < list.size(); i++) {
                StageTrialModel trialProdSumarryModel = list.get(i);
                ImportDocTrialModel model = new ImportDocTrialModel();
                Date trialDate = trialProdSumarryModel.getTrialDate();
                if (trialDate != null) {
                    date = DateUtil.format(trialDate, "yyyy-MM-dd");
                }
                if (StringUtils.hasLength(trialProdSumarryModel.getTrialProduct())) {
                    hasTrialProduct = true;
                }
                model.setIndex(i + 1);
                model.setTrialProduct(trialProdSumarryModel.getTrialProduct());
                model.setTrialDate(date);
                model.setActualPO(trialProdSumarryModel.getRdYield());
                model.setPlace(place);
                Date startTime = trialProdSumarryModel.getStartTime();
                Date endTime = trialProdSumarryModel.getEndTime();
                model.setUnit(trialProdSumarryModel.getUnit());
                model.setStartTime(startTime != null ? DateUtil.format(startTime, "HH:mm:ss") : null);
                model.setEndTime(endTime != null ? DateUtil.format(endTime, "HH:mm:ss") : null);
                importDocTrialModels.add(model);
            }
        }
        map.put("hasTrialProduct", hasTrialProduct);
        map.put("list", importDocTrialModels);
        return map;
    }
}
