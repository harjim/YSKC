package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.MaterialExcel;
import com.yskc.docservice.models.rs.material.ImportMaterialModel;
import java.util.List;

/**
 * @author Administrator
 */
public interface MaterialService {


    /**
     * 导入物料清单
     *
     * @param userInfo
     * @param materialExcels
     * @param type
     * @param year
     * @param isRD
     * @return
     * @throws OwnerException
     */
    ImportMaterialModel importMaterial(RsUserInfo userInfo, List<MaterialExcel> materialExcels, Integer type,
                                       Integer year, Boolean isRD) throws OwnerException;

    /**
     * 确认导出
     *
     * @param info
     * @param data
     * @param type
     * @param year
     * @param isRD
     * @return
     * @throws OwnerException
     */
    boolean confirmImport(RsUserInfo info, List<MaterialExcel> data, Integer type, Integer year
            , Boolean isRD) throws OwnerException;
}
