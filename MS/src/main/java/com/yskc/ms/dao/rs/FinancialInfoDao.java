package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.FinancialInfoEntity;
import com.yskc.ms.models.company.FinanceInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:25
 * description:
 */
@Repository
public interface FinancialInfoDao extends BaseMapper<FinancialInfoEntity> {
    /**
     * 插入并更新
     * @param finances
     * @return
     */
    Boolean insertOrUpdate(@Param("finances") List<FinancialInfoEntity> finances);

    /**
     * 获取单位财务信息
     * @param companyId
     * @param years
     * @return
     */
    List<FinanceInfoModel> getFinanceInfo(@Param("companyId") int companyId, @Param("years") List<Integer> years);
}
