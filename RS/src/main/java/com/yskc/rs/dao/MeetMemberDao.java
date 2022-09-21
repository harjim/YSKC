package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.MeetMemberEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: hck
 * @DateTime: 2021/8/26 17:09
 * @Description:
 */
@Repository
public interface MeetMemberDao extends BaseMapper<MeetMemberEntity>{
    /**
     * 获取项目会议纪要参会人员
     * @param projectId
     * @return
     */
    MeetMemberEntity getByProject(@Param("projectId") Integer projectId);
}
