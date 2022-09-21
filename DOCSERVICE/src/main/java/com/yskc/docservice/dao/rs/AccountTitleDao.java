package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.models.rs.excel.AccountExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-18 08:38:43
 */
@Repository
public interface AccountTitleDao extends BaseMapper<AccountTitleEntity> {

    /**
     * 通过全路径获取数据
     *
     * @param companyId
     * @param fullAccountNameList
     * @return List<AccountTitleEntity>
     */
    List<AccountTitleEntity> getByFullAccountName(@Param("companyId") Integer companyId, @Param("fullAccountNameList") Set<String> fullAccountNameList);

    /**
     * 获取层级数据
     *
     * @param companyId
     * @return List<AccountExcel>
     */
    List<AccountExcel> getLevelData(@Param("companyId") Integer companyId);
}
