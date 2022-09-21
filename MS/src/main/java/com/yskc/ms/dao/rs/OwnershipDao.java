package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.OwnershipEntity;
import com.yskc.ms.models.company.OwnershipModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:26
 * description:
 */
@Repository
public interface OwnershipDao extends BaseMapper<OwnershipEntity> {
    /**
     * 获取公司股权架构
     * @param companyId
     * @return
     */
    List<OwnershipModel> getByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 批量插入
     * @param insertList
     * @return
     */
    Integer insertList(@Param("insertList") List<OwnershipEntity> insertList);

    /**
     * 批量更新
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<OwnershipEntity> updateList);
}
