package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.dao.rs.TechProjectCostDao;
import com.yskc.docservice.entity.rs.tech.TechProjectCostEntity;
import com.yskc.docservice.enums.ProjectCostTypeEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.TechProjectCostExcel;
import com.yskc.docservice.service.rs.TechProjectCostService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 08:25
 * @Description: 项目支出业务实现层
 */
@Service
public class TechProjectCostServiceImpl implements TechProjectCostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TechProjectCostDao techProjectCostDao;

    @Override
    public boolean importInfo(RsUserInfo userInfo, List<TechProjectCostExcel> data, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            return true;
        }
        Map<String, Integer> typeMap = ProjectCostTypeEnum.getTypeMap();
        List<TechProjectCostEntity> costList = new ArrayList<>(data.size());
        TechProjectCostEntity costEntity;
        TechProjectCostExcel costExcel;
        Integer currentType = 1;
        Integer defaultQuantity = 1;
        int defaultType = 0;
        String yes = "是";
        Date now = new Date();
        for (int i = 0; i < data.size(); i++) {
            costExcel = data.get(i);
            costEntity = new TechProjectCostEntity();
            BeanUtil.copyProperties(costExcel, costEntity);
            currentType = typeMap.get(costExcel.getCtypeName());
            costEntity.setCtype(currentType != null ? currentType : defaultType);
            if (StringUtils.isEmpty(costEntity.getQuantity())) {
                costEntity.setQuantity(defaultQuantity);
            }
            costEntity.setIsBankTransfer(!StringUtils.isEmpty(costExcel.getIsBankTransferStr()) && costExcel.getIsBankTransferStr().trim().equals(yes));
            costEntity.setCompanyId(userInfo.getCompanyId());
            costEntity.setCreatorId(userInfo.getId());
            costEntity.setLastUpdatorId(userInfo.getId());
            costEntity.setCreateTime(now);
            costEntity.setLastUpdateTime(now);
            costEntity.setProjectId(projectId);
            costList.add(costEntity);
        }
        return techProjectCostDao.addList(costList) > 0;
    }
}
