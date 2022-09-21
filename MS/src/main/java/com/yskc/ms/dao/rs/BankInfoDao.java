package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.BankInfoEntity;
import com.yskc.ms.models.company.BankInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by hck
 * on 2020/12/4 14:23
 * description:
 */
@Repository
public interface BankInfoDao extends BaseMapper<BankInfoEntity> {
    /**
     * 获取公司银行账户信息
     *
     * @param companyId
     * @return
     */
    BankInfoModel getByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 通过年获取
     *
     * @param companyId
     * @param year
     * @return
     */
    BankInfoEntity getByYear(@Param("companyId") Integer companyId,@Param("year") Integer year);
}
