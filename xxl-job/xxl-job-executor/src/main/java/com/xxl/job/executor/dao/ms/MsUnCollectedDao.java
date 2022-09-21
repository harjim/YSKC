package com.xxl.job.executor.dao.ms;

import com.xxl.job.executor.models.rs_project_uncollected.RsProjectUnCollectedModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MsUnCollectedDao {
    Integer insertUncollectedRds(List<RsProjectUnCollectedModel> list, @Param("date") Date date);

    Integer delCollected(@Param("list")List<Integer> list);

    List<Integer> getCollectedIds(@Param("list")List<RsProjectUnCollectedModel> list);
}
