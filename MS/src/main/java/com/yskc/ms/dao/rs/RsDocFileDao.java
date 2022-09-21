package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.DocFileEntity;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import org.apache.ibatis.annotations.Param;

public interface RsDocFileDao extends BaseMapper<DocFileEntity> {

    Integer updateDocFile(@Param("model") RsDocFileTemplateModel model);

}
