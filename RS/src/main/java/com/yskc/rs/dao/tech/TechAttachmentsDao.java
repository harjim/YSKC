package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.TechAttachmentsEntity;
import com.yskc.rs.models.tech.TechAttachmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/8/14 13:31
 * description:
 */
@Repository
public interface TechAttachmentsDao extends BaseMapper<TechAttachmentsEntity> {

    List<TechAttachmentModel> getByProjectId(@Param("projectId") Integer projectId);
}
