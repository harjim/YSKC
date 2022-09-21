package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.ReportEntity;
import com.yskc.rs.models.project.PlanInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-12 16:17:24
 */
@Repository
public interface ReportDao extends BaseMapper<ReportEntity> {

    ReportEntity queryByYear(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取年技术中心简介
     *
     * @param companyId
     * @param year
     * @return
     */
    String getTechIntro(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取预计归集数
     *
     * @param companyId
     * @param year
     * @return
     */
    PlanInfo getPlanInfo(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取预计归集数无年份限制
     *
     * @param companyId
     * @return
     */
    List<String> getPlanInfoNoYear(@Param("companyId") Integer companyId);

    /**
     * 保存cnt或TechInto
     *
     * @param report
     * @return
     */
    int saveCntOrTechIntro(@Param("report") ReportEntity report);

    /**
     * 获取人员总数
     *
     * @param year
     * @param companyId
     * @return
     */
    Integer  getEmployeeAmount(@Param("year") Integer year,@Param("companyId") Integer companyId);
}
