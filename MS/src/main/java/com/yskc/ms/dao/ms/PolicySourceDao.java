package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PolicySource;
import com.yskc.ms.entity.ms.PolicySourceUser;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.policy.PolicySourceModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import com.yskc.ms.models.policy.QuerySourceUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:22
 * @Description: 政策来源dao层
 */
@Repository
public interface PolicySourceDao extends BaseMapper<PolicySource> {

    /**
     * 政策来源列表
     *
     * @param page
     * @param query
     * @return
     */
    List<PolicySourceModel> getList(@Param("page") Pagination page, @Param("query") QueryPolicyContent query,@Param("userId") Integer userId);

    /**
     * 获取订阅者
     *
     * @param userId
     * @param sourceIds
     * @return
     */
    List<PolicySourceUser> getSubscription(@Param("userId") Integer userId, @Param("sourceIds") List<Integer> sourceIds);

    /**
     * 政策关注人员列表
     *
     * @param page
     * @param query
     * @return
     */
    List<DeptUserInfo> getSourceUserList(@Param("page") Pagination page, @Param("query") QuerySourceUser query, @Param("perm") DataPermModel perm);

    /**
     * 通过typeUrl获取政策id
     *
     * @param typeUrl
     * @return
     */
    Integer getByTypeUrl(@Param("typeUrl") String typeUrl);

    /**
     * 获取政策来源(单条)
     *
     * @param sourceId
     * @return
     */
    Map<String, Object> getSource(@Param("sourceId") Integer sourceId);
}
