package com.yskc.docservice.dao.rs.project;

import com.yskc.docservice.models.rs.DocFileDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:07
 * @Description: 项目文档dao层
 */
@Repository
public interface ProjectDocFileDataDao {
    /**
     * 获取项目文档数据
     *
     * @param pDocFileId
     * @return
     */
    DocFileDataModel getData(@Param("pDocFileId") Integer pDocFileId);
}
