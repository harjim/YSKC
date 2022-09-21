package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.models.accounttitle.AccountTitleModel;
import com.yskc.rs.models.accounttitle.AccountTree;
import com.yskc.rs.models.excel.AccountExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-18 08:38:43
 */
@Repository
public interface AccountTitleDao extends BaseMapper<AccountTitleEntity> {

    /**
     * 查询科目
     *
     * @param companyId
     * @param accountName
     * @return List<AccountTitleModel>
     */
    List<AccountTitleModel> queryAccountTitle(@Param("companyId") int companyId, @Param("accountName") String accountName);

    /**
     * 查询所有科目信息
     *
     * @param companyId
     * @return List<AccountTree>
     */
    List<AccountTree> queryAllAccountTitle(@Param("companyId") int companyId);

    /**
     * 验证科目编码的唯一性
     *
     * @param companyId
     * @param accountNumber
     * @return AccountTitleEntity
     */
    AccountTitleEntity getByAccountNumber(@Param("companyId") int companyId, @Param("accountNumber") String accountNumber);

    /**
     * 通过全路径获取数据
     *
     * @param companyId
     * @param fullAccountNameList
     * @return List<AccountTitleEntity>
     */
    List<AccountTitleEntity> getByFullAccountName(@Param("companyId") Integer companyId, @Param("fullAccountNameList") List<String> fullAccountNameList);

    /**
     * 获取层级数据
     *
     * @param companyId
     * @return List<AccountExcel>
     */
    List<AccountExcel> getLevelData(@Param("companyId") Integer companyId);

    /**
     * 获取所有子节点
     *
     * @param companyId
     * @param fullAccountName
     * @param selfId
     * @return List<AccountTitleEntity>
     */
    List<AccountTitleEntity> getChildren(@Param("companyId") Integer companyId,
                                         @Param("fullAccountName") String fullAccountName, @Param("selfId") Integer selfId);

    /**
     * 批量更新
     *
     * @param updateList
     * @return Integer
     */
    Integer updateList(@Param("updateList") List<AccountTitleEntity> updateList);

    /**
     * 下层子节点个数
     *
     * @param parentId
     * @return Integer
     */
    Integer getByParentId(@Param("parentId") Integer parentId);

    /**
     * 通过科目名获取科目
     *
     * @param companyId
     * @param parentId
     * @param accountName
     * @return AccountTitleEntity
     */
    AccountTitleEntity getByAccountName(@Param("companyId") Integer companyId,
                                        @Param("parentId") Integer parentId, @Param("accountName") String accountName);
}
