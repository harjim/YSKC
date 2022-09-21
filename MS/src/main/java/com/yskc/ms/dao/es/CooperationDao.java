package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.CooperationEntity;
import com.yskc.ms.models.newexpert.cooperation.CooperationModel;
import com.yskc.ms.models.newexpert.cooperation.QueryCooperationModel;
import com.yskc.ms.models.newexpert.cooperation.CooperationInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/11/12 8:39
 * @Description:
 * @author: hsx
 */
@Repository
public interface CooperationDao extends BaseMapper<CooperationEntity> {

    /**
     * 修改意向
     * @param entity
     * @return
     */
    Integer updateCooperation(@Param("entity") CooperationEntity entity);

    /**
     * 获取专家意向列表
     * @param page
     * @param model
     * @return
     */
    List<CooperationModel> getExpertList(@Param("page") Pagination page, @Param("model")QueryCooperationModel model);

    /**
     * 获取意向列表
     * @param page
     * @param model
     * @return
     */
    List<CooperationInfoModel> getList(@Param("page") Pagination page, @Param("model") QueryCooperationModel model);

    /**
     * 获取对应的专家详情以及合作说明
     * @param id
     * @return
     */
    CooperationModel getData(@Param("id") Integer id);
}

