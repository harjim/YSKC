package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.customer.CustomerOwnerModel;
import com.yskc.ms.models.serviceApply.QueryMobileApply;
import com.yskc.ms.models.serviceApply.ServiceNoModel;
import com.yskc.ms.models.serviceRecord.QueryRecordModel;
import com.yskc.ms.models.serviceRecord.WorkRecordModel;

import java.util.List;

public interface ServiceRecordService {
    /**
     * 根据条件查询服务记录
     * @param query
     * @param info
     * @return
     */
    PageResult getList(QueryRecordModel query, UserInfo info, DataPermModel dataPerm);

    /**
     * 根据条件查询服务记录
     * @param query
     * @param info
     * @return
     */
    PageResult getMobileList(QueryMobileApply query, UserInfo info, DataPermModel dataPerm);

    /**根据条件查询客户数据
     *
     * @param companyName
     * @return
     */
    List<CustomerOwnerModel> getCustomerList(String companyName);

    /**
     * 根据客户查询单号
     * @param query
     * @return
     */
    PageModel<List<ServiceNoModel>> getServiceNo(QueryRecordModel query);

    /**
     * 根据id查询服务记录
     * @param recordId
     * @param info
     * @return
     */
    WorkRecordModel getRecordInfo(Integer recordId,UserInfo info);

    /**
     * 添加服务记录
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean addRecord(WorkRecordModel model,UserInfo info) throws OwnerException;

    /**
     * 编辑服务记录
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean editRecord(WorkRecordModel model,UserInfo info)throws OwnerException;

    /**
     * 提交服务记录
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean submit(WorkRecordModel model,UserInfo info)throws OwnerException;

    /**
     * 删除服务记录
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean delRecord(List<Integer> ids) throws OwnerException;
}
