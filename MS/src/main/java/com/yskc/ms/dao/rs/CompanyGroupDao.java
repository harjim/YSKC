package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.CompanyGroupEntity;
import com.yskc.ms.models.customer.FindCustomerModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @DateTime: 2022/1/6 9:08
 * @Description:
 * @author: hsx
 */
@Repository
public interface CompanyGroupDao extends BaseMapper<CompanyGroupEntity> {

    /**
     * 根据公司ID查询
     * @param ids
     * @return
     */
    List<CompanyGroupEntity> getByCompanyId(@Param("ids") List<Integer> ids);

    /**
     * 查询集团下的子公司
     * @param page
     * @param fullPath
     * @param companyId
     * @param companyName
     * @return
     */
    List<FindCustomerModel> getCompanyByFullPath(@Param("page") Pagination page,
                                                 @Param("fullPath") String fullPath,
                                                 @Param("companyId") Integer companyId,
                                                 @Param("companyName") String companyName);


    /**
     * 查询集团下的子公司
     * @param page
     * @param companyId
     * @param companyName
     * @return
     */
    List<FindCustomerModel> getCompanyByGroupId(@Param("page") Pagination page,
                                                 @Param("companyId") Integer companyId,
                                                 @Param("companyName") String companyName);

    /**
     * 根据子公司查询集团
     * @param companyIds
     * @return
     */
    List<FindCustomerModel> getGroupByCompanyId(@Param("companyIds") Set<Integer> companyIds);

    /**
     * 根据companyId和groupId获取公司信息
     * @param id
     * @return
     */
    List<CompanyGroupEntity> getByCompanyIdOrGroupId(@Param("id") Integer id);

    /**
     * 批量新增company_group表
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<CompanyGroupEntity> list);

    /**
     * 批量修改company_group表
     * @param entity
     * @param list
     * @return
     */
    int updateGroups(@Param("list") List<Integer> list, @Param("entity") CompanyGroupEntity entity);

    /**
     * 子集团升级为集团时，子公司的level和fullPath更改
     * @param fullPaths
     * @param entity
     * @return
     */
    int subsidiariesDemotion(@Param("fullPaths") List<String> fullPaths, @Param("entity") CompanyGroupEntity entity);

    /**
     * 删除子公司记录
     * @param ids
     * @return
     */
    int delByCompanyIds(@Param("ids") List<Integer> ids);

    /**
     * 子集团更改为子公司
     * @param entity
     * @param list
     * @return
     */
    int upNotGroup(@Param("entity") CompanyGroupEntity entity, @Param("companyIds") List<Integer> list);

    /**
     * 子集团升级为集团时，子公司的level和fullPath更改
     * @param fullPaths
     * @param entity
     * @return
     */
    int subsidiariesUpgrade(@Param("fullPaths") List<String> fullPaths, @Param("entity") CompanyGroupEntity entity);
}
