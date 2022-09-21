package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.PatentFileEntity;
import com.yskc.rs.models.PatentPlan.PatentFileModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 15:56
 * @Description:
 */
@Repository
public interface PatentFileDao extends BaseMapper<PatentFileEntity> {
    /**
     * 获取专利文件
     * @param patentId
     * @param patentNo
     * @return
     */
    List<PatentFileModel> getPatentFiles(@Param("patentId") Integer patentId, @Param("patentNo") String patentNo);
}
