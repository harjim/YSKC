package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.VoucherEntity;
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

    List<VoucherEntity> getListByVoucherNos(@Param("voucherNos") List<String> voucherNos, @Param("companyId") Integer companyId);

    Integer insertList(@Param("voucherEntities") List<VoucherEntity> voucherEntities);

    Integer updateList(@Param("voucherEntities") List<VoucherEntity> voucherEntities);

}
