package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdEquipmentDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.company.CompanyRdSummaryDao;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.companyrdsummary.QueryCompanyRdSummaryModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.hightech.HighTechModel;
import com.yskc.rs.models.hightech.QueryHighTechModel;
import com.yskc.rs.models.project.ProjectListModel;
import com.yskc.rs.models.projectrdemployee.QueryProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdemployee.RdEmployeeAggModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.rdequipment.RdEquipmentInfoModel;
import com.yskc.rs.models.summary.DataFundsModel;
import com.yskc.rs.models.sysDocument.DocListModel;
import com.yskc.rs.service.CompanyRdSummaryService;
import com.yskc.rs.service.HighTechService;
import com.yskc.rs.service.ProjectRdEmployeeService;
import com.yskc.rs.service.SysDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-05 08:37
 * @Description: 公司研发汇总业务实现层
 */
@Service
public class CompanyRdSummaryServiceImpl implements CompanyRdSummaryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyRdSummaryDao companyRdSummaryDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectRdEmployeeService projectRdEmployeeService;
    @Autowired
    private RdEquipmentDao rdEquipmentDao;
    @Autowired
    private HighTechService highTechService;
    @Autowired
    private SysDocumentService sysDocumentService;
    @Autowired
    private CompanyDao companyDao;

    @Override
    public PageResult getList(QueryCompanyRdSummaryModel query, UserInfo info) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("fullPath", "c.companyName"));
        }
        List<Integer> companyIds = info.getCompanyIds();
        String groupFullPath = info.getGroupFullPath();
        return PageUtils.buildPageResult(page, companyRdSummaryDao.getList(page, query, groupFullPath, companyIds),
                companyRdSummaryDao.getTotal(query, groupFullPath, companyIds));
    }

    @Override
    public Map<String, BigDecimal> getGroupFunds(Integer year, Integer companyId, UserInfo info) throws OwnerException {
        Date begin = DateUtil.getYearFirstDay(year);
        Date end = DateUtil.getYearLastDay(year);
        List<DataFundsModel> fundList = summaryDao.getGroupFunds(begin, end, year, companyId, info.getGroupFullPath());
        Map<String, BigDecimal> result = new HashMap<>();
        if (CollectionUtils.isEmpty(fundList)) {
            return result;
        }
        fundList.forEach(item -> {
            Integer rdType = item.getRdType() / 100;
            if (rdType == 204 || (rdType >= 501 && rdType <= 503)) {
                return;
            } else if (rdType >= 600 && rdType <= 603) {
                rdType = 699;
            }
            String key = rdType + "_" + (cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1);
            if (!result.containsKey(key)) {
                result.put(key, BigDecimal.ZERO);
            }
            result.put(key, result.get(key).add(item.getRdFunds()).setScale(2, BigDecimal.ROUND_HALF_UP));
        });
        return result;
    }

    @Override
    public List<ProjectListModel> getCompanyRdList(Integer year, Integer companyId) throws OwnerException {
        return projectDao.getAllProject(companyId, year);
    }

    @Override
    public PageModel<List<RdEmployeeAggModel>> getCompanyRdEmployeeList(Integer companyId, QueryProjectRdEmployeeModel query) throws OwnerException {
        return projectRdEmployeeService.getRdEmployeeRdsList(companyId, query);
    }

    @Override
    public PageModel<List<RdEquipmentInfoModel>> getCompanyRdEquipmentList(Integer companyId, QueryEquipmentModel query) throws OwnerException {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("re.ecode");
            page.setAscs(ascs);
        }
        List<RdEquipmentInfoModel> result = rdEquipmentDao.getInfoList(page, companyId, query);
        if (!CollectionUtils.isEmpty(result)) {
            List<String> ecodes = result.stream().map(RdEquipmentInfoModel::getEcode).collect(Collectors.toList());
            List<FullYearProjectModel> ecodeRdInfos = rdEquipmentDao.getRdTitles(ecodes, companyId, query.getYear(), false);
            if (!CollectionUtils.isEmpty(ecodeRdInfos)) {
                Map<String, String> ecodeRdMap = new HashMap<>();
                ecodeRdInfos.forEach(item -> ecodeRdMap.put(item.getKey(), item.getRdTitle()));
                result.forEach(item -> {
                    if (ecodeRdMap.containsKey(item.getEcode())) {
                        item.setRds(ecodeRdMap.get(item.getEcode()));
                    }
                });
            }
        }
        return PageUtils.build(page, result);
    }

    @Override
    public List<HighTechModel> getCompanyHighTechList(Integer companyId, QueryHighTechModel query) throws OwnerException {
        return highTechService.getList(companyId, query);
    }

    @Override
    public List<DocListModel> getBuildList(Integer year, Integer companyId, int listType) throws OwnerException {
        return sysDocumentService.queryAppendixDocList(listType, companyId, null, year);
    }

    /**
     * 检查当前companyId 是否是groupId 的子公司
     *
     * @param companyId
     * @param groupId
     * @throws OwnerException
     */
    @Override
    public void checkChildCompany(Integer companyId, Integer groupId, String fullPath) throws OwnerException {
        if (null == companyId || groupId.equals(companyId)) {
            return;
        }
        if (companyDao.countChild(companyId, fullPath) > 0) {
            return;
        }
        logger.error(MessageFormat.format("当前集团公司【{0,number,#}】不存在id为【{1,number,#}】的子公司。", groupId, companyId));
        throw new OwnerException("获取数据失败，请稍后重试。");
    }

}
