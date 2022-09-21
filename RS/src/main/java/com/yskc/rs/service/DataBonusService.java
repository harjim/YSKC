package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataBonusInfo;
import com.yskc.rs.models.data.DataBonusModel;
import com.yskc.rs.models.data.DataBonusQuery;
import com.yskc.rs.models.excel.BonusExcel;

import java.util.List;

/**
 * 奖金服务
 * @author huronghua
 */
public interface DataBonusService {
    /**
     * 添加员工奖金
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean add(UserInfo userInfo,DataBonusModel model) throws OwnerException;

    /**
     * 获取奖金列表
     * @param dataBonusQuery
     * @return
     * @throws OwnerException
     */
    PageModel<List<DataBonusInfo>> getDataBonusList(DataBonusQuery dataBonusQuery) throws OwnerException;

    /**
     * 批量删除
     * @param userInfo
     * @param ids
     * @return
     * @throws OwnerException
     */
    boolean deleteList(UserInfo userInfo,List<Integer> ids)throws OwnerException;

    /**
     * 导入奖金报表
     * @param userInfo
     * @param bonusExcels
     * @param year
     * @return
     * @throws OwnerException
     */
    String importData(UserInfo userInfo, List<BonusExcel> bonusExcels,Integer year)throws OwnerException;

    /**
     * 导出奖金报表
     * @param dataBonusQuery
     * @return
     */
    List<BonusExcel> exportBonusData( DataBonusQuery dataBonusQuery);
}
