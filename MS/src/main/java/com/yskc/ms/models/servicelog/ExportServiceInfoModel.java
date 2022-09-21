package com.yskc.ms.models.servicelog;

import com.yskc.common.utils.DateUtil;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.servicelog
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-01 08:49
 * @Description: 导出model
 */
public class ExportServiceInfoModel extends ServiceInfoModel {

    private String serviceTypeStr;

    public String getStartDateStr() {
        Date startDate = getStartDate();
        return startDate != null ? DateUtil.format(startDate, "yyyy-MM-dd HH:mm") : null;
    }

    public String getEndDateStr() {
        Date endDate = getEndDate();
        return endDate != null ? DateUtil.format(endDate, "yyyy-MM-dd HH:mm") : null;
    }


    public String getServiceTypeStr() {
        return serviceTypeStr;
    }

    public void setServiceTypeStr(String serviceTypeStr) {
        this.serviceTypeStr = serviceTypeStr;
    }
}
