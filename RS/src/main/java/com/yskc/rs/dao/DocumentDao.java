package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.DocumentEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.models.document.DocumentModel;
import com.yskc.rs.models.document.ProjectDocModel;
import com.yskc.rs.models.document.TemplateModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-27 10:46:24
 */
@Repository
public interface DocumentDao extends BaseMapper<DocumentEntity> {

	List<DocumentModel> getDocData(@Param("id") Integer id, @Param("projectId") Integer projectId, @Param("companyId") Integer companyId);

    List<TemplateModel> getTemplateList();

    List<ProjectDocModel> docList(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId);

    void addDocList(@Param("docModelList") List<ProjectDocModel> docModelList, @Param("companyId") Integer companyId, @Param("userId") Integer userId);

    void delDoc(@Param("ids") Integer[] ids, @Param("companyId") Integer companyId);

    List<StageEntity> getStageList(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId);


}
