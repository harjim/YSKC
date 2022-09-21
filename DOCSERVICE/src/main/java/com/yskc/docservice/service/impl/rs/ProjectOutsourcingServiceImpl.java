package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.ProjectOutsourcingDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.ProjectOutsourcing;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectOutsourcingExcel;
import com.yskc.docservice.models.rs.outsourcing.ProjectOutsourcingModel;
import com.yskc.docservice.service.rs.ProjectOutsourcingService;
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
    public Boolean importOutsourcing(RsUserInfo info, List<ProjectOutsourcingExcel> data, Integer type, Integer year) throws OwnerException {
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

    private Boolean save(List<ProjectOutsourcing> list, List<Integer> deleteIds) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            if (!CollectionUtils.isEmpty(deleteIds)) {
                projectOutsourcingDao.deleteBatchIds(deleteIds);
            }
            if (!CollectionUtils.isEmpty(list)) {
                for (List<ProjectOutsourcing> currentList : ListUtils.subList(list, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    projectOutsourcingDao.insertOrUpdate(currentList);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }
        return true;
    }

}
