package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentBuyingList;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentBuyingDemandModel;
import com.yskc.ms.models.patent.PatentBuyingModel;
import com.yskc.ms.models.patent.QueryPatentModel;
import com.yskc.ms.models.patentbuying.PatentCanBuyModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:21
 * @Description:
 */
@Repository
public interface PatentBuyingListDao extends BaseMapper<PatentBuyingList> {
    /**
     * 批量插入
     *
     * @param buyingList
     * @return
     */
    Integer insertList(@Param("buyingList") List<PatentBuyingList> buyingList);

    /**
     * 获取专利购买列表(分页)
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<PatentBuyingModel> getList(@Param("page") Pagination page, @Param("query") QueryPatentModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 更新购买状态
     *
     * @param updateIds
     * @param status
     * @param userId
     * @param date
     * @return
     */
    Integer updateStatus(@Param("updateIds") List<Integer> updateIds, @Param("status") int status, @Param("userId") Integer userId, @Param("date") Date date);

    /**
     * 删除未完成的购买列表
     *
     * @param patentSeaIds
     * @param demandId
     * @return
     */
    Integer deleteBySea(@Param("patentSeaIds") List<Integer> patentSeaIds, @Param("demandId") Integer demandId);

    /**
     * 获取导出的数据
     *
     * @param query
     * @return
     */
    List<PatentCanBuyModel> getExportList(@Param("query") QueryPatentModel query);

    /**
     * 更新转让资料
     *
     * @param id
     * @param sellFile
     * @param userId
     * @param now
     * @return
     */
    int updateSellFile(@Param("id") Integer id, @Param("sellFile") String sellFile, @Param("userId") Integer userId,
                       @Param("now") Date now);

    /**
     * 获取购买数统计
     *
     * @param demandIds
     * @param inventorType
     * @param utilityType
     * @param appearanceDesignType
     * @return
     */
    List<PatentBuyingDemandModel> getBuyCnt(@Param("demandIds") List<Integer> demandIds,@Param("inventorType") int inventorType,
                                            @Param("utilityType") int utilityType, @Param("appearanceDesignType") int appearanceDesignType);
}
