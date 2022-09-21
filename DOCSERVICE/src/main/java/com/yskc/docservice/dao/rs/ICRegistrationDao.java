package com.yskc.docservice.dao.rs;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.ICRegistrationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/11/2 14:39
 * @Description:集成电路设计类Dao层
 * @author: hsx
 */
@Repository
public interface ICRegistrationDao extends BaseMapper<ICRegistrationEntity> {

    /**
     * 查询公司下所有的集成电路设计
     * @param companyId
     * @return
     */
    List<ICRegistrationEntity> getListByCompany(@Param("companyId") Integer companyId);

    /**
     * 导入批量新增
     * @param icRegistrations
     * @param companyId
     * @return
     */
    Integer insertICs(@Param("icRegistrations") List<ICRegistrationEntity> icRegistrations, @Param("companyId") Integer companyId);

    /**
     * 导入批量编辑
     * @param icRegistrations
     * @param companyId
     * @return
     */
    Integer updateICs(@Param("icRegistrations") List<ICRegistrationEntity> icRegistrations, @Param("companyId") Integer companyId);
}
