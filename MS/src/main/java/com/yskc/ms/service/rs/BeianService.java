package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.tech.BeianInfoModel;
import com.yskc.ms.models.tech.QueryBeianModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/29 8:10
 * @Description:备案管理（新增）
 */
public interface BeianService {
    /**
     * 获取备案列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<BeianInfoModel>> getList(QueryBeianModel query, DataPermModel dataPerm);

    /**
     * 导出备案列表
     * @param query
     * @param dataPerm
     * @param out
     * @param path
     * @throws OwnerException
     */
    void getExport(QueryBeianModel query, DataPermModel dataPerm, OutputStream out, String path) throws OwnerException;

}
