package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PatentDemand;
import com.xxl.job.executor.entity.ms.PatentDemandMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatentDemandDao  extends BaseMapper<PatentDemand> {
    void insertMembers(@Param("members")List<PatentDemandMember> members);
}
