package com.yskc.ms.service.rs;


import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.RsDocFileTemplateEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import com.yskc.ms.models.rs.RsDocFilesModel;

import java.util.List;

public interface RsDocFileTemplateService {

    /**
     * 获取模板id获取模板
     *
     * @param docFileTemplateId
     * @return
     */
    RsDocFileTemplateModel getData(Integer docFileTemplateId);


    /**
     * 保存版本内容
     *
     * @param userInfo
     * @param rsDocFileTemplateModel
     * @return
     */
    Boolean saveData(UserInfo userInfo, RsDocFileTemplateModel rsDocFileTemplateModel, Boolean isHave);

    /**
     * 获取所有模板
     *
     * @param pageNo
     * @param pageSize
     * @param docName
     * @return
     */
    PageModel<List<RsDocFilesModel>> getDocFilesList(int pageNo, int pageSize, String docName);

    /**
     * 删除模板
     *
     * @param id
     * @return
     */
    Boolean delTemplate(Integer id);

    List<RsDocFileTemplateEntity> findDocFileId(Integer docFileId);

}
