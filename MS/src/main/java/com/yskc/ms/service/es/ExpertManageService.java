package com.yskc.ms.service.es;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.expert.*;
import com.yskc.ms.models.user.ResetPasswordModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/9/23 15:35
 * @Description:
 * @author: hsx
 */
public interface ExpertManageService {

    /**
     * @param query
     * 查询专家库用户详细信息
     *
     * @return
     */
    PageModel<List<ExpertModel>> getExpertList(QueryExpertModel query);

    /**
     * @param id
     * @param status
     * 启用或者禁用用户
     * @return
     */
    Boolean ChangeUserStatus(Integer id, Integer status) throws OwnerException;

    /**
     * @param rpm
     * 重置专家用户密码
     * @return
     */
    Boolean resetPassword(ResetPasswordModel rpm) throws OwnerException;

    /**
     * 编辑专家库用户信息
     * @param model
     * @return
     */
    Boolean updateUser(ExpertModel model) throws OwnerException;

    /**
     * 查询专家库用户审核信息
     * @param query
     * @return
     */
    PageModel<List<ExpertAuditModel>> getExpertAuditList(QueryExpertModel query);

    /**
     * 编辑专家标签
     * @param model
     * @return
     */
    Boolean updateExpertAuditList(ExpertTagsModel model);

    /**
     * 批量审核用户入驻信息
     * @param model
     * @return
     */
    Boolean expertAudit(UserExpertLogModel model, Integer id) throws OwnerException;

    /**
     * 获取单个用户的审核日志
     * @param id
     * @return
     */
    List<UserExpertLogModel> getUserAuditLog(Integer id);

    /**
     * 获取待审核用户统计
     * @return
     */
    Map<String,Integer> getStatusTotal();

    /**
     * 获取用户入驻提交的资料
     * @param id
     * @return
     */
    UserEnterModel getUserEnterData(@Param("id") Integer id);
}
