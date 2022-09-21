package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.VoucherEntity;
import com.yskc.rs.models.voucher.QueryVoucherModel;
import com.yskc.rs.models.voucher.VoucherModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/14 14:54
 * description:
 */
@Repository
public interface VoucherDao extends BaseMapper<VoucherEntity> {

    List<VoucherModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryVoucherModel query);

    VoucherEntity checkVoucherNo(@Param("voucherNo") String voucherNo, @Param("companyId") Integer companyId);

    List<VoucherEntity> getListByVoucherNos(@Param("voucherNos") List<String> voucherNos, @Param("companyId") Integer companyId);

    Integer insertList(@Param("voucherEntities") List<VoucherEntity> voucherEntities);

    Integer updateList(@Param("voucherEntities") List<VoucherEntity> voucherEntities);

}
