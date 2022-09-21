package com.yskc.ms.service.impl.rs;

import com.yskc.common.utils.TextUtils;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.models.rs.CheckModel;
import com.yskc.ms.models.rs.ReportCheckModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.service.rs.ProjectCheckService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectCheckServiceImpl implements ProjectCheckService {
    @Autowired
    RsProjectDao rsProjectDao;
    @Override
    public Map<Integer, List<CheckModel<RsProjectBaseModel>>> getDuplicateNameList(Integer[] ids, Double limitRate) {
        List<RsProjectBaseModel> projectList = rsProjectDao.getCheckProject();
        // 获取要查重的项目信息
        List<RsProjectBaseModel> sourceList = projectList.stream().parallel().filter(a-> ArrayUtils.contains(ids,a.getId())).collect(Collectors.toList());

        Set<String>[] projectWordSets = new Set[sourceList.size()];
        for (int i = 0; i < sourceList.size(); i++) {
            projectWordSets[i] = TextUtils.getWords(sourceList.get(i).getPname());
        }
        Map<Integer, List<CheckModel<RsProjectBaseModel>>> resultMap = new HashMap<>();
        for (RsProjectBaseModel pInfo:
             projectList) {
            Set<String> words = TextUtils.getWords(pInfo.getPname());
            for (int i = 0; i < projectWordSets.length; i++) {
                Integer pId =sourceList.get(i).getId();
                if (pId.equals(pInfo.getId()))
                    continue;
                double similarity = TextUtils.getTitleSimilarity(words,projectWordSets[i]);
                if (similarity>=limitRate){
                   //  System.out.println(projectNames[i] + "    "+pInfo.getPname()+"   "+similarity);
                    List<CheckModel<RsProjectBaseModel>> simProjectList = resultMap.get(pId);
                    if (simProjectList ==null){
                        simProjectList = new ArrayList<>();
                        resultMap.put(pId,simProjectList);
                    }
                    CheckModel<RsProjectBaseModel> checkModel = new CheckModel<>();
                    checkModel.setData(pInfo);
                    checkModel.setSimilarity(similarity);
                    simProjectList.add(checkModel);
                }
            }
        }
        return resultMap;
    }

    @Override
    public Map<Integer, List<CheckModel<ReportCheckModel>>> getDuplicateReportList(Integer[] docIds, Double limitRate) {
        List<ReportCheckModel> reportCheckModelList = rsProjectDao.getCheckReport();
        // 获取要查重的立项报告信息
        List<ReportCheckModel> sourceList = reportCheckModelList.stream().parallel().filter(a-> ArrayUtils.contains(docIds,a.getDocId())).collect(Collectors.toList());
        Map<Integer, List<CheckModel<ReportCheckModel>>> resultMap = new HashMap<>();

        for (ReportCheckModel reportCheckModel:
             reportCheckModelList) {
            for (ReportCheckModel source:
                 sourceList) {
                Integer docId = source.getDocId();
                 if (reportCheckModel.getDocId().equals(docId))
                     continue;
                 double similarity = TextUtils.getSimilarity(source.getSimCode(),reportCheckModel.getSimCode());

                 if (similarity>=limitRate){
                     List<CheckModel<ReportCheckModel>> simReportList = resultMap.get(docId);
                     if (simReportList ==null){
                         simReportList = new ArrayList<>();
                         resultMap.put(docId,simReportList);
                     }
                     CheckModel<ReportCheckModel> checkModel = new CheckModel<>();
                     checkModel.setSimilarity(similarity);
                     checkModel.setData(reportCheckModel);
                     simReportList.add(checkModel);
                 }

            }
        }

        return resultMap;
    }
}
