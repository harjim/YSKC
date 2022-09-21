package com.yskc.docservice.dao.rs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.SysDictionaryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典表
 * @author huronghua
 */
@Repository
public interface SysDictionaryDao extends BaseMapper<SysDictionaryEntity> {

    /**
     * 获取高新领域名
     * @return
     */
    List<String> getHighTecIndustry(@Param("list")List<Integer> list);
}
