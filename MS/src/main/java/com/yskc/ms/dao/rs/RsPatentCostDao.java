package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentCostModel;
import com.yskc.ms.models.patent.QueryPatentCostModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/2 10:44
 * description:
 */
@Repository
public interface RsPatentCostDao extends BaseMapper<RsPatentCostEntity> {

    List<PatentCostModel> queryPatentCost(@Param("page") Pagination page, @Param("query") QueryPatentCostModel query, @Param("perm") DataPermModel perm,
                                          @Param("startDates") Date startDates, @Param("endDates") Date endDates);

    List<PatentCostModel> export(@Param("query") QueryPatentCostModel query, @Param("perm") DataPermModel perm,
                                 @Param("startDates") Date startDates, @Param("endDates") Date endDates);
   /**
     * 获取专利费用
     *
     * @param patentNo
     * @return
     */
    List<RsPatentCostEntity> getPatentCost(@Param("patentNo") String patentNo);
    /**
     * 批量更新(sign:1.备注 2.提醒日期）
     *
     * @param costList
     * @return
     */
    Integer updateRemarks(@Param("costList") List<RsPatentCostEntity> costList, @Param("sign") Integer sign);

    /**
     * 查询缴费信息
     *
     * @param patentNo
     * @return
     */
    List<PatentCostModel> getPayDataBypatentNo(@Param("patentNo") String patentNo);

    /**
     * 批量更新
     *
     * @param costList
     * @return
     */
    int updateBatch(@Param("costList") List<RsPatentCostEntity> costList);

    /**
     * 批量插入
     *
     * @param costList
     * @return
     */
    int insertBatch(@Param("costList") List<RsPatentCostEntity> costList);
}
