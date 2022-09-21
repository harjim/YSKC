package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.QueryTechProjectModel;
import com.yskc.ms.models.rs.RelatedProjectModel;
import com.yskc.ms.models.rs.RsProductListModel;
import com.yskc.ms.models.rs.RsTechProjectModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/15 11:34
 * description:
 */
public interface RsTProjectService {
    PageModel<List<RsTechProjectModel>> getList(QueryTechProjectModel query, DataPermModel dataPerm);

    /**
     * 获取申报项目列表
     *
     * @return
     */
    List<RsProductListModel> getProductList(String addressCode, Integer year);


    /**
     * 关联申报项目
     *
     * @param model
     * @param userInfo
     * @return
     */
    boolean relatedProject(RelatedProjectModel model, UserInfo userInfo);

    /**
     * 取消关联申报项目
     *
     * @param model
     * @param userInfo
     * @return
     */
    boolean disassociation(RsTechProjectModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取技改项目
     *
     * @param projectId
     * @param beian
     * @return
     */
    RelatedProjectModel getProject(Integer projectId,Boolean beian);

    /**
     * 关联备案项目
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean saveBeian(RelatedProjectModel model, UserInfo userInfo)throws OwnerException;
}
