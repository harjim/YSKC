package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.DocFileEntity;
import com.yskc.rs.models.docFile.DocFileModel;
import com.yskc.rs.models.docFile.DocFileTemplateModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocFileDao extends BaseMapper<DocFileEntity> {

    List<DocFileModel> queryDocFile(@Param("stage") String stage);

    List<DocFileTemplateModel> queryTplByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取不能重复添加的文档
     * @return
     */
    List<Integer> getDocList(@Param("stageKey") String stageKey);

    /**
     * NR(Not required) 获取非必填的文件类型id
     * @return
     */
    List<Integer> getNRDocList();
}
