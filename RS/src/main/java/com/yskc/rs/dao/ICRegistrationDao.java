package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.ICRegistrationEntity;
import com.yskc.rs.models.ICRegistrationModel;
import com.yskc.rs.models.QueryICRegistrationModel;
import com.yskc.rs.models.SoftRegistrationModel;
import io.swagger.models.auth.In;
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
     * 校验设计登记号唯一
     * @param companyId
     * @param registerNo
     * @param id
     * @return
     */
    List<String> checkRegisterNo(@Param("companyId") Integer companyId, @Param("registerNo") String registerNo,@Param("id") Integer id);

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
    List<ICRegistrationModel> getList(@Param("page") Pagination page, @Param("model") QueryICRegistrationModel model);

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

    /**
     * 单个编辑
     * @param entity
     * @return
     */
    Integer editIC(@Param("entity") ICRegistrationEntity entity);

    /**
     * 获取单个文本信息
     * @param id
     * @return
     */
    ICRegistrationModel getText(@Param("id") Integer id);
}
