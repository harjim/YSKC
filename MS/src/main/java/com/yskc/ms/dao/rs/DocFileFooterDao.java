package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.DocFileFooterEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by hck
 * on 2021/2/19 9:30
 * description:
 */
@Repository
public interface DocFileFooterDao extends BaseMapper<DocFileFooterEntity> {
    /**
     * 根据项目获取页脚设置
     *
     * @param projectId
     * @return
     */
    DocFileFooterEntity getFooter(@Param("projectId") Integer projectId, @Param("year") Integer year);


    Integer updateFooter(@Param("id") Integer id, @Param("msUserId") Integer msUserId, @Param("userId") Integer userId, @Param("date") Date date, @Param("approvalEnumber") String approvalEnumber, @Param("auditEnumber") String auditEnumber, @Param("toCompileEnumber") String toCompileEnumber);

}
