package com.yskc.ms.dao.rs;


import com.yskc.ms.models.rs.buildconfig.BuildConfigDocModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildConfigDao{
    List<BuildConfigDocModel> getConfigList(@Param("companyId")Integer companyId, @Param("year")Integer year);
}
