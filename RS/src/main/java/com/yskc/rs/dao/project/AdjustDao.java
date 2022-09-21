package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AdjustEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/8/26 13:52
 * description:
 */
@Repository
public interface AdjustDao extends BaseMapper<AdjustEntity> {
    /**
     * 根据材料id和类型获取误差费用
     * @param materialIds
     * @param rdTypes
     * @return
     */
    List<AdjustEntity> getByTypeIds(@Param("materialIds") List<Integer> materialIds, @Param("rdTypes") List<Integer> rdTypes);

    Integer insertOrUpdate(@Param("adjustEntities") List<AdjustEntity> adjustEntities);

}
