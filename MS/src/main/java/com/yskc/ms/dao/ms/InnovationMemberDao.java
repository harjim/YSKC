package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.InnovationMember;
import com.yskc.ms.models.innovationproject.InnovationMemberSelectModel;
import com.yskc.ms.models.project.RsProjectSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-26 11:35
 * @Description: 创新项目成员daoceng
 */
@Repository
public interface InnovationMemberDao extends BaseMapper<InnovationMember> {

    /**
     * 删除成员
     *
     * @param innovationIds
     * @param mTypes
     * @return
     */
    int deleteMembers(@Param("innovationIds") List<Integer> innovationIds, @Param("mTypes") List<Integer> mTypes);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<InnovationMember> list);

    /**
     * 获取项目组成员
     *
     * @param companyId
     * @param year
     * @param mType
     * @return
     */
    List<InnovationMemberSelectModel> getMemberListByCompany(@Param("companyId") Integer companyId,
                                                             @Param("year") Integer year, @Param("mType") Integer mType);

    /**
     * 获取项目负责人
     *
     * @param list
     * @param mType
     * @return
     */
    List<RsProjectSummaryModel> getMasters(@Param("list") List<RsProjectSummaryModel> list, @Param("mType") Integer mType);

    /**
     * 获取成员
     *
     * @param innovationId
     * @return
     */
    List<InnovationMember> getMembers(@Param("innovationId") Integer innovationId);
}
