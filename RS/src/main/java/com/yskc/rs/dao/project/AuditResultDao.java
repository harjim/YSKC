package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.ResultAuditEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/9/3 15:29
 * @Description:
 */
@Repository
public interface AuditResultDao extends BaseMapper<ResultAuditEntity> {

    /**
     * 获取不能提交审核的研发成果
     * @param documentIds
     * @param moduleId
     * @return
     */
    List<ResultAuditEntity> getResultAudits(@Param("documentIds") List<Integer> documentIds, @Param("moduleId") Integer moduleId);

    /**
     * 插入并更新研发成果审核
     * @param audits
     * @return
     */
    Integer insertOrUpdate(@Param("audits") List<ResultAuditEntity> audits);

    /**
     * 获取审核状态
     * @param id
     * @return
     */
    Integer getStatus(@Param("id") Integer id);
}
