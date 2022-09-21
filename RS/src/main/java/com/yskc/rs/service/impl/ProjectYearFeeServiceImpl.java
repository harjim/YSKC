package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.project.ProjectOutsourcingDao;
import com.yskc.rs.dao.project.ProjectYearFeeDao;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.entity.project.ProjectOutsourcing;
import com.yskc.rs.entity.project.ProjectYearFeeEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.ProjectDetailModel;
import com.yskc.rs.models.projectyearfee.ProjectYearFeeInfoModel;
import com.yskc.rs.models.summary.DataFundsModel;
import com.yskc.rs.service.CompanySettingService;
import com.yskc.rs.service.ProjectYearFeeService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-07 08:57
 * @Description: 优惠明细表年费用业务实现层
 */
@Service
public class ProjectYearFeeServiceImpl implements ProjectYearFeeService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private ProjectOutsourcingDao projectOutsourcingDao;
    @Autowired
    private CompanySettingService companySettingService;
    @Autowired
    private ProjectYearFeeDao projectYearFeeDao;

    @Override
    public void exportDetailData(Integer year, UserInfo info, OutputStream out) throws OwnerException {
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "费用明细.xls";
        Map<Integer, List<Map<String, Object>>> map = new HashMap<>();
        List<String> sheetNames = new ArrayList<>();
        sheetNames.add("加计扣除优惠明细表");
        sheetNames.add("总归集表");
        List<Map<String, Object>> list = new ArrayList<>();
        map.put(0, CollUtil.newArrayList(getDetailData(year, info, list, sheetNames)));
//        list.add(totalMap);
//        projectMap.keySet().forEach(item -> {
//            Map<String, Object> pMap = projectMap.get(item);
//            if (pMap.containsKey(exportKey)) {
//                sheetNames.add((String) pMap.get("rdNumber"));
//                fieldSet.forEach(fieldStr -> pMap.computeIfAbsent(fieldStr, k -> BigDecimal.ZERO));
//                list.add(pMap);
//            }
//        });
        CollUtil.reverse(list);
        map.put(1, list);
        YsExcelUtils.generalMoreSheetsReport(map, templatePath, (workbook) -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, sheetNames);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }


    @Override
    public Map<String, Object> getDetailData(Integer year, UserInfo info, List<Map<String, Object>> list, List<String> sheetNames) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        Integer companyId = info.getCompanyId();
        CompanyEntity company = companyDao.selectById(companyId);
        List<ProjectDetailModel> projects = projectDao.getProjectIdsByYear(companyId, year, true);// // TODO: 21/06/11 zdf child改为了true
        Map<Integer, Map<String, Object>> projectMap = new LinkedHashMap<>();
        projects.forEach(item -> {
            Map<String, Object> pMap = new HashMap<>();
            putRdDetail(pMap, item.getPname(), company.getCompanyName(), company.getTaxpayerId(), year);
            pMap.put("rdNumber", item.getRdTitle());
            projectMap.put(item.getId(), pMap);
        });
        List<DataFundsModel> fundsList = summaryDao.getYearFunds(companyId, beginDate, endDate);
        List<ProjectOutsourcing> outsourcingList = projectOutsourcingDao.getYearOutsourcing(beginDate, endDate, companyId);
        Map<Integer, CostEnum> parentCost = CostEnum.getParentMap(false);
        Map<String, Object> totalMap = new HashMap<>();
        totalMap.put("inside", BigDecimal.ZERO);
        totalMap.put("outside", BigDecimal.ZERO);
        putRdDetail(totalMap, "研发项目可加计扣除研究开发费用情况归集表", company.getCompanyName(), company.getTaxpayerId(), year);
        if (!CollectionUtils.isEmpty(outsourcingList)) {
            outsourcingList.forEach(item -> {
                if (item.getType() == 0) {
                    totalMap.put("inside", item.getRdFunds());
                } else {
                    totalMap.put("outside", item.getRdFunds());
                }
            });
        }
        totalMap.put("rdCount", projectMap.size());
//        Map<Integer, Map<String, Object>> projectFundMap = new LinkedHashMap<>();
        fundsList.forEach(item -> {
            Integer currentProjectId = item.getParentId() > 0 ? item.getParentId() : item.getProjectId();
            Map<String, Object> pMap = projectMap.get(currentProjectId);
            if (!CollectionUtils.isEmpty(pMap)) {
                pMap.put("export", true);
                String field = parentCost.get(ToolUtils.getParentType(item.getRdType(), true)).getField();
                pMap.put(field, item.getRdFunds().add((BigDecimal) pMap.getOrDefault(field, BigDecimal.ZERO)));
                totalMap.put(field, item.getRdFunds().add((BigDecimal) totalMap.getOrDefault(field, BigDecimal.ZERO)));
            }
        });

        ProjectYearFeeInfoModel yearFeeInfo = projectYearFeeDao.getYearFee(companyId, year);
        if (null == yearFeeInfo) {
            yearFeeInfo = new ProjectYearFeeInfoModel();
        }
        BigDecimal rdRatio = yearFeeInfo.getRdRatio();
        String rdRatioStr = new DecimalFormat("0.00%").format(rdRatio);
        Set<String> fieldSet = CostEnum.getFiledMap().keySet();
        if (list != null) {
            String exportKey = "export";
            list.add(totalMap);
            projectMap.values().forEach(item -> {
                if (item.containsKey(exportKey)) {
                    fieldSet.forEach(fieldStr -> item.computeIfAbsent(fieldStr, k -> BigDecimal.ZERO));
                    item.put("rdRatioStr", rdRatioStr);
                    item.put("rdRatio", rdRatio);
                    sheetNames.add((String) item.get("rdNumber"));
                    list.add(item);
                }
            });
        }
        fieldSet.forEach(fieldStr -> totalMap.computeIfAbsent(fieldStr, k -> BigDecimal.ZERO));
//        CompanySettingModel companySettingModel = companySettingService.getSetting(companyId);
//        totalMap.put("rdRatio", companySettingModel.getRdRatio().getOrDefault(year.toString(), "0.75"));
        totalMap.putAll(BeanUtil.beanToMap(yearFeeInfo));
        totalMap.put("rdRatioStr", rdRatioStr);
        return totalMap;
    }

    @Override
    public Boolean save(ProjectYearFeeEntity entity, UserInfo userInfo) {
        entity.create(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        entity.setCompanyId(userInfo.getCompanyId());
        return projectYearFeeDao.insertOrUpdate(entity) > 0;

    }

    public void putRdDetail(Map<String, Object> map, String pname, String companyName, String taxpayerId, Integer year) {
        map.put("pname", pname);
        map.put("companyName", companyName);
        map.put("taxpayerId", taxpayerId);
        map.put("year", year);
    }
}
