package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.HighTechIncomeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: hck
 * @DateTime: 2021/5/27 17:30
 * @Description:
 */
@Repository
public interface HighTechIncomeDao extends BaseMapper<HighTechIncomeEntity> {

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<HighTechIncomeEntity> list);

}
