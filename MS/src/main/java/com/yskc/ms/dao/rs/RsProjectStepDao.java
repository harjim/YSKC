package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsProjectStepEntity;
import com.yskc.ms.models.project.StepTreeModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/19 17:16
 */
@Repository
public interface RsProjectStepDao extends BaseMapper<RsProjectStepEntity> {
    /**
     * 根据项目类型id获取父节点
     * @param productId
     * @return
     */
    RsProjectStepEntity getStepByProductId(@Param("productId") Integer productId, @Param("parentId")Integer parentId);

    /**
     * 查询父节点下的子节点
     * @param parentId
     * @return
     */
    List<RsProjectStepEntity> getListByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据类型获取所有步骤
     * @param productId
     * @return
     */
    List<StepTreeModel> queryStepTree(@Param("productId") Integer productId);

    /**
     * 删除该类型下所有步骤
     * @param productId
     * @return
     */
    Integer delStepsByProductId(@Param("productId") Integer productId);

    Integer addStepList(@Param("rsProjectStepEntities") List<RsProjectStepEntity> rsProjectStepEntities);

    /**
     * 批量更新父节点id
     * @param rsProjectStepEntities
     * @return
     */
    Integer updateList(@Param("rsProjectStepEntities") List<RsProjectStepEntity> rsProjectStepEntities);

}
