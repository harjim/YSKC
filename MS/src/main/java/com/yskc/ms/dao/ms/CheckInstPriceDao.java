package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.CheckInstEntity;
import com.yskc.ms.entity.ms.CheckInstPriceEntity;
import com.yskc.ms.models.checkInst.CheckInstModel;
import com.yskc.ms.models.checkInst.CheckPriceModel;
import com.yskc.ms.models.checkInst.QueryCheckInstModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CheckInstPriceDao extends BaseMapper<CheckInstEntity> {
    /**
     * 添加查新单价列
     * @param list
     * @return
     */
    Integer insertPriceList(@Param("list")List<CheckInstPriceEntity> list);

    /**
     * 编辑查新单价列
     * @param list
     * @return
     */
    Integer updatePriceList(@Param("list")List<CheckInstPriceEntity> list);

    /**
     * 编辑时删除
     * @param ids
     * @return
     */
    Integer delPriceList(@Param("ids")List<Integer> ids);

    /**
     * 根据instId删除
     * @param instIds
     * @return
     */
    Integer delPriceByInst(@Param("instIds")List<Integer> instIds);

    /**
     * 查询查新单价列
     * @param instIds
     * @return
     */
    List<CheckPriceModel> getPriceList(@Param("instIds")Set<Integer> instIds);
}
