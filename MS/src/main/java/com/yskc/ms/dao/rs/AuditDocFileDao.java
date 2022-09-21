package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.AuditDocFileEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/12 13:56
 * @Description:
 */
@Repository
public interface AuditDocFileDao extends BaseMapper<AuditDocFileEntity> {
    /**
     * 获取文件审核状态
     *
     * @param projectId
     * @param docFileIds
     * @return
     */
    List<AuditDocFileEntity> getDocAuditStatus(@Param("projectId") Integer projectId, @Param("docFileIds") List<Integer> docFileIds);
}
