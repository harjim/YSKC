package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/7/1 8:38
 * description:
 */
public interface RsPatentService {
    /**
     * 查询专利列表
     *
     * @param model
     * @return
     */
    PageModel<List<PatentModel>> queryPatentList(QueryPatentModel model);

    /**
     * 关联客户
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean updatePatentCustomerId(PatentModel model, UserInfo userInfo);

    /**
     * 修改专利
     *
     * @param info
     * @param model
     * @return
     */
    boolean updatePatent(UserInfo info, PatentModel model);

    /**
     * 添加专利
     *
     * @param model
     * @return
     */
    boolean addPatent(PatentModel model, UserInfo info) throws OwnerException;

    /**
     * 检查专利号是否存在
     *
     * @param patentNo
     * @return
     */
    Boolean checkPatentNo(String patentNo) throws OwnerException;

    /**
     * 删除专利
     *
     * @param model
     * @return
     */
    boolean delete(PatentModel model) throws OwnerException;

    /**
     * 查询专利申请数据
     *
     * @param patentNo
     * @return
     */
    List<RsPatentApplyModel> getDataByPatentNo(String patentNo);

    boolean importPatent(UserInfo info, List<Map<String, Object>> list) throws OwnerException;

    /**
     * 获取所有专利
     *
     * @param ids
     * @param patentNo
     * @return
     */
    List<RsPatentEntity> getAll(List<Integer> ids, String patentNo);

    /**
     * 批量操作专利
     *
     * @param unionModel
     * @param info
     */
    void savePatent(PatentUnionModel unionModel, UserInfo info);

    /**
     * 获取权利要求内容/说明书内容
     *
     * @param id
     * @return
     * @throws OwnerException
     */
    PatentSpecificationModel getSpecification(Integer id) throws OwnerException;
}
