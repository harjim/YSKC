package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.BeianEntity;
import com.yskc.ms.models.rs.RelatedProjectModel;
import com.yskc.ms.models.rs.TechProjectResultModel;
import com.yskc.ms.models.tech.BeianExportModel;
import com.yskc.ms.models.tech.BeianInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hck
 * @since 2021-03-18
 */
@Repository
public interface BeianDao extends BaseMapper<BeianEntity> {

    /**
     * 获取项目信息
     *
     * @param projectIds
     * @return
     */
    List<TechProjectResultModel> getBySourceIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 更新备案
     *
     * @param id
     * @param year
     * @param remark
     * @param now
     * @param msUserId
     * @return
     */
    int updateBeian(@Param("id") Integer id, @Param("year") Integer year, @Param("remark") String remark,
                    @Param("now") Date now, @Param("msUserId") Integer msUserId);

    /**
     * 通过id获取
     *
     * @param id
     * @return
     */
    RelatedProjectModel getById(@Param("id") Integer id);

    /**
     * @param id
     * @return
     */
    int countData(@Param("id") Integer id);

    /**
     * 通过sourceProjectId获取备案id
     *
     * @param sourceProjectId
     * @return
     */
    Integer getBySourceProjectId(@Param("sourceProjectId") Integer sourceProjectId);

    /**
     * 获取备案列表
     *
     * @param projectIds
     * @return
     */
    List<BeianInfoModel> getList(@Param("projectIds") List<Integer> projectIds);

    /**
     * 导出备案列表
     *
     * @param projectIds
     * @return
     */
    List<BeianExportModel> getExport(@Param("projectIds") Set<Integer> projectIds);

    /**
     * 统计数据
     *
     * @param companyIds
     * @return
     */
    List<BeianInfoModel> getData(@Param("companyIds") List<Integer> companyIds);


}
