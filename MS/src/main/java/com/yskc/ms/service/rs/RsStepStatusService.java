package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.project.StepStatusModel;

/**
 * Created by hck
 * on 2020/5/20 14:06
 */
public interface RsStepStatusService {

    Boolean editStepStatus(StepStatusModel model, UserInfo userInfo)  throws OwnerException;
}
