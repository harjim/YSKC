package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.DocFileTemplateEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: hck
 * @DateTime: 2021/5/12 11:19
 * @Description:
 */
@Repository
public interface DocFileTemplateDao extends BaseMapper<DocFileTemplateEntity> {
    /**
     * 获取过程文件默认版本
     *
     * @param docFileId
     * @return
     */
    DocFileTemplateEntity getByDocId(@Param("docFileId") Integer docFileId);
}
