package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.DesignDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.ProjectDesignDao;
import com.yskc.rs.entity.DesignEntity;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.project.ProjectDesignEntity;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UsedModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.design.DesignAmountModel;
import com.yskc.rs.models.projectdesign.ProjectDesignModel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;
import com.yskc.rs.service.ProjectDesignService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class ProjectDesignServiceImpl implements ProjectDesignService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectDesignDao projectDesignDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private DesignDao designDao;
    @Autowired
    private CommonService commonService;

    @Override
    public boolean addProjectDesign(UserInfo info, ProjectDesignModel projectDesignModels) throws OwnerException {
        Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(projectDesignModels.getProjectMonth());
        CheckStatusModel statusModel = new CheckStatusModel(projectDesignModels.getProjectId(), beginDate);
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(AuditRdTypeEnum.DESIGN.getRdType()),info);
        List<DesignEntity> entityList = projectDesignModels.getDesignEntityList();
        List<ProjectDesignEntity> projectDesignEntities = new ArrayList<>();
        Date now = new Date();
        Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(beginDate);
        List<Integer> designIds = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            ProjectDesignEntity entity = new ProjectDesignEntity();
            entity.setCompanyId(info.getCompanyId());
            entity.setProjectId(projectDesignModels.getProjectId());
            entity.setDesignDataId(entityList.get(i).getId());
            designIds.add(entityList.get(i).getId());
            entity.setCreateTime(new Date());
            entity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setMsCreatorId(info.getUserSource() == 1 ? info.getId() : -1);
            entity.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
            entity.setLastUpdateTime(new Date());
            entity.setRdAmount(entityList.get(i).getRemainDFee());
            projectDesignEntities.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            //添加数据后修改剩余金额
            designDao.updateRemainFee(now, info.getUserId(), info.getMsUserId(), designIds);
            projectDesignDao.addProjectDesignBatch(projectDesignEntities);
            insertSummaryEntity(projectDesignModels.getProjectId(), now, info, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 获取汇总信息表数据
     *
     * @param now
     * @param userInfo
     * @param projectDesignModels
     * @return
     */
    private List<SummaryEntity> getSummaryEntityList(Date now, UserInfo userInfo, List<ProjectDesignModel> projectDesignModels) {
        List<SummaryEntity> summaryEntities = new ArrayList<>();
        Map<Date, BigDecimal> dataMap = new HashMap<>();
        projectDesignModels.forEach((model) -> {
            Date month = cn.hutool.core.date.DateUtil.beginOfMonth(model.getDesignDate());
            dataMap.put(month, dataMap.get(month) == null ? model.getdFee() : dataMap.get(month).add(model.getdFee()));
        });
        dataMap.forEach((key, value) -> {
            summaryEntities.add(ToolUtils.build(now, key, projectDesignModels.get(0).getProjectId(), CostEnum.DESIGN.getType(), value, userInfo));
        });

        return summaryEntities;
    }

    /**
     * 添加汇总信息
     *
     * @param projectId
     * @param now
     * @param userInfo
     */
    private void insertSummaryEntity(Integer projectId, Date now, UserInfo userInfo, Date beginDate, Date endDate) {
        List<ProjectDesignModel> projectDesignModelList = projectDesignDao.getDesignDate(projectId, beginDate, endDate);
        projectDesignModelList.remove(null);
        if (projectDesignModelList.size() > 0) {
            List<SummaryEntity> summaryEntities = getSummaryEntityList(now, userInfo, projectDesignModelList);
            summaryDao.insertOrUpdate(summaryEntities);
        }
    }

    /**
     * 删除
     *
     * @param projectId
     * @param now
     * @param userInfo
     * @param month
     */
    private void deleteSummaryEntity(Integer projectId, Date now, UserInfo userInfo, Date month, Date beginDate, Date endDate) {
        List<ProjectDesignModel> designModelList = projectDesignDao.getDesignDate(projectId, beginDate, endDate);
        designModelList.remove(null);
        if (designModelList.size() > 0) {
            List<SummaryEntity> summaryEntities = getSummaryEntityList(now, userInfo, designModelList);
            summaryDao.insertOrUpdate(summaryEntities);
        } else {
            List<Integer> rdTypeList = new ArrayList<>();
            rdTypeList.add(CostEnum.DESIGN.getType());
            summaryDao.deleteInfo(projectId, month, rdTypeList);
        }
    }

    @Override
    public PageModel<List<ProjectDesignModel>> queryProjectDesign(Integer companyId, QueryProjectDesignModel query) {
        Pagination page = query.getPagination();
        Date beginDate = null;
        Date endDate = null;
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("designDate");
            page.setDescs(desc);
        }
        if (query.getProjectMonth() != null) {
            beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(query.getProjectMonth());
            endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(beginDate);
        }
        List<ProjectDesignModel> designModels = projectDesignDao.queryProjectDesign(page, companyId, query, beginDate, endDate);
        if (!CollectionUtils.isEmpty(designModels)) {
            List<Integer> designIds = designModels.stream().map(ProjectDesignModel::getDesignDataId).collect(Collectors.toList());
            List<UsedModel> models = projectDesignDao.getUsedList(designIds, query.getProjectId());
            Map<Integer, List<UsedModel>> map = new HashMap<>();
            models.forEach(item -> {
                if (!map.containsKey(item.getId())) {
                    List<UsedModel> modelList = new ArrayList<>();
                    map.put(item.getId(), modelList);
                }
                map.get(item.getId()).add(item);
            });
            designModels.forEach(model -> {
                model.setUsedModels(map.get(model.getDesignDataId()));
            });
        }
        return PageUtils.buildPageResult(page, designModels,
                null,
                projectDesignDao.getProjectDesign(companyId, beginDate, endDate, query));
    }

    @Override
    public boolean addProjectDesignByTerm(UserInfo info, String eName, Integer projectId, Date projectMonth, String deptName) throws OwnerException {
        Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(projectMonth);
        CheckStatusModel statusModel = new CheckStatusModel(projectId, projectMonth);
        commonService.checkStatus(Arrays.asList(statusModel),Arrays.asList(AuditRdTypeEnum.DESIGN.getRdType()),info);
        Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(beginDate);
        Date now = new Date();
        List<DesignEntity> entities = designDao.getDesignByTerm(info.getCompanyId(), eName, beginDate, endDate, deptName, projectId);
        // 判断有没可添加的数据
        if (entities.size() == 0) {
            throw new OwnerException(ErrorEnum.DESIGN_NO_CONSISTENT_DATA);
        }
        List<Integer> designIds = new ArrayList<>();
        List<ProjectDesignEntity> projectDesignEntities = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            ProjectDesignEntity entity = new ProjectDesignEntity();
            entity.setCompanyId(info.getCompanyId());
            entity.setProjectId(projectId);
            entity.setCreateTime(new Date());
            entity.setCreatorId(info.getUserId());
            entity.setLastUpdatorId(info.getUserId());
            entity.setMsCreatorId(info.getMsUserId());
            entity.setMsLastUpdatorId(info.getMsUserId());
            entity.setLastUpdateTime(new Date());
            entity.setDesignDataId(entities.get(i).getId());
            entity.setRdAmount(entities.get(i).getRemainDFee());
            designIds.add(entities.get(i).getId());
            projectDesignEntities.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            designDao.updateRemainFee(now, info.getUserId(), info.getMsUserId(), designIds);
            projectDesignDao.addProjectDesignBatch(projectDesignEntities);
            insertSummaryEntity(projectId, now, info, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public boolean delDesignBatch(UserInfo userInfo, List<ProjectDesignModel> model) throws OwnerException {
        Date accDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(model.get(0).getDesignDate());
        Integer projectId = model.get(0).getProjectId();
        Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(model.get(0).getDesignDate());
        CheckStatusModel statusModel = new CheckStatusModel(projectId, beginDate);
        commonService.checkStatus(Arrays.asList(statusModel),Arrays.asList(AuditRdTypeEnum.DESIGN.getRdType()),userInfo);
        Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(model.get(0).getDesignDate());
        List<Integer> projectDesginIds = new ArrayList<Integer>();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            Map<Integer, BigDecimal> designMap = new HashMap<>();
            for (int i = 0; i < model.size(); i++) {
                ProjectDesignModel designModel = model.get(i);
                projectDesginIds.add(designModel.getId());
            }
            List<ProjectDesignEntity> designEntities = projectDesignDao.selectBatchIds(projectDesginIds);
            List<Integer> updateIds = new ArrayList<>();
            designEntities.forEach(item -> {
                designMap.put(item.getDesignDataId(), item.getRdAmount());
                updateIds.add(item.getDesignDataId());
            });
            List<DesignEntity> designs = designDao.selectBatchIds(updateIds);
            designs.forEach(entity -> {
                entity.setRemainDFee(entity.getRemainDFee().add(designMap.get(entity.getId())));
                entity.setLastUpdateTime(new Date());
                entity.setLastUpdatorId(userInfo.getUserId());
                entity.setMsLastUpdatorId(userInfo.getMsUserId());
            });
            //更新d_design
            designDao.updateList(designs);
            //删除
            if (projectDesignDao.deleteDesign(projectDesginIds) > 0) {
                deleteSummaryEntity(projectId, new Date(), userInfo, accDate, beginDate, endDate);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public Boolean setRdAmounts(UserInfo userInfo, List<ProjectDesignModel> models) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<CheckStatusModel> list = new ArrayList<>(models.size());
        models.forEach(item->{
            Date monthFirstDay = DateUtil.getMonthFirstDay(item.getDesignDate());
            list.add(new CheckStatusModel(item.getProjectId(), monthFirstDay));
        });
        commonService.checkStatus(list, Arrays.asList(AuditRdTypeEnum.DESIGN.getRdType()),userInfo);
        List<Integer> pDesignIds = new ArrayList<>();
        Integer projectId = models.get(0).getProjectId();
        Date projectMonth = models.get(0).getProjectMonth();
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfMonth(projectMonth);
        Date endDate = cn.hutool.core.date.DateUtil.endOfMonth(projectMonth);
        Map<Integer, BigDecimal> dataMap = new HashMap<>();
        for (ProjectDesignModel model : models) {
            pDesignIds.add(model.getId());
            dataMap.put(model.getId(), model.getRdAmount());//要修改的金额
        }
        //更新pdesign数据
        List<Integer> designIds = new ArrayList<>();
        Map<Integer, BigDecimal> designMap = new HashMap<>();
        List<ProjectDesignEntity> entities = projectDesignDao.selectBatchIds(pDesignIds);
        Map<Integer, BigDecimal> boomMap = new HashMap<>();
        for (ProjectDesignEntity design : entities) {
            designMap.put(design.getDesignDataId(), design.getRdAmount());
            design.setRdAmount(dataMap.get(design.getId()));
            design.setLastUpdateTime(date);
            design.setLastUpdatorId(userInfo.getUserId());
            design.setMsLastUpdatorId(userInfo.getMsUserId());
            designIds.add(design.getDesignDataId());
            boomMap.put(design.getDesignDataId(), dataMap.get(design.getId()));

        }
        //更新design数据
        List<DesignEntity> designEntities = designDao.selectBatchIds(designIds);
        for (DesignEntity entity : designEntities) {
            BigDecimal maxAmount = entity.getRemainDFee().add(designMap.get(entity.getId()));
            if (maxAmount.compareTo(boomMap.get(entity.getId())) < 0) {
                String message = MessageFormat.format("{0}设置的研发费用不能高于最大可归集的费用（{1}）", entity.getDname(), maxAmount);
                throw new OwnerException(message);
            }
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            entity.setRemainDFee(maxAmount.subtract(boomMap.get(entity.getId())));
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectDesignDao.updateList(entities);
            designDao.updateList(designEntities);
            //更新sumarry
            insertSummaryEntity(projectId, date, userInfo, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }

    }

    @Override
    public Boolean setDesignRdAmounts(UserInfo userInfo, DesignAmountModel model) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(model.getIds())) {
            throw new OwnerException("请选择要修改的数据");
        }
        List<ProjectDesignEntity> projectDesignEntities = projectDesignDao.selectBatchIds(model.getIds());
        if (CollectionUtils.isEmpty(projectDesignEntities)) {
            throw new OwnerException("要修改的数据已删除或不存在,删除失败");
        }
        Map<Integer, BigDecimal> designData = new HashMap<>();
        List<Integer> designIds = new ArrayList<>();
        List<Integer> projectDesignIds = new ArrayList<>();
        for (ProjectDesignEntity entity : projectDesignEntities) {
            projectDesignIds.add(entity.getId());
            designData.put(entity.getDesignDataId(), entity.getRdAmount());
            designIds.add(entity.getDesignDataId());
        }
        List<DesignEntity> designEntities = designDao.selectBatchIds(designIds);
        for (DesignEntity design : designEntities) {
            //最大研发费用额度
            BigDecimal maxAmount = design.getRemainDFee().add(designData.get(design.getId()));
            if (maxAmount.compareTo(model.getRdAmount()) < 0) {
                String message = MessageFormat.format("{0}设置的研发费用不能高于最大可归集的费用（{1}）", design.getDname(), maxAmount);
                throw new OwnerException(message);
            }
            design.setRemainDFee(maxAmount.subtract(model.getRdAmount()));
            design.setLastUpdatorId(userInfo.getUserId());
            design.setLastUpdateTime(date);
            design.setMsLastUpdatorId(userInfo.getMsUserId());
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            designDao.updateList(designEntities);
            projectDesignDao.updateAmounts(projectDesignIds, date, userInfo.getUserId(), userInfo.getMsUserId(), model.getRdAmount());
            //更新sumarry
            Date beginDate = cn.hutool.core.date.DateUtil.beginOfMonth(model.getProjectMonth());
            Date endDate = cn.hutool.core.date.DateUtil.endOfMonth(model.getProjectMonth());
            insertSummaryEntity(model.getProjectId(), date, userInfo, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }
}
