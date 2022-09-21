package com.yskc.docservice.dao.rs.company;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.company.CompanyEntity;
import com.yskc.docservice.models.rs.company.CompanyMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-09 10:13:52
 */
@Repository
public interface CompanyDao extends BaseMapper<CompanyEntity> {

    /**
     * 获取用户公司信息
     *
     * @param companyId
     * @return
     */
    CompanyMember getCompanyMember(@Param("companyId") Integer companyId);

    String getCompanyName(@Param("companyId") Integer companyId);
}
