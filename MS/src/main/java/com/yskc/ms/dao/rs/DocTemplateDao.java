package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.DocTemplateEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-05 08:51
 * @Description: 模板管理Dao
 */
@Repository
public interface DocTemplateDao extends BaseMapper<DocTemplateEntity> {
    /**
     * 获取文档模板数据
     * @param page
     * @param docName
     * @return
     */
    List<DocTemplateEntity> queryDocTemplate(Pagination page, @Param("docName") String docName);
}
