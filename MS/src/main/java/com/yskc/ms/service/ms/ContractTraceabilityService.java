package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.ContractTraceability;
import com.yskc.ms.entity.ms.models.ContractTraceabilityModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.QueryContractTraceabilityModel;
import com.yskc.ms.models.UserInfo;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: wangxing
 * @CreateTime: 2020-03-09 10:40
 * @Description: ContractTraceabilityService
 */
public interface ContractTraceabilityService {
    /**
     * 查询所有合同追溯
     *
     * @return
     */
    PageModel<List<ContractTraceabilityModel>> queryConTraceabilityList(QueryContractTraceabilityModel query, DataPermModel dataPerm);

    /**
     * 添加合同追溯
     *
     * @param info
     * @param ct
     * @return
     * @throws OwnerException
     */
    boolean addConTraceability(UserInfo info, ContractTraceability ct) throws OwnerException;

    /**
     * 修改合同追溯
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateConTraceability(UserInfo info, ContractTraceabilityModel model);

    /**
     * 检查是否存在相同合同编号
     *
     * @param contractNo
     * @return
     */
    boolean checkContractNo(String contractNo);

    /**
     * 删除合同追溯
     *
     * @param id
     * @return
     */
    Boolean delete(Integer id);

    /**
     * 生成合同追踪
     * @param info
     * @param customerId
     * @param contractNo
     * @return 返回二维码
     * @throws OwnerException
     */
    String generateTraceability(UserInfo info, Integer customerId, String contractNo)throws OwnerException;
}
