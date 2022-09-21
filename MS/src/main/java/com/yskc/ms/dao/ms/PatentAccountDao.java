package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentAccount;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.expert.QueryExpertModel;
import com.yskc.ms.models.patent.PatentAccountModel;
import com.yskc.ms.models.patent.PatentAccountTreeModel;
import com.yskc.ms.models.patent.QueryPatentListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: wangxing
 * @CreateTime: 2020-01-03 14:13
 * @Description: 专利账号管理Dao
 */
@Repository
public interface PatentAccountDao extends BaseMapper<PatentAccount> {

    /**
     * 查询所有的账号信息
     * @param page
     * @return
     */
    List<PatentAccountModel> queryPatentAccount(@Param("page") Pagination page, @Param("query") QueryPatentListModel query, @Param("perm") DataPermModel perm);

    /**
     * 查询账号是否已存在
     * @param username
     * @return
     */
    PatentAccountModel getPatentAccountByUserName(@Param("username")String username);

    /**
     * 获取专利账号tree
     * @return
     */
    List<PatentAccountTreeModel> getPatentAccountTree();

    /**
     * 更新账号状态
     * @param id
     * @param status
     * @param password
     * @return
     */
    Boolean updateStatus(@Param("id") Integer id,@Param("password") String password,@Param("status") Integer status);
}
