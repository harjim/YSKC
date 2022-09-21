package com.yskc.rs.service.impl;

import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.RedisUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdEmployeeDao;
import com.yskc.rs.dao.RdEquipmentDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.company.CompanyRdSummaryDao;
import com.yskc.rs.dao.company.NameHistoryDao;
import com.yskc.rs.dao.highTech.HighTechDao;
import com.yskc.rs.dao.project.ProjectYearInfoDao;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.entity.company.NameHistoryEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.*;
import com.yskc.rs.models.hightech.HighTechIncomeModel;
import com.yskc.rs.models.login.UserSession;
import com.yskc.rs.service.CompanyService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private NameHistoryDao nameHistoryDao;

    /**
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean saveCompany(UserInfo userInfo, AppCompanyModel model, String token) throws Exception {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(userInfo.getCompanyId());
        entity.setCompanyName(model.getCompanyName());
        entity.setCompanyAddress(model.getCompanyAddress());
        String[] addressCode = model.getAddressCode();
        String addStr = "";
        for (int i = 0; i < addressCode.length; i++) {
            if (i == addressCode.length - 1) {
                addStr += addressCode[i];
            } else {
                addStr += addressCode[i] + ",";
            }
        }
        entity.setAddressCode(addStr);
        entity.setTaxpayerId(model.getTaxpayerId());
        entity.setCreditCode(model.getCreditCode());
        entity.setInvoiceTitle(model.getInvoiceTitle());
        String[] industryCodeArr = model.getIndustryCode();
        String icode = "";
        for (int i = 0; i < industryCodeArr.length; i++) {
            if (i == industryCodeArr.length - 1) {
                icode += industryCodeArr[i];
            } else {
                icode += industryCodeArr[i] + ",";
            }
        }
        entity.setIndustryCode(icode);
        entity.setMainIndustry(model.getMainIndustry());
        entity.setLinkMan(model.getLinkMan());
        entity.setLinkTel(model.getLinkTel());
        entity.setDomain(model.getDomain());
        entity.setEmail(model.getEmail());
        entity.setOwner(model.getOwner());
        entity.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
        entity.setMembers(0);
        entity.setDepts(0);
        entity.setRegisterTime(model.getRegisterTime());
        entity.setFirstDevFee(model.getFirstDevFee());
        entity.setAccountSystem(model.getAccountSystem());
        entity.setTaxAuthorities(model.getTaxAuthorities());
        entity.setRealTaxAuthorities(model.getRealTaxAuthorities());
        if (model.getHasDevAccount() != null) {
            entity.setHasDevAccount(model.getHasDevAccount());
        } else {
            entity.setHasDevAccount(false);
        }

        if (model.getHighTec() != null) {
            entity.setHighTec(model.getHighTec());
        } else {
            entity.setHighTec(false);
        }

        entity.setHighTecIndustry(model.getHighTecIndustry());
        entity.setBusinessLicense(model.getBusinessLicense());
        entity.setLogo(model.getLogo());
        ///添加字段start
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        entity.setLastUpdateTime(new Date());
        ///添加字段end
        entity.setSynopsis(model.getSynopsis());
        entity.setCapitalUnit(model.getCapitalUnit());
        entity.setFinished(true);

        if (entity.getId() != null) {
            companyDao.updateById(entity);
        } else {
            entity.setCreateTime(new Date());
            entity.setCreatorId(userInfo.getUserId());
            entity.setMsCreatorId(userInfo.getMsUserId());
            ///添加字段end
            companyDao.insert(entity);
        }
        String sessionKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, token);
        UserSession userSession = redisUtils.get(sessionKey, UserSession.class);
        if (null != userSession) {
            String memberKey = MessageFormat.format("CMember:{0}", entity.getId());
            CompanyMember companyMember = new CompanyMember();
            companyMember.setCompanyId(entity.getId());
            companyMember.setCompanyLogoPath(model.getLogo());
            companyMember.setCompanyName(model.getCompanyName());
            redisUtils.set(memberKey, companyMember);
            userSession.setCompanyLogoPath(model.getLogo());
            redisUtils.set(sessionKey, userSession, userInfo.getUserSource() == 1 ? Constant.MS_USER_SESSION_TIMEOUT : Constant.RS_USER_SESSION_TIMEOUT);
        }
        return true;
    }

    /*@Override
    public boolean updateCompanyName(UserInfo userInfo, CompanyNameModel model) throws Exception {
        if (model.getOldName().equals(model.getNewName())) {
            throw new OwnerException("新公司名与旧公司名相同!");
        }
        Integer companyId = userInfo.getCompanyId();
        Date now = new Date();
        //获取旧名称的结束日期
        NameHistoryEntity nameEntity = new NameHistoryEntity();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(model.getBeginTime());
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date endDate = date.getTime();

        NameHistoryEntity historyEntity = nameHistoryDao.getNameId(companyId);

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            nameEntity.setEndTime(endDate);
            if (null != historyEntity) {
                if (!model.getBeginTime().after(historyEntity.getBeginTime())) {
                    throw new OwnerException("当前时间已存在公司名历史记录,请核对日期之后再更新!");
                }
                nameEntity.update(userInfo.getUserId(), userInfo.getMsUserId(), now);
                nameEntity.setId(historyEntity.getId());
                nameHistoryDao.updateById(nameEntity);
            } else {
                nameEntity.create(userInfo.getUserId(), userInfo.getMsUserId(), now);
                nameEntity.setBeginTime(dft.parse("2000-01-01"));
                nameEntity.setCompanyId(companyId);
                nameEntity.setCompanyName(model.getOldName());
                nameHistoryDao.insert(nameEntity);
            }
            NameHistoryEntity insertNameEntity = new NameHistoryEntity();
            insertNameEntity.create(userInfo.getUserId(), userInfo.getMsUserId(), now);
            insertNameEntity.setCompanyId(companyId);
            insertNameEntity.setCompanyName(model.getNewName());
            insertNameEntity.setBeginTime(model.getBeginTime());
            nameHistoryDao.insert(insertNameEntity);

            CompanyEntity entity = new CompanyEntity();
            entity.setCompanyName(model.getNewName());
            entity.setId(userInfo.getCompanyId());
            entity.setLastUpdateTime(now);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            companyDao.updateById(entity);

            String memberKey = MessageFormat.format("CMember:{0}", userInfo.getCompanyId());
            redisUtils.del(memberKey);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (OwnerException oe) {
            TransactionUtils.rollback(transactionStatus);
            throw oe;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("updateCompanyName",e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }*/

    /**
     * @param id
     * @return
     */
    @Override
    public AppCompanyModel queryAll(Integer id) {
        CompanyEntity model = companyDao.selectById(id);
        AppCompanyModel entity = new AppCompanyModel();

        entity.setCompanyName(model.getCompanyName());
        entity.setId(model.getId());
        entity.setCompanyName(model.getCompanyName());
        entity.setCompanyAddress(model.getCompanyAddress());
        entity.setAddressCode(model.getAddressCode().split(","));
        entity.setTaxpayerId(model.getTaxpayerId());
        entity.setCreditCode(model.getCreditCode());
        entity.setInvoiceTitle(model.getInvoiceTitle());

        String industryCode = model.getIndustryCode();
        String[] industryCodeArr = null;
        if (!StringUtils.isEmpty(industryCode)) {
            industryCodeArr = industryCode.split(",");
        }
        entity.setIndustryCode(industryCodeArr);
        entity.setIndustryCodeArr(industryCode);
        entity.setMainIndustry(model.getMainIndustry());
        entity.setLinkMan(model.getLinkMan());
        entity.setLinkTel(model.getLinkTel());
        entity.setDomain(model.getDomain());
        entity.setEmail(model.getEmail());
        entity.setOwner(model.getOwner());
        if (model.getCapital() != null) {
            entity.setCapital(new BigDecimal(model.getCapital()).divide(new BigDecimal(10000)));
        }
        entity.setMembers(0);
        entity.setDepts(0);

        entity.setRegisterTime(model.getRegisterTime());

        entity.setFirstDevFee(model.getFirstDevFee());

        entity.setAccountSystem(model.getAccountSystem());
        entity.setTaxAuthorities(model.getTaxAuthorities());
        entity.setRealTaxAuthorities(model.getRealTaxAuthorities());
        if (model.getHasDevAccount() != null) {
            entity.setHasDevAccount(model.getHasDevAccount());
        } else {
            entity.setHasDevAccount(false);
        }

        if (model.getHighTec() != null) {
            entity.setHighTec(model.getHighTec());
        } else {
            entity.setHighTec(false);
        }

        entity.setHighTecIndustry(model.getHighTecIndustry());
        entity.setBusinessLicense(model.getBusinessLicense());
        entity.setLogo(model.getLogo());
        entity.setSynopsis(model.getSynopsis());
        entity.setCapitalUnit(model.getCapitalUnit() != null ? model.getCapitalUnit() : "rmb");
        return entity;
    }

    /**
     * @param companyName
     * @return
     */
    @Override
    public Integer registerCheckCompany(String companyName) {
        if (companyName.length() == 0) {
            return 1;
        }
        CompanyEntity company = companyDao.registerCheckCompany(companyName);
        if (company != null) {
            return 2;
        }
        return 0;
    }

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private RdEquipmentDao rdEquipmentDao;
    @Autowired
    private CompanyRdSummaryDao companyRdSummaryDao;
    @Autowired
    private HighTechDao highTechDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;

    @Override
    public CountDataModel getCountData(Integer companyId, Integer year) {
        //项目立项书/研发人员/设备 基建数
        CountProjectModel projectModel = getProjectData(companyId, year);
        List< ProjectEntity > projects = projectDao.getProjects(companyId, year);
        if (CollectionUtils.isEmpty(projects)) {
            return new CountDataModel();
        }
        List< Integer > projectIds = projects.stream().map(e -> e.getId()).collect(Collectors.toList());
        //获取所有项目预算及按年归集
        List< ProjectDataModel > projectDataModels = getProjectBudgetData(companyId, year, projectIds);
        //高品收入占比
        List< HighTechIncomeModel > highModels = highTechDao.countData(companyId, year);
        if (!CollectionUtils.isEmpty(highModels)) {
            BigDecimal total = BigDecimal.ZERO;
            for (HighTechIncomeModel model : highModels) {
                total = total.add(model.getIncome());
            }
            for (HighTechIncomeModel model : highModels) {
                model.setTotalIncome(total);
            }
        }
        //年度研发费用
        List< Map< String, Object > > costList = getYearCosts(projectIds, year, companyId);
        //研发费占比
        List< CountRdFundModel > countRdFundModels = getRdFunds(projectIds, year, companyId);
        //归集费占总预算比
        List< CountBudgetModel > costModels = getCostPercent(year, projectIds, companyId);
        return CountDataModel.build(projectModel, projectDataModels, highModels, costList, countRdFundModels, costModels);
    }

    @Override
    public Boolean getCompanyFinished(Integer companyId) {
        return companyDao.getCompanyFinished(companyId);
    }

    @Override
    public List< CompanyNameHistoryModel > getCompanyHistoryName(Integer companyId) {
        return nameHistoryDao.getNameHistory(companyId);
    }

    @Override
    public CompanyNameHistoryModel delCompanyHistoryName(Integer nameId, UserInfo userInfo) throws OwnerException {
        // 判断删除后是否会出现相邻时间相同公司名
        if (nameId == null) {
            throw new OwnerException("请选择需要删除的记录!");
        }
        Integer companyId = userInfo.getCompanyId();
        // 判断当前名称是否为公司的最新名称，是就更新主表company
        // 删除当前记录
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        CompanyNameHistoryModel lastNameHistoryModel = new CompanyNameHistoryModel();
        try {
            NameHistoryEntity currNameModel = nameHistoryDao.selectById(nameId);
            nameHistoryDao.deleteById(nameId);
            NameHistoryEntity lastNameModel = nameHistoryDao.selectNameIsFinally(companyId);
            Date currStartDate = currNameModel.getStartDate();
            if (lastNameModel != null && DateUtil.compareDate(lastNameModel.getStartDate(), currStartDate) <= 0) {
                // 当前删除记录为最后一条且删除后还存在记录
                // 更新表company中companyName
                String lastName = lastNameModel.getCompanyName();
                CompanyEntity companyEntity = new CompanyEntity();
                companyEntity.setId(companyId);
                companyEntity.setCompanyName(lastName);
                companyDao.updateById(companyEntity);
                lastNameHistoryModel.setCompanyName(lastName);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("updateCompanyName", e);
            throw new OwnerException(ErrorEnum.FAIL);
        } finally {
            // 更新当前公司信息
            String memberKey = MessageFormat.format("CMember:{0}", userInfo.getCompanyId());
            redisUtils.del(memberKey);
        }
        return lastNameHistoryModel;
    }

    @Override
    public CompanyNameHistoryModel saveCompanyName(CompanyNameHistoryModel nameHistoryModel, UserInfo userInfo) throws OwnerException {
        String companyName = nameHistoryModel.getCompanyName();
        Date startDate = nameHistoryModel.getStartDate();
        Integer companyId = userInfo.getCompanyId();
        Integer insertNameId = nameHistoryModel.getId();
        if (!StringUtils.hasText(companyName)) {
            throw new OwnerException("公司名称不可为空");
        }
        if (startDate == null) {
            throw new OwnerException("生效时间不可为空");
        }
        Integer hasHistory = nameHistoryDao.selectCountByIdAndSDate(companyName, startDate, insertNameId, companyId);
        if (hasHistory > 0) {
            throw new OwnerException("当前生效时间或公司名已存在，请重新输入!");
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        CompanyNameHistoryModel lastNameHistoryModel = new CompanyNameHistoryModel();
        try {
            Date now = new Date();
            // 判断当前名称是否为最后一条
        NameHistoryEntity nameHistoryEntity = new NameHistoryEntity();
            NameHistoryEntity lastHistory = nameHistoryDao.selectNameIsFinally(companyId);
            if (!(lastHistory != null && DateUtil.compareDate(lastHistory.getStartDate(), startDate) > 0)) {
                // 修改主表company的companyName
                CompanyEntity companyEntity = new CompanyEntity();
                companyEntity.setId(companyId);
                companyEntity.setCompanyName(companyName);
                companyEntity.setLastUpdateTime(now);
                companyEntity.setLastUpdatorId(userInfo.getUserId());
                companyEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                companyDao.updateById(companyEntity);
                lastNameHistoryModel.setCompanyName(companyName);
            }
            // 插入记录
            nameHistoryEntity.setCompanyName(nameHistoryModel.getCompanyName());
            nameHistoryEntity.setStartDate(nameHistoryModel.getStartDate());
            nameHistoryEntity.setRemark(nameHistoryModel.getRemark());
            nameHistoryEntity.setLastUpdatorId(userInfo.getUserId());
            nameHistoryEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            nameHistoryEntity.setLastUpdateTime(now);
            if (insertNameId != null) {
                // 数据库中存有对应id
                nameHistoryEntity.setId(insertNameId);
                nameHistoryDao.updateById(nameHistoryEntity);
            } else {
                nameHistoryEntity.setCompanyId(companyId);
                nameHistoryEntity.setCreateTime(now);
                nameHistoryEntity.setCreatorId(userInfo.getUserId());
                nameHistoryEntity.setMsCreatorId(userInfo.getMsUserId());
                nameHistoryDao.insert(nameHistoryEntity);
            }
            TransactionUtils.commit(transactionStatus);
            lastNameHistoryModel.setId(nameHistoryEntity.getId());
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("saveCompanyName", e);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        // 更新当前公司信息
        String memberKey = MessageFormat.format("CMember:{0}", userInfo.getCompanyId());
        redisUtils.del(memberKey);
        // 保存
        return lastNameHistoryModel;
    }

    private List< CountBudgetModel > getCostPercent(Integer year, List< Integer > projectIds, Integer companyId) {
        List< CountBudgetModel > list = new ArrayList<>();
        BigDecimal totalBudget = projectYearInfoDao.getBudgetByYear(projectIds, companyId, year);
        if (null != totalBudget && totalBudget.compareTo(BigDecimal.ZERO) != 0) {
            totalBudget = totalBudget.multiply(Constant.TEN_THOUSAND);
        }
        Date beginOfYear = DateUtil.getYearFirstDay(year);
        Date endOfYear = DateUtil.getYearLastDay(year);
        List< CountBudgetModel > models = summaryDao.getFundByMonth(projectIds, beginOfYear, endOfYear);
        Map< String, BigDecimal > fundMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(models)) {
            models.forEach(item -> {
                String monthKey = DateUtil.format(item.getMonth(), "yyyy-MM");
                fundMap.put(monthKey, item.getRdFunds());
            });

            BigDecimal totalRdFunds = BigDecimal.ZERO;//累计归集费用
            BigDecimal allocations = BigDecimal.ZERO;//可分配额度
            for (int i = 1; i <= 12; i++) {
                String month = year + "-" + (i < 10 ? "0" + i : i);
                BigDecimal percent;//占比
                BigDecimal rdFunds = BigDecimal.ZERO;
                if (fundMap.containsKey(month)) {
                    totalRdFunds = totalRdFunds.add(fundMap.get(month));
                    rdFunds = fundMap.get(month);
                }
                if (null == totalBudget || totalBudget.compareTo(BigDecimal.ZERO) == 0) {
                    percent = BigDecimal.ZERO;
                } else {
                    if (!fundMap.containsKey(month)) {
                        percent = BigDecimal.ZERO;
                    } else {
                        percent = fundMap.get(month).divide(totalBudget, 2, BigDecimal.ROUND_HALF_UP);
                    }
                    allocations = (totalRdFunds).divide(totalBudget, 2, BigDecimal.ROUND_HALF_UP);
                }
                CountBudgetModel model = new CountBudgetModel(month, rdFunds, totalRdFunds, allocations, totalBudget, percent);
                list.add(model);
            }
        }
        return list;
    }

    private List< Map< String, Object > > getYearCosts(List< Integer > projectIds, Integer year, Integer companyId) {
        List< Map< String, Object > > list = new ArrayList<>();
        Date beginOfYear = DateUtil.getYearFirstDay(year);
        Date endOfYear = DateUtil.getYearLastDay(year);
        List< CountRdFundModel > models = summaryDao.getDataByYear(projectIds, beginOfYear, endOfYear);
        if (!CollectionUtils.isEmpty(models)) {
            Map< String, List< CountRdFundModel > > dataMap = new HashMap<>();
            for (CountRdFundModel model : models) {
                String monthKey = DateUtil.format(model.getMonth(), "yyyy-MM");
                if (!dataMap.containsKey(monthKey)) {
                    List< CountRdFundModel > modelList = new ArrayList<>();
                    dataMap.put(monthKey, modelList);
                }
                dataMap.get(monthKey).add(model);
            }
            for (int i = 1; i <= 12; i++) {
                Map< String, Object > map = new HashMap<>();
                String month = year + "-" + (i < 10 ? "0" + i : i);
                map.put("month", month);
                if (dataMap.containsKey(month)) {
                    List< CountRdFundModel > fundModels = dataMap.get(month);
                    for (CountRdFundModel rdFundModel : fundModels) {
                        Integer rdType = rdFundModel.getRdType() / 100;
                        if (rdType == 600 || rdType == 601 || rdType == 602 || rdType == 603) {
                            rdType = 699;
                        }
                        if (!map.containsKey(rdType.toString())) {
                            map.put(rdType.toString(), rdFundModel.getRdFund());
                        } else {
                            BigDecimal fund = (BigDecimal) map.get(rdType.toString());
                            map.put(rdType.toString(), fund.add(rdFundModel.getRdFund()));
                        }
                    }
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 研发费用占比
     *
     * @param projectIds
     * @param year
     * @return
     */
    private List< CountRdFundModel > getRdFunds(List< Integer > projectIds, Integer year, Integer companyId) {
        List< CountRdFundModel > resultModels = new ArrayList<>();
        List< CountRdFundModel > models = summaryDao.countFunds(projectIds, year, companyId);
        Map< Integer, List< CountRdFundModel > > dataMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(models)) {
            BigDecimal total = BigDecimal.ZERO;
            for (CountRdFundModel model : models) {
                Integer rdType = model.getRdType() / 100;
                if (rdType == 600 || rdType == 601 || rdType == 602 || rdType == 603) {
                    rdType = 699;
                }
                if (!dataMap.containsKey(rdType)) {
                    List< CountRdFundModel > modelList = new ArrayList<>();
                    dataMap.put(rdType, modelList);
                }
                total = total.add(model.getRdFund());
                dataMap.get(rdType).add(model);

            }
            for (Integer type : dataMap.keySet()) {
                List< CountRdFundModel > rdFunds = dataMap.get(type);
                BigDecimal totalFund = BigDecimal.ZERO;
                for (CountRdFundModel model : rdFunds) {
                    totalFund = totalFund.add(model.getRdFund());
                }
                CountRdFundModel model = new CountRdFundModel(type, totalFund, total);
                resultModels.add(model);
            }
        }
        return resultModels;
    }

    /**
     * 项目归集费用预算占比
     *
     * @param companyId
     * @param year
     * @param projectIds
     * @return
     */
    private List< ProjectDataModel > getProjectBudgetData(Integer companyId, Integer year, List< Integer > projectIds) {
        List< ProjectDataModel > dataModels = projectDao.getBudgetAndCost(projectIds, year);
        //List<ProjectDataModel> parents = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataModels)) {
            List< Integer > childProjectIds = dataModels.stream().map(e -> e.getId()).collect(Collectors.toList());
            BigDecimal totalBudget = projectYearInfoDao.getBudgetByYear(childProjectIds, companyId, year);
            Boolean divideSign = false;
            if (totalBudget != null && totalBudget.compareTo(BigDecimal.ZERO) != 0) {
                totalBudget = totalBudget.multiply(Constant.TEN_THOUSAND);
                divideSign = true;
            }
            //  Map<Integer, List<ProjectDataModel>> childMap = new LinkedHashMap<>();
            for (ProjectDataModel dataModel : dataModels) {
                dataModel.setTotalBudget(totalBudget);
                if (divideSign) {
                    dataModel.setPercent(dataModel.getRdFunds().divide(totalBudget, 2, BigDecimal.ROUND_HALF_UP));
                }
//                if (0 == dataModel.getParentId()) {
//                    if (!dataModel.getHasChild()) {
//                        //占总预算比 项目当前年归集费用/所有项目当前年预算
//                        if(divideSign){
//                            dataModel.setPercent(dataModel.getRdFunds().divide(totalBudget,2,BigDecimal.ROUND_HALF_UP));
//                        }
////                        if (dataModel.getBudget().compareTo(BigDecimal.ZERO) != 0) {
////                            dataModel.setPercent(dataModel.getRdFunds().divide(dataModel.getBudget(), 2, BigDecimal.ROUND_HALF_UP));
////                        }
//                    }
//                    parents.add(dataModel);
//                } else {
//                    if(divideSign){
//                        dataModel.setPercent(dataModel.getRdFunds().divide(totalBudget,2,BigDecimal.ROUND_HALF_UP));
//                    }
////                    if (dataModel.getBudget().compareTo(BigDecimal.ZERO) != 0) {
////                        dataModel.setPercent(dataModel.getRdFunds().divide(dataModel.getBudget(), 2, BigDecimal.ROUND_HALF_UP));
////                    }
//                    if (!childMap.containsKey(dataModel.getParentId())) {
//                        List<ProjectDataModel> models = new ArrayList<>();
//                        childMap.put(dataModel.getParentId(), models);
//                    }
//                    childMap.get(dataModel.getParentId()).add(dataModel);
//                }
            }
//            for (ProjectDataModel parent : parents) {
//                parent.setTotalBudget(totalBudget);
//                if (parent.getHasChild()) {
//                    if (childMap.containsKey(parent.getId())) {
//                        List<ProjectDataModel> childs = childMap.get(parent.getId());
//                        parent.setChilds(childs);
//                        //BigDecimal totalBudget = BigDecimal.ZERO;
//                        BigDecimal totalFunds = BigDecimal.ZERO;
//                        for (ProjectDataModel child : childs) {
//                            //totalBudget = totalBudget.add(child.getBudget());
//                            totalFunds = totalFunds.add(child.getRdFunds());
//                        }
//                        if(divideSign){
//                            parent.setPercent(totalFunds.divide(totalBudget,2,BigDecimal.ROUND_HALF_UP));
//                        }
////                        if (totalBudget.compareTo(BigDecimal.ZERO) != 0) {
////                            parent.setPercent(totalFunds.divide(totalBudget, 2, BigDecimal.ROUND_HALF_UP));
////                        }
//                    }
//                }
//            }
        }
        return dataModels;
    }

    private CountProjectModel getProjectData(Integer companyId, Integer year) {
        //立项数为大项目+原子项目
        Integer projectNum = projectDao.getProjectNum(companyId, year, 1);
        Integer preProjectNum = projectDao.getProjectNum(companyId, year - 1, 1);
        BigDecimal projectPercent = null;
        Integer projectTrend = null;
        if (preProjectNum != null && preProjectNum != 0) {
            projectPercent = new BigDecimal((projectNum - preProjectNum) * 100).divide(new BigDecimal(preProjectNum), 2, BigDecimal.ROUND_HALF_UP);
            projectTrend = projectPercent.compareTo(BigDecimal.ZERO);
        }
        //子项目
        Integer childProjectNum = projectDao.getProjectNum(companyId, year, 2);
        //研发人员
        BigDecimal rdEmployeePercent = null;
        Integer rdEmployeeTrend = null;
        Integer rdEmployeeNum = rdEmployeeDao.countNum(companyId, year);
        Integer preRdEmployeeNum = rdEmployeeDao.countNum(companyId, year - 1);
        if (preRdEmployeeNum != null && preRdEmployeeNum != 0) {
            rdEmployeePercent = new BigDecimal((rdEmployeeNum - preRdEmployeeNum) * 100).divide(new BigDecimal(preRdEmployeeNum), 2, BigDecimal.ROUND_HALF_UP);
            rdEmployeeTrend = rdEmployeePercent.compareTo(BigDecimal.ZERO);
        }
        //研发设备
        BigDecimal rdEquipmentPercent = null;
        Integer rdEquipmentTrend = null;
        Integer rdEquipmentNum = rdEquipmentDao.countNum(companyId, year);
        Integer preRdEquipmentNum = rdEquipmentDao.countNum(companyId, year - 1);
        if (preRdEquipmentNum != null && preRdEquipmentNum != 0) {
            rdEquipmentPercent = new BigDecimal((rdEquipmentNum - preRdEquipmentNum) * 100).divide(new BigDecimal(preRdEquipmentNum), 2, BigDecimal.ROUND_HALF_UP);
            rdEquipmentTrend = rdEquipmentPercent.compareTo(BigDecimal.ZERO);
        }
        //机构建设数
        Integer summary = companyRdSummaryDao.getInfo(companyId, year);
        CountProjectModel model = new CountProjectModel(projectNum, projectPercent != null ? projectPercent + "%" : "-", projectTrend, childProjectNum,
                rdEmployeeNum, rdEmployeePercent != null ? rdEmployeePercent + "%" : "-", rdEmployeeTrend, rdEquipmentNum, rdEquipmentPercent != null ? rdEquipmentPercent + "%" : "-", rdEquipmentTrend, summary);
        return model;
    }

}
