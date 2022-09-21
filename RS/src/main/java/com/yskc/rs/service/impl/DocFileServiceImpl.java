package com.yskc.rs.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.dao.project.AuditReportDao;
import com.yskc.rs.dao.project.ProjectDocFileDataDao;
import com.yskc.rs.entity.MeetMemberEntity;
import com.yskc.rs.entity.project.ProjectDocFileDataEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.*;
import com.yskc.rs.models.init.member.InitMemberModel;
import com.yskc.rs.models.trialprod.StageTrialModel;
import com.yskc.rs.service.DocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocFileServiceImpl implements DocFileService {

    @Autowired
    private DocFileDao docFileDao;
    @Autowired
    private DocListDao docListDao;
    @Autowired
    private AuditReportDao auditReportDao;
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private TrialProdDao trialProdDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private DocFileTemplateDao docFileTemplateDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private MeetMemberDao meetMemberDao;

    @Override
    public List<DocFileModel> queryDocFile(String stage) {
        List<DocFileModel> docFileModels = docFileDao.queryDocFile(stage);
        if(CollectionUtils.isEmpty(docFileModels)){
            return new ArrayList<>();
        }
        List<Integer> ids = docFileModels.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(DocFileModel::getId).collect(Collectors.toList());
        List<DocFileTemplateModel> docFileTemplateModels = docFileDao.queryTplByIds(ids);
        for (DocFileModel dModel : docFileModels) {
            for (DocFileTemplateModel tModel : docFileTemplateModels) {
                if (tModel.getDocFileId().equals(dModel.getId())) {
                    List<DocFileTemplateModel> docTplList = dModel.getDocTplList();
                    if (docTplList != null) {
                        docTplList.add(tModel);
                    } else {
                        docTplList = new ArrayList<>();
                        docTplList.add(tModel);
                    }
                    dModel.setDocTplList(docTplList);
                }
            }
        }

        return docFileModels;
    }

    @Override
    public PageModel<List<NewReportsModel>> getNewReports(QueryReportsModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        List<NewReportsModel> models = docListDao.getDocList(page, query.getProjectId());
        return PageUtils.build(page, models);
    }

    @Override
    public List<NewReportsModel> getProjectReports(Integer projectId) {
        List<NewReportsModel> models = docListDao.getReports(projectId);
        return models;
    }

    @Override
    public String getMeetMember(Integer projectId) {
        MeetMemberEntity memberEntity=meetMemberDao.getByProject(projectId);
        return null==memberEntity?"":memberEntity.getMembers();

    }

    @Override
    public Boolean setMeetMember(MeetMemberEntity model, UserInfo userInfo) throws OwnerException{
        if(null==model && null==model.getProjectId()){
            throw new OwnerException("数据异常，请联系管理员！");
        }
        MeetMemberEntity entity=meetMemberDao.getByProject(model.getProjectId());
        Date date=new Date();
        if(null==entity){
            MeetMemberEntity memberEntity=new MeetMemberEntity();
            memberEntity.setProjectId(model.getProjectId());
            memberEntity.setMembers(model.getMembers());
            memberEntity.create(userInfo.getUserId(),userInfo.getMsUserId(),date);
           return meetMemberDao.insert(memberEntity)>0;
        }else {
            entity.setMembers(model.getMembers());
            entity.update(userInfo.getUserId(),userInfo.getMsUserId(),date);
            return meetMemberDao.updateById(entity)>0;
        }
    }


    @Override
    public List<TissueFormModel> importReportData(Integer projectId, Integer companyId) {
        List<TissueFormModel> models = new ArrayList<>();
        List<ProjectDocFileEntity> docFiles = projectDocFileDataDao.getDesignReportData(projectId);
        Map<Integer, Date> docMonthMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(docFiles)) {
            List<Integer> docFileIds = new ArrayList<>();
            List<Date> monthes = new ArrayList<>();
            for (ProjectDocFileEntity docFile : docFiles) {
                docFileIds.add(docFile.getId());
                if (docFile.getMonth() != null) {
                    monthes.add(docFile.getMonth());
                    docMonthMap.put(docFile.getId(), docFile.getMonth());
                }
            }
            List<ProjectDocFileDataEntity> docFileDatas = projectDocFileDataDao.getDocFiles(docFileIds);
            Map<Integer, Map<String, Object>> map = new HashMap<>();
            if (!CollectionUtils.isEmpty(docFileDatas)) {
                for (ProjectDocFileDataEntity docFileData : docFileDatas) {
                    if (!StringUtils.isEmpty(docFileData.getData())) {
                        Map<String, Object> dataMap = JsonUtils.jsonToPojo(docFileData.getData(), Map.class);
                        if (dataMap.containsKey("month")) {
                            Date month = DateUtil.parse(dataMap.get("month").toString(), "yyyy-MM");
                            monthes.add(DateUtil.beginOfMonth(month));
                            if (!docMonthMap.containsKey(docFileData.getpDocFileId())) {
                                docMonthMap.put(docFileData.getpDocFileId(), DateUtil.beginOfMonth(month));
                            }
                        }
                        map.put(docFileData.getpDocFileId(), dataMap);
                    }
                }
            }
            if (CollectionUtils.isEmpty(monthes)) {
                return models;
            }
            List<StageTrialModel> list = trialProdDao.getTrials(companyId, monthes, projectId);
            List<InitMemberModel> initMemberModels = initMemberDao.getInitMemberByType(projectId);
            Map<Integer, Boolean> usedMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(list)) {
                Map<Date, List<StageTrialModel>> dataModelMap = new HashMap<>();
                for (StageTrialModel model : list) {
                    if (!dataModelMap.containsKey(model.getTrialMonth())) {
                        List<StageTrialModel> trialModels = new ArrayList<>();
                        dataModelMap.put(model.getTrialMonth(), trialModels);
                    }
                    dataModelMap.get(model.getTrialMonth()).add(model);
                }
                for (ProjectDocFileEntity docFile : docFiles) {
                    if (!docMonthMap.containsKey(docFile.getId())) {
                        continue;
                    }
                    Date dataMonth = docMonthMap.get(docFile.getId());
                    if (!dataModelMap.containsKey(dataMonth)) {
                        continue;
                    }
                    List<StageTrialModel> modelList = dataModelMap.get(dataMonth);
                    for (StageTrialModel model : modelList) {
                        TissueFormModel tissueFormModel = new TissueFormModel();
                        tissueFormModel.setActualPO(model.getRdYield().stripTrailingZeros().toPlainString() + model.getUnit());
                        tissueFormModel.setAddr(model.getDeptName());
                        tissueFormModel.setBeginDate(model.getTrialDate());
                        if (map.containsKey(docFile.getId())) {
                            if (!usedMap.containsKey(docFile.getId())) {
                                Map<String, Object> praMap = map.get(docFile.getId());
                                if (praMap.containsKey("existingProblems")) {
                                    tissueFormModel.setProblem(praMap.get("existingProblems").toString());
                                }
                                if (praMap.containsKey("proposal")) {
                                    tissueFormModel.setSolution(praMap.get("proposal").toString());
                                }
                                usedMap.put(docFile.getId(), true);
                            }
                        }
                        if (!CollectionUtils.isEmpty(initMemberModels)) {
                            Random random = new Random();
                            int index = random.nextInt(initMemberModels.size());
                            InitMemberModel initMemberModel = initMemberModels.get(index);
                            tissueFormModel.setEname(initMemberModel.getEname());
                            tissueFormModel.setEnumber(initMemberModel.getEnumber());
                        }
                        models.add(tissueFormModel);
                    }
                }

            }
        }
        return models;
    }
}
