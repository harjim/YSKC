package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentSeaEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentSeaModel;
import com.yskc.ms.models.patent.QueryPatentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:22
 * @Description:
 */
@Repository
public interface PatentSeaDao extends BaseMapper<PatentSeaEntity> {
    /**
     * 获取专利公海
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<PatentSeaModel> getList(@Param("page") Pagination page, @Param("query") QueryPatentModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 专利公海关联/取消关联专利购买需求
     *
     * @param ids
     * @param demandId null取消
     * @param userId
     * @param date
     * @return
     */
    Boolean relatedDemands(@Param("ids") List<Integer> ids, @Param("demandId") Integer demandId, @Param("userId") Integer userId, @Param("date") Date date);

    /**
     * 获取专利公海专利
     *
     * @param id
     * @return
     */
    PatentSeaModel getPatentSea(@Param("id") Integer id);

    /**
     * 获取专利需求列表（未购买）
     *
     * @param demandId
     * @return
     */
    List<PatentSeaEntity> getByDemand(@Param("demandId") Integer demandId);

    /**
     * 获取专利号相同的专利
     *
     * @param model
     * @return
     */
    List<PatentSeaEntity> limitPatentNo(@Param("model") PatentSeaModel model);
}
