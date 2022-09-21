package com.yskc.rs.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.InsuranceConfigEntity;
import com.yskc.rs.models.InsuranceConfig.InsuranceConfigModel;
/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 14:56:07
 */
@Repository
public interface InsuranceConfigDao extends BaseMapper<InsuranceConfigEntity> {

	List<InsuranceConfigEntity> queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(@Param("companyId") Integer companyId, @Param("deptId") Integer deptId, @Param("enumber") String enumber);

	List<InsuranceConfigModel> queryStartDate(@Param("month") Date month, @Param("companyId") Integer companyId, @Param("deptId") Integer deptId, @Param("enumber") String enumber, @Param("id") Integer id);

	List<InsuranceConfigEntity> queryContainStartAndEnd(@Param("startMonth") Date startMonth, @Param("companyId") Integer companyId, @Param("deptId") Integer deptId, @Param("enumber") String enumber, @Param("id") Integer id);

	void updateEndMonth(@Param("model")InsuranceConfigEntity model);

	List<InsuranceConfigEntity> queryInsuranceConfigByCompanyIdAndDeptIdAndEnumberAndStartMonthAndEndMonth(@Param("companyId") Integer companyId, @Param("deptId") Integer deptId,
																										   @Param("enumber") String enumber, @Param("month") Date month);
	
}
