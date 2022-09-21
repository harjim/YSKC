package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectDocFileEntity;
import com.yskc.docservice.models.rs.docfile.PDataListFormModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectDocFileDao extends BaseMapper<ProjectDocFileEntity> {

    List<PDataListFormModel> queryFileList(@Param("projectId") Integer projectId, @Param("pDocFileIds") List<Integer> pDocFileIds);
}
