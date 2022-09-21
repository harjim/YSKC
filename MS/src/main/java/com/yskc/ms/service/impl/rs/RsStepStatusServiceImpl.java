package com.yskc.ms.service.impl.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.rs.RsStepStatusDao;
import com.yskc.ms.entity.rs.RsStepStatusEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.project.StepStatusModel;
import com.yskc.ms.service.rs.RsStepStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by hck
 * on 2020/5/20 14:08
 */
@Service
public class RsStepStatusServiceImpl implements RsStepStatusService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsStepStatusDao rsStepStatusDao;

    @Override
    public Boolean editStepStatus(StepStatusModel model, UserInfo userInfo) throws OwnerException {
        RsStepStatusEntity stepStatus = rsStepStatusDao.getStepStatus(model.getProductId());
        RsStepStatusEntity rsStepStatusEntity = new RsStepStatusEntity();
        switch (model.getStatus()) {
            case 0:
                if (stepStatus == null) {
                    rsStepStatusEntity.setStatus(0);
                    rsStepStatusEntity.setAuditTime(new Date());
                    rsStepStatusEntity.setAuditorId(userInfo.getId());
                    rsStepStatusEntity.setRemark("");
                    rsStepStatusEntity.setProductId(model.getProductId());
                    return rsStepStatusDao.insert(rsStepStatusEntity) > 0;
                }
                stepStatus.setStatus(model.getStatus());
                stepStatus.setAuditorId(userInfo.getId());
                stepStatus.setAuditTime(new Date());
                return rsStepStatusDao.updateById(stepStatus) > 0;

            case 1:
                if (stepStatus == null) {
                    rsStepStatusEntity.setStatus(0);
                    rsStepStatusEntity.setAuditTime(new Date());
                    rsStepStatusEntity.setAuditorId(userInfo.getId());
                    rsStepStatusEntity.setRemark("");
                    rsStepStatusEntity.setProductId(model.getProductId());
                    return rsStepStatusDao.insert(rsStepStatusEntity) > 0;
                } else {
                    if (stepStatus.getStatus() == 0 || stepStatus.getStatus() == 3) {
                        stepStatus.setStatus(model.getStatus());
                        stepStatus.setAuditorId(userInfo.getId());
                        stepStatus.setAuditTime(new Date());
                        return rsStepStatusDao.updateById(stepStatus) > 0;
                    } else {
                        throw new OwnerException("已提交或已审核,提交失败");
                    }
                }
            case 2:
                if (stepStatus != null && stepStatus.getStatus() == 1) {
                    stepStatus.setStatus(model.getStatus());
                    stepStatus.setAuditorId(userInfo.getId());
                    stepStatus.setAuditTime(new Date());
                    stepStatus.setRemark(model.getRemake());
                    return rsStepStatusDao.updateById(stepStatus) > 0;
                } else {
                    throw new OwnerException("请提交后再审核");
                }
            case 3:
                if (stepStatus != null && stepStatus.getStatus() == 1) {
                    stepStatus.setStatus(model.getStatus());
                    stepStatus.setAuditorId(userInfo.getId());
                    stepStatus.setAuditTime(new Date());
                    stepStatus.setRemark(model.getRemake());
                    return rsStepStatusDao.updateById(stepStatus) > 0;
                } else {
                    throw new OwnerException("尚未提交,无法撤回");
                }
        }
        throw new OwnerException("数据异常,操作失败");
    }
}
