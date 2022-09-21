package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsProductStageListEntity;
import com.yskc.ms.models.rs.RsProductStageListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/13 14:45
 * description:
 */
@Repository
public interface RsProductStageListDao extends BaseMapper<RsProductStageListEntity> {
    /**
     * 批量插入阶段资料
     * @param stageList
     * @return
     */
    Integer insertList(@Param("stageList") List<RsProductStageListEntity> stageList);

    /**
     * 获取方向所有配置的资料列表
     * @param directionIds
     * @return
     */
    List<RsProductStageListEntity> getByDirectionIds(@Param("directionIds") List<Integer> directionIds);

    /**
     * 删除配置文件类型
     * @param directionIds
     * @return
     */
    Integer delByDirection(@Param("directionIds") List<Integer> directionIds);

    /**
     * 根据阶段获取资料
     * @param stageIds
     * @return
     */
    List<RsProductStageListModel> getByStageId(@Param("stageIds") List<Integer> stageIds);

    /**
     * 根据阶段删除资料
     * @param stageIds
     * @return
     */
    Integer delByStageIds(@Param("stageIds") List<Integer> stageIds);



    /**
     * 批量更新
     * @param updateList
     * @param date
     * @param userId
     * @return
     */
    Integer updateList(@Param("updateList") List<RsProductStageListModel> updateList, @Param("date") Date date, @Param("userId") Integer userId);

    /**
     * 获取阶段类型的资料
     * @param stageId
     * @param itemType
     * @return
     */
    List<RsProductStageListModel> getByType(@Param("stageId") Integer stageId, @Param("itemType") Integer itemType);
}
