package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsNameHistoryEntity;
import com.yskc.ms.models.company.NameHistoryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/dao/rs
 * @Author: hm
 * @CreateTime: 2022/8/25
 * @Description: rs c_name_history 相关操作
 */
@Repository
public interface NameHistoryDao extends BaseMapper< RsNameHistoryEntity > {
    /*
     * 获取公司历史名称记录
     * @param companyId 公司id
     */
    List< NameHistoryModel > getNameHistoryList(@Param("companyId") Integer companyId);

    Integer selectCountByIdAndSDate(@Param("companyName") String companyName, @Param("companyId") Integer companyId, @Param("nameId") Integer nameId, @Param("startDate") Date startDate);

    NameHistoryModel selectLastHistory(@Param("companyId") Integer companyId);
}
