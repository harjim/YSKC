package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.models.projectuncollected.ProjectUnCollectedModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUnCollectedDao {
    List<ProjectUnCollectedModel> getList(@Param("page") Pagination page);
}
