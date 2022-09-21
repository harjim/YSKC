package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.RsProjectMaster;
import com.yskc.ms.models.project.RsProjectSummaryModel;
import com.yskc.ms.models.project.RsProjectMasterModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/26 8:23
 * @Description:
 */
@Repository
public interface RsProjectMasterDao extends BaseMapper<RsProjectMaster> {
    /**
     * 获取项目负责人
     *
     * @param list
     * @return
     */
    List<RsProjectMaster> getMasters(@Param("list") List<RsProjectSummaryModel> list);

    /**
     * 获取项目负责人
     *
     * @param model
     * @return
     */
    RsProjectMaster getMaster(@Param("model") RsProjectMasterModel model);

    /**
     * 通过技术人员id 查询项目
     *
     * @param tIds
     * @param mType
     * @return
     */
    List<Integer> getRsProjectIds(@Param("tIds") List<Integer> tIds, @Param("mType") Integer mType);
}
