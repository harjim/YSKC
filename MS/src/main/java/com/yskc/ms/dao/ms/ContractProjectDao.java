package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ContractMemberEntity;
import com.yskc.ms.entity.ms.ContractProjectEntity;
import com.yskc.ms.models.contract.ContractMemberModel;
import com.yskc.ms.models.contract.ContractProjectModel;
import com.yskc.ms.models.contract.QueryProductModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 合同项目dao
 * @author wjy
 */
@Repository
public interface ContractProjectDao extends BaseMapper<ContractProjectEntity> {
    /**
     * 查询合同项目列
     * @param contractIds
     * @return
     */
    List<ContractProjectModel> getProjectList(@Param("contractIds") Set<Integer> contractIds);

    /**
     * 查询次数
     * @param query
     * @return
     */
    Integer getProduct(@Param("query")QueryProductModel query);

    /**
     * 添加合同项目
     * @param list
     * @return
     */
    Integer addProjectList(@Param("list") List<ContractProjectEntity> list);

    /**
     * 编辑合同项目
     * @param list
     * @return
     */
    Integer editProjectList(@Param("list") List<ContractProjectEntity> list);

    /**
     * 查询签约次数重复的
     * @param list
     * @param customerId
     * @return
     */
    String getModel(@Param("list") List<ContractProjectModel> list,@Param("customerId")Integer customerId);

    /**
     * 查询起止年份重复的
     * @param list
     * @param customerId
     * @return
     */
    List<String> checkProduct(@Param("list") List<ContractProjectModel> list,@Param("customerId")Integer customerId);

    /**
     * 编辑时删除
     * @param ids
     * @param contractId
     * @return
     */
    Integer delByIds(@Param("ids")List<Integer> ids,@Param("contractId")Integer contractId);

    /**
     * 根据ContractId删除
     * @param ids
     * @return
     */
    Integer delByContractIds(@Param("ids")List<Integer> ids);
}
