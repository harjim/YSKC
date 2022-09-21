package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.project.ProjectOutsourcingDao;
import com.yskc.rs.entity.project.ProjectOutsourcing;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ProjectOutsourcingExcel;
import com.yskc.rs.models.outsourcing.BaseOutsourcingModel;
import com.yskc.rs.models.outsourcing.ProjectOutsourcingModel;
import com.yskc.rs.models.outsourcing.SaveOutsourcingModel;
import com.yskc.rs.service.ProjectOutsourcingService;
import com.yskc.rs.utils.ListUtils;
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

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:02
 * @Description: 项目委外费用业务层实现
 */
@Service
public class ProjectOutsourcingServiceImpl implements ProjectOutsourcingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectOutsourcingDao projectOutsourcingDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<ProjectOutsourcingModel> getOutsourcingList(Integer year, Integer type, Integer companyId) {
        if (year == null) {
            return new ArrayList<>();
        }
        List<ProjectOutsourcingModel> result = projectDao.getOutsourcingProject(year, companyId);
        if (CollectionUtils.isEmpty(result)) {
            return result;
        }
        result.forEach(item -> item.setFundMap(new HashMap<>()));
        Date beginDate = DateUtil.getYearFirstDay(year);
        Date endDate = DateUtil.getYearLastDay(year);
        List<BaseOutsourcingModel> funds = projectOutsourcingDao.getMonthFunds(beginDate, endDate, type, companyId);
        if (CollectionUtils.isEmpty(funds)) {
            return result;
        }
        Map<Integer, Map<Integer, BaseOutsourcingModel>> baseMap = new HashMap<>();
        funds.forEach(item -> {
            Integer projectId = item.getProjectId();
            if (!baseMap.containsKey(projectId)) {
                baseMap.put(projectId, new HashMap<>());
            }
            baseMap.get(projectId).put((cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
        });
        result.forEach(item -> {
            Map<Integer, BaseOutsourcingModel> tempMap = baseMap.get(item.getProjectId());
            if (null != tempMap) {
                item.getFundMap().putAll(tempMap);
                BigDecimal total = BigDecimal.ZERO;
                for (Integer k : tempMap.keySet()) {
                    total = total.add(tempMap.get(k).getRdFunds());
                }
                total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
                item.setTotal(total);
            }
        });
        return result;
    }

    @Override
    public Boolean save(SaveOutsourcingModel saveOutsourcing, UserInfo userInfo) throws OwnerException {
        List<ProjectOutsourcing> list = new ArrayList<>();
        Integer type = saveOutsourcing.getType();
        Date now = new Date();
        Integer companyId = userInfo.getCompanyId(), userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId();
        BigDecimal currentFunds;
        List<Integer> deleteIds = new ArrayList<>();
        for (BaseOutsourcingModel item : saveOutsourcing.getList()) {
            currentFunds = item.getRdFunds();
            if (currentFunds == null || currentFunds.compareTo(BigDecimal.ZERO) == 0) {
                if (item.getId() != null) {
                    deleteIds.add(item.getId());
                }
                continue;
            }
            list.add(ProjectOutsourcing.build(DateUtil.getMonthFirstDay(item.getMonth()), item.getProjectId(), currentFunds, companyId, type, userId, msUserId, now));
        }
        return save(list, deleteIds);
    }

    @Override
    public Boolean importOutsourcing(UserInfo info, List<ProjectOutsourcingExcel> data, Integer type, Integer year) throws OwnerException {
        if(CollectionUtils.isEmpty(data)){
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        Integer companyId = info.getCompanyId();
        List<ProjectOutsourcingModel> projects = projectDao.getOutsourcingProject(year, companyId);
        if (CollectionUtils.isEmpty(projects)) {
            throw new OwnerException("未获取到委外项目，导入失败。");
        }
        Map<String, ProjectOutsourcingModel> rdTitleMap = new HashMap<>();
        for (ProjectOutsourcingModel project : projects) {
            project.setBeginDate(DateUtil.getMonthFirstDay(project.getBeginDate()));
            project.setEndDate(DateUtil.getMonthLastDay(project.getEndDate()));
            rdTitleMap.put(project.getRdTitle().trim(), project);
        }
        String keyFormat = "{0}_{1}";
        Map<String, ProjectOutsourcing> dataMap = new LinkedHashMap<>();
        Integer userId = info.getUserId(), msUserId = info.getMsUserId();
        Date currentMonth, now = new Date();
        BigDecimal currentRdFunds;
        ProjectOutsourcingModel currentProject;
        List<ProjectOutsourcing> noRdFundList = new ArrayList<>();
        for (ProjectOutsourcingExcel item : data) {
            currentProject = rdTitleMap.get(item.getRdTitle().trim());
            if (currentProject == null) {
                throw new OwnerException(MessageFormat.format("RD【{0}】不存在或不是委外项目，请检查。", item.getRdTitle()));
            }
            currentMonth = DateUtil.getMonthFirstDay(item.getMonth());
            if (currentProject.getBeginDate().compareTo(currentMonth) > 0 ||
                    currentProject.getEndDate().compareTo(currentMonth) < 0) {
                throw new OwnerException(MessageFormat.format("月份【{0}】不存在【{1}】的项目周期【{2}-{3}】内。",
                        DateUtil.format(currentMonth, DateUtil.DEFAULT_YYMM_FORMAT), currentProject.getRdTitle(),
                        DateUtil.format(currentProject.getBeginDate(),DateUtil.DEFAULT_YYMM_FORMAT),
                        DateUtil.format(currentProject.getEndDate(),DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            currentRdFunds = item.getRdFunds();
            String key = MessageFormat.format(keyFormat, currentProject.getProjectId(), currentMonth.getTime());
            if (currentRdFunds.compareTo(BigDecimal.ZERO) == 0) {
                dataMap.remove(key);
                noRdFundList.add(ProjectOutsourcing.build(currentProject.getProjectId(), currentMonth, type));
                continue;
            }
            dataMap.put(key, ProjectOutsourcing.build(currentMonth, currentProject.getProjectId(), currentRdFunds, companyId, type, userId, msUserId, now));
        }
        List<Integer> deleteIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(noRdFundList)) {
            deleteIds = projectOutsourcingDao.getExistIds(noRdFundList);
        }
        return save(new ArrayList<>(dataMap.values()), deleteIds);
    }

    @Override
    public List<Map<String, Object>> getExportOutsourcing(Integer year, Integer type, Integer companyId) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<ProjectOutsourcingModel> data = getOutsourcingList(year, type, companyId);
        data.forEach(item -> {
            Map<String, Object> currentMap = BeanUtil.beanToMap(item);
            Map<Integer, BaseOutsourcingModel> fundMap = item.getFundMap();
            fundMap.keySet().forEach(key -> {
                currentMap.put(key.toString(), fundMap.get(key).getRdFunds());
            });
            currentMap.put("rdFunds", "");
            result.add(currentMap);
        });
        return result;
    }

    private Boolean save(List<ProjectOutsourcing> list, List<Integer> deleteIds) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(deleteIds)) {
                projectOutsourcingDao.deleteBatchIds(deleteIds);
            }
            if (!CollectionUtils.isEmpty(list)) {
                for (List<ProjectOutsourcing> currentList : ListUtils.subList(list, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    projectOutsourcingDao.insertOrUpdate(currentList);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }
        return true;
    }

}
