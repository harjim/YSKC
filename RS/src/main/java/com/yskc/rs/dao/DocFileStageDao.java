package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.DocFileStageEntity;
import com.yskc.rs.models.docFile.DocFileStageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/4/20 8:34
 * @Description:
 */
@Repository
public interface DocFileStageDao extends BaseMapper<DocFileStageEntity> {
    /**
     * 获取阶段配置
     * @param stageKeys
     * @param autoAdd 有此参数查询需自动填加的文件
     * @param type 有此参数查询需自动填加的文件
     * @return
     */
    List<DocFileStageModel> getByStages(@Param("stageKeys") List<String> stageKeys, @Param("autoAdd") Integer autoAdd,
                                        @Param("type") Integer type);

    /**
     * 根据文件类型id获取阶段配置
     * @param docFileId
     * @return
     */
    DocFileStageEntity getByDocFileId(@Param("docFileId") Integer docFileId);
}
