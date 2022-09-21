package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.tech.TechProjectCostDao;
import com.yskc.rs.dao.tech.TechProjectDao;
import com.yskc.rs.entity.tech.TechProjectCostEntity;
import com.yskc.rs.entity.tech.TechProjectEntity;
import com.yskc.rs.enums.ProjectCostTypeEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.TechProjectCostExcel;
import com.yskc.rs.models.tech.cost.ExportTechProjectCostModel;
import com.yskc.rs.models.tech.cost.MinProjectCostModel;
import com.yskc.rs.models.tech.cost.QueryTechProjectCostModel;
import com.yskc.rs.models.tech.cost.TechProjectCostModel;
import com.yskc.rs.service.TechProjectCostService;
import com.yskc.rs.utils.YsExcelUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private TechProjectDao techProjectDao;

    @Autowired
    private RsConfig rsConfig;

    @Override
    public PageModel<List<TechProjectCostModel>> getList(Integer companyId, QueryTechProjectCostModel query) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, techProjectCostDao.getList(page, companyId, query));
    }

    @Override
    public boolean add(UserInfo userInfo, TechProjectCostModel techProjectCostModel) {
        Date now = new Date();
        TechProjectCostEntity techProjectCostEntity = getCostEntity(userInfo, techProjectCostModel, now);
        techProjectCostEntity.setCompanyId(userInfo.getCompanyId());
        techProjectCostEntity.setCreatorId(userInfo.getId());
        techProjectCostEntity.setCreateTime(now);
        return techProjectCostDao.insert(techProjectCostEntity) > 0;
    }

    @Override
    public boolean update(UserInfo userInfo, TechProjectCostModel techProjectCostModel) {
        TechProjectCostEntity techProjectCostEntity = getCostEntity(userInfo, techProjectCostModel, new Date());
        return techProjectCostDao.updateById(techProjectCostEntity) > 0;
    }

    @Override
    public boolean del(Integer id) {
        return techProjectCostDao.deleteById(id) > 0;
    }

    @Override
    public boolean dels(List<TechProjectCostEntity> techProjectCostEntities) {
        List<Integer> ids = techProjectCostEntities.stream().map(TechProjectCostEntity::getId).collect(Collectors.toList());
        return techProjectCostDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean importInfo(UserInfo userInfo, List<TechProjectCostExcel> data, Integer projectId) throws OwnerException {
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

    @Override
    public void exportCost(Integer projectId, UserInfo userInfo, OutputStream out) {
        QueryTechProjectCostModel query = new QueryTechProjectCostModel();
        query.setProjectId(projectId);
        List<TechProjectCostModel> exportData = techProjectCostDao.getList(userInfo.getCompanyId(), query);
        TechProjectEntity techProjectEntity = techProjectDao.selectById(projectId);
        Map<String, Object> dataMap = new HashMap<>();
        List<ExportTechProjectCostModel> eList = new ArrayList<>();
        List<ExportTechProjectCostModel> cList = new ArrayList<>();
        List<ExportTechProjectCostModel> iList = new ArrayList<>();
        BigDecimal eTotal = BigDecimal.ZERO;
        BigDecimal cTotal = BigDecimal.ZERO;
        BigDecimal iTotal = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal oneHundred = new BigDecimal(100);
        for (int i = 0; i < exportData.size(); i++) {
            TechProjectCostModel item = exportData.get(i);
            totalAmount = totalAmount.add(item.getAuditAmount());
            if (ProjectCostTypeEnum.EQUIPMENT.getType() == item.getCtype()) {
                eList.add(ExportTechProjectCostModel.build(item, eList.size() + 1));
                eTotal = eTotal.add(item.getFillAmount());
                continue;
            }
            if (ProjectCostTypeEnum.CONSTRUCTION.getType() == item.getCtype()) {
                cList.add(ExportTechProjectCostModel.build(item, cList.size() + 1));
                cTotal = cTotal.add(item.getFillAmount());
                continue;
            }
            if (ProjectCostTypeEnum.INIT_WORK_CAPITAL.getType() == item.getCtype()) {
                iList.add(ExportTechProjectCostModel.build(item, iList.size() + 1));
                iTotal = iTotal.add(item.getFillAmount());
            }

        }

        dataMap.put("pname", techProjectEntity.getPname());
        dataMap.put("eTotal", eTotal);
        dataMap.put("cTotal", cTotal);
        dataMap.put("iTotal", iTotal);
        dataMap.put("totalAmount", totalAmount);
        BigDecimal payeeAmount = eTotal.add(cTotal).add(iTotal);
        dataMap.put("payeeAmount", payeeAmount);
        dataMap.put("eList", eList);
        dataMap.put("cList", cList);
        dataMap.put("iList", iList);
        dataMap.put("eRatio", eTotal.doubleValue() <= 0.0 ? 0 : eTotal.multiply(oneHundred).divide(payeeAmount, 2, BigDecimal.ROUND_HALF_UP));
        dataMap.put("cRatio", cTotal.intValue() <= 0.0 ? 0 : cTotal.multiply(oneHundred).divide(payeeAmount, 2, BigDecimal.ROUND_HALF_UP));
        dataMap.put("iRatio", iTotal.intValue() <= 0.0 ? 0 : iTotal.multiply(oneHundred).divide(payeeAmount, 2, BigDecimal.ROUND_HALF_UP));
        dataMap.put("beginAndEnd", DateUtil.format(techProjectEntity.getBeginDate(), "yyyy年MM月") + '-' + DateUtil.format(techProjectEntity.getEndDate(), "yyyy年MM月"));
        YsExcelUtils.generalReport(dataMap, rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "项目支出明细模板.xls", (workbook) -> {
            completeExcel(workbook, eList, cList, iList);
            try {
                workbook.write(out);
                workbook.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public Map<String, Object> getProjectCost(Integer companyId, Integer projectId) {
        BigDecimal[] costArr = {
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP)};
        List<MinProjectCostModel> datas = techProjectCostDao.getProjectCost(companyId, projectId);
        datas.forEach(item -> {
            if (ProjectCostTypeEnum.EQUIPMENT.getType() == item.getCtype()) {
                costArr[0] = costArr[0].add(item.getAuditAmount());
                return;
            }
            if (ProjectCostTypeEnum.CONSTRUCTION.getType() == item.getCtype()) {
                costArr[1] = costArr[1].add(item.getAuditAmount());
                return;
            }
            if (ProjectCostTypeEnum.INIT_WORK_CAPITAL.getType() == item.getCtype()) {
                costArr[2] = costArr[2].add(item.getAuditAmount());
            }
        });
        costArr[3] = costArr[0].add(costArr[1]).add(costArr[2]);
        Map<String, Object> result = new HashMap<>();
        result.put("costArr", costArr);
        result.put("datas", datas);
        return result;
    }

    /**
     * 获取支出实体
     *
     * @param userInfo
     * @param techProjectCostModel
     * @param now
     * @return
     */
    private TechProjectCostEntity getCostEntity(UserInfo userInfo, TechProjectCostModel techProjectCostModel, Date now) {
        TechProjectCostEntity techProjectCostEntity = new TechProjectCostEntity();
        BeanUtil.copyProperties(techProjectCostModel, techProjectCostEntity);
        if (techProjectCostModel.getModel() == null) {
            techProjectCostEntity.setModel("");
        }
        if (StringUtils.isEmpty(techProjectCostEntity.getQuantity())) {
            // 默认数量
            techProjectCostEntity.setQuantity(1);
        }
        techProjectCostEntity.setLastUpdatorId(userInfo.getId());
        techProjectCostEntity.setLastUpdateTime(now);
        return techProjectCostEntity;
    }

    private static void completeExcel(Workbook workbook, List<ExportTechProjectCostModel> eList, List<ExportTechProjectCostModel> cList, List<ExportTechProjectCostModel> iList) {
        Sheet sheet = workbook.getSheetAt(0);
        int row = 4;
        if (!CollectionUtils.isEmpty(eList)) {
            YsExcelUtils.mergeCell(workbook, 0, row, row + eList.size(), 1, 1, "一、设备");
            for (int i = 0; i < eList.size(); i++, row++) {
                YsExcelUtils.setCellValue(sheet, row, 0, (i + 1) + "");
            }
        } else {
            YsExcelUtils.mergeCell(workbook, 0, row, ++row, 1, 1, "一、设备");
        }
        row += 1;
        if (!CollectionUtils.isEmpty(cList)) {
            YsExcelUtils.mergeCell(workbook, 0, row, row + cList.size(), 1, 1, "二、建设费");
            for (int i = 0; i < cList.size(); i++, row++) {
                YsExcelUtils.setCellValue(sheet, row, 0, (i + 1) + "");
            }
        } else {
            YsExcelUtils.mergeCell(workbook, 0, row, ++row, 1, 1, "二、建设费");
        }
        row += 1;
        if (!CollectionUtils.isEmpty(iList)) {
            YsExcelUtils.mergeCell(workbook, 0, row, row + iList.size(), 1, 1, "三、铺底流动资金");
            for (int i = 0; i < iList.size(); i++, row++) {
                YsExcelUtils.setCellValue(sheet, row, 0, (i + 1) + "");
            }
        } else {
            YsExcelUtils.mergeCell(workbook, 0, row, row + 1, 1, 1, "三、铺底流动资金");
        }
    }

}
