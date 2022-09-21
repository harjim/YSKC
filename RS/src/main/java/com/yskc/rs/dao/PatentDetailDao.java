package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.PatentDetailEntity;
import com.yskc.rs.models.Patent.PatentDetailModel;
import com.yskc.rs.models.Patent.QueryPatentDetialModel;
import com.yskc.rs.models.highscore.HighTechPatentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/29 11:32
 * description:
 */
@Repository
public interface PatentDetailDao extends BaseMapper<PatentDetailEntity> {

    PatentDetailEntity checkPatentSole(@Param("patentNo") String patentNo);

    List<PatentDetailModel> getPatentList(@Param("companyId") Integer companyId, @Param("page") Pagination pagination, @Param("query") QueryPatentDetialModel query);

    List<PatentDetailEntity> getListByPatentNos(@Param("companyId") Integer companyId);

    Integer insertPatents(@Param("patentDetails") List<PatentDetailEntity> patentDetails, @Param("companyId") Integer companyId);

    Integer updatePatents(@Param("patentDetails") List<PatentDetailEntity> patentDetails, @Param("companyId") Integer companyId);


    Integer updateProjectId(@Param("patentNos") List<String> patentNos, @Param("projectId") Integer projectId,
                            @Param("updateTime") Date updateTime, @Param("userId") Integer userId,@Param("msUserId")Integer msUserId);

    List<PatentDetailEntity> getByPatentNos(@Param("patentNos") List<String> newPatentNos);

    /**
     * 根据项目获取关联项目专利列表
     *
     * @param projectIds
     * @param companyId
     * @return
     */
    List<PatentDetailEntity> getListByProject(@Param("projectIds") List<Integer> projectIds, @Param("companyId") Integer companyId);

    /**
     * 获取权利要求内容/说明书内容
     *
     * @param id
     * @return
     */
    Map<String, String> getSpecification(@Param("id") Integer id);


    /**
     * 获取高新技术专利
     *
     * @param companyId
     * @param year
     * @return
     */
    List<HighTechPatentModel> getHighTechPatents(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取关联项目高新领域，最多两条
     *
     * @param companyId
     * @param year
     * @return
     */
    List<String> getTecIndustries(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
