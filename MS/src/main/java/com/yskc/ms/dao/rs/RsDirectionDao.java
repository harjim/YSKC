package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsDirectionEntity;
import com.yskc.ms.models.rs.RsDirectionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/13 8:41
 * description:
 */
@Repository
public interface RsDirectionDao extends BaseMapper<RsDirectionEntity> {

    Integer insertList(@Param("directionEntities") List<RsDirectionEntity> directionEntities);

    /**
     * 根据申报项目id获取方向集合
     * @param productIds
     * @return
     */
    List<RsDirectionEntity> getByProductId(@Param("productIds") List<Integer> productIds);

    Integer updateList(@Param("updateList") List<RsDirectionModel> updateList, @Param("date") Date date, @Param("userId") Integer userId);

    /**
     * 根据申报项目id删除方向
     * @param ids
     * @return
     */
    Integer delByProductId(@Param("ids") List<Integer> ids);

    /**
     * 根据申报项目获取所有方向
     * @param ids
     * @return
     */
    List<RsDirectionModel> getListByProduct(@Param("ids") List<Integer> ids);

}
