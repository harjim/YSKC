package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ContractMemberEntity;
import com.yskc.ms.entity.ms.SysSession;
import com.yskc.ms.models.contract.ContractMemberModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 合同人员dao
 * @author wjy
 */
@Repository
public interface ContractMemberDao extends BaseMapper<ContractMemberEntity> {
    /**
     * 查询合同人员列
     * @param contractIds
     * @return
     */
    List<ContractMemberModel> getMemberList(@Param("contractIds")Set<Integer> contractIds);

    /**
     * 添加合同人员
     * @param list
     * @return
     */
    Integer addMemberList(@Param("list") List<ContractMemberEntity> list);

    /**
     * 编辑合同人员
     * @param list
     * @return
     */
    Integer editMemberList(@Param("list") List<ContractMemberEntity> list);

    /**
     *编辑时删除
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
