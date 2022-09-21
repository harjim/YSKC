package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.innovationproject.BatchMasterStaffModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.rdfunds.QueryFundsModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 9:57
 * @Description:
 */
public interface InnovationProjectService {
    /**
     * 获取创新项目列表
     *
     * @param query
     * @param userInfo
     * @param dataPerm
     * @return
     */
    PageResult getList(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm);

    /**
     * 设置技术负责人及成员
     *
     * @param batch
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean setProjectTechIds(BatchMasterStaffModel batch, UserInfo userInfo)throws OwnerException;

    /**
     * 设置财务负责人及成员
     *
     * @param batch
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean setFinanceIds(BatchMasterStaffModel batch, UserInfo userInfo)throws OwnerException;

    /**
     * 获取创新项目报表数据
     * @param model
     * @param dataPerm
     * @return
     */
    PageResult getTableData(QueryFundsModel model,DataPermModel dataPerm);

    void getTableExport(QueryFundsModel model, DataPermModel dataPerm, OutputStream out, String path) throws OwnerException;

}
