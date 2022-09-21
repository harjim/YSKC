package com.yskc.ms.service.es;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.cooperation.CooperationModel;
import com.yskc.ms.models.newexpert.cooperation.QueryCooperationModel;
import com.yskc.ms.models.newexpert.cooperation.CooperationInfoModel;

import java.util.List;

/**
 * @DateTime: 2021/11/12 8:53
 * @Description:
 * @author: hsx
 */
public interface CooperationService {

    /**
     * 修改意向为达成合作
     * @param model
     * @param id
     * @return
     */
    Boolean update(CooperationModel model,Integer id);

    /**
     * 获取专家意向列表
     * @param model
     * @return
     */
    PageModel<List<CooperationModel>> getExpertList(QueryCooperationModel model);

    /**
     * 获取成果和技术需求意向列表
     * @param model
     * @return
     */
    PageModel<List<CooperationInfoModel>> getList(QueryCooperationModel model);

    /**
     * 意向详情
     * @param id
     * @return
     */
    CooperationModel getData(Integer id);

    /**
     * 技术需求-服务中/已合作专家状态查询
     * @param intentionId 当前技术需求id
     * @return
     */
    Integer getCooperationStatus(Integer intentionId);

}
