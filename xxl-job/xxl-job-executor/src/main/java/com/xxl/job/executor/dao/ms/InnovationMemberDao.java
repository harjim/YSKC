package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.InnovationMember;
import com.xxl.job.executor.models.innovationmember.InnovationMemberModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InnovationMemberDao extends BaseMapper<InnovationMember> {
    void insertOrUpdate(@Param("innovationMembers") List<InnovationMember> innovationMembers);

    /**
     * 根据创建时间获取相关信息
     * @param lastTime
     * @return
     */
    List<InnovationMemberModel> getMessage(@Param("lastTime")Date lastTime);
}
