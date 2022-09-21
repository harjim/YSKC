package com.xxl.job.executor.dao.rs;

import com.xxl.job.executor.models.rs_project_uncollected.RsProjectUnCollectedModel;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RsUnCollectedDao {
    List<RsProjectUnCollectedModel> getList();
}
