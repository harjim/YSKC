package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.DocFileFooterEntity;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.models.docFile.QueryDocFileFooterModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2021/2/19 9:30
 * description:
 */
@Repository
public interface DocFileFooterDao extends BaseMapper<DocFileFooterEntity> {
    /**
     * 根据项目获取页脚设置
     * @param projectId
     * @return
     */
    DocFileFooterEntity getFooter(@Param("projectId") Integer projectId, @Param("year") Integer year);


    Integer updateFooter(@Param("id") Integer id, @Param("msUserId") Integer msUserId,
                         @Param("userId") Integer userId, @Param("date") Date date,
                         @Param("approvalEnumber") String approvalEnumber,
                         @Param("auditEnumber") String auditEnumber, @Param("toCompileEnumber") String toCompileEnumber);

    Integer insertOrUpdate(@Param("entity")DocFileFooterEntity entity);

    /**
     * 根据条件查询签名汇总数据
     * @param page
     * @param model
     * @param rdTitles
     * @return
     */
    List<DocFileFooterModel> getList(@Param("page") Pagination page, @Param("model") QueryDocFileFooterModel model,@Param("rdTitles") List<String> rdTitles);

    /**
     * 根据条件查询签名汇总数据不分页
     * @param model
     * @param rdTitles
     * @return
     */
    List<DocFileFooterModel> getList(@Param("model") QueryDocFileFooterModel model,@Param("rdTitles") List<String> rdTitles);
}
