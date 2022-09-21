package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.SoftRegistrationEntity;
import com.yskc.rs.models.QuerySoftRegistrationModel;
import com.yskc.rs.models.SoftRegistrationModel;
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
     * 校验设计登记号唯一
     * @param companyId
     * @param registerNo
     * @param id
     * @return
     */
    List<String> checkRegisterNo(@Param("companyId") Integer companyId, @Param("registerNo") String registerNo, @Param("id") Integer id);

    /**
     * 校验登记证书号唯一
     * @param companyId
     * @param certificateNo
     * @param id
     * @return
     */
    List<String> checkCertificateNo(@Param("companyId") Integer companyId, @Param("certificateNo") String certificateNo,@Param("id") Integer id);


    /**
     * 条件查询
     * @param page
     * @param model
     * @return
     */
    List<SoftRegistrationModel> getList(@Param("page") Pagination page, @Param("model") QuerySoftRegistrationModel model);

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

    /**
     * 单个编辑
     * @param entity
     * @return
     */
    Integer editSR(@Param("entity") SoftRegistrationEntity entity);

    /**
     * 获取单个文本信息
     * @param id
     * @return
     */
    SoftRegistrationModel getText(@Param("id") Integer id);
}
