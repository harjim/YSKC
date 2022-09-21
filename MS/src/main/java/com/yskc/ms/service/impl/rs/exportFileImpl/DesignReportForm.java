package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.dao.rs.RsDeptDao;
import com.yskc.ms.dao.rs.TrialProdDao;
import com.yskc.ms.entity.rs.Dept;
import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.ImportDocTrialModel;
import com.yskc.ms.models.rs.StageTrialModel;
import com.yskc.ms.service.rs.ExportDocFileService;
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
    private RsDeptDao deptDao;

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
        String deptName = null;
        if (projectEntity.getDeptId()!=null){
            Dept dept = deptDao.selectById(projectEntity.getDeptId());
            deptName = dept==null?null:dept.getDeptName();
        }
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            if (dataMap.containsKey("month")) {
                String dataMonth = dataMap.get("month").toString() + "-01 00:00:00";
                month = DateUtil.parse(dataMonth, "yyyy-MM-dd HH:mm:ss");
            }
        }
        //2021/6/15  验证报告地点数据来源项目的部门/车间/产线/工艺段
        List<String> strs = new ArrayList<>();
        if(!StringUtils.isEmpty(projectEntity.getDeptName()) && !Objects.equals(projectEntity.getDeptName(),"/")){
            strs.add(projectEntity.getDeptName());
        }
        if(!StringUtils.isEmpty(projectEntity.getWorkshop()) && !Objects.equals(projectEntity.getWorkshop(),"/")){
            strs.add(projectEntity.getWorkshop());
        }
        if(!StringUtils.isEmpty(projectEntity.getProductLine()) && !Objects.equals(projectEntity.getProductLine(),"/")){
            strs.add(projectEntity.getProductLine());
        }
        if(!StringUtils.isEmpty(projectEntity.getProcessSection()) && !Objects.equals(projectEntity.getProcessSection(),"/")){
            strs.add(projectEntity.getProcessSection());
        }
        String place = CollectionUtils.isEmpty(strs) ? "" : org.apache.commons.lang3.StringUtils.join(strs, "/");
        if (projectDocFile.getMonth() != null) {
            list = trialProdDao.getTrialByMonth(projectEntity.getCompanyId(), projectEntity.getId(), projectDocFile.getMonth());
        } else if (month != null) {
            list = trialProdDao.getTrialByMonth(projectEntity.getCompanyId(), projectEntity.getId(), month);
        } else {
            list = trialProdDao.queryDocFileTrial(projectEntity.getCompanyId(), dataModel.getpDocFileId(), projectEntity.getId());
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
                if(StringUtils.hasLength(trialProdSumarryModel.getTrialProduct())){
                    hasTrialProduct = true;
                }
                model.setIndex(i + 1);
                model.setTrialProduct(trialProdSumarryModel.getTrialProduct());
                model.setTrialDate(date);
                model.setActualPO(trialProdSumarryModel.getRdYield());
                //model.setPlace(trialProdSumarryModel.getDeptName());
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

//    @Override
//    public Map<String, Object> exportDocFile1(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
//        Map<String, Object> dataMap = new HashMap<>();
//        List<StageTrialModel> list = designReportForm.trialProdDao.queryDocFileTrial(projectEntity.getCompanyId(), dataModel.getpDocFileId(), projectEntity.getId());
//        List<ImportDocTrialModel> importDocTrialModels = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(list)) {
//            String date = "";
//            for (int i = 0; i < list.size(); i++) {
//                StageTrialModel trialProdSumarryModel = list.get(i);
//                ImportDocTrialModel model = new ImportDocTrialModel();
//                Date trialDate = trialProdSumarryModel.getTrialDate();
//                if (trialDate != null) {
//                    date = DateUtil.format(trialDate, "yyyy-MM-dd");
//                }
//                model.setIndex(i + 1);
//                model.setTrialDate(date);
//                Date startTime = trialProdSumarryModel.getStartTime();
//                Date endTime = trialProdSumarryModel.getEndTime();
//                model.setStartTime(startTime != null ? DateUtil.format(startTime, "hh:mm:ss") : "");
//                model.setEndTime(endTime != null ? DateUtil.format(endTime, "hh:mm:ss") : "");
//                model.setActualPO(trialProdSumarryModel.getRdYield());
//                model.setPlace(trialProdSumarryModel.getDeptName());
//                importDocTrialModels.add(model);
//            }
//        }
//        if (!StringUtils.isEmpty(fileData)) {
//            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
//        }
//        dataMap.put("list", importDocTrialModels);
//        return dataMap;
//    }
}
