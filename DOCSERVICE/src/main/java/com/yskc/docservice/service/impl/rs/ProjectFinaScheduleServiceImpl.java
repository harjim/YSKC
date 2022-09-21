package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectFinaScheduleDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectFinaSchedule;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectFinaScheduleExcel;
import com.yskc.docservice.service.rs.ProjectFinaScheduleService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
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
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:19
 * @Description: 项目财务排期表业务实现层
 */
@Service
public class ProjectFinaScheduleServiceImpl implements ProjectFinaScheduleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectFinaScheduleDao projectFinaScheduleDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public Boolean importFinaSchedule(RsUserInfo info, List<ProjectFinaScheduleExcel> data) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        Set<String> rdTitles = data.stream().map(ProjectFinaScheduleExcel::getRdTitle).collect(Collectors.toSet());
        Integer companyId = info.getCompanyId();
        List<ProjectEntity> projects = projectDao.getByRdTitles(rdTitles, companyId);
        if (CollectionUtils.isEmpty(projects)) {
            throw new OwnerException("导入的RD全部不存在，请检查。");
        }
        Map<String, ProjectEntity> rdTitleMap = projects.stream().collect(Collectors.toMap(ProjectEntity::getRdTitle, b -> {
            b.setBeginDate(DateUtil.getMonthFirstDay(b.getBeginDate()));
            b.setEndDate(DateUtil.getMonthLastDay(b.getEndDate()));
            return b;
        }, (o, n) -> n));
        Collection<String> noRdTitles = CollUtil.disjunction(rdTitleMap.keySet(), rdTitles);
        if (!CollectionUtils.isEmpty(noRdTitles)) {
            throw new OwnerException("RD【" + String.join(",", noRdTitles) + "】不存在，请检查。");
        }
        List<ProjectFinaScheduleExcel> deleteList = new ArrayList<>();
        List<ProjectFinaSchedule> dataList = new ArrayList<>();
        Date now = new Date();
        Integer userId = info.getUserId(), msUserId = info.getMsUserId();
        for (ProjectFinaScheduleExcel item : data) {
            Date currentMonth = DateUtil.getMonthFirstDay(item.getMonth());
            ProjectEntity currentProject = rdTitleMap.get(item.getRdTitle());
            if (currentProject.getBeginDate().compareTo(currentMonth) > 0 ||
                    currentProject.getEndDate().compareTo(currentMonth) < 0) {
                throw new OwnerException(MessageFormat.format("月份【{0}】不存在【{1}】的项目周期【{2}-{3}】内。",
                        DateUtil.format(currentMonth, DateUtil.DEFAULT_YYMM_FORMAT), currentProject.getRdTitle(),
                        DateUtil.format(currentProject.getBeginDate(), DateUtil.DEFAULT_YYMM_FORMAT),
                        DateUtil.format(currentProject.getEndDate(), DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            if ((null == item.getTestHour() || item.getTestHour().compareTo(BigDecimal.ZERO) == 0) &&
                    (null == item.getTrialHour() || item.getTrialHour().compareTo(BigDecimal.ZERO) == 0) &&
                    (null == item.getWorkHours() || item.getWorkHours().compareTo(BigDecimal.ZERO) == 0)) {
                item.setProjectId(currentProject.getId());
                item.setMonth(currentMonth);
                deleteList.add(item);
                continue;
            }
            dataList.add(ProjectFinaSchedule.build(item.getTestHour(), item.getTrialHour(), item.getWorkHours(), currentMonth,
                    currentProject.getId(), companyId, now, userId, msUserId, item.getDeptName()));
        }
        return save(dataList, CollectionUtils.isEmpty(deleteList) ? null : projectFinaScheduleDao.getExistIds(deleteList, companyId));
    }

    private Boolean save(List<ProjectFinaSchedule> data, List<Integer> deleteIds) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            if (!CollectionUtils.isEmpty(data)) {
                for (List<ProjectFinaSchedule> currentList : ListUtils.subList(data, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    projectFinaScheduleDao.insertOrUpdate(currentList);
                }
            }
            if (!CollectionUtils.isEmpty(deleteIds)) {
                projectFinaScheduleDao.deleteBatchIds(deleteIds);
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }

    }
}
