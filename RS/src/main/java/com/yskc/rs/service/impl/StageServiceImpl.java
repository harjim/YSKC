package com.yskc.rs.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.company.CompanyStageEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.DocFileStageModel;
import com.yskc.rs.models.excel.StageExcel;
import com.yskc.rs.models.stage.QueryStageModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.service.ProjectDocFileService;
import com.yskc.rs.service.StageService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class StageServiceImpl extends ServiceImpl<StageDao, StageEntity> implements StageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StageDao stageDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DocFileStageDao docFileStageDao;
    @Autowired
    private ProjectDocFileService projectDocFileService;
    @Autowired
    private DocFileDao docFileDao;

    @Override
    public List<StageModel> queryStage(Integer companyId, QueryStageModel queryStageModel) {

        return stageDao.queryStageList(companyId, queryStageModel);

    }

    @Override
    public List<Map<String, Object>> getCStage(Integer companyId) {
        return stageDao.getCStage(companyId);
    }

    @Override
    public List<CompanyStageEntity> getStage(Integer companyId) {

        return stageDao.getStageList(companyId);
    }

    @Override
    public boolean saveStage(Integer companyId, Integer projectId,
                             List<CompanyStageEntity> list, UserInfo userInfo,Boolean ChangeCStage) throws OwnerException {
        if (CollectionUtils.isEmpty(list)) {
            throw new OwnerException("数据不能为空！");
        }
        for (CompanyStageEntity entity : list) {
            entity.update(userInfo.getUserId(),userInfo.getMsUserId(),new Date());
        }
        Boolean isRight = true;
        if (ChangeCStage){
            Boolean isAdd = false;
            List<CompanyStageEntity> list1 = stageDao.getStageList(companyId);
            for (CompanyStageEntity model : list1) {
                if (companyId.equals(model.getCompanyId())){
                    isAdd = true;
                }
            }
            if (isAdd){
                isRight = stageDao.updateStage(companyId,list);
            }else {
                for (CompanyStageEntity entity : list1) {
                    entity.create(userInfo.getUserId(),userInfo.getMsUserId(),new Date());
                }
                stageDao.savaStage(companyId,list1);
                isRight = stageDao.updateStage(companyId,list);
            }
        }
        if (projectId!=0){
            return stageDao.updateProjectStage(companyId,list,projectId);
        }
        return isRight;
    }

    @Override
    public boolean addStage(UserInfo userInfo, StageModel model) throws OwnerException {
        Map<String, Boolean> typeList = model.getTypeList();
        List<String> queryList = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : typeList.entrySet()) {
            if (entry.getValue()) {
                queryList.add(entry.getKey());
            }
        }
        List<StageModel> stageModels = stageDao.queryStage(userInfo.getCompanyId(), model.getProjectId());
