package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.ProjectRegisterEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.projectRegister.ProjectRegisterDataModel;
import com.yskc.ms.models.projectRegister.ProjectRegisterModel;
import com.yskc.ms.models.projectRegister.QueryRegisterModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/8/6 11:57
 * description:
 */
@Repository
public interface ProjectRegisterDao extends BaseMapper<ProjectRegisterEntity> {

    List<ProjectRegisterDataModel> getData(@Param("query") QueryRegisterModel query, @Param("page") Pagination page,@Param("dataPerm") DataPermModel dataPerm);

    List<ProjectRegisterModel> getByProjectIds(@Param("projectIds") List<Integer> projectIds);
}
