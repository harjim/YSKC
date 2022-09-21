package com.yskc.ms.dao.rs;

import com.yskc.ms.entity.rs.DocProcessTemplateEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-05 11:34:28
 */
@Repository
public interface DocProcessTemplateDao extends BaseMapper<DocProcessTemplateEntity> {
    /**
     * 通过过程id删除
     * @param processId
     * @return
     */
	boolean delProcessTemplateByProcessId(@Param("processId") int processId);


    /**
     * 批量新增
     * @param entityList
     * @return
     */
    Integer addProcessTemplateBatch(@Param("entityList") List<DocProcessTemplateEntity> entityList);
}
