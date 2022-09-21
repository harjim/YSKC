package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.TrialProdDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.trialprod.StageTrialModel;
import com.yskc.rs.models.trialprod.StageTrialShowModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/16 11:14
 * description:
 */
@Component
public class TrialProductionDetailForm implements ExportDocFileService {

    @Autowired
    private TrialProdDao trialProdDao;

    private static TrialProductionDetailForm trialProductionDetailForm;


    @PostConstruct
    public void init() {
        trialProductionDetailForm = this;
        trialProductionDetailForm.trialProdDao = this.trialProdDao;
    }


    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(fileData)) {
            dataMap.put("fileList", new ArrayList<>());
        } else {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        dataMap.put("fileList", buildTableData(trialProductionDetailForm.trialProdDao.getTrialByProject(projectEntity.getId(), projectEntity.getCompanyId())));
        return dataMap;
    }

    /**
     * 构建列表数据
     *
     * @param trialModels
     * @return
     */
    public static List<StageTrialShowModel> buildTableData(List<StageTrialModel> trialModels) {
        List<StageTrialShowModel> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(trialModels)) {
            return result;
        }
        String timeFormat = "HH:mm";
        Date trialDate;
        for (StageTrialModel model : trialModels) {
            trialDate = model.getTrialDate();
            if (null == trialDate) {
                continue;
            }
            StageTrialShowModel showModel = new StageTrialShowModel();
            showModel.setWorkHours(new BigDecimal(model.getMinute()).abs().divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP));
            showModel.setMonth(DateUtil.format(trialDate, DateUtil.DEFAULT_YYMM_FORMAT));
            if (model.getStartTime() != null) {
                showModel.setStartTime(DateUtil.getTimeStr(model.getStartTime(), timeFormat));
            }
            if (model.getEndTime() != null) {
                showModel.setEndTime(DateUtil.getTimeStr(model.getEndTime(), timeFormat));
            }
            String timeStr = DateUtil.getTimeStr(trialDate, DateUtil.DEFAULT_DATE_FORMAT);
            showModel.setStartDate(timeStr);
            // 开始时间大于结束时间，结束日期向前推进一天
            if (DateUtil.compareStr(showModel.getStartTime(), showModel.getEndTime(), timeFormat) == 1) {
                showModel.setEndDate(DateUtil.getTimeStr(cn.hutool.core.date.DateUtil.offsetDay(trialDate, 1), DateUtil.DEFAULT_DATE_FORMAT));
            }else{
                showModel.setEndDate(timeStr);
            }
            showModel.setStatus("试制");
            showModel.setType("开机");
            result.add(showModel);
        }
        return result;
    }

}
