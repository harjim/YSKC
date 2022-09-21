package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsDocFileTemplateEntity;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import com.yskc.ms.models.rs.RsDocFilesModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RsDocFileTemplateDao extends BaseMapper<RsDocFileTemplateEntity> {
    /**
     * 获取模板id获取数据
     *
     * @param docTemplateFileId
     * @return
     */
    RsDocFileTemplateModel getData(@Param("docTemplateFileId") Integer docTemplateFileId);

    /**
     * 根据文件ids获取模板
     *
     * @param docFileIds
     * @return
     */
    List<RsDocFileTemplateModel> getDataList(@Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取所有模板
     *
     * @param page
     * @param docName
     * @return
     */
    List<RsDocFilesModel> getDocFileList(@Param("page") Pagination page, @Param("docName") String docName);

    /**
     * 删除模板
     *
     * @param templateId
     * @return
     */
    Integer delTemplate(@Param("templateId") Integer templateId);

    List<RsDocFileTemplateEntity> findDocFileId(@Param("docFileId") Integer docFileId);

    Integer updateDefaultTemplate(@Param("model") RsDocFileTemplateModel model);

    Integer updateDefault(@Param("model") RsDocFileTemplateModel model);
}
