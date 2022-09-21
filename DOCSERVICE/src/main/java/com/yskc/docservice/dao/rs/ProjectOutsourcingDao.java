package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.ProjectOutsourcing;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 14:59
 * @Description: 项目委外费用dao层
 */
@Repository
public interface ProjectOutsourcingDao extends BaseMapper<ProjectOutsourcing> {

    /**
     * 插入或更新
     *
     * @param list"
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectOutsourcing> list);

    /**
     * 获取已存在的数据id
     *
     * @param list
     * @return
     */
    List<Integer> getExistIds(@Param("list") List<ProjectOutsourcing> list);
}
