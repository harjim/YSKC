package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.CheckPaymentRdEntity;
import com.yskc.ms.models.checkPayment.PaymentProjectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 查新付费rd dao
 *
 * @author wjy
 */
@Repository
public interface CheckPaymentRdDao extends BaseMapper<CheckPaymentRdEntity> {
    /**
     * 查新付费rd列
     * @param checkPaymentIds
     * @return
     */
    List<PaymentProjectModel> getProjectList(@Param("checkPaymentIds") Set<Integer> checkPaymentIds);

    /**
     * 添加查新付费rd
     * @param list
     * @return
     */
    Integer addProjectList(@Param("list") List<CheckPaymentRdEntity> list);

    /**
     * 编辑查新付费rd
     * @param list
     * @return
     */
    Integer editProjectList(@Param("list") List<CheckPaymentRdEntity> list);

    /**
     * 编辑时删除
     * @param ids
     * @param checkPaymentId
     * @return
     */
    Integer delByIds(@Param("ids")List<Integer> ids,@Param("checkPaymentId")Integer checkPaymentId);

    /**
     * 根据CheckPaymentId删除
     * @param ids
     * @return
     */
    Integer delByCheckPaymentIds(@Param("ids")List<Integer> ids);
}
