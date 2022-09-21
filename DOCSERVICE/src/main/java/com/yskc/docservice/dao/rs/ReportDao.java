package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.ReportEntity;
import com.yskc.docservice.models.rs.project.PlanInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDao  extends BaseMapper<ReportEntity> {
    /**
     * 获取预计归集数
     *
     * @param companyId
     * @param year
     * @return
     */
    PlanInfo getPlanInfo(@Param("companyId") Integer companyId, @Param("year") Integer year);

}
