package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.FinaDailyDao;
import com.yskc.ms.dao.ms.FlowInstanceFormDao;
import com.yskc.ms.entity.ms.FinaDailyEntity;
import com.yskc.ms.entity.ms.FlowInstanceFormEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.finaDaily.FinaDailyModel;
import com.yskc.ms.models.finaDaily.QueryDailyModel;
import com.yskc.ms.models.finaDaily.SaveFinaDailyModel;
import com.yskc.ms.service.ms.FinaDailyService;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/service/impl/ms
 * @Author: hm
 * @CreateTime: 2022/9/6
 * @Description: 财务日报对应serviceImpl
 */
@Service
public class FinaDailyServiceImpl implements FinaDailyService {
    private final Integer moduleId = AuditModeEnum.FINA_DAILY.getModuleId();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FinaDailyDao finaDailyDao;

    @Autowired
    private FlowInstanceService flowInstanceService;

    @Autowired
    private KafkaQueueService kafkaQueueService;

    @Autowired
    private FlowInstanceFormDao flowInstanceFormDao;

    @Override
    public PageModel< List< FinaDailyModel > > getDailyList(QueryDailyModel query, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("lastUpdateTime");
            page.getDescs().add("createTime");
        }
        List< FinaDailyModel > list = finaDailyDao.getList(moduleId, page, query, userInfo, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            // 判断是否有审核权限
            Map< Integer, FinaDailyModel > formMap = new HashMap<>();
            for (FinaDailyModel model :
                    list) {
                formMap.put(model.getId(), model);
            }
            List< FlowInstanceFormEntity > hasPermission = flowInstanceFormDao.getHasPermission(formMap.keySet(), moduleId, userInfo.getId());
            hasPermission.forEach(item -> {
                formMap.get(item.getId()).setHasPermission(true);
            });
        }
        return PageUtils.buildPageResult(page, list);
    }

    private Boolean saveDaily(SaveFinaDailyModel model, UserInfo userInfo, Boolean isAdd) {

        FinaDailyEntity finaDailyEntity = new FinaDailyEntity();
        finaDailyEntity.setCustomerId(model.getCustomerId());
        finaDailyEntity.setDeptId(model.getDeptId());
        finaDailyEntity.setOwnerId(model.getOwnerId());
        finaDailyEntity.setItemType(model.getItemType());
        finaDailyEntity.setContent(model.getContent());
        finaDailyEntity.setFilepath(model.getFilepath());
        finaDailyEntity.setWorkDate(model.getWorkDate());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        finaDailyEntity.setLastUpdateTime(now);
        finaDailyEntity.setLastUpdatorId(userInfo.getId());

        if (isAdd) {
            String label = MessageFormat.format("{0}-{1}-{2}", format.format(now), model.getItemTypeValue(), model.getCompanyName());
            finaDailyEntity.setLabel(label);
            finaDailyEntity.setCreatorId(userInfo.getId());
            finaDailyEntity.setCreateTime(now);
            finaDailyDao.insert(finaDailyEntity);
            model.setId(finaDailyEntity.getId());
        } else {
            finaDailyEntity.setId(model.getId());
            finaDailyDao.updateById(finaDailyEntity);
        }
        return true;
    }

    @Override
    public Boolean saveFinaDaily(SaveFinaDailyModel model, UserInfo userInfo, Boolean isAdd) throws OwnerException {
        // 未提交内容
        if (model == null) {
            throw new OwnerException("请添加记录");
        }
        // 编辑无id
        if (!isAdd && model.getId() == null) {
            throw new OwnerException("请选择已暂存记录保存");
        }

        saveDaily(model, userInfo, isAdd);
        return true;
    }

    @Override
    public Boolean submitFinaDaily(SaveFinaDailyModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            throw new OwnerException("请添加记录");
        }
        Integer dailyId = model.getId();
        Boolean isAdd = dailyId == null;
        Integer id;
        Integer userId = userInfo.getId();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            saveDaily(model, userInfo, isAdd);

            dailyId = model.getId();

            id = flowInstanceService.submitForm(dailyId, userId, moduleId);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("系统异常");
        }
        kafkaQueueService.submitAudit(Arrays.asList(id), userId, FlowModuleTypeEnum.FORM);

        return null;
    }

    @Override
    public Boolean delFinaDaily(List< Integer > delIdList) throws OwnerException {
        if (CollectionUtils.isEmpty(delIdList)) {
            throw new OwnerException("请选择需要删除的财务日志");
        }
        // flowInstance_from 表删除 formId
        List< FlowInstanceFormEntity > formList = flowInstanceFormDao.getStatus(delIdList, moduleId);

        // 判断日志是否可以删除
        for (int i = 0; i < formList.size(); i++) {
            Integer status = formList.get(i).getStatus();
            Integer formId = formList.get(i).getFormId();
            if (!FlowInstanceStatusEnum.canModify(status)) {
                // 移除不可删除选项
                delIdList.remove(formId);
            }
        }

        if (CollectionUtils.isEmpty(delIdList)) {
            throw new OwnerException("所有数据都已提交审核，删除失败!");
        }

        finaDailyDao.deleteBatchIds(delIdList);
        return finaDailyDao.deleteBatchIds(delIdList) > 0;

    }
}
