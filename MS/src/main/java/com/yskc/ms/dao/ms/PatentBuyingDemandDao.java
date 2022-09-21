package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentBuyingDemand;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentBuyingDemandModel;
import com.yskc.ms.models.patent.QueryDemandModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:20
 * @Description:
 */
@Repository
public interface PatentBuyingDemandDao extends BaseMapper<PatentBuyingDemand> {
    /**
     * 获取专利需求列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<PatentBuyingDemandModel> getBuyDemandList(@Param("page") Pagination page, @Param("query") QueryDemandModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 统计重复
     *
     * @param customerId
     * @param type
     * @param year
     * @param id
     * @return
     */
    int countRepeat(@Param("customerId") Integer customerId, @Param("year") Integer year, @Param("type") Integer type, @Param("id") Integer id);

    /**
     * 获取需求列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<PatentBuyingDemandModel> getList(@Param("page") Pagination page, @Param("query") QueryDemandModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取需求公司和年份
     *
     * @param id
     * @return
     */
    PatentBuyingDemandModel getDemandCompanyYear(@Param("id") Integer id);

    /**
     * 更新工程师
     *
     * @param ids
     * @param engineerId
     * @param userId
     * @param now
     * @return
     */
    int updateEngineer(@Param("ids") List<Integer> ids, @Param("engineerId") Integer engineerId,
                           @Param("userId") Integer userId, @Param("now") Date now);
}
