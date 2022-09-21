package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.PatentPlanProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/8/23 10:28
 * @Description:
 */
@Repository
public interface PatentPlanProcessDao extends BaseMapper<PatentPlanProcess> {
    /**
     * 获取专利进度
     *
     * @param patentPlanIds
     * @return
     */
    List<PatentPlanProcess> getByPatent(@Param("patentPlanIds") List<Integer> patentPlanIds);

    /**
     * 批量新增节点记录
     * @param planProcesses
     * @return
     */
    int insertBatch(@Param("planProcesses") List<PatentPlanProcess> planProcesses);
}
