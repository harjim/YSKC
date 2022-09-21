package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.FieldConfigEntity;
import com.yskc.docservice.models.rs.fieldconfig.FieldConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: wjy
 * @CreateTime: 2020-03-16 10:30
 * @Description: 薪资配置dao层
 */
@Repository
public interface FieldConfigDao extends BaseMapper<FieldConfigEntity> {
    /**
     * 获取默认配置
     *
     * @return
     */
    FieldConfigModel getDefault();
    /**
     * 获取薪资五险一金配置
     * @param companyId
     * @return
     */
    List<FieldConfigModel> getSalaryConfig(@Param("companyId") Integer companyId);
}
