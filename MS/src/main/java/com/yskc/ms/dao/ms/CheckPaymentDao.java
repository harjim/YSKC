package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.CheckPaymentEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.checkPayment.CheckPaymentModel;
import com.yskc.ms.models.checkPayment.QueryCheckModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查新付费dao
 *
 * @author wjy
 */
@Repository
public interface CheckPaymentDao extends BaseMapper<CheckPaymentEntity> {
    /**
     * 根据条件查询查新付费列表
     * @param page
     * @param moduleId
     * @param query
     * @param dataPerm
     * @return
     */
   List<CheckPaymentModel> getList(@Param("page") Pagination page, @Param("moduleId") Integer moduleId,
                                   @Param("query") QueryCheckModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 查询查新付费信息
     * @param id
     * @param moduleId
     * @return
     */
    CheckPaymentModel getInfo(@Param("id")Integer id, @Param("moduleId")Integer moduleId);

}
