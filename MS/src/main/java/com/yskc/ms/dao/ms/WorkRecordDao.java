package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.WorkRecordEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.serviceApply.QueryMobileApply;
import com.yskc.ms.models.serviceRecord.QueryRecordModel;
import com.yskc.ms.models.serviceRecord.WorkRecordModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wjy
 * on 2020/12/15 11:00
 * description:
 */
@Repository
public interface WorkRecordDao extends BaseMapper<WorkRecordEntity> {
    /**
     * 获取服务记录列表
     * @param page
     * @param query
     * @param moduleId
     * @return
     */
    List<WorkRecordModel> getList(@Param("page")Pagination page, @Param("query") QueryRecordModel query,
                                  @Param("moduleId")Integer moduleId,@Param("perm") DataPermModel dataPerm);
    /**
     * 获取服务记录列表
     * @param query
     * @param moduleId
     * @return
     */
    List<WorkRecordModel> getMobileList(@Param("page")Pagination page, @Param("query") QueryMobileApply query,
                                        @Param("moduleId")Integer moduleId,@Param("perm") DataPermModel dataPerm);

    /**
     * 根据id获取详情
     * @param recordId
     * @return
     */
    WorkRecordModel getRecordInfo(@Param("recordId")Integer recordId);

    /**
     * 查询合计
     * @param query
     * @param moduleId
     * @param dataPerm
     * @return
     */
    BigDecimal getTotal(@Param("query") QueryRecordModel query,
                        @Param("moduleId")Integer moduleId,@Param("perm") DataPermModel dataPerm);
}
