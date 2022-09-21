package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.CheckInstEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.checkInst.CheckInstModel;
import com.yskc.ms.models.checkInst.QueryCheckInstModel;

import java.util.List;

public interface CheckInstService {

    PageModel<List<CheckInstModel>> getList(QueryCheckInstModel query,UserInfo info);

    Boolean addCheckInst(CheckInstModel model,UserInfo info) throws OwnerException;

    Boolean updateCheckInst(CheckInstModel model,UserInfo info) throws OwnerException;

    Boolean delCheckInst(List<Integer> ids) throws OwnerException;
}
