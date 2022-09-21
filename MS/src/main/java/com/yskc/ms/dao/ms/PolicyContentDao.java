package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PolicyContent;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.policy.PolicyContentModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-25 09:04
 * @Description: 政策内容dao层
 */
@Repository
public interface PolicyContentDao extends BaseMapper<PolicyContent> {
    /**
     * 获取政策内容列表
     *
     * @param page
     * @param query
     * @return
     */
    List<PolicyContentModel> getList(@Param("page") Pagination page, @Param("query") QueryPolicyContent query,
                                     @Param("dataPerm") DataPermModel dataPerm,
                                     @Param("userId") Integer userId);

    /**
     * 根据sourceIds 删除政策内容
     *
     * @param sourceIds
     * @return
     */
    int deleteBySourceIds(@Param("sourceIds") List<Integer> sourceIds);
}
