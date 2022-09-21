package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.PatentEntity;
import com.yskc.rs.models.Patent.PatentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-26 10:45:09
 */
@Repository
public interface PatentDao extends BaseMapper<PatentEntity> {

    List<PatentModel> queryPatent(@Param("pagination")Pagination pagination, @Param("model")PatentModel model);

    List<PatentEntity> checkPatentNo(@Param("patentNo")String patentNo, @Param("projectId")Integer projectId, @Param("pid")Integer pid);

    List<PatentEntity> queryPatentByProjectId(@Param("companyId")Integer companyId, @Param("projectId")Integer projectId);

    Integer insertPatentList(@Param("insertList")List<PatentEntity> insertList);

    Integer updatePatentList(@Param("updateList")List<PatentEntity> updateList);
}
