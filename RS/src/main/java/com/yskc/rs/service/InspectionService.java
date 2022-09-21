package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.InspectionExcel;
import com.yskc.rs.models.inspection.InspectionModel;
import com.yskc.rs.models.inspection.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.QueryProjectInspectionModel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 15:05
 * @Description: 检测修理业务层接口
 */
public interface InspectionService {
    /**
     * @Description: 操作设备 // 新增或修改
     * @Param: [currentUserId, companyId, modal]
     * @return: java.lang.Boolean
     * @Author: zhangdingfu
     * @date: 2019-07-09
     */
    Boolean modifyInspection(UserInfo info, InspectionModel model) throws OwnerException;

    /**
     * 查询所有信息
     *
     * @param companyId
     * @param inspectionQuery
     * @return
     */
    PageModel<List<InspectionModel>> queryAll(Integer companyId, QueryInspectionModel inspectionQuery);

    /**
     * @Description: 导入数据
     * @Param: [info, equipmentExcels]
     * @return: java.lang.Boolean
     * @Author: zhangdingfu
     * @date: 2019-07-10
     */
    String importInspection(UserInfo info, List<InspectionExcel> equipmentExcels, Integer type, Integer year) throws OwnerException;

    /**
     * 导出数据
     *
     * @param compantyId
     * @param inspectionQuery
     * @return
     */
    List<InspectionExcel> exportInspection(Integer compantyId, QueryInspectionModel inspectionQuery);

    /**
     * 删除数据
     *
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean deleteById(Integer id) throws OwnerException;

    /**
     * 根据条件查询检测修理信息
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<InspectionModel>> queryInspectionByTerm(Integer companyId,
                                                           QueryProjectInspectionModel query);

    /**
     * 批量研发部门
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateInspectionRdDept(UserInfo info, InspectionModel model);


    /**
     * 批量费用类型
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateInspetioneType(UserInfo info, InspectionModel model);


    /**
     * 批量删除
     *
     * @param models
     * @return
     * @throws OwnerException
     */
    boolean delInspetiontBatch(List<InspectionModel> models) throws OwnerException;

    /**
     * 查询
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InspectionModel>> queryTravelFee(Integer companyId, QueryProjectInspectionModel query);


}
