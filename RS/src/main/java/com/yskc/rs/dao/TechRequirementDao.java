package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.TechRequirement;
import com.yskc.rs.models.techrequirement.QueryTechRequirement;
import com.yskc.rs.models.techrequirement.TechRequirementModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:21
 * @Description: 技术需求dao层
 */
@Repository
public interface TechRequirementDao extends BaseMapper<TechRequirement> {
    /**
     * 获取技术需求列表
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<TechRequirementModel> getList(@Param("page") Pagination page,
                                       @Param("query") QueryTechRequirement query,
                                       @Param("companyId") Integer companyId);

    /**
     * 获取附件地址
     *
     * @param id
     * @return
     */
    String getFilePath(@Param("id") Integer id);

    /**
     * 是否存在id
     *
     * @param id
     * @return
     */
    Integer checkExistId(@Param("id") Integer id);

    /**
     * 作废
     * @param ids
     * @param userId
     * @param msUserId
     * @param now
     * @param status
     * @return
     */
    int updateStatus(@Param("ids") List<Integer> ids,
                         @Param("userId") Integer userId,
                         @Param("msUserId") Integer msUserId,
                         @Param("now") Date now,@Param("status") Integer status);
}
