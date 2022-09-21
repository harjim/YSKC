package com.yskc.docservice.dao.rs;

import com.yskc.docservice.models.rs.trialprod.StageTrialModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrialProdDao {

    /**
     * 查询文档试制
     * @param companyId
     * @param docFileIds
     * @param projectId
     */
    List<StageTrialModel> queryDocFileTrial(@Param("companyId")Integer companyId,
                                            @Param("docFileIds") List<Integer> docFileIds, @Param("projectId")Integer projectId);

    /**
     * 根据月获取项目试制
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    List<StageTrialModel> chooseGetTrial(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 查询文档试制
     * @param projectId
     * @param companyId
     */
    List<StageTrialModel> getTrialByProject(@Param("projectId") Integer projectId,
                                            @Param("companyId") Integer companyId);
}
