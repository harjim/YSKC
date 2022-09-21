package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.SysSession;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.contract.*;

import java.util.List;

/**
 * 合同
 * @author wjy
 */
public interface ContractService {
    /**
     * 查看信息
     * @param id
     * @param info
     * @return
     */
    ContractModel getInfo(Integer id,UserInfo info);

    /**
     * 查询列表
     * @param query
     * @param info
     * @return
     */
    PageModel<List<ContractModel>> getList(QueryContractModel query, UserInfo info, DataPermModel dataPerm);

    /**
     * 根据条件查询签约次数
     * @param query
     * @param info
     * @return
     */
    Integer getProduct(QueryProductModel query,UserInfo info);

    /**
     * 根据条件查询重复项目类型
     * @param model
     * @return
     * @throws OwnerException
     */
    String checkProduct(CheckProductModel model) throws OwnerException;

    /**
     * 添加合同邮寄地址
     * @param id
     * @param info
     * @param expressNo
     * @param expressAddress
     * @return
     * @throws OwnerException
     */
    Boolean addExpress(Integer id,UserInfo info,String expressNo,String expressAddress) throws OwnerException;

    /**
     * 合同审核时更新文件
     * @param id
     * @param info
     * @throws Exception
     */
    void addPassContract(Integer id,UserInfo info) throws Exception;

    /**
     * 添加合同
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean addContract(AddContractModel model,UserInfo info) throws OwnerException;

    /**
     * 编辑合同
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean editContract(AddContractModel model,UserInfo info) throws OwnerException;

    /**
     * 删除合同
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean delContract(List<Integer> ids) throws OwnerException;

    /**
     * 提交审核
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean submit(AddContractModel model,UserInfo info) throws OwnerException;
}
