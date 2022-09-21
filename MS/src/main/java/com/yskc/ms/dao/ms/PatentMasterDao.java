package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentMasterEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.patentMaster.PatentMasterModel;
import com.yskc.ms.models.patentMaster.QueryMasterModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/7 8:35
 * description:
 */
@Repository
public interface PatentMasterDao extends BaseMapper<PatentMasterEntity> {

    List<PatentMasterEntity> getListByIds(@Param("ids") List<Integer> ids);

    List<PatentMasterModel> getList(@Param("query") Pagination page, @Param("query") QueryMasterModel query, DataPermModel perm);

    List<PatentMasterModel> getMasterList(@Param("model") QueryMasterModel model);

    /**
     * 检查负责人名称唯一
     *
     * @param patentName
     * @param id
     * @return
     */
    PatentMasterEntity checkName(@Param("patentName") String patentName, @Param("id") Integer id);

    /**
     * 获取下拉
     *
     * @return
     */
    List<MiniModel> getSelect();

    /**
     * 根据id获取userId
     * @param masterId
     * @return
     */
    Integer getUserId(@Param("masterId") Integer masterId);

    /**
     * 根据patentPlanId获取代理人姓名以及电话号码
     * @param patentPlanId
     * @return
     */
    PatentMasterEntity getNameAndLineTle(Integer patentPlanId);
}
