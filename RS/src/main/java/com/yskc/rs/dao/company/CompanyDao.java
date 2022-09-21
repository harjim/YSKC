package com.yskc.rs.dao.company;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.models.company.CompanyMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-09 10:13:52
 */
@Repository
public interface CompanyDao extends BaseMapper<CompanyEntity> {

	CompanyEntity getCompanyInfo(@Param("companyName") String companyName);

    CompanyEntity registerCheckCompany(@Param("companyName") String companyName);

	/**
	 * 获取用户公司信息
	 * @param companyId
	 * @return
	 */
	CompanyMember getCompanyMember(@Param("companyId") Integer companyId);

	/**
	 * 获取辅助帐info
	 * @param companyId
	 * @return
	 */
	CompanyEntity getGeneralLedgerInfo(@Param("companyId") Integer companyId);

	/**
	 * count 存在的子公司个数
	 * @param childId
	 * @param fullPath
	 * @return
	 */
    int countChild(@Param("childId") Integer childId,@Param("fullPath") String fullPath);

	/**
	 * 注册时间
	 * @param id
	 * @return
	 */
	Date getRegisterTime(@Param("id") Integer id);

	/**
	 * 获取公司信息是否完善
	 * @param companyId
	 * @return
	 */
    Boolean getCompanyFinished(@Param("companyId") Integer companyId);

}
