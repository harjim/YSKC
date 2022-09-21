package com.yskc.ms.service.rs;

import com.yskc.ms.entity.rs.PatentFileEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.PatentOpinionModel;

import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 16:13
 * @Description:
 */
public interface PatentFileService {

    Integer addFile(Integer patentId, Integer fileType, String filePath, String fileName, UserInfo userInfo, String patentNo);

    /**
     * 获取专利文件
     *
     * @param patentId
     * @return
     */
    Map<Integer, Object> getPatentFiles(Integer patentId, String patentNo);

    /**
     * 删除专利文件
     *
     * @param id
     * @return
     */
    Boolean delPatentFile(Integer id);

    /**
     * 获取意见列表
     *
     * @param patentId
     * @param patentNo
     * @return
     */
    List<PatentOpinionModel> getOpinions(Integer patentId, String patentNo);

    PatentFileEntity getById(Integer id, String patentNo);
}
