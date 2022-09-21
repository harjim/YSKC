package com.yskc.docservice.dao.rs.init;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.init.InitMemberEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.initmember.InitMemberListModel;
import com.yskc.docservice.models.rs.initmember.InitMemberModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-23 16:40:58
 */
@Repository
public interface InitMemberDao extends BaseMapper<InitMemberEntity> {

    /**
     * 批量新增人员
     *
     * @param initMemberList
     * @return
     */
    int addbatch(@Param("initMemberList") List<InitMemberEntity> initMemberList);

    /**
     * 清除项目负责人（按年）
     * @param projectIds
     * @param year
     * @return
     */
    Boolean cleanMasters(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year,
                         @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date);

    /**
     * 删除项目负责人
     * @param dels
     * @param year
     * @return
     */
    Integer delMasterByProject(@Param("dels") List<ProjectEntity> dels, @Param("year") Integer year);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<InitMemberEntity> list);
    /**
     * 通过enumber获取
     *
     * @param companyId
     * @param projectId
     * @param enumbers
     * @param year      在业务未完成修改前可空
     * @return
     */
    List<InitMemberEntity> getByEnumbers(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("enumbers") List<String> enumbers, @Param("year") Integer year);

    /**
     * 按年获取项目负责人
     *
     * @param projectId
     * @param year
     * @return
     */
    InitMemberEntity getMasterByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 根据项目id获取项目成员信息
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<InitMemberListModel> getStaffInfoList(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId, @Param("year") Integer year, @Param("memberIds") List<Integer> memberIds);

    /**
     * 获取项目组成员(名称，limit20)
     *
     * @param projectId
     * @return
     */
    List<InitMemberModel> getMemberEname(@Param("projectId") Integer projectId, @Param("year") Integer year);

    List<InitMemberModel> getMemberEnames(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);
}
