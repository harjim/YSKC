package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EnergyExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 08:18
 * @Description: 能源损耗业务层接口
 */
public interface EnergyService {

    /**
     * 导入数据
     *
     * @param info
     * @param energyExcels
     * @param type
     * @return
     * @throws OwnerException
     */
    boolean importEnergy(RsUserInfo info, List<EnergyExcel> energyExcels, Integer type, Integer year) throws OwnerException;

}
