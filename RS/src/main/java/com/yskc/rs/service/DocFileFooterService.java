package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.QueryDocFileFooterModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @DateTime: 2022/3/8 8:22
 * @Description:
 * @author: hsx
 */
public interface DocFileFooterService {

    /**
     * 获取签名汇总数据列表
     * @param model
     * @return
     */
    PageModel<List<DocFileFooterModel>> getList(QueryDocFileFooterModel model);

    Boolean addDocFileFooter(DocFileFooterModel model,UserInfo info);

    Boolean editDocFileFooter(DocFileFooterModel model,UserInfo info);

    /**
     * 导出项目签名汇总
     * @param model
     * @param info
     * @param out
     */
    void exportEquipment(QueryDocFileFooterModel model, UserInfo info, OutputStream out) throws OwnerException;
}
