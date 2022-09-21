package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.models.rs.PDocFile;
import com.yskc.docservice.models.rs.outsourcing.ProjectOutsourcingModel;
import com.yskc.docservice.models.rs.project.ProjectDetailModel;
import com.yskc.docservice.models.rs.project.ProjectListModel;
import com.yskc.docservice.models.rs.project.ProjectYearModel;
import com.yskc.docservice.models.rs.project.RdStatusModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-15 10:15:45
 */
@Repository
public interface ProjectDao extends BaseMapper<ProjectEntity> {

    @Options(useGeneratedKeys = true)
    Integer insertProjectList(List<ProjectEntity> projectEntitys);

    Integer updateProjectList(@Param("projectEntitys") List<ProjectEntity> updateProjectEntitys);

    /**
     * 通过rdTitles获取数据
     *
     * @param rdTitles
     * @param companyId
     * @return
     */
    List<ProjectEntity> getByRdTitles(@Param("rdTitles") Set<String> rdTitles, @Param("companyId") Integer companyId);

    /**
     * 通过rdTitles获取基本信息及试制信息
     *
     * @param rdTitles
     * @param companyId
     * @return
     */
    List<ProjectEntity> getTrialByRdTitles(@Param("rdTitles") Set<String> rdTitles, @Param("companyId") Integer companyId);

    List<PDocFile> getDocFileList(@Param("docParam")DocParam docParam);

    /**
     * 根据年份获取项目列表(导入用,只查传进year当年)
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectListModel> getProjectsForImport(@Param("year") Integer year, @Param("companyId") Integer companyId);

    List<ProjectEntity> getRdTitle(@Param("rdTitles") Set<String> rdTitles, @Param("companyId") Integer companyId);

    /**
     * 获取公司所有项目
     *
     * @param companyId
     * @return
     */
    List<ProjectEntity> getCompanyProjectList(@Param("companyId") Integer companyId);

    /**
     * 获取委外项目
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectOutsourcingModel> getOutsourcingProject(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取项目信息
     * 根据rdTitle排序，rd有序
     *
     * @param ids
     * @return
     */
    List<ProjectEntity> getInfoByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取项目状态
     *
     * @param projectIds
     * @return
     */
    List<RdStatusModel> getRdsStatus(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目开展类型
     *
     * @param projectId
     * @return
     */
    ProjectDetailModel getProject(@Param("projectId") Integer projectId);

    /**
     * 获取父项目下的子项目列表
     *
     * @param parentId
     * @return
     */
    List<ProjectEntity> getChildrenById(@Param("parentId") Integer parentId);

    List<ProjectYearModel> getYearInfos(@Param("projectId")Integer projectId);
}
