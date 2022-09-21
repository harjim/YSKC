package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.DeclarationEntity;
import com.yskc.rs.models.tech.DeclarationInfo;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 技改申请报告
 * @author huronghua
 */
@Repository
public interface TechDeclarationDao extends BaseMapper<DeclarationEntity> {
    /**
     * 批量添加
     * @param declarationEntities
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addBatch(List<DeclarationEntity> declarationEntities);

    /**
     * 获取技改项目信息
     * @param companyId
     * @param projectId
     * @return
     */
    List<DeclarationInfo> getDeclarationInfoList(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 批量更新
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<DeclarationEntity> updateList);
}
