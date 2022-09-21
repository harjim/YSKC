package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.ProductAddStageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.*;
import org.apache.xpath.operations.Bool;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/10/13 8:28
 * description:
 */
public interface RsProductService {

    Boolean add(RsProductModel model, UserInfo userInfo) throws OwnerException;

    List<RsDirectionModel> update(RsProductModel model, UserInfo userInfo) throws OwnerException;

    PageModel<List<RsProductModel>> getList(QueryRsProductModel query, DataPermModel dataPermModel);

    Boolean del(List<RsProductModel> models, Integer userId) throws OwnerException;
    /**
     * 获取方向配置的阶段阶段
     * @param directionId
     * @return
     */
    List<RsProductStageModel> getDirectionStage(Integer directionId);

    /**
     * 获取申报项目方向列表
     * @param productId
     * @return
     */
    List<RsDirectionModel> getDirection(Integer productId);

    /**
     * 申报项目方向添加阶段
     * @param model
     * @param userInfo
     * @return
     */
    boolean addStages(List<ProductAddStageModel> model, UserInfo userInfo) throws OwnerException;

    /**
     * 申报项目删除阶段
     * @param model
     * @param userInfo
     * @return
     */
    boolean delStages(ProductAddStageModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 编辑阶段
     * @param models
     * @param userInfo
     * @return
     */
    boolean editStages(List<ProductAddStageModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 保存方向阶段资料
     * @param models
     * @param userInfo
     * @return
     */
    List<RsProductStageListModel> SetStageList(List<RsProductStageListModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 删除阶段资料
     * @param models
     * @param userInfo
     * @return
     */
    boolean delStageList(List<RsProductStageListModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 删除申报项目方向
     * @param model
     * @param userId
     * @return
     */
    Boolean delDirection(RsDirectionModel model, Integer userId) throws OwnerException;

    /**
     * 导入申报项目
     * @param info
     * @param readAll
     * @return
     */
    boolean importProduct(UserInfo info, List<Map<String, Object>> readAll) throws OwnerException;

    /**
     * 复制申报项目
     * @param addressCode
     * @param year
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean copyProject(String addressCode, Integer year, Integer id,UserInfo info) throws OwnerException;

    /**
     * 分配负责人
     * @param models
     * @param userInfo
     * @return
     */
    Boolean allocatingStaff(List<RsProductModel> models, UserInfo userInfo);
}
