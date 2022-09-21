package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.ContractEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.contract.ContractModel;
import com.yskc.ms.models.contract.QueryContractModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 合同dao
 *
 * @author wjy
 */
@Repository
public interface ContractDao extends BaseMapper<ContractEntity> {
    /**
     * 查询合同列
     * @param page
     * @param moduleId
     * @param query
     * @param dataPerm
     * @return
     */
    List<ContractModel> getList(@Param("page") Pagination page, @Param("moduleId") Integer moduleId,
                                @Param("query") QueryContractModel query,@Param("dataPerm") DataPermModel dataPerm);

    /**
     * 查询合同信息
     * @param id
     * @param moduleId
     * @return
     */
    ContractModel getInfo(@Param("id")Integer id, @Param("moduleId")Integer moduleId);

    /**
     * 获取简单信息
     * @param id
     * @return
     */
    ContractModel getPassInfo(@Param("id")Integer id);

    String getContractNo(@Param("today") Date today, @Param("tomorrow") Date tomorrow);

    /**
     * 更新文件
     *
     * @param id
     * @param qrcode
     * @param userId
     * @param now
     */
    void updateQrcode(@Param("id") Integer id, @Param("qrcode") String qrcode,
                    @Param("userId") Integer userId, @Param("now") Date now);

}
