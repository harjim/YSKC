package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.DocFileMeetingEntity;
import com.yskc.rs.models.docFile.MeetingCountModel;
import com.yskc.rs.models.docFile.MeetingFromModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/2/16 11:42
 * @Description:
 * @author: hsx
 */
@Repository
public interface DocFileMeetingDao extends BaseMapper<DocFileMeetingEntity> {

    /**
     * 根据文件id获取文件附件数
     * @param fileIds
     * @return
     */
    List<Map<Integer,Integer>> getFilesByFileIds(@Param("fileIds") List<Integer> fileIds);

    /**
     * 获取会议纪要相关数据
     * @param companyId
     * @param month
     * @return
     */
    List<MeetingFromModel> getMeetingFromData(@Param("companyId") Integer companyId,@Param("month") Date month);

    /**
     * 根据会议纪要文件id获取附件
     * @param fileIds
     * @return
     */
    List<MeetingFromModel> getMeetingModel(@Param("fileIds") List<Integer> fileIds);

    /**
     * 获取当年各月份会议纪要统计
     * @param openMonth
     * @param endMonth
     * @param companyId
     * @return
     */
    List<MeetingCountModel> getAnnualData(@Param("openMonth") Date openMonth
            , @Param("endMonth") Date endMonth, @Param("companyId") Integer companyId);

    /**
     * 获取当月份会议纪要统计
     * @param month
     * @param companyId
     * @return
     */
    List<MeetingCountModel> getStatByMonth(@Param("month") Date month, @Param("companyId") Integer companyId);
}
