package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.BatchZipModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.*;
import com.yskc.ms.models.patentPlan.SetPatentEngineerModel;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:26
 * @Description:
 */
public interface PatentBuyingDemandService {
    /**
     * 获取需求列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<PatentBuyingDemandModel>> getList(QueryDemandModel query, DataPermModel dataPerm);

    /**
     * 保存专利需求
     *
     * @param model
     * @param userInfo
     * @return
     */
    Integer saveDemand(PatentBuyingDemandModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 提交专利需求
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean submitDemand(PatentBuyingDemandModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 添加专利公海
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean savePatentSea(PatentSeaModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取专利公海列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<PatentSeaModel>> getPatentSea(QueryPatentModel query, DataPermModel dataPerm);

    /**
     * 专利公海关联购买需求
     *
     * @param models
     * @param userInfo
     * @return
     */
    Boolean relatedDemand(List<PatentSeaModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 删除专利公海专利
     *
     * @param ids
     * @param userInfo
     * @return
     */
    Boolean delPatentSea(List<Integer> ids, UserInfo userInfo) throws OwnerException;

    /**
     * 获取专利预购列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<PatentBuyingModel>> getBuyingList(QueryPatentModel query, DataPermModel dataPerm);

    /**
     * 购买专利
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean buyPatent(PatentBuyingModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 完成购买专利
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean completePurchase(PatentBuyingModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 判断专利号唯一
     *
     * @param model
     * @return
     */
    Boolean limitPatentNo(PatentSeaModel model) throws OwnerException;

    /**
     * 删除需求
     *
     * @param models
     * @param userInfo
     * @return
     */
    Boolean delDemand(List<PatentBuyingDemandModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 专利返销
     *
     * @param ids
     * @param userInfo
     * @return
     */
    Boolean buyBack(List<Integer> ids, UserInfo userInfo) throws OwnerException;

    /**
     * 获取购买需求列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<PatentBuyingDemandModel>> getBuyDemandList(QueryDemandModel query, DataPermModel dataPerm);

    /**
     * 获取导出的数据流
     *
     * @param query
     * @return
     * @throws OwnerException
     */
    BatchZipModel getExportStream(QueryPatentModel query) throws OwnerException, FileNotFoundException;

    /**
     * 更新转让资料
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean updateSellFile(PatentBuyingModel model, UserInfo userInfo);

    /**
     * 设置工程师
     *
     * @param setEngineer
     * @param userId
     * @return
     */
    Boolean setEngineer(SetPatentEngineerModel setEngineer, Integer userId);
}
