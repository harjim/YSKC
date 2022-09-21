package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.StageEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-08 11:28:54
 */
@Repository
public interface StageDao extends BaseMapper<StageEntity> {


    /**
     * 获取阶段信息
     *
     * @param projectId
     * @param pDocFileId
     * @return
     */
    StageEntity getStageInfo(@Param("projectId") Integer projectId, @Param("pDocFileId") Integer pDocFileId);
    List<StageEntity> getStageList(@Param("projectId") Integer projectId);
}