//        List<StageEntity> entities = stageDao.queryStageByTypeList(model.getProjectId());
        List<String> keyList = stageModels.stream().filter(a -> !StringUtils.isEmpty(a.getStageKey())).map(StageModel::getStageKey).collect(toList());


        List<String> addKey = queryList.stream().filter(item -> !keyList.contains(item)).collect(toList());
        List<String> delKey = keyList.stream().filter(item -> !queryList.contains(item)).collect(toList());

        Date nowDate = new Date();
        List<StageEntity> insertList = new ArrayList<>();
        for (String key : addKey) {
            StageEntity entity = new StageEntity();
            entity.setProjectId(model.getProjectId());
            entity.setStageKey(key);
            entity.setCompanyId(userInfo.getCompanyId());
            entity.setCreatorId(userInfo.getUserId());
            entity.setCreateTime(nowDate);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setMsCreatorId(userInfo.getMsUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            entity.setLastUpdateTime(nowDate);
            insertList.add(entity);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (insertList.size() > 0) {
                stageDao.addBatch(insertList);
            }
            if (delKey.size() > 0) {
                stageDao.deleteByTypeList(model.getProjectId(), delKey);
            }
            projectDao.updateStage(nowDate, model.getProjectId(), userInfo.getUserId(), userInfo.getMsUserId());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("添加失败。");
        }
        return true;
    }

    @Override
    public boolean editStage(UserInfo userInfo, StageEntity model) throws OwnerException {
        model.setLastUpdatorId(userInfo.getUserId());
        Date now = new Date();
        model.setLastUpdateTime(now);
        model.setMsLastUpdatorId(userInfo.getMsUserId());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectDao.updateStage(now, model.getProjectId(), userInfo.getUserId(), userInfo.getMsUserId());
            stageDao.updateById(model);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("保存失败。");
        }
    }

    @Override
    public boolean delStage(StageEntity model, UserInfo info) throws OwnerException {
        if (projectDocFileDao.countByProject(model.getProjectId(), model.getStageKey()) > 0) {
            throw new OwnerException("该阶段存在过程文件，不能删除！");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectDao.updateStage(new Date(), model.getProjectId(), info.getUserId(), info.getMsUserId());
            stageDao.deleteById(model.getId());
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("删除失败。");
        }
    }

    @Override
    public boolean importStage(UserInfo info, List<StageExcel> stageExcels, Integer projectId) throws OwnerException {
        if (stageExcels.size() == 0) {
            return true;
        }
        Map<String, StageExcel> removeExcels = stageExcels.stream().collect(Collectors.toMap(StageExcel::getStageName, Function.identity(), (existing, replacement) -> existing));
        if (!CollectionUtils.isEmpty(removeExcels)) {
            stageExcels = new ArrayList<>();
            for (StageExcel e : removeExcels.values()) {
                stageExcels.add(e);
            }
        } else {
            return true;
        }
        List<String> stageNameList = stageExcels.stream().filter(a -> !StringUtils.isEmpty(a.getStageName())).map(StageExcel::getStageName).collect(toList());
        List<StageEntity> stageEntityList = stageNameList.size() == 0 ? new ArrayList<>() : stageDao.getStageByName(info.getCompanyId(), projectId, stageNameList);
        Map<String, StageEntity> stageEntityMap = stageEntityList.stream().collect(Collectors.toMap(StageEntity::getStageName, b -> b));
        List<StageEntity> insertEntitys = new ArrayList<>();
        List<StageEntity> updateEntitys = new ArrayList<>();
        for (int i = 0; i < stageExcels.size(); i++) {
            StageExcel stageExcel = stageExcels.get(i);
            StageEntity stageEntity = stageEntityMap.get(stageExcel.getStageName());
            if (StringUtils.isEmpty(stageEntity)) {
                stageEntity = setValue(info, projectId, stageExcel);
                stageEntity.setCreateTime(new Date());
                stageEntity.setCreatorId(info.getUserId());
                stageEntity.setMsCreatorId(info.getMsUserId());
                insertEntitys.add(stageEntity);
            } else {
                stageEntity = setValue(info, projectId, stageExcel);
                updateEntitys.add(stageEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (insertEntitys.size() > 0) {
                List<List<StageEntity>> insertList = ListUtils.subList(insertEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<StageEntity> items : insertList) {
                    stageDao.addBatch(items);
                }
            }
            if (updateEntitys.size() > 0) {
                List<List<StageEntity>> updateList = ListUtils.subList(updateEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<StageEntity> items : updateList) {
                    stageDao.updateBatch(items);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("importStage", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public boolean editList(UserInfo userInfo, List<StageEntity> stageList, Boolean autoAdd, Integer type) throws OwnerException {
        Date now = new Date();
        if (CollectionUtils.isEmpty(stageList)) {
            return true;
        }
        Integer projectId = stageList.get(0).getProjectId();
        List<StageEntity> stages = stageDao.getStagesByProject(projectId);
        Map<String, StageEntity> stageMap = new HashMap<>();
        String key = "{0}-{1}";
        if (!CollectionUtils.isEmpty(stages)) {
            stages.forEach(item -> {
                stageMap.put(MessageFormat.format(key, item.getProjectId(), item.getStageKey()), item);
            });
        }
        List<StageEntity> insertList = new ArrayList<>();
        List<StageEntity> updateList = new ArrayList<>();
        Map<String, StageEntity> exist = new HashMap<>();
        for (StageEntity entity : stageList) {
            if (exist.containsKey(entity.getStageKey())) {
                throw new OwnerException("阶段不能重复添加，保存失败！");
            }
            exist.put(entity.getStageKey(), entity);
            entity.setCompanyId(userInfo.getCompanyId());
            entity.setLastUpdateTime(now);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            String skey = MessageFormat.format(key, entity.getProjectId(), entity.getStageKey());
            if (stageMap.containsKey(skey)) {
                StageEntity stageEntity = stageMap.get(skey);
                BeanUtils.copyProperties(entity, stageEntity);
                updateList.add(stageEntity);
            } else {
                entity.setCreateTime(now);
                entity.setCreatorId(userInfo.getUserId());
                entity.setMsCreatorId(userInfo.getMsUserId());
                insertList.add(entity);
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectDao.updateStage(now, projectId, userInfo.getUserId(), userInfo.getMsUserId());
            if (!CollectionUtils.isEmpty(updateList)) {
                stageDao.updateBatch(stageList);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                stageDao.addBatch(insertList);
                //自动生成过程文档
                if (autoAdd) {
                    autoAddFile(insertList, projectId, now, userInfo,type);
                }
                //添加没阶段的过程文件到阶段
                List<ProjectDocFileEntity> docFiles = projectDocFileDao.getFileNoStage(projectId);
                if (!CollectionUtils.isEmpty(docFiles)) {
                    addFileByStage(docFiles, projectId, stageList, userInfo);
                }
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        }catch (OwnerException own) {
            TransactionUtils.rollback(transactionStatus);
            logger.warn("editList", own.getMsg());
            throw own;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("editList", e);
            throw new OwnerException("保存失败");
        }
    }

    private void addFileByStage(List<ProjectDocFileEntity> docFiles, Integer projectId, List<StageEntity> stageList, UserInfo userInfo) {
        Date date = new Date();
        List<ProjectDocFileEntity> files = projectDocFileDao.getStageMaxSeq(projectId);
        Map<String, Integer> seqMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(files)) {
            seqMap = files.stream().collect(Collectors.toMap(e -> e.getStage(), e -> e.getSeq()));
        }
        StageEntity stageEntity = stageList.get(0);
        List<ProjectDocFileEntity> updateList = new ArrayList<>();
        Map<Date, ProjectDocFileEntity> monthDataMap = new ConcurrentHashMap<>();
        for (ProjectDocFileEntity docFileEntity : docFiles) {
            if (docFileEntity.getMonth() == null) {
                Integer seq = 1;
                if (seqMap.containsKey(stageEntity.getStageKey())) {
                    seq = seqMap.get(stageEntity.getStageKey()) + 1;
                }
                docFileEntity.setSeq(seq);
                seqMap.put(stageEntity.getStageKey(), seq);
                docFileEntity.setStage(stageEntity.getStageKey());
                docFileEntity.setLastUpdateTime(date);
                docFileEntity.setLastUpdatorId(userInfo.getUserId());
                docFileEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                updateList.add(docFileEntity);
            } else {
                monthDataMap.put(docFileEntity.getMonth(), docFileEntity);
            }
        }
        for (StageEntity stage : stageList) {
            if (!CollectionUtils.isEmpty(monthDataMap)) {
                Iterator<Date> it = monthDataMap.keySet().iterator();
                while (it.hasNext()) {
                    Date month = it.next();
                    ProjectDocFileEntity entity = monthDataMap.get(month);
                    if (month.compareTo(stage.getBeginDate()) >= 0 && month.compareTo(stage.getEndDate()) <= 0) {
                        entity.setStage(stage.getStageKey());
                        Integer seq = 1;
                        if (seqMap.containsKey(stageEntity.getStageKey())) {
                            seq = seqMap.get(stageEntity.getStageKey()) + 1;
                        }
                        entity.setSeq(seq);
                        entity.setLastUpdatorId(userInfo.getUserId());
                        entity.setLastUpdateTime(date);
                        entity.setMsLastUpdatorId(userInfo.getMsUserId());
                        updateList.add(entity);
                        monthDataMap.remove(month);
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(monthDataMap)) {
            Iterator<Date> it = monthDataMap.keySet().iterator();
            while (it.hasNext()) {
                Date month = it.next();
                ProjectDocFileEntity entity = monthDataMap.get(month);
                StageEntity lastYearStage = stageDao.getStageByYear(projectId, DateUtil.year(month));
                if (lastYearStage != null) {
                    entity.setStage(lastYearStage.getStageKey());
                    Integer seq = 1;
                    if (seqMap.containsKey(stageEntity.getStageKey())) {
                        seq = seqMap.get(stageEntity.getStageKey()) + 1;
                    }
                    entity.setSeq(seq);
                    entity.setLastUpdatorId(userInfo.getUserId());
                    entity.setLastUpdateTime(date);
                    entity.setMsLastUpdatorId(userInfo.getMsUserId());
                    updateList.add(entity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            projectDocFileDao.updateStages(updateList);
        }

    }

    private void autoAddFile(List<StageEntity> stages, Integer projectId, Date date, UserInfo userInfo,Integer type) throws OwnerException{
        List<String> stageKeys = new ArrayList<>();
        Map<String, StageEntity> stageMap = new HashMap<>();
        for (StageEntity stage : stages) {
            stageKeys.add(stage.getStageKey());
            stageMap.put(stage.getStageKey(), stage);
        }

        List<DocFileStageModel> docFileStages = docFileStageDao.getByStages(stageKeys, 1,null == type ? 0 : type);
        if (CollectionUtils.isEmpty(docFileStages)) {
            return;
        }
        // 获取项目已存在过的单文档docFileId
        List<ProjectDocFileEntity> existDocFiles = projectDocFileDao.getExistSingleDocFiles(stageKeys, projectId);
        Map<Integer, Boolean> existDocFileIdMap = new HashMap<>();
        Map<String, Map<Integer, Boolean>> existStageSeqMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existDocFiles)) {
            existDocFiles.forEach(a -> {
                existDocFileIdMap.put(a.getDocFileId(), true);
                if (!existStageSeqMap.containsKey(a.getStage())) {
                    existStageSeqMap.put(a.getStage(), new HashMap<>());
                }
                existStageSeqMap.get(a.getStage()).put(a.getSeq(), true);
            });
        }
        List<ProjectDocFileEntity> insertList = new ArrayList<>();
        String cKey = "";
        StageEntity cStage = null;
        List<Date> monthList = null;
        int startIndex = -1;
        int docSeq = 1;
        boolean existStageSeq = !CollectionUtils.isEmpty(existStageSeqMap);
        for (int i = 0; i < docFileStages.size(); i++) {
            DocFileStageModel projectDocFileEntity = docFileStages.get(i);
            if (existDocFileIdMap.containsKey(projectDocFileEntity.getDocFileId())) {
                continue;
            }
            if (!cKey.equals(projectDocFileEntity.getStageKey())) {
                // 新阶段重置全局变量
                cKey = projectDocFileEntity.getStageKey();
                startIndex = -1;
                docSeq = 1;
                cStage = stageMap.get(cKey);
                // 计算当前阶段的月份
                Date d = DateUtil.beginOfDay(DateUtil.endOfMonth(cStage.getBeginDate()));
                monthList = new ArrayList<>();
                while (d.compareTo(cStage.getEndDate()) <= 0) {
                    monthList.add(DateUtil.beginOfMonth(d));
                    d = DateUtil.beginOfDay(DateUtil.endOfMonth(DateUtil.offsetMonth(d, 1)));
                }
            }
            // 当前阶段每次开始要按月循环时,设置循环开始索引
            if (startIndex == -1 && projectDocFileEntity.getMonthly()) {
                // 当前阶段的月份没有时, 跳过按月处理的文档
                if (monthList.size() == 0)
                    continue;
                startIndex = i;
            }
            if (startIndex != -1) {
                // 可以开始按月循环时,开始按月添加文档
                for (Date month : monthList) {
                    for (int j = startIndex; j < docFileStages.size(); j++) {
                        // 存在seq，则判断当前阶段是否有重复seq，有则seq+1
                        if (existStageSeq) {
                            docSeq = getNextStageSeq(existStageSeqMap, docSeq, cKey);
                        }
                        projectDocFileEntity = docFileStages.get(j);
                        if (projectDocFileEntity.getMonthly()) {
                            String docName = projectDocFileEntity.getmPrefix() ? (DateUtil.month(month) + 1) + "月" + projectDocFileEntity.getDocName() : projectDocFileEntity.getDocName();
                            insertList.add(new ProjectDocFileEntity(cStage.getProjectId(), cKey, projectDocFileEntity.getDocFileId(), docName,
                                    date, userInfo, docSeq, projectDocFileEntity.getDocTemplateId(), month));
                            docSeq++;
                            i = j; // 当前最后一个月份循环的文档的索引
                        } else {
                            break;
                        }
                    }
                }
                startIndex = -1;
            } else {
                // 存在seq，则判断当前阶段是否有重复seq，有则seq+1
                if (existStageSeq) {
                    docSeq = getNextStageSeq(existStageSeqMap, docSeq, cKey);
                }
                insertList.add(new ProjectDocFileEntity(cStage.getProjectId(), cKey, projectDocFileEntity.getDocFileId(), projectDocFileEntity.getDocName(),
                        date, userInfo, docSeq, projectDocFileEntity.getDocTemplateId(), null));
                docSeq++;
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            List<Integer> nrDocList = docFileDao.getNRDocList();
            insertList.forEach(item->{
                if (nrDocList.contains(item.getDocFileId())){
                    item.setFinished(true);
                }else {
                    item.setFinished(false);
                }
            });
            projectDocFileDao.insertList(insertList);
            for (ProjectDocFileEntity entity:insertList){
                projectDocFileService.limitDocFileOnly(entity,projectId,entity.getMonth());
            }
        }
    }

    private int getNextStageSeq(Map<String, Map<Integer, Boolean>> existStageSeqMap, Integer docSeq, String stage) {
        if (!existStageSeqMap.containsKey(stage)) {
            return docSeq;
        }
        while (existStageSeqMap.get(stage).containsKey(docSeq)) {
            docSeq++;
        }
        return docSeq;
    }

    private List<ProjectDocFileEntity> batchAddFile(List<DocFileStageModel> list, StageEntity stage, Date date, UserInfo userInfo, int betweenMonth) {
        Integer seq = 1;
        List<ProjectDocFileEntity> insertList = new ArrayList<>();
        Map<String, Boolean> existMap = new HashMap<>();
        for (int i = 0; i <= betweenMonth; i++) {
            for (DocFileStageModel model : list) {
                if (!model.getMonthly()) {
                    String sign = MessageFormat.format("{0}-{1}", model.getStageKey(), model.getDocFileId());
                    if (!existMap.containsKey(sign)) {
                        ProjectDocFileEntity docFile = new ProjectDocFileEntity(stage.getProjectId(), model.getStageKey(), model.getDocFileId(), model.getDocName(),
                                date, userInfo, seq, model.getDocTemplateId(), null);
                        insertList.add(docFile);
                        seq += 1;
                        existMap.put(sign, true);
                    } else {
                        continue;
                    }
                } else {
                    Date monthSign = DateUtil.offsetMonth(DateUtil.beginOfMonth(stage.getBeginDate()), i);
                    ProjectDocFileEntity docFile = new ProjectDocFileEntity(stage.getProjectId(), model.getStageKey(), model.getDocFileId(), (model.getmPrefix() ? DateUtil.month(monthSign) + 1 + "月" : "") + model.getDocName(),
                            date, userInfo, seq, model.getDocTemplateId(), monthSign);
                    insertList.add(docFile);
                    seq += 1;
                }
            }
        }
        return insertList;
    }

    private StageEntity setValue(UserInfo info, Integer projectId, StageExcel stageExcel) {
        StageEntity entity = new StageEntity();
        entity.setCompanyId(info.getCompanyId());
        entity.setProjectId(projectId);
        entity.setStageName(stageExcel.getStageName());
        if (stageExcel.getBeginDate().compareTo(stageExcel.getEndDate()) == -1) {
            entity.setBeginDate(stageExcel.getBeginDate());
            entity.setEndDate(stageExcel.getEndDate());
        } else {
            entity.setBeginDate(stageExcel.getEndDate());
            entity.setEndDate(stageExcel.getBeginDate());
        }
        entity.setLastUpdatorId(info.getUserId());
        entity.setLastUpdateTime(new Date());
        entity.setMsLastUpdatorId(info.getMsUserId());
        entity.setRemark(stageExcel.getRemark());
        entity.setWorkDesc(stageExcel.getWorkDesc());
        return entity;
    }

}
