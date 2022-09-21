package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.RdVoucherEntity;
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

    Integer insertList(@Param("rdVouchers") List<RdVoucherEntity> rdVouchers);

    Integer delList(@Param("voucherNos") List<String> voucherNos, @Param("companyId") Integer companyId);
}
