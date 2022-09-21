package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accounttitle.AccountTitleModel;
import com.yskc.rs.models.accounttitle.AccountTree;
import com.yskc.rs.models.excel.AccountExcel;

import java.util.List;

/**
 * @author Administrator
 */
public interface AccountTitleService {

    /**
     * 获取所有的科目
     *
     * @param companyId
     * @param accountName
     * @return
     */
    List<AccountTitleModel> queryAccountTitle(int companyId, String accountName);

    /**
     * 新增科目
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean addAccountTitle(UserInfo info, AccountTitleModel model) throws OwnerException;

    /**
     * 修改科目
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean updateAccountTitle(UserInfo info, AccountTitleModel model) throws OwnerException;

    /**
     * 删除科目
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delAccountTitle(UserInfo info, AccountTitleModel model) throws OwnerException;

    /**
     * 查询所有科目信息
     *
     * @param companyId
     * @return
     */
    List<AccountTree> queryAllAccountTitle(int companyId);

    /**
     * 导入科目
     * @param userInfo
     * @param data
     * @return
     * @throws OwnerException
     */
    String importAccount(UserInfo userInfo, List<AccountExcel> data) throws OwnerException;
}
