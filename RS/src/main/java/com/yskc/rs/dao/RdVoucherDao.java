package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.RdVoucherEntity;
import com.yskc.rs.models.voucher.ProjectVoucherModel;
import com.yskc.rs.models.voucher.RdVoucherModel;
import com.yskc.rs.models.voucher.VoucherModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/14 14:55
 * description:
 */
@Repository
public interface RdVoucherDao extends BaseMapper<RdVoucherEntity> {

    List<RdVoucherModel> getListByVoucher(@Param("voucherNos") List<String> voucherNos, @Param("companyId") Integer companyId);

    Integer insertList(@Param("rdVouchers") List<RdVoucherEntity> rdVouchers);

    Integer delByVoucherNo(@Param("voucherNo") String voucherNo, @Param("companyId") Integer companyId);

    List<RdVoucherEntity> getRdVoucherList(@Param("companyId") Integer companyId, @Param("voucherNo") String voucherNo);
   Integer updateList(@Param("model")VoucherModel model, @Param("projectIds")List<Integer> projectIds,@Param("date") Date date, @Param("userId")Integer userId,@Param("beginMoth")Date beginMoth,@Param("voucherNo")String voucherNo,@Param("companyId")Integer companyId);

    Integer delByProjectIds(@Param("voucherNo") String voucherNo, @Param("companyId") Integer companyId, @Param("projectIds") List<Integer> projectIds);

    List<RdVoucherEntity> getListByNos(@Param("voucherNos") List<String> voucherNos, @Param("companyId") Integer companyId);

    Integer delList(@Param("voucherNos") List<String> voucherNos, @Param("companyId") Integer companyId);

    /**
     * 获取研发凭证
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<ProjectVoucherModel> getRdVouchers(@Param("companyId") Integer companyId, @Param("beginMonth") Date beginMonth,
                                            @Param("endMonth") Date endMonth);

    /**
     * 根据项目获取研发凭证
     * @param projectIds
     * @param companyId
     * @return
     */
    List<RdVoucherEntity> getListByProject(@Param("projectIds") List<Integer> projectIds,@Param("companyId") Integer companyId);
}
