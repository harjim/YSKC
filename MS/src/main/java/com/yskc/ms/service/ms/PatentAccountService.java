package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.PatentAccountModel;
import com.yskc.ms.models.patent.PatentAccountTreeModel;
import com.yskc.ms.models.patent.QueryPatentListModel;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: wangxing
 * @CreateTime: 2020-01-03 14:14
 * @Description: 专利账号管理接口
 */
public interface PatentAccountService {
    /**
     * 获取账号信息
     * @return
     */
    PageModel<List<PatentAccountModel>> queryPatentAccountList(QueryPatentListModel query, DataPermModel perm);

    /**
     * 添加
     * @param model
     * @param info
     * @return
     */
    boolean addPatentAccount(PatentAccountModel model, UserInfo info);

    /**
     * 修改
     * @param model
     * @param info
     * @return
     */
    boolean updatePatentAccount(PatentAccountModel model,UserInfo info);

    /**
     * 刪除
     * @param id
     * @return
     */
    boolean delPatentAccount(Integer id);

    /**
     * 批量刪除
     * @param patentAccountModels
     * @return
     */
    boolean delBatch(List<PatentAccountModel> patentAccountModels);


    /**
     * 检查专利账号是否重复
     *
     * @param userName
     * @return
     */
    boolean checkUsername(String userName,Integer id);

    /**
     * 获取专利账号tree
     * @return
     */
    List<PatentAccountTreeModel> getPatentAccountTree();

    /**
     * 更新状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(Integer id,String password,Integer status);
}
