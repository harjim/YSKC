package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.SoftRegistrationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/11/2 14:38
 * @Description:计算机软件著作类Dao层
 * @author: hsx
 */
@Repository
public interface SoftRegistrationDao extends BaseMapper<SoftRegistrationEntity> {

    /**
     * 查询公司下所有的计算机软件著作
     * @param companyId
     * @return
     */
    List<SoftRegistrationEntity> getListByCompany(@Param("companyId") Integer companyId);

    /**
     * 导入批量新增
     * @param softRegistrations
     * @param companyId
     * @return
     */
    Integer insertSRs(@Param("softRegistrations") List<SoftRegistrationEntity> softRegistrations, @Param("companyId") Integer companyId);

    /**
     * 导入批量编辑
     * @param softRegistrations
     * @param companyId
     * @return
     */
    Integer updateSRs(@Param("softRegistrations") List<SoftRegistrationEntity> softRegistrations, @Param("companyId") Integer companyId);
}
