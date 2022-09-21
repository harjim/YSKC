package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ProjectStatus;
import com.yskc.ms.models.rs.ProjectBaseStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-15 11:49
 * @Description: 项目状态 dao层(月：初始，提交，拒绝，定稿，撤回）
 */
@Repository
public interface ProjectStatusDao extends BaseMapper<ProjectStatus> {
    /**
     * 查询存在的数据
     *
     * @param audits
     * @return
     */
    List<ProjectStatus> getExist(@Param("audits") List<ProjectBaseStatusModel> audits);

    /**
     * 更新状态
     *
     * @param existList
     * @param status
     * @param now
     * @param msUserId
     * @return
     */
    int updateStatus(@Param("list") List<ProjectStatus> existList, @Param("status") Integer status, @Param("now") Date now, @Param("msUserId") Integer msUserId);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<ProjectStatus> list);

    /**
     * 获取项目状态
     *
     * @param audits
     * @return
     */
    List<ProjectStatus> getProjectStatus(@Param("list") List<ProjectBaseStatusModel> audits);
}
