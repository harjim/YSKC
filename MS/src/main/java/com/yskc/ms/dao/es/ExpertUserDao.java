package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.UserEntity;
import com.yskc.ms.models.CountModel;
import com.yskc.ms.models.newexpert.expert.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/9/23 15:36
 * @Description:
 * @author: hsx
 */
@Repository
public interface ExpertUserDao extends BaseMapper<UserEntity> {

    /**
     * 查询专家库用户详细信息
     * @param page
     * @param query
     * @return
     */
    List<ExpertModel> getExpertList(@Param("page") Pagination page, @Param("query") QueryExpertModel query);

    /**
     * 启用或者禁用用户
     * @param id
     * @param disabled
     * @param now
     * @return
     */
    Integer ChangeUserStatus(@Param("id") Integer id, @Param("disabled") Integer disabled,@Param("now") Date now);

    /**
     * 重置专家用户密码
     * @param id
     * @param password
     * @param now
     * @return
     */
    Integer resetPassword(@Param("id") Integer id, @Param("password") String password,@Param("now") Date now);

    /**
     * 查询专家库用户审核列表
     * @param page
     * @param query
     * @return
     */
    List<ExpertAuditModel> getExpertAuditList(@Param("page") Pagination page, @Param("query") QueryExpertModel query);

    /**
     * 修改专家标签
     * @param list
     * @return
     */
    Boolean updateExpertTags(@Param("list") List<ExpertAuditModel> list);
    /**
     * 获取用户入驻提交的资料
     * @param id
     * @return
     */
    UserEnterModel getUserEnterData(@Param("id") Integer id);

    /**
     * 获取各个审核状态用户的总数
     * @return
     */
    List<CountModel> getStatusTotal();

    /**
     * 批量修改user_expert表的审核状态
     * @param model
     * @return
     */
    Integer editExpertStatus(@Param("model") UserExpertLogModel model);

    /**
     * 获取用户审核状态
     * @param ids
     * @return
     */
    List<Integer> getUserStatus(@Param("ids") List<Integer> ids);

    /**
     * 验证身份证号码是否唯一
     * @param idNo
     * @param id
     * @param idType
     * @return
     */
    String verifyUniqueIdNo(@Param("idNo") String idNo,@Param("idType") Integer idType,@Param("id") Integer id);

    /**
     * 编辑用户信息
     * @param user
     * @return
     */
    Integer updateUser(@Param("user") UserEntity user);
}
