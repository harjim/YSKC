package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;

import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectYieldConfigDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectYieldConfigEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.service.rs.ProjectYieldConfigService;
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
 * @CreateTime: 2020-09-02 18:17
 * @Description: 项目试制量配置业务实现层
 */
@Service
public class ProjectYieldConfigServiceImpl implements ProjectYieldConfigService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectYieldConfigDao projectYieldConfigDao;
    @Autowired
    private ProjectDao projectDao;


    @Override
    public boolean importYield(RsUserInfo userInfo, List<ProjectYieldConfigEntity> data, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未导入任何数据。");
        }
        //获取所有rdTitle
        Set<String> rdTitles = new HashSet<>();
        for (ProjectYieldConfigEntity entity : data) {
            rdTitles.add(entity.getRdTitle());
        }
        //根据所有的rdTitle与公司id查询出所有数据
        List<ProjectEntity> tdtitleList = projectDao.getRdTitle(rdTitles, userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(tdtitleList)) {
            throw new OwnerException("导入的RD不存在");
        }

        Map<String, ProjectEntity> projectEntityMap = new HashMap<>();
        //rdTitle作为Key
        for (ProjectEntity projectEntity : tdtitleList) {
            projectEntityMap.put(projectEntity.getRdTitle(), projectEntity);
        }

        Map<String, ProjectYieldConfigEntity> filterMap = new HashMap<>();
        //格式化key
        String keyFormat = "{0}_{1}_{2}_{3}";
        String timeFormat = "HH:mm";
        for (int i = 0; i < data.size(); i++) {
            ProjectYieldConfigEntity yield = data.get(i);
            ProjectEntity projectEntity = projectEntityMap.get(yield.getRdTitle());
            if (null == projectEntity) {
                throw new OwnerException(MessageFormat.format("第{1,number,#}行，不存在【{0}】的项目。", yield.getRdTitle(), i + 2));
            }
            String key = MessageFormat.format(keyFormat, projectEntity.getId(), yield.getDeptName(),
                    DateUtil.format(yield.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT),DateUtil.format(yield.getStartTime(),"HH:mm"));
            if (filterMap.containsKey(key)) {
                continue;
            }
            Date month = DateUtil.getMonthFirstDay(yield.getMonth());
            if (month.compareTo(projectEntity.getBeginDate()) < 0 || month.compareTo(projectEntity.getEndDate()) > 0) {
                throw new OwnerException(MessageFormat.format("第{1,number,#}行，月份【{4}】不存在项目【{0}】的起止日期【{2}~{3}】内。",
                        yield.getRdTitle(), i + 2, DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(yield.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            if (month.compareTo(DateUtil.getMonthFirstDay(yield.getTrialDate())) != 0) {
                throw new OwnerException(MessageFormat.format("第{0,number,#}行，试制日期【{1}】不存在月份【{2}】内。", i + 2,
                        DateUtil.format(yield.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(yield.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            if (yield.getStartTime() != null && yield.getEndTime() != null && yield.getStartTime().getTime() > yield.getEndTime().getTime()) {

                throw new OwnerException(MessageFormat.format(
                        "第{0,number,#}行，月份【{1}】的开始时间【{2}】不能大于结束时间【{3}】", i + 2,
                        DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT),
                        DateUtil.format(yield.getStartTime(), timeFormat),
                        DateUtil.format(yield.getEndTime(), timeFormat)
                ));
            }
            yield.setMonth(month);
            yield.setProjectId(projectEntity.getId());
            yield.setCreatorId(projectEntity.getCreatorId());
            // source默认为0
            yield.setSource(0);
            filterMap.put(key, yield);
        }

        List<ProjectYieldConfigEntity> projectEntityList = projectYieldConfigDao.findByBean(new ArrayList<>(filterMap.values()));
        //需要修改的数据集合
        List<ProjectYieldConfigEntity> updateList = new ArrayList<>();
        //需要插入的数据集合
        List<ProjectYieldConfigEntity> insertList = new ArrayList<>();
        Map<String, Integer> idMap = new HashMap<>();
        for (ProjectYieldConfigEntity bean : projectEntityList) {
            idMap.put(MessageFormat.format(keyFormat, bean.getProjectId(), bean.getDeptName(),
                    DateUtil.format(bean.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT),DateUtil.format(bean.getStartTime(),"HH:mm")), bean.getId());
        }
        Date date = new Date();
        List<ProjectYieldConfigEntity> checkList = new ArrayList<>();
        for (String key : filterMap.keySet()) {
            ProjectYieldConfigEntity project = filterMap.get(key);
            project.setLastUpdateTime(date);
            project.setLastUpdatorId(userInfo.getUserId());
            project.setMsLastUpdatorId(userInfo.getMsUserId());
            if (idMap.containsKey(key)) {
                // 更新
                project.setId(idMap.get(key));
                updateList.add(project);
            } else {
                // 插入
                project.setCompanyId(userInfo.getCompanyId());
                project.setCreatorId(userInfo.getUserId());
                project.setCreateTime(date);
                project.setMsCreatorId(userInfo.getMsUserId());
                insertList.add(filterMap.get(key));
            }
            checkList.add(project);
        }

        ProjectYieldConfigEntity report = projectYieldConfigDao.getReport(checkList);
        if (report!=null){
            String format = DateUtil.format(report.getTrialDate(), DateUtil.DEFAULT_YYMM_FORMAT);
            String start = DateUtil.format(report.getStartTime(),timeFormat)+"-"+DateUtil.format(report.getEndTime(),timeFormat);
            String end = DateUtil.format(report.getTestStartTime(),timeFormat)+"-"+DateUtil.format(report.getTestEndTime(),timeFormat);
            if (report.getTestStartTime()!=null){
                throw new OwnerException("系统中已有数据：项目【"+ report.getRdTitle()+"】部门【"+report.getDeptName()+"】日期【"+format+"】" +
                        "时间段【"+start+"】时间段【"+end+"】，请检查重复时间！");
            }else {
                throw new OwnerException("系统中已有数据：项目【"+ report.getRdTitle()+"】部门【"+report.getDeptName()+"】日期【"+format+"】" +
                        "时间段【"+start+"】，请检查重复时间！");
            }
        }

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            if (!CollectionUtils.isEmpty(updateList)) {
                List<List<ProjectYieldConfigEntity>> updateLists = ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectYieldConfigEntity> currents : updateLists) {
                    projectYieldConfigDao.updateBatch(currents);
                }
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                List<List<ProjectYieldConfigEntity>> insertLists = ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectYieldConfigEntity> currents : insertLists) {
                    projectYieldConfigDao.addBatch(currents);
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
