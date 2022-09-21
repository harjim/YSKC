package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.PatentFileEntity;
import com.yskc.ms.models.patentPlan.PatentFileModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 15:56
 * @Description:
 */
@Repository
public interface PatentFileDao extends BaseMapper<PatentFileEntity> {
    /**
     * 获取专利文件
     *
     * @param patentId
     * @param patentNo
     * @return
     */
    List<PatentFileModel> getPatentFiles(@Param("patentId") Integer patentId, @Param("patentNo") String patentNo);

    /**
     * 根据type获取专利文件
     * @param patentId
     * @param type
     * @return
     */
    List<PatentFileModel> getPatentByType(@Param("patentId") Integer patentId, @Param("type") Integer type);

    /**
     * 更新专利上传文件专利号
     *
     * @param patentPlanId
     * @param patentNo
     * @param userId
     * @param msUserId
     * @param date
     * @return
     */
    Boolean updatePatentNo(@Param("patentPlanId") Integer patentPlanId, @Param("patentNo") String patentNo,
                           @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date);

    Boolean updatePatent(@Param("existPatentNo") String existPatentNo, @Param("patentNo") String patentNo,
                         @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date);

    /**
     * 批量插入
     *
     * @param files
     * @return
     */
    Integer addBatch(@Param("files") List<PatentFileEntity> files);

    /**
     * 删除交底书
     * @param existIds
     * @param patentPlanId
     * @param fileType
     * @return
     */
    Integer delFile(@Param("existIds") List<Integer> existIds, @Param("patentPlanId") Integer patentPlanId,
                    @Param("fileType")Integer fileType);

    /**
     * 获取专利申请交底书
     *
     * @param patentPlanIds
     * @return
     */
    List<PatentFileModel> getFiles(@Param("patentPlanIds") List<Integer> patentPlanIds);
}
