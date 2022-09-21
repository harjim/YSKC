package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.DeptDao;
import com.yskc.rs.dao.ProjectDocFileDao;
import com.yskc.rs.dao.TrialProdDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.trialprod.ImportDocTrialModel;
import com.yskc.rs.models.trialprod.StageTrialModel;
import com.yskc.rs.service.ExportDocFileService;
import com.yskc.rs.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/28 15:01
 * description:验证报告
 */
@Component
public class DesignReportForm implements ExportDocFileService {

    @Autowired
    private TrialProdDao trialProdDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    @Autowired
    private DeptDao deptDao;

    public static DesignReportForm designReportForm;


    @PostConstruct
    public void init() {
        designReportForm = this;
        designReportForm.trialProdDao = this.trialProdDao;
        designReportForm.projectDocFileDao = this.projectDocFileDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        ProjectDocFileEntity projectDocFile = designReportForm.projectDocFileDao.selectById(dataModel.getpDocFileId());
        List<StageTrialModel> list;
        Date month = null;
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            if (dataMap.containsKey("month")) {
                String dataMonth = dataMap.get("month").toString() + "-01 00:00:00";
                month = DateUtil.parse(dataMonth, "yyyy-MM-dd HH:mm:ss");
            }
        }
        String deptName = null;
        if (projectEntity.getDeptId()!=null){
            Dept dept = deptDao.selectById(projectEntity.getDeptId());
            deptName = dept==null?null:dept.getDeptName();
        }

        if (projectDocFile.getMonth() != null) {
            list = trialProdDao.chooseGetTrial(projectEntity.getCompanyId(), projectEntity.getId(), projectDocFile.getMonth());
        } else if (month != null) {
            list = trialProdDao.chooseGetTrial(projectEntity.getCompanyId(), projectEntity.getId(), month);
        } else {
            list = trialProdDao.queryDocFileTrial(projectEntity.getCompanyId(), Arrays.asList(dataModel.getpDocFileId()), projectEntity.getId());
        }
        //2021/6/15  验证报告地点数据来源项目的部门/车间/产线/工艺段
        String place = ToolUtils.getProjectPlace(projectEntity);
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
                if(StringUtils.hasLength(trialProdSumarryModel.getTrialProduct())){
                    hasTrialProduct = true;
                }
                model.setIndex(i + 1);
                model.setTrialProduct(trialProdSumarryModel.getTrialProduct());
                model.setTrialDate(date);
                model.setActualPO(trialProdSumarryModel.getRdYield());
                // model.setPlace(trialProdSumarryModel.getDeptName());
                if (deptName!=null){
                    model.setPlace(deptName);
                }else {
                    model.setPlace(place);
                }
                Date startTime = trialProdSumarryModel.getStartTime();
                Date endTime = trialProdSumarryModel.getEndTime();
                model.setUnit(trialProdSumarryModel.getUnit());
                model.setStartTime(startTime != null ? DateUtil.format(startTime, "HH:mm:ss") : null);
                model.setEndTime(endTime != null ? DateUtil.format(endTime, "HH:mm:ss") : null);
                importDocTrialModels.add(model);
            }
        }
        dataMap.put("hasTrialProduct",hasTrialProduct);
        dataMap.put("list", importDocTrialModels);
        return dataMap;
    }
}
