package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.*;
import com.yskc.rs.entity.DocFileEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.SysDictionaryEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.DocFileInfoModel;
import com.yskc.rs.models.docFile.DocFileModel;
import com.yskc.rs.models.docFile.MeetingSignInModel;
import com.yskc.rs.models.docFile.ReceptionModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.hightechprogress.HighTechFileModel;
import com.yskc.rs.models.hightechprogress.QueryHighTechFileModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.project.ProjectModel;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.service.ProjectDocFileService;
import com.yskc.rs.service.exportFileImpl.PDataListForm;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectDocFileServiceImpl implements ProjectDocFileService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DocFileTrialDao docFileTrialDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private DocFileDao docFileDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Boolean addDocList(UserInfo info, ProjectDocFileModel model) throws OwnerException {
        commonService.checkProjectState(model.getProjectId());
        ProjectModel projectModel = stageDao.getBeginDate(model.getProjectId(), model.getStage());
        List<Integer> nrDocList = docFileDao.getNRDocList();
        if (null == projectModel) {
            throw new OwnerException("操作失败，未获取到项目。");
        }
        List<DocFileModel> docList = model.getDocList();
        if(CollectionUtils.isEmpty(docList)){
            throw new OwnerException("请选择要添加的文档！");
        }
        docList.sort(Comparator.comparing(DocFileModel::getOrder));
        ProjectEntity project=projectDao.selectById(model.getProjectId());
        List<ProjectDocFileEntity> entities = new ArrayList<>();
        List<ProjectDocFileEntity> updateList = new ArrayList<>();
        List<Integer> docIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(model.getpDocFileIds())) {
            addDocFiles(entities, updateList, model, info);
            docIds=docList.stream().map(e->e.getId()).distinct().collect(Collectors.toList());
        } else {
            Integer maxSeq = projectDocFileDao.getMaxSeq(model.getProjectId(), model.getStage());
            Integer seq = maxSeq != null ? maxSeq : 0;
            for (DocFileModel doc : docList) {
                Integer docNum = doc.getDocNum();
                for (int i = 0; i < docNum; i++) {
                    seq += 1;
                    ProjectDocFileEntity entity = new ProjectDocFileEntity(model.getProjectId(), model.getStage(), doc.getId(), doc.getDocName(), new Date(),
                            info, seq, doc.getDocFileTemplateId(), null);
                    entities.add(entity);
                    docIds.add(doc.getId());
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(entities)) {
                if(!CollectionUtils.isEmpty(docIds)) {
                    filterRepeatAdd(project, docIds, model.getStage(), null);
                }
                entities.forEach(item->{
                    if (nrDocList.contains(item.getDocFileId())){
                        item.setFinished(true);
                    }else {
                        item.setFinished(false);
                    }
                });
                projectDocFileDao.insertList(entities);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                projectDocFileDao.updateSeq(updateList);
            }
            TransactionUtils.commit(transactionStatus);
        }catch (OwnerException own) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addDocList", own);
            throw  own;
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addDocList", ex);
        }
        return true;
    }

    /**
     * 选择文档后插入
     *
     * @param entities
     * @param updateList
     * @param model
     * @param userInfo
     */
    private void addDocFiles(List<ProjectDocFileEntity> entities, List<ProjectDocFileEntity> updateList,
                             ProjectDocFileModel model, UserInfo userInfo) throws OwnerException {
        List<ProjectDocFileEntity> docFiles = projectDocFileDao.getDocsByStage(model.getStage(), model.getProjectId());
        if (CollectionUtils.isEmpty(docFiles)) {
            throw new OwnerException("阶段不存在过程文件或已删除,操作失败！");
        }
        List<Integer> docIds = model.getDocList().stream().map(e -> e.getId()).distinct().collect(Collectors.toList());
        Map<Integer,Integer> templateMap=model.getDocList().stream().collect(Collectors.toMap(e->e.getId(),e->e.getDocFileTemplateId()));
        List<DocFileEntity> docFileEntities = docFileDao.selectBatchIds(docIds);
        int seq = 0;
        Map<Integer,Boolean> existFileMap=new HashMap<>();
        Map<Integer, Boolean> addMap = new HashMap<>();
        for (ProjectDocFileEntity doc : docFiles) {
            seq += 1;
            if (!doc.getSeq().equals(seq)) {
                doc.setSeq(seq);
                updateList.add(doc);
            }
            if(!existFileMap.containsKey(doc.getDocFileId())){
                existFileMap.put(doc.getDocFileId(),true);
            }
            if (model.getpDocFileIds().contains(doc.getId())) {
                for (DocFileEntity file : docFileEntities) {
                    if(!file.isMultiple() && existFileMap.containsKey(file.getId())){
                        throw new OwnerException(file.getDocName()+"已存在于该阶段，不能重复添加！");
                    }
                    if (!addMap.containsKey(file.getId())) {
                        seq += 1;
                        ProjectDocFileEntity entity = new ProjectDocFileEntity(model.getProjectId(), model.getStage(), file.getId(), file.getDocName(), new Date(),
                                userInfo, seq, templateMap.get(file.getId()), null);
                        entities.add(entity);
                    }
                    if (!file.isMultiple() && !addMap.containsKey(file.getId())) {
                        addMap.put(file.getId(), true);
                    }
                }
            }

        }

    }

    /**
     * 过滤不能重复添加的文档
     * @param project
     * @param docIds
     * @throws OwnerException
     */
    @Override
    public void filterRepeatAdd(ProjectEntity project, List<Integer> docIds, String stageKey,Integer year) throws OwnerException {
        Integer projectId=project.getId();
        List<DocFileEntity> docs=docFileDao.selectBatchIds(docIds);
         Map<Integer,List<DocFileEntity>> docMap=new HashMap<>();
        for (DocFileEntity doc:docs){
            if(!docMap.containsKey(doc.getUniqueness())){
                List<DocFileEntity> list=new ArrayList<>();
                docMap.put(doc.getUniqueness(),list);
            }
            docMap.get(doc.getUniqueness()).add(doc);
        }
        //0 不限制 1阶段唯一 2 月份唯一 3年唯一 4项目唯一
        //String forMat="过程文件：{0}在【{1}】中已添加，不能再次添加！";
        StageEntity stageEntity =null;
        if(!StringUtils.isEmpty(stageKey)) {
            stageEntity=stageDao.queryEntityByKey(projectId, stageKey);
        }
        if(year==null && stageEntity!=null && stageEntity.getBeginDate()!=null) {
           year = DateUtil.year(stageEntity.getBeginDate());
        }
        for (Integer key:docMap.keySet()){
            List<DocFileEntity> datas=docMap.get(key);
            List<Integer> docFileIds=datas.stream().map(e->e.getId()).distinct().collect(Collectors.toList());
            if(key==1 && stageEntity!=null){
               List<ProjectDocFileEntity> existDocFiles=projectDocFileDao.getDocFileByStage(docFileIds,stageKey,projectId,null);
               if(!CollectionUtils.isEmpty(existDocFiles)){
                   ProjectDocFileEntity docFileEntity=existDocFiles.get(0);
                   SysDictionaryEntity sysDictionary=sysDictionaryDao.getByKey(stageEntity.getStageKey());
                   throw new OwnerException(MessageFormat.format("{0}{1}阶段已存在【{2}】,操作失败！",project.getRdTitle(),sysDictionary.getValue(),docFileEntity.getDocFileName()));
               }
            }else if(key==3 && year!=null){
                List<ProjectDocFileEntity> yearDocFiles=projectDocFileDao.getDocFileByYear(docFileIds,projectId,year,null);
                if(!CollectionUtils.isEmpty(yearDocFiles)){
                    ProjectDocFileEntity docFileEntity=yearDocFiles.get(0);
                    throw new OwnerException(MessageFormat.format("{0}{1,number,#}年已存在【{2}】,操作失败！",project.getRdTitle(),year,docFileEntity.getDocFileName()));
                }
                List<ProjectDocFileEntity> existDocFiles=projectDocFileDao.getDocByYear(docFileIds,projectId,year,null);
                if(!CollectionUtils.isEmpty(existDocFiles)){
                    ProjectDocFileEntity docFileEntity=existDocFiles.get(0);
                    throw new OwnerException(MessageFormat.format("{0}{1,number,#}年已存在【{2}】,操作失败！",project.getRdTitle(),year,docFileEntity.getDocFileName()));
                }
            }else if(key==4){
                List<ProjectDocFileEntity> existDocFiles=projectDocFileDao.getDocFileByStage(docFileIds,null,projectId,null);
                if(!CollectionUtils.isEmpty(existDocFiles)){
                    ProjectDocFileEntity docFileEntity=existDocFiles.get(0);
                    throw new OwnerException(MessageFormat.format("{0}已存在【{1}】,操作失败！",project.getRdTitle(),docFileEntity.getDocFileName()));
                }
            }
        }
//        List<Integer> ids = docFileDao.getDocList(stageKey);
//        SysDictionaryEntity dictionary = sysDictionaryDao.getByKey(stageKey);
//        List<ProjectDocFileEntity> docFiles = projectDocFileDao.getByDocIds(ids, projectId, stageKey);
//        Map<Integer, ProjectDocFileEntity> docFileMap = docFiles.stream().collect(Collectors.toMap(e -> e.getDocFileId(), e -> e, (o, n) -> n));
//        for (Integer docId : docIds) {
//            if (docFileMap.containsKey(docId)) {
//                ProjectDocFileEntity docFileEntity = docFileMap.get(docId);
//                throw new OwnerException(MessageFormat.format("{0}已存在{1}中，不能重复添加!请联系管理员", docFileEntity.getDocFileName(), dictionary.getValue()));
//            }
//        }
    }
    @Override
    public void limitDocFileOnly(ProjectDocFileEntity docFileEntity,Integer projectId,Date month)throws OwnerException{
        DocFileEntity doc = docFileDao.selectById(docFileEntity.getDocFileId());
        ProjectEntity project=projectDao.selectById(projectId);
        StageEntity stage=null;
        if(!StringUtils.isEmpty(docFileEntity.getStage())){
            stage=stageDao.queryEntityByKey(projectId,docFileEntity.getStage());
        }
        switch (doc.getUniqueness()) {
            case 1:
                if(stage!=null) {
                    List<ProjectDocFileEntity> stageExistFiles = projectDocFileDao.getDocFileByStage(Arrays.asList(docFileEntity.getDocFileId()), docFileEntity.getStage(),
                            projectId, docFileEntity.getId());
                    if (!CollectionUtils.isEmpty(stageExistFiles)) {
                        SysDictionaryEntity sysDictionary=sysDictionaryDao.getByKey(stage.getStageKey());
                        throw new OwnerException(MessageFormat.format("{0}{1}阶段已存在【{2}】,操作失败！",project.getRdTitle(),sysDictionary.getValue(),doc.getDocName()));
                    }
                }
                break;
            case 2:
                if (month != null) {
                    Date beginMonth = cn.hutool.core.date.DateUtil.beginOfMonth(month);
                    Date endMonth = cn.hutool.core.date.DateUtil.endOfMonth(month);
                    List<ProjectDocFileEntity> monthExistFiles=projectDocFileDao.getDocFileByMonth(projectId,beginMonth,endMonth,docFileEntity.getDocFileId(),docFileEntity.getId());
                    if (!CollectionUtils.isEmpty(monthExistFiles)) {
                        String monthStr= cn.hutool.core.date.DateUtil.format(beginMonth,"yyyy年MM月");
                        throw new OwnerException(MessageFormat.format("{0}{1}已存在【{2}】,操作失败！",project.getRdTitle(),monthStr,doc.getDocName()));
                    }
                }
                break;
            case 3:
                Integer year=null;
                if(month!=null){
                    year= cn.hutool.core.date.DateUtil.year(docFileEntity.getMonth());
                }else if(stage!=null && stage.getBeginDate()!=null) {
                    year=cn.hutool.core.date.DateUtil.year(stage.getBeginDate());
                }
                if(year!=null) {
                    List<ProjectDocFileEntity> yearDocFiles = projectDocFileDao.getDocFileByYear(Arrays.asList(docFileEntity.getDocFileId()), projectId, year, docFileEntity.getId());
                    if (!CollectionUtils.isEmpty(yearDocFiles)) {
                        throw new OwnerException(MessageFormat.format("{0}{1,number,#}年已存在【{2}】,操作失败！", project.getRdTitle(), year,doc.getDocName()));
                    }
                    List<ProjectDocFileEntity> existDocFiles = projectDocFileDao.getDocByYear(Arrays.asList(docFileEntity.getDocFileId()), projectId, year, docFileEntity.getId());
                    if (!CollectionUtils.isEmpty(existDocFiles)) {
                        throw new OwnerException(MessageFormat.format("{0}{1,number,#}年已存在【{2}】,操作失败！",project.getRdTitle(), year,doc.getDocName()));
                    }
                }
                break;
            case 4:
                List<ProjectDocFileEntity> projectExistFiles=projectDocFileDao.getDocFileByStage(Arrays.asList(docFileEntity.getDocFileId()),null,projectId,docFileEntity.getId());
                if(!CollectionUtils.isEmpty(projectExistFiles)){
                    throw new OwnerException(MessageFormat.format("{0}已存在【{1}】,操作失败！",project.getRdTitle(),doc.getDocName()));
                }
                break;
            default:
                break;
        }
    }



    @Override
    public List<StageModel> queryStage(UserInfo info, Integer projectId, int year) throws OwnerException {
        Integer companyId = info.getCompanyId();
        List<StageModel> queryModels = stageDao.queryStage(companyId, projectId);
        if (CollectionUtil.isEmpty(queryModels)) {
            return new ArrayList<>();
        }
        List<String> stageKeys = new ArrayList<>();
        Map<String, StageModel> stageMap = new LinkedHashMap<>();
        for (StageModel stage : queryModels) {
            stageKeys.add(stage.getStageKey());
            stage.setProjectDocList(new ArrayList<>());
            stageMap.put(stage.getStageKey(), stage);
        }
        List<ProjectDocFileModel> projectDocFileModels = projectDocFileDao.selectByStageList(projectId, stageKeys);
        for (ProjectDocFileModel docModel : projectDocFileModels) {
            if (docModel.getLastUpdateTime() != null) {
                docModel.setNeedEdit(false);
            }
            StageModel model = stageMap.get(docModel.getStage());
            if (docModel.getLastUpdateTime() == null) {
                docModel.setLastUpdateTime(docModel.getCreateTime());
            }
            model.getProjectDocList().add(docModel);
        }
        return new ArrayList<>(stageMap.values());
    }

    @Override
    public boolean delete(ProjectDocFileModel model, UserInfo userInfo) throws OwnerException {
        commonService.checkDocModify(userInfo.getUserSource(), model.getProjectId(), model.getDocFileId());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            docFileTrialDao.delByDocFileId(model.getId(), userInfo.getCompanyId());
            projectDocFileDao.updateDataStatus(Arrays.asList(model.getId()), new Date(), userInfo.getUserId(), userInfo.getMsUserId());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
        }
        return true;
    }

    @Override
    public boolean deleteDocFiles(List<Integer> ids, UserInfo userInfo) throws OwnerException {
        if(CollectionUtils.isEmpty(ids)){
            throw new OwnerException("请选择要删除的过程文档！");
        }
        List<DocFileInfoModel> docFiles=projectDocFileDao.getFileDetail(ids);
        List<Integer> delIds=new ArrayList<>();
        for(DocFileInfoModel model:docFiles){
            if(model.getDeleted()){
                throw new OwnerException("过程文件："+model.getDocFileName()+"已删除，删除失败！");
            }
            if(userInfo.getUserSource()==1){
                if(model.getHasSubmit()!=2 && model.getHasSubmit()!=3 && model.getHasSubmit()!=5){
                    throw new OwnerException("过程文件："+model.getDocFileName()+"已提交审核，不能删除！");
                }
            }
            if(!StringUtils.isEmpty(model.getData())){
                throw new OwnerException("过程文件："+model.getDocFileName()+"已保存数据，不能批量删除！");
            }
            delIds.add(model.getId());
        }
        if(CollectionUtils.isEmpty(delIds)){
            return true;
        }
        return projectDocFileDao.updateDataStatus(delIds, new Date(), userInfo.getUserId(), userInfo.getMsUserId());
    }

    @Override
    public List<EmployeeSelectModel> getList(Integer companyId, Integer year ,String ename) {
        return projectDao.getList(companyId,year,ename);
    }

    @Override
    public List<HighTechFileModel> getHighTechFiles(QueryHighTechFileModel model) {
        return projectDocFileDao.getHighTechFiles(model);
    }

    @Override
    public boolean editDocFileName(UserInfo userInfo, ProjectDocFileEntity entity) throws OwnerException {
        commonService.checkDocModify(userInfo.getUserSource(), entity.getProjectId(), entity.getId());
        Date date = new Date();
        ProjectDocFileEntity source = projectDocFileDao.selectById(entity.getId());
        source.setDocFileName(entity.getDocFileName());
        source.setLastUpdatorId(userInfo.getUserId());
        source.setMsLastUpdatorId(userInfo.getMsUserId());
        source.setLastUpdateTime(date);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (entity.getSeq() != null) {
                Integer maxSeq = projectDocFileDao.getMaxSeq(entity.getProjectId(), entity.getStage());
                if (entity.getSeq() < 1 || entity.getSeq() > maxSeq) {
                    return true;
                }
                ProjectDocFileEntity target = projectDocFileDao.getFileByOrder(entity.getProjectId(), entity.getStage(), entity.getSeq());
                if (target != null) {
                    target.setLastUpdateTime(date);
                    target.setLastUpdatorId(userInfo.getUserId());
                    target.setSeq(source.getSeq());
                    projectDocFileDao.updateById(target);
                }
                source.setSeq(entity.getSeq());
            }
            projectDocFileDao.updateById(source);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("updateProject", ex);
        }
        return true;
    }

    @Override
    public List<StageModel> queryFileList(Integer projectId, int year) {
        PDataListForm pDataListForm = new PDataListForm();
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        List<StageModel> models = pDataListForm.getDocFileList(projectEntity,null);
        return models;
    }

    @Override
    public List<EmployeeSelectModel> getMeetingEmployee(Integer projectId, int companyId, Date docDate, Integer pDocFileId) {
        Integer year;
        if (docDate != null) {
            year = DateUtil.year(docDate);
        } else {
            StageEntity stageEntity = stageDao.getByDocFile(projectId, pDocFileId);
            if (stageEntity.getBeginDate() != null) {
                year = DateUtil.year(stageEntity.getBeginDate());
            } else {
                ProjectEntity project = projectDao.selectById(projectId);
                year = DateUtil.year(project.getBeginDate());
            }
        }

        List<EmployeeSelectModel> list = projectDao.getMeetingEmployees(projectId, companyId, year, DateUtil.beginOfMonth(docDate));
        return list;
    }

    @Override
    public List<EmployeeSelectModel> getAuditor(Integer projectId, Integer companyId, Date docDate, Integer pDocFileId) {
        Integer year;
        if (docDate != null) {
            year = DateUtil.year(docDate);
        } else {
            StageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
            if (stage != null && stage.getBeginDate() != null) {
                year = DateUtil.year(stage.getBeginDate());
            } else {
                ProjectEntity projectEntity = projectDao.selectById(projectId);
                year = projectEntity.getBeginYear();
            }
        }
        List<EmployeeSelectModel> list = projectDao.getAuditors(projectId, companyId, year, docDate);
        return list;
    }

    @Override
    public List<ReceptionModel> getReception(Integer projectId,int id) {
        List<ReceptionModel> receptions = projectDocFileDao.getReceptions(projectId, id);
        if (CollectionUtils.isEmpty(receptions)) {
            return new ArrayList<>();
        }
        return receptions;
    }

    @Override
    public List<MeetingSignInModel> getMeeting(Integer projectId, Integer companyId, Integer year) throws Exception {
        List<MeetingSignInModel> meeting = projectDocFileDao.getMeeting(projectId);
        if (CollectionUtils.isEmpty(meeting)){
            return new ArrayList<>();
        }
        return meeting;
    }

    @Override
    public List<StageModel> getReportStage(Integer projectId, Integer companyId) {
        List<StageModel> queryModels = stageDao.queryStage(companyId, projectId);
        return queryModels;
    }
}
