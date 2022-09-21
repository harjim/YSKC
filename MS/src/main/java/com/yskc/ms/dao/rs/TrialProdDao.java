package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.TrialProdEntity;
import com.yskc.ms.models.rs.StageTrialModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/9/2 16:26
 * description:
 */
@Repository
public interface TrialProdDao extends BaseMapper<TrialProdEntity> {

    /**
     * 查询文档试制
     *
     * @param companyId
     * @param docFileId
     * @param projectId
     */
    List<StageTrialModel> queryDocFileTrial(@Param("companyId") Integer companyId, @Param("docFileId") Integer docFileId, @Param("projectId") Integer projectId);

    /**
     * 根据月获取项目试制
     *
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    List<StageTrialModel> getTrialByMonth(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 查询文档试制
     * @param projectId
     * @param companyId
     */
    List<StageTrialModel> getTrialByProject(@Param("projectId") Integer projectId,
                                            @Param("companyId") Integer companyId);
}
