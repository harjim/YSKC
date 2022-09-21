package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.checkInst.CheckMiniModel;
import com.yskc.ms.models.checkPayment.AddPaymentModel;
import com.yskc.ms.models.checkPayment.CheckPaymentModel;
import com.yskc.ms.models.checkPayment.QueryCheckModel;
import com.yskc.ms.models.rs.ReportProjectModel;

import java.util.List;

/**
 * 查新付费
 * @author wjy
 */
public interface CheckPaymentService {

    /**
     * 根据条件查询查新付费列表
     * @param query
     * @param info
     * @param dataPerm
     * @return
     */
    PageModel<List<CheckPaymentModel>> getList(QueryCheckModel query, UserInfo info, DataPermModel dataPerm);

    /**
     * 查询查新付费信息
     * @param id
     * @param info
     * @return
     */
    CheckPaymentModel getInfo(Integer id, UserInfo info);

    /**
     * 根据客户查询项目列
     * @param customerId
     * @param year
     * @return
     */
    List<ReportProjectModel> getProjectList(Integer customerId,Integer year);

    /**
     * 查询查新机构
     * @param checkInstName
     * @return
     */
    List<CheckMiniModel> getCheckInstList(String checkInstName);

    /**
     * 添加查新付费方式
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean addCheckPayment(AddPaymentModel model,UserInfo info) throws OwnerException;

    /**
     * 添加查新付费
     * @param id
     * @param info
     * @param payType
     * @param paymentFile
     * @return
     * @throws OwnerException
     */
    Boolean addPaymentFile(Integer id,UserInfo info,String payType,String paymentFile) throws OwnerException;

    /**
     * 编辑查新付费
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean editCheckPayment(AddPaymentModel model,UserInfo info) throws OwnerException;

    /**
     * 删除查新付费
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean delCheckPayment(List<Integer> ids) throws OwnerException;

    /**
     * 提交查新付费
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean submit(AddPaymentModel model,UserInfo info) throws OwnerException;

}
