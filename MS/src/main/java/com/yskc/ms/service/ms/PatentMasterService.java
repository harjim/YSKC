package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentMaster.PatentMasterModel;
import com.yskc.ms.models.patentMaster.QueryMasterModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/7 14:58
 * description:
 */
public interface PatentMasterService {

    PageModel<List<PatentMasterModel>> queryMasterList(QueryMasterModel model, UserInfo userInfo, DataPermModel perm);

    Boolean addMaster(PatentMasterModel model, UserInfo userInfo) throws OwnerException;

    Boolean updateMaster(PatentMasterModel model, UserInfo userInfo) throws OwnerException;

    Boolean delMaster(PatentMasterModel model) throws OwnerException;

    List<PatentMasterModel> getMasterList(QueryMasterModel model);


    boolean checkMasterName(PatentMasterModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取代理人下拉
     *
     * @return
     */
    List<MiniModel> getSelect();
}
