package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.RdEmployeePlanEntity;
import com.yskc.docservice.models.rs.rdplan.RdPlanAllocationInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/16 17:18
 * description:
 */
@Repository
public interface RdEmployeePlanDao extends BaseMapper<RdEmployeePlanEntity> {
    /**
     * 批量插入或更新更新
     *
     * @param list
     * @return
     */
    Integer insertOrUpdate(@Param("list") List<RdEmployeePlanEntity> list);

    /**
     * 删除数据
     *
     * @param info
     * @return
     */
    int deleteData(@Param("info") RdPlanAllocationInfoModel info);
}
