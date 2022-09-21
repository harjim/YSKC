package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.TalentRequirementEntity;
import com.yskc.ms.models.newexpert.talentdelivery.QueryTalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentdelivery.TalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentrequirement.QueryTalentRequirementModel;
import com.yskc.ms.models.newexpert.talentrequirement.TalentRequirementModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TalentRequirementDao extends BaseMapper<TalentRequirementEntity> {

    List<TalentRequirementModel> getList(@Param("page") Pagination page, @Param("query")QueryTalentRequirementModel query);

    TalentRequirementModel getData(@Param("id") Integer id);

    Integer changeStatus(@Param("id") Integer id, @Param("status")Integer status, @Param("updatorId") Integer updatorId, @Param("updateTime")Date updateTime);

    List<TalentRequirementModel> getStatusList(@Param("ids")Integer[] ids);

    List<TalentDeliveryModel> getDeliveryList(@Param("page") Pagination page, @Param("query") QueryTalentDeliveryModel query);
}
