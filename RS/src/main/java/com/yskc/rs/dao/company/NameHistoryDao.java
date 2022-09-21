package com.yskc.rs.dao.company;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.company.NameHistoryEntity;
import com.yskc.rs.models.company.CompanyNameHistoryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/2/15 14:25
 * @Description:
 * @author: hsx
 */
@Repository
public interface NameHistoryDao extends BaseMapper<NameHistoryEntity> {

    /**
     * 查询公司是否有历史公司名记录
     * @param companyId
     * @return
     */
//    NameHistoryEntity getNameId(@Param("companyId") Integer companyId);

    /**
     * 查询公司历史公司名记录
     * @param companyId
     * @return
     */
    List< CompanyNameHistoryModel > getNameHistory(Integer companyId);

    /**
     * 查询公司历史公司名最后一条记录
     * @param companyId
     * @return
     */
    NameHistoryEntity selectNameIsFinally(@Param("companyId")Integer companyId);

    /**
     * 查询公司历史公司名特定id或生效时间
     * @param companyName
     * @param startDate
     * @param companyId
     * @return
     */
    Integer selectCountByIdAndSDate(@Param( "companyName" ) String companyName, @Param( "startDate" ) Date startDate, @Param( "nameId" ) Integer nameId, @Param( "companyId" ) Integer companyId);
}
