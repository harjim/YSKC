package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.CompanyEntity;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.role.RsRoleModel;
import com.yskc.ms.models.company.CompanyInfoModel;
import com.yskc.ms.models.company.QueryCompanyModel;
import com.yskc.ms.models.params.CompanyParams;
import com.yskc.ms.models.patent.PcompanyModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-09 10:13:52
 */
@Repository
public interface CompanyDao extends BaseMapper<CompanyEntity> {

    /**
     * 查询客户
     *
     * @param page
     * @param query
     * @return
     */
    List<CompanyModel> queryCompanyList(@Param("page") Pagination page, @Param("query") QueryCompanyModel query);

    List<CompanyModel> getCompanyList(Pagination pagination, @Param("params") CompanyParams params);

    CompanyEntity getCompanyInfo(String companyName, Integer id);

    Boolean updateCompanyName(@Param("id")Integer id,@Param("userId")Integer userId,@Param("companyName")String companyName);


    List<RsRoleModel> queryRoles(@Param("companyIds") List<Integer> companyIds);

    List<PcompanyModel> getCompanyById(@Param("companyIds") Integer[] companyIds);


    CompanyInfoModel getInfo(@Param("companyId") Integer companyId);

    @Options(useGeneratedKeys = true)
    Integer insertBatch(List<CompanyEntity> list);

    /**
     * 更新所属集团
     * @param msUserId
     * @param now
     * @param companyType
     * @param groupId
     * @param ids
     * @return
     */
    Integer updateGroups(@Param("msUserId") Integer msUserId, @Param("now") Date now,@Param("companyType")Integer companyType,
                        @Param("groupId") Integer groupId,@Param("ids")List<Integer> ids);

    Integer upNotCompany(@Param("company") CompanyEntity company,@Param("ids")List<Integer> ids,@Param("companyType") Integer companyType);

    String getLogo(@Param("companyId") Integer companyId);

    /**
     *
     * @param list
     * @return
     */
    List<CompanyModel> getCompanyByNames(@Param("list")List<String> list);
}
