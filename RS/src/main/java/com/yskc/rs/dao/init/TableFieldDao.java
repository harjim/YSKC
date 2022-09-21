package com.yskc.rs.dao.init;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.excel.TableFieldInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 表格头字段配置表
 *
 * @author huronghua
 */
@Repository
public interface TableFieldDao extends BaseMapper<TableField> {
    /**
     * 获取表格头
     *
     * @param companyId
     * @param tableId
     * @return
     */
    TableFieldInfo getTableFieldInfo(@Param("companyId") Integer companyId, @Param("tableId") String tableId);
}
