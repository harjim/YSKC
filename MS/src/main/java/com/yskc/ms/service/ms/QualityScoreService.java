package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.qualityscore.*;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/5/13 15:47
 * @Description:
 * @author: hsx
 */
public interface QualityScoreService {

    /**
     * 保存评分
     * @param info
     * @param model
     * @return
     */
    Boolean saveScore(QualityScoreModel model, UserInfo info) throws OwnerException;

    /**
     * 获取评分历史记录
     * @param model
     * @return
     */
    Map<String,List<QualityScoreLogModel>> getList(QueryQualityScoreModel model);

    /**
     * 获取最后评分记录
     * @param model
     * @return
     */
    QualityScoreInfoModel getScoreConfig(QueryQualityScoreModel model);

    /**
     * 获取批量评分工程师信息
     * @param instanceIds
     * @return
     */
    List<EngineerModel> getCheckConfig(List<Integer> instanceIds);

    /**
     * 修改评分（限制为一天之内的记录）
     * @param model
     * @param userInfo
     * @return
     */
    Boolean editScore(QualityScoreModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取全局权值配置
     * @return
     */
    List<ScoreTypeModel> getWeights();

    /**
     * 修改权值配置
     * @param list
     * @param userId
     * @return
     */
    Boolean saveWeight(List<ScoreTypeModel> list,  Integer userId);

    /**
     * 获取审核序时账表数据
     * @param model
     * @param dataPermModel
     * @return
     */
    PageResult getCollectList(QueryScoreCollectModel model, DataPermModel dataPermModel);

    /**
     * 获取评分下拉列表数据
     * @param model
     * @param dataPermModel
     * @return
     */
    PageModel<List<ScoreModel>> getScoreList(QueryScoreModel model,DataPermModel dataPermModel);

    /**
     * 导出下拉列表数据
     * @param out
     * @param model
     * @param dataPerm
     */
    void export(OutputStream out, QueryScoreCollectModel model, DataPermModel dataPerm);

    /**
     * 批量修改评分记录完成情况
     * @param model
     * @param id
     * @return
     */
    Boolean editCompletion(CompletionEditModel model, Integer id);
}
