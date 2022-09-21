package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.EquipmentUsedEntity;
import com.yskc.docservice.entity.rs.project.ProjectEquipmentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-13 18:13:59
 */
@Repository
public interface EquipmentUsedDao extends BaseMapper<EquipmentUsedEntity> {

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<EquipmentUsedEntity> list);

    /**
     * 获取使用过的工时记录
     * @param list
     * @param companyId
     * @return
     */
    List<EquipmentUsedEntity> getUseless(@Param("companyId")Integer companyId,@Param("list") List<ProjectEquipmentEntity> list);
}
