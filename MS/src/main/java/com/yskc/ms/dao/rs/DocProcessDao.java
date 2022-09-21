package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.DocProcessEntity;
import com.yskc.ms.entity.rs.models.DocProcessModel;
import com.yskc.ms.entity.rs.models.DocProcessTemplateListModel;
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
public interface DocProcessDao extends BaseMapper<DocProcessEntity> {
    /**
     * 获取过程数据
     * @param page
     * @param processName
     * @return
     */
    List<DocProcessModel> queryDocProcess(Pagination page, @Param("processName") String processName);

    /**
     * 通过过程id获取数据
     * @param docProcessId
     * @return
     */
    List<DocProcessTemplateListModel> queryListModels(@Param("docProcessId")int docProcessId);


    List<DocProcessTemplateListModel> getDataById(Pagination page,@Param("id") int id);
	
}
