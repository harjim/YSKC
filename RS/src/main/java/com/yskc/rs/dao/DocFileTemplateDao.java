package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.DocFileTemplateEntity;
import com.yskc.rs.models.docFile.DocFileTemplateModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/4/21 10:47
 */
@Repository
public interface DocFileTemplateDao extends BaseMapper<DocFileTemplateEntity> {
    /**
     * 获取过程文件默认版本
     * @param docFileId
     * @return
     */
    DocFileTemplateEntity getByDocId(@Param("docFileId") Integer docFileId);

    /**
     * 获取过程文件所有模板
     * @param docFileIds
     * @return
     */
    List<DocFileTemplateEntity> getTemplates(@Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取设备折旧模板
     * @return
     */
    List<DocFileTemplateModel> getEquipmentTemplate();
}
