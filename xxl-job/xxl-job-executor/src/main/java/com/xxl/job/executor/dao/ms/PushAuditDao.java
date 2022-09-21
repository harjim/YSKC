package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PushAuditEntity;
import com.xxl.job.executor.models.flowInstance.FlowInstanceProjectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-16 13:43
 * @Description: 推送审批消息dao层
 */
@Repository
public interface PushAuditDao extends BaseMapper<PushAuditEntity> {
    /**
     * 获取存在数据
     *
     * @param list
     * @return
     */
    List<PushAuditEntity> getExist(@Param("list") List<FlowInstanceProjectModel> list);

    /**
     * 批量添加DFF
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<PushAuditEntity> list);

    /**
     * 获取最后操作时间
     *
     * @return
     */
    Date getLastTime();
}
