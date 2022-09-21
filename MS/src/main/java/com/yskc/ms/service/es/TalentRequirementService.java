package com.yskc.ms.service.es;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.talentdelivery.QueryTalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentdelivery.TalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentrequirement.QueryTalentRequirementModel;
import com.yskc.ms.models.newexpert.talentrequirement.TalentRequirementModel;

import java.util.List;

public interface TalentRequirementService {

    PageModel<List<TalentRequirementModel>> getList(QueryTalentRequirementModel model);

    TalentRequirementModel getData(Integer id);

    Boolean del(Integer[] ids) throws OwnerException;

    Boolean save(TalentRequirementModel model,Integer id) throws OwnerException;

    Boolean changeStatus(Integer id,Integer status,Integer userId);

    PageModel<List<TalentDeliveryModel>> getDeliveryList(QueryTalentDeliveryModel query);

}
