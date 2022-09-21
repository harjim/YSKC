package com.yskc.ms.dao.rs;

import com.yskc.ms.models.QueryYieldConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:17
 * @Description: 项目试制量配置dao层
 */
@Repository
public interface ProjectYieldConfigDao {

    /**
     * 获取试制时间
     *
     * @param query
     * @return
     */
    List<Date> getTrialDate(@Param("query") QueryYieldConfigModel query);

    /**
     * 获取区间月份
     *
     * @param projectId
     * @param begin
     * @param end
     * @return
     */
    List<Date> getRangeMonths(@Param("projectId") Integer projectId,@Param("begin") Date begin,@Param("end") Date end);
}
