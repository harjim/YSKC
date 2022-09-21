package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.WorkshopExcel;
import com.yskc.rs.models.workshop.WorkshopTreeModel;
import com.yskc.rs.models.workshop.WorkshopModel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-11-21 09:18
 * @Description: WorkshopService
 */
public interface WorkshopService {
    /**
     * 获取工艺线/车间数据
     *
     * @param companyId
     * @param workshopName
     * @return
     */
    List<WorkshopModel> queryWorkshop(Integer companyId, String workshopName);

    /**
     * 获取工艺线/车间下拉数据
     *
     * @param companyId
     * @return
     */
    List<WorkshopTreeModel> getWorkshopTree(Integer companyId);

    /**
     * 添加工艺线
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean addWorkshop(UserInfo info, WorkshopModel model) throws OwnerException;

    /**
     * 编辑工艺线
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateWorkshop(UserInfo info, WorkshopModel model) throws OwnerException;

    /**
     * 删除
     *
     * @param id
     * @return
     */
    boolean delWorkshop(Integer companyId, Integer id) throws OwnerException;

    /**
     * 检查重复序号
     * @param companyId
     * @param parentId
     * @param seq
     * @return
     */
    boolean checkSeq(Integer companyId, Integer parentId, Integer seq);

    /**
     * 查询该节点最大的序号
     * @param companyId
     * @param parentId
     * @return
     */
    Integer selectMaxSeq(Integer companyId, Integer parentId);

    String importWorkshop(UserInfo info, List<WorkshopExcel> data) throws OwnerException;
}
