package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectMemberDao;
import com.yskc.ms.dao.ms.ProjectSummaryDataDao;
import com.yskc.ms.dao.rs.RsDeptDao;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.projectsummary.ProjectSummaryModel;
import com.yskc.ms.models.projectsummary.QuerySummaryModel;
import com.yskc.ms.models.projectsummary.SummaryDataModel;
import com.yskc.ms.service.ms.SummaryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/11/10 11:45
 * description:
 */
@Service
public class SummaryDataServiceImpl implements SummaryDataService {

    @Autowired
    private ProjectSummaryDataDao summaryDataDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;
    @Autowired
    private RsDeptDao rsDeptDao;

    @Override
    public PageModel<List<ProjectSummaryModel>> getList(QuerySummaryModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        String keyFormat = "{0}_{1}";
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("pj.lastUpdateTime");
        }
        List<ProjectSummaryModel> list=summaryDataDao.getHighTechData(page,dataPerm,query);
        List<ProjectProgressModel> progressModels=new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            List<Integer> projectIds=new ArrayList<>();
            for (ProjectSummaryModel model:list){
                projectIds.add(model.getProjectId());
                ProjectProgressModel progressModel=new ProjectProgressModel();
                progressModel.setYear(model.getYear());
                progressModel.setCompanyId(model.getCompanyId());
                progressModels.add(progressModel);
            }
            Map<String, List<String>> mapMember = new HashMap<>();
            List<ProjectMemberModel> projectMemberList = projectMemberDao.queryMemberByIds(projectIds);
            for (ProjectMemberModel pmm :
                    projectMemberList) {
                if (pmm.getRealName() == null)
                    continue;
                String k = MessageFormat.format(keyFormat, pmm.getProjectId(), pmm.getmType());
                if (mapMember.get(k) == null) {
                    mapMember.put(k, new ArrayList<>());
                }
                mapMember.get(k).add(pmm.getRealName());
            }

            List<SummaryDataModel> models=summaryDataDao.getByCustomerYear(list);
            List<ProjectProgressModel> rdDeptLevels = rsDeptDao.getRdDeptLevels(progressModels);
            Map<String, Integer> levelMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(rdDeptLevels)) {
                rdDeptLevels.forEach(item -> {
                    levelMap.put(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()), item.getRdDeptLevel());
                });
            }
            Map<String,SummaryDataModel> dataModelMap=new HashMap<>();
            for (SummaryDataModel model:models) {
                String key= MessageFormat.format(keyFormat,model.getCustomerId(),model.getYear());
                if(!dataModelMap.containsKey(key)){
                    dataModelMap.put(key,model);
                }
            }
            for (ProjectSummaryModel summaryModel:list) {
                List<SummaryDataModel> modelList=new ArrayList<>();
                for (int i=1;i<4;i++){
                    Integer year=summaryModel.getYear()-i;
                    String sign=MessageFormat.format(keyFormat,summaryModel.getCustomerId(),year);
                    SummaryDataModel summaryDataModel=new SummaryDataModel();
                    if(dataModelMap.containsKey(sign)){
                        summaryDataModel=dataModelMap.get(sign);
                        String dk = MessageFormat.format(keyFormat, summaryDataModel.getCompanyId(), summaryDataModel.getYear());
                        if (levelMap.containsKey(dk)) {
                            summaryDataModel.setRdDeptLevel(levelMap.get(dk));
                        }
                    }else{
                        summaryDataModel.setYear(year);
                    }
                    modelList.add(summaryDataModel);

                }
                summaryModel.setSummaryData(modelList);
                // 技术人员
                String tk = MessageFormat.format(keyFormat, summaryModel.getProjectId(), MemberTypeEnum.Tech.getId());
                // 财务人员
                String fk = MessageFormat.format(keyFormat, summaryModel.getProjectId(), MemberTypeEnum.FINANCE.getId());

                if (mapMember.get(tk) != null) {
                    summaryModel.setTechRealName(String.join(",", mapMember.get(tk)));
                }
                if (mapMember.get(fk) != null) {
                    summaryModel.setFinanceRealName(String.join(",", mapMember.get(fk)));
                }

            }
        }
        return PageUtils.build(page, list);
    }
}
