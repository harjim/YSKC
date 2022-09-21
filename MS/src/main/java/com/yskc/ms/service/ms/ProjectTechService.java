package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.tech.*;
import org.apache.xpath.operations.Bool;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/23 15:48
 * description:
 */
public interface ProjectTechService {

    /**
     * 获取项目技改进度详情列表
     * @param query
     * @param userInfo
     * @param dataPerm
     * @return
     */
    PageModel<List<ProjectTechProgressModel>> getTechList(QueryTechModel query, UserInfo userInfo, DataPermModel dataPerm);

    /**
     * 查询技改日志列表
     * @param projectId
     * @return
     */
   List<ProjectTechLogModel> getLogList(Integer projectId);

    /**
     * 添加技改日志
     * @param model
     * @param userInfo
     * @return
     */
    Boolean addTechLog(ProjectTechLogModel model,UserInfo userInfo) throws OwnerException;

    /**
     * 更新技改日志
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean updateTechLog(ProjectTechLogModel model,UserInfo userInfo) throws OwnerException;

    /**
     * 删除技改日志
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean delTechLog(ProjectTechLogModel model,UserInfo userInfo) throws OwnerException;

}
