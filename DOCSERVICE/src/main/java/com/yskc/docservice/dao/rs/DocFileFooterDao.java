package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.DocFileFooterEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by hck
 * on 2021/2/19 9:30
 * description:
 */
@Repository
public interface DocFileFooterDao extends BaseMapper<DocFileFooterEntity> {
    /**
     * 根据项目获取页脚设置
     * @param projectId
     * @return
     */
    DocFileFooterEntity getFooter(@Param("projectId") Integer projectId, @Param("year") Integer year);

    Integer insertList(@Param("list")List<DocFileFooterEntity> list);
}
