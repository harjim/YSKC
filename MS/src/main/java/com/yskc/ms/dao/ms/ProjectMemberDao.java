package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.common.model.ProductMtype;
import com.yskc.ms.entity.ms.ProjectMember;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import com.yskc.ms.models.project.ProjectMembersModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberDao extends BaseMapper<ProjectMember> {
    /**
     * 批量插入
     *
     * @param insertList
     */
    void addMemberList(@Param("insertList") List<ProjectMember> insertList);

    /**
     * 批量更新
     *
     * @param updateList
     */
    void updateMemberList(@Param("updateList") List<ProjectMember> updateList);

    List<ProjectMemberModel> queryMemberByIds(@Param("ids") List<Integer> ids);

    void deleteByProjectId(@Param("projectId") Integer projectId);

    void deleteByIds(@Param("deleteMember") Integer[] deleteMember);

    /**
     * 获取存在的用户id （memberId）
     *
     * @param memberIds
     * @param customerId
     * @return
     */
    List<Integer> getExistUserIds(@Param("customerId") Integer customerId, @Param("memberIds") List<Integer> memberIds);

    /**
     * 通过项目id/mType获取数据
     *
     * @param projectId
     * @param mTypes
     * @return
     */

    List<ProjectMember> getByProjectIdAndType(@Param("projectId") Integer projectId, @Param("mTypes") List<Integer> mTypes);

    /**
     * 根据人员类型和项目ID批量修改
     *
     * @param updateList
     */
    void updateMemberListByMType(@Param("updateList") List<ProjectMember> updateList);

    ProjectMember queryTechByProjectId(@Param("projectId") Integer projectId);

    ProjectMember queryFinanceByProjectId(@Param("projectId") Integer projectId);

    List<ProjectMemberModel> getMemberList(@Param("customerIds") List<Integer> customerIds, @Param("projectIds") List<Integer> projectIds, @Param("mTypes") List<Integer> mTypes);

    void batchUpdateMembers(@Param("projectId") Integer projectId, @Param("mTypes") List<Integer> mTypes, @Param("memberList") List<ProjectMemberModel> memberList);

    List<ProductMtype> getMTypesByCustomerId(@Param("customerId") Integer customerId, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 删除项目（mType）人员
     *
     * @param mTypes
     * @param projectIds
     * @return
     */
    int deleteMember(@Param("mTypes") List<Integer> mTypes, @Param("projectIds") List<Integer> projectIds);

    /**
     * 根据项目ids获取所有技术人员
     *
     * @param projectIds
     * @return
     */
    List<ProjectMembersModel> getByProjectIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目成员（该年pt.productType in (1,2)）
     * @param customerId
     * @param year
     * @return
     */
    List<ProjectMember> getMembers(@Param("customerId") Integer customerId,@Param("year") Integer year);
}
