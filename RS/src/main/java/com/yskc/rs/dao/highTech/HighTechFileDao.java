package com.yskc.rs.dao.highTech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.hightech.HighTechFileEntity;
import com.yskc.rs.models.hightech.HighTechFileModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 17:16
 * @Description:
 */
@Repository
public interface HighTechFileDao extends BaseMapper<HighTechFileEntity> {
    /**
     * 获取上传文件列表
     * @param highTechId
     * @param companyId
     * @return
     */
    List<HighTechFileModel> getByTech(@Param("highTechId") Integer highTechId, @Param("companyId") Integer companyId);

    /**
     * 根据高品删除文件
     * @param highTechIds
     * @param companyId
     * @return
     */
    Integer delByTech(@Param("highTechIds") List<Integer> highTechIds, @Param("companyId") Integer companyId);
}
