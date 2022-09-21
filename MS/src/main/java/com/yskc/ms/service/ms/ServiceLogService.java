package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.QueryServiceModel;
import com.yskc.ms.entity.ms.ServiceLog;
import com.yskc.ms.models.*;
import com.yskc.ms.models.servicelog.ServiceAddLogModel;
import com.yskc.ms.models.servicelog.ServiceInfoModel;
import com.yskc.ms.models.servicelog.ServiceLogModel;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by hck
 * on 2020/5/5 14:45
 */
public interface ServiceLogService {

    /**
     * 根据条件查询服务记录
     *
     * @return
     */
    PageModel<List<ServiceInfoModel>> queryByParams(QueryServiceModel model, UserInfo userInfo, DataPermModel dataPerm);

    /**
     * 添加客户服务记录及服务人员记录
     *
     * @param serviceLogModel
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean addServiceLog(ServiceAddLogModel serviceLogModel, Integer id) throws OwnerException;

    /**
     * 编辑客户服务记录及服务人员记录
     *
     * @param serviceLogModel
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean editServiceLog(ServiceLogModel serviceLogModel, Integer id) throws OwnerException;

    /**
     * 删除客户服务记录及服务人员
     *
     * @param id
     * @return
     */
    Boolean delServiceLog(Integer id) throws OwnerException;

    /**
     * 修改服务状态
     *
     * @param model
     * @return
     */
    Boolean updateStatus(ServiceLogModel model, Integer id) throws OwnerException;

    /**
     * 根据id获取服务记录
     *
     * @param id
     * @return
     */
    ServiceLog getLogById(Integer id);

    /**
     * 导出服务记录
     *
     * @param out
     * @param model
     * @param dataPerm
     * @throws OwnerException
     */
    void exportServiceLog(OutputStream out, QueryServiceModel model, DataPermModel dataPerm) throws OwnerException;
}
