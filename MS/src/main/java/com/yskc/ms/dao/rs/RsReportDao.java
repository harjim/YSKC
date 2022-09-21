package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ReportEntity;
import com.yskc.ms.models.project.ProjectProgressModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RsReportDao extends BaseMapper<ReportEntity> {

    List<ReportEntity> queryCnt(@Param("list")List<ProjectProgressModel> list);

    /**
     * 获取年技术中心简介
     *
     * @param companyId
     * @param year
     * @return
     */
    String getTechIntro(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
