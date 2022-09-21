package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.WorkItemEntity;
import com.yskc.ms.entity.ms.WorkRecordEntity;
import com.yskc.ms.models.serviceRecord.WorkItemModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Created by wjy
 * on 2020/12/15 11:00
 * description:
 */
@Repository
public interface WorkItemDao extends BaseMapper<WorkItemEntity> {
    /**
     * 添加事项
     * @param list
     * @return
     */
    Integer addItem(@Param("list")List<WorkItemEntity> list);

    /**
     * 修改事项
     * @param list
     * @return
     */
    Integer updateItem(@Param("list")List<WorkItemEntity> list);

    /**
     * 根据id删除事项
     * @param ids
     * @param recordId
     * @return
     */
    Integer delItem(@Param("ids")List<Integer> ids,@Param("recordId")Integer recordId);

    /**
     * 根据recordId删除
     * @param ids
     * @return
     */
    Integer delItemByRecord(@Param("ids")List<Integer> ids);

    /**
     * 获取事项列
     * @param recordIds
     * @return
     */
    List<WorkItemModel> getItemList(@Param("recordIds")Set<Integer> recordIds);

}
