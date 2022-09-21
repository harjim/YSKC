package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.InitMemberEntity;
import com.yskc.ms.models.projectApproval.QueryProjectMemberModel;
import com.yskc.ms.models.rs.InitMemberListModel;
import com.yskc.ms.models.rs.InitMemberModel;
import com.yskc.ms.models.rs.QueryProjectInitMemberModel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/9/2 16:55
 * description:
 */
@Repository
public interface InitMemberDao extends BaseMapper<InitMemberEntity> {

    /**
     * 获取项目成员列表
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<InitMemberModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryProjectInitMemberModel query);

    /**
     * 根据项目id获取项目成员信息
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<InitMemberListModel> getStaffInfoList(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId,
                                               @Param("year") Integer year, @Param("memberIds") List<Integer> memberIds);

    /**
     * 获取项目组成员(名称，limit20)
     *
     * @param projectId
     * @return
     */
    List<InitMemberModel> getMemberEname(@Param("projectId") Integer projectId, @Param("year") Integer year);

    List<InitMemberModel> getMemberEnames(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);

    /**
     * 获取项目成员
     *
     * @param page
     * @param query
     * @return
     */
    List<InitMemberModel> getMemberList(@Param("page") Pagination page, @Param("query") QueryProjectMemberModel query);

    /**
     * 根据项目id获取项目成员信息
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<InitMemberListModel> getStaffsInfo(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId, @Param("year") Integer year, @Param("memberIds") Integer[] memberIds);

    /**
     * 获取项目负责人
     *
     * @param projectId
     * @param year
     * @return
     */
    InitMemberEntity getMasterByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 根据项目id和年份获取当前员工人数
     *
     * @param projectIds
     * @param year
     * @return
     */
    @MapKey("projectId")
    Map<Integer, Map<String,Long>> getMemberByProAndYear(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);

    /**
     * 获取项目成员列表
     * @param page
     * @param query
     * @return
     */
    List<InitMemberModel> getMembers(@Param("page") Pagination page,@Param("query") QueryProjectInitMemberModel query);
}
