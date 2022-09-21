package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.ms.Customer;
import com.yskc.ms.entity.rs.*;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.entity.rs.models.role.RsRoleModel;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.CompanyInfoModel;
import com.yskc.ms.models.company.NameHistoryModel;
import com.yskc.ms.models.customer.*;
import com.yskc.ms.models.project.ProjectInfoModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import com.yskc.ms.service.ms.CustomerService;
import com.yskc.ms.utils.TransactionUtils;
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

/**
 * 客户业务实现层
 *
 * @author zdf
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private RsUserDao rdUserDao;

    @Autowired
    private RsDeptDao rsDeptDao;

    @Autowired
    private NameHistoryDao nameHistoryDao;

    @Autowired
    private ProjectMemberDao projectMemberDao;

    @Autowired
    private UserDeptDao userDeptDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RsUserRoleDao rsUserRoleDao;
    @Autowired
    private CompanyGroupDao companyGroupDao;
    @Autowired
    private RsRoleDao rsRoledao;
    @Autowired
    private CustomerTraceDao customerTraceDao;

    @Override
    public List< MiniCustomerModel > customerForSelect(String customerName, String addressStr, Integer deptId) {
        return customerDao.customerForSelect(customerName, addressStr, deptId);
    }

    @Override
    public PageModel< List< CustomerModel > > getList(UserInfo userInfo, QueryCustomerModel query, DataPermModel dataPerm, Boolean unsigned) {
        Boolean platform = userInfo.getPlatform() == 0;
        Pagination page = query.getPagination();
        if (platform) {
            if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
                List< String > desc = new ArrayList<>();
                desc.add("c.lastUpdateTime");
                page.setDescs(desc);
            }
        }
        List< CustomerModel > customerList = null;
        if (unsigned) {
            customerList = customerDao.getUnsignedList(page, query);
        } else {
            customerList = customerDao.getList(page, query, dataPerm);
        }
        List< Integer > companies = new ArrayList<>();
        Map< Integer, CustomerModel > map = new HashMap<>();
        Map< Integer, CustomerModel > customerModelMap = new HashMap<>();
        customerList.forEach(model -> {
            if (model.getIndustryCode() != null) {
                model.setIndustryCodeArr(model.getIndustryCode().split(","));
            }
            if (model.getAddressCode() != null) {
                model.setAddressCodeArr(model.getAddressCode().split(","));
            }
            if (model.getCapital() != null) {
                model.setCapital(model.getCapital().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            }
            if (model.getCompanyId() != 0) {
                Integer companyType = model.getCompanyType();
                if (null != companyType && (companyType == 1 || companyType == 3)) {
                    companies.add(model.getCompanyId());
                }
                map.put(model.getCompanyId(), model);
            }
            if (!platform && unsigned) {
                customerModelMap.put(model.getCustomerId(), model);
            }
        });
        if (!CollectionUtils.isEmpty(customerModelMap)) {
            Set< Integer > customerIds = customerModelMap.keySet();
            Map< Integer, CustomerTraceModel > mobileTraceMap = customerTraceDao.getMobileTraceMap(customerIds);
            for (Integer customerId : customerIds) {
                if (mobileTraceMap.containsKey(customerId) && mobileTraceMap.get(customerId) != null) {
                    CustomerModel customerModel = customerModelMap.get(customerId);
                    CustomerTraceModel traceModel = mobileTraceMap.get(customerId);
                    customerModel.setLastFollowId(traceModel.getCreatorId());
                    customerModel.setLastFollowName(traceModel.getEffectUser());
                    customerModel.setLastFollowTime(traceModel.getEffectDate());
                }
            }
        }
        if (!CollectionUtils.isEmpty(companies)) {
            List< CompanyGroupEntity > entities = companyGroupDao.getByCompanyId(companies);
            if (!CollectionUtils.isEmpty(entities)) {
                entities.forEach(item -> {
                    if (map.containsKey(item.getCompanyId())) {
                        map.get(item.getCompanyId()).setGroupFullPath(item.getFullPath());
                    }
                });
            }
        }
        return PageUtils.build(page, customerList);
    }

    @Override
    public PageModel< List< CustomerModel > > getMobileList(UserInfo userInfo, QueryCustomerModel query, DataPermModel dataPerm, Boolean unsigned) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List< String > desc = new ArrayList<>();
            desc.add("c.lastUpdateTime");
            page.setDescs(desc);
        }
        List< CustomerModel > customerList = null;
        if (unsigned) {
            customerList = customerDao.getMobileUnsignedList(page, query);
        } else {
            customerList = customerDao.getMobileList(page, query, dataPerm);
        }
        List< Integer > companies = new ArrayList<>();
        Map< Integer, CustomerModel > map = new HashMap<>();
        Map< Integer, CustomerModel > customerModelMap = new HashMap<>();
        customerList.forEach(model -> {
            if (model.getIndustryCode() != null) {
                model.setIndustryCodeArr(model.getIndustryCode().split(","));
            }
            if (model.getAddressCode() != null) {
                model.setAddressCodeArr(model.getAddressCode().split(","));
            }
            if (model.getCapital() != null) {
                model.setCapital(model.getCapital().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            }
            if (model.getCompanyId() != 0) {
                Integer companyType = model.getCompanyType();
                if (null != companyType && (companyType == 1 || companyType == 3)) {
                    companies.add(model.getCompanyId());
                }
                map.put(model.getCompanyId(), model);
            }
            if (unsigned) {
                customerModelMap.put(model.getCustomerId(), model);
            }
        });
        if (!CollectionUtils.isEmpty(customerModelMap)) {
            Set< Integer > customerIds = customerModelMap.keySet();
            Map< Integer, CustomerTraceModel > mobileTraceMap = customerTraceDao.getMobileTraceMap(customerIds);
            for (Integer customerId : customerIds) {
                if (mobileTraceMap.containsKey(customerId) && mobileTraceMap.get(customerId) != null) {
                    CustomerModel customerModel = customerModelMap.get(customerId);
                    CustomerTraceModel traceModel = mobileTraceMap.get(customerId);
                    customerModel.setLastFollowId(traceModel.getCreatorId());
                    customerModel.setLastFollowName(traceModel.getEffectUser());
                    customerModel.setLastFollowTime(traceModel.getEffectDate());
                }
            }
        }
        if (!CollectionUtils.isEmpty(companies)) {
            List< CompanyGroupEntity > entities = companyGroupDao.getByCompanyId(companies);
            if (!CollectionUtils.isEmpty(entities)) {
                entities.forEach(item -> {
                    if (map.containsKey(item.getCompanyId())) {
                        map.get(item.getCompanyId()).setGroupFullPath(item.getFullPath());
                    }
                });
            }
        }
        return PageUtils.build(page, customerList);

    }

    @Override
    public List< MiniModel > getCustomerSelect(String customerName) {
        if (StringUtils.isEmpty(customerName)) {
            return new ArrayList<>();
        }
        return customerDao.getCustomerSelect(customerName);
    }

    @Override
    public List<CustomerOwnerModel> getCustomerList(String companyName) {
        if (StringUtils.isEmpty(companyName)) {
            return new ArrayList<>();
        }
        return customerDao.getOwnerDept(companyName);
    }

    @Override
    public boolean openingAccount(CompanyModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        CompanyEntity company = new CompanyEntity();
        company.setCompanyName(model.getCompanyName());
        company.setCompanyAddress(model.getCompanyAddress());
        String[] addressCode = model.getAddressCodeArr();
        String addStr = "";
        if (!StringUtils.isEmpty(addressCode)) {
            for (int i = 0; i < addressCode.length; i++) {
                if (i == addressCode.length - 1) {
                    addStr += addressCode[i];
                } else {
                    addStr += addressCode[i] + ",";
                }
            }
        }
        company.setAddressCode(addStr);
        company.setTaxpayerId(model.getTaxpayerId());
        company.setCreditCode(model.getCreditCode());
        company.setInvoiceTitle(model.getInvoiceTitle());
        String[] industryCodeArr = model.getIndustryCodeArr();
        String icode = "";
        if (!StringUtils.isEmpty(industryCodeArr)) {
            for (int i = 0; i < industryCodeArr.length; i++) {
                if (i == industryCodeArr.length - 1) {
                    icode += industryCodeArr[i];
                } else {
                    icode += industryCodeArr[i] + ",";
                }
            }
        }

        company.setIndustryCode(icode);
        company.setMainIndustry(model.getMainIndustry());
        company.setLinkMan(model.getLinkMan());
        company.setLinkTel(model.getLinkTel());
        company.setDomain(model.getDomain());
        company.setEmail(model.getEmail());
        company.setOwner(model.getOwner());
        if (model.getCapital() != null) {
            company.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
        }
        company.setMembers(0);
        company.setDepts(0);
        company.setRegisterTime(model.getRegisterTime());
        company.setFirstDevFee(model.getFirstDevFee());
        company.setAccountSystem(model.getAccountSystem());
        company.setTaxAuthorities(model.getTaxAuthorities());
        company.setRealTaxAuthorities(model.getRealTaxAuthorities());
        if (model.getHasDevAccount() != null) {
            company.setHasDevAccount(model.getHasDevAccount());
        } else {
            company.setHasDevAccount(false);
        }

        if (model.getHighTec() != null) {
            company.setHighTec(model.getHighTec());
        } else {
            company.setHighTec(false);
        }
        company.setCreateTime(date);
        company.setMsCreatorId(userInfo.getId());
        company.setCreatorId(-1);
        company.setMsLastUpdatorId(userInfo.getId());
        company.setLastUpdatorId(-1);
        company.setHighTecIndustry(model.getHighTecIndustry());
        company.setLastUpdateTime(date);

        company.setFrom(1);
        company.setStatus(1);
        company.setRemark("开通账号");
        company.setLogo("");
        company.setBusinessLicense("");
        company.setCapitalUnit(model.getCapitalUnit() != null ? model.getCapitalUnit() : "rmb");
        String userName = model.getUserName();
        String password = model.getPassword();
        User user = rdUserDao.getUserInfo(model.getUserName());
        if (user != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        } else {
            user = new User();
        }
        user.setUserName(userName);
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", userName, password)));
        user.setRealName("");
        user.setGender(0);
        user.setTel("");
        user.setStatus(0);
        user.setCreatorId(-1);
        user.setCreateTime(date);
        user.setLastUpdatorId(-1);
        user.setLastUpdateTime(date);
        user.setType(0);

        Dept dept = new Dept();
        dept.setDeptName(company.getCompanyName());
        dept.setParentId(-1);
        dept.setLevel(0);
        dept.setIdentity("");
        dept.setRemark("");
        dept.setCreatorId(-1);
        dept.setCreateTime(date);
        try {
            companyDao.insert(company);
            dept.setCompanyId(company.getId());
            rsDeptDao.insert(dept);
            dept.setFullPath(dept.getId() + Constant.PATH_SEPARATOR);
            rsDeptDao.updateById(dept);
            user.setCompanyId(company.getId());
            rdUserDao.insert(user);
            Customer customer = new Customer();
            customer.setId(model.getCustomerId());
            customer.setCompanyId(company.getId());
            customerDao.updateById(customer);
            return true;
        } catch (Exception ex) {
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }

    }

    @Override
    public boolean followUp(QueryFollowModel query, UserInfo userInfo) throws OwnerException {
        if (null != query) {
            query.setCreateTime(new Date());
            query.setCreatorId(userInfo.getId());
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
                customerDao.updateStatus(query.getCustomerId(), query.getStatus(), userInfo.getId());
                customerTraceDao.insertInfo(query);
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            } catch (Exception e) {
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                throw new OwnerException("跟进失败！");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean followUpList(QueryFollowListModel query, UserInfo userInfo) throws OwnerException {
        if (null != query) {
            Integer deptId = userDeptDao.getUserDeptId(userInfo.getId());
            query.setCreateTime(new Date());
            query.setCreatorId(userInfo.getId());
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
                customerDao.updateStatusList(query.getCustomerId(), query.getStatus(), userInfo.getId(), deptId);
                customerTraceDao.insertInfoList(query.getCustomerId(), query.getStatus(), userInfo.getId(), query.getInfo());
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            } catch (Exception e) {
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                throw new OwnerException("跟进失败！");
            }
            return true;
        }
        return false;
    }

    @Override
    public PageModel< List< CustomerTraceModel > > getTraceList(QueryTraceModel query, UserInfo userInfo) throws OwnerException {
        if (null != query.getCustomerId() && query.getCustomerId() != 0) {
            Pagination page = query.getPagination();
            if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
                List< String > desc = new ArrayList<>();
                desc.add("ct.createTime");
                page.setDescs(desc);
            }
            List< CustomerTraceModel > traceList = customerTraceDao.getTraceList(query.getCustomerId(), page);
            for (CustomerTraceModel model : traceList) {
                if (model.getCreatorId() == (-2)) {
                    model.setEffectUser("System");
                }
            }
            page.setTotal(traceList.size());
            return PageUtils.build(page, traceList);
        }
        return new PageModel<>();
    }

    @Override
    public PageModel< List< CustomerTraceModel > > getMobileTraceList(QueryTraceModel query, UserInfo userInfo) throws OwnerException {
        if (null != query.getCustomerId() && query.getCustomerId() != 0) {
            Pagination page = query.getPagination();
            if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
                List< String > desc = new ArrayList<>();
                desc.add("ct.createTime");
                page.setDescs(desc);
            }
            List< CustomerTraceModel > traceList = customerTraceDao.getMobileTraceList(query.getCustomerId(), page);
            for (CustomerTraceModel model : traceList) {
                if (model.getCreatorId() == (-2)) {
                    model.setEffectUser("System");
                }
            }
            page.setTotal(traceList.size());
            return PageUtils.build(page, traceList);
        }
        return new PageModel<>();
    }

    @Override
    public boolean changeName(Integer id, String companyName, UserInfo userInfo) throws OwnerException {
        if (null != id && id != 0 && null != companyName) {
            companyName = companyName.replace((char) 12288, ' ').replaceAll(" ", "");
            companyName = companyName.replace("（", "(").replace("）", ")");
            Customer customer = customerDao.selectById(id);
            CompanyInfoModel info = companyDao.getInfo(customer.getCompanyId());
            List< CustomerModel > model = customerDao.checkName(companyName);
            if (model != null && model.size() > 0) {
                throw new OwnerException("公司名称重名！");
            }
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
                QueryFollowModel query = new QueryFollowModel();
                query.setInfo(customer.getCompanyName() + "变更名称为：" + companyName);
                query.setStatus(4);
                query.setCustomerId(id);
                query.setCreateTime(new Date());
                query.setCreatorId(userInfo.getId());
                customerDao.updateCompanyName(id, companyName, userInfo.getId());
                customerTraceDao.insertInfo(query);
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            } catch (Exception e) {
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                throw new OwnerException("变更名称失败！");
            }
            if (customer.getCompanyId() != null && customer.getCompanyId() != 0 && info != null) {
                companyDao.updateCompanyName(customer.getCompanyId(), userInfo.getId(), companyName);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean setOwnerUser(UserInfo userInfo, CustomerOwnerUserModel customerOwnerUserModel) throws OwnerException {
        Integer deptId = userDeptDao.getUserDeptId(customerOwnerUserModel.getUserId());
        Integer userId = userInfo.getId();
        String owerName;
        Date date = new Date();
        if (null == customerOwnerUserModel.getUserId() || customerOwnerUserModel.getUserId() == 0) {
            List< CompanyModel > companyModels = customerDao.checkCustomer(customerOwnerUserModel.getCustomerIds(), 30);
            if (!CollectionUtils.isEmpty(companyModels)) {
                throw new OwnerException("已签公司需指定业务员！");
            }
            owerName = "空";
            date = null;
            deptId = null;
            customerOwnerUserModel.setUserId(null);
        } else {
            owerName = userDao.getUser(customerOwnerUserModel.getUserId()).getRealName();
        }
        List< CustomerOwerModel > owerList = customerDao.getOwerList(customerOwnerUserModel.getCustomerIds());
        for (CustomerOwerModel model : owerList) {
            if (model.getOwerName() != null) {
                model.setInfo("业务员：" + model.getOwerName() + " 变更为 " + owerName);
            } else {
                model.setInfo("业务员指定为：" + owerName);
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            customerDao.updateOwnUser(date, customerOwnerUserModel, userId, deptId);
            customerTraceDao.insertOwerList(2, owerList, userInfo.getId());
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("指定业务员失败！");
        }
    }

    @Override
    public boolean addCustomer(CustomerModel model, Integer userId, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        Customer entity = new Customer();
        if (model.getStatus() == 0) {
            throw new OwnerException("不可添加未签客户！");
        }
        BeanUtil.copyProperties(model, entity);

        entity.setCreatorId(userId);
        entity.setLastUpdatorId(userId);
        entity.setCreatorTime(date);
        entity.setLastUpdateTime(date);
        entity.setDeptId(model.getDeptId() != null ? model.getDeptId() : 0);
        entity.setTaxpayerId(model.getTaxpayerId() != null ? model.getTaxpayerId() : "");
        entity.setCreditCode(model.getCreditCode() != null ? model.getCreditCode() : "");
        entity.setEmail(model.getEmail() != null ? model.getEmail() : "");
        entity.setOwner(model.getOwner() != null ? model.getOwner() : "");
        entity.setStatus(model.getStatus());

        if (userInfo.getPlatform() == 1) {
            entity.setOwerId(userId);
            entity.setDeptId(userDeptDao.getUserDeptId(userId));
        }
        if (model.getCompanyLevel() != null) {
            entity.setCompanyLevel(model.getCompanyLevel());
        }
        String addStr = "";
        String[] addressCode = model.getAddressCodeArr();
        if (addressCode != null) {
            addStr = String.join(",", addressCode);
        }
        String[] industryCodeArr = model.getIndustryCodeArr();
        if (entity.getAddressCode() == null) {
            entity.setAddressCode(addStr);
        }
        String icode = "";
        if (industryCodeArr != null) {
            icode = String.join(",", industryCodeArr);
        }
        entity.setIndustryCode(icode);
        entity.setFrom(2);
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
        if (model.getCapital() != null) {
            entity.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
        }
        entity.setCapitalUnit(model.getCapitalUnit() != null ? model.getCapitalUnit() : "rmb");
        if (entity.getStatus() == 10 || entity.getStatus() == 20) {
            entity.setEffectDate(date);
        }
        QueryFollowModel query = new QueryFollowModel();
        query.setCreatorId(userId);
        query.setCreateTime(date);
        query.setStatus(1);
        query.setInfo("添加客户");
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            Integer insert = customerDao.insert(entity);
            Integer customerId = customerDao.getIdByCompanyName(entity.getCompanyName());
            query.setCustomerId(customerId);
            Boolean insertInfo = customerTraceDao.insertInfo(query);
            if (insert > 0 && insertInfo) {
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
                return true;
            } else {
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                return false;
            }
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("添加失败！");
        }
    }

    @Override
    public boolean editCustomer(CustomerModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        Customer entity = new Customer();
        BeanUtil.copyProperties(model, entity);
        entity.setLastUpdatorId(userInfo.getId());
        entity.setLastUpdateTime(date);
        entity.setTaxpayerId(model.getTaxpayerId() != null ? model.getTaxpayerId() : "");
        entity.setCreditCode(model.getCreditCode() != null ? model.getCreditCode() : "");
        entity.setEmail(model.getEmail() != null ? model.getEmail() : "");
        entity.setOwner(model.getOwner() != null ? model.getOwner() : "");
        String addStr = "";
        String[] addressCode = model.getAddressCodeArr();
        if (addressCode != null) {
            addStr = String.join(",", addressCode);
        }
        String[] industryCodeArr = model.getIndustryCodeArr();
        entity.setAddressCode(addStr);
        String icode = "";
        if (industryCodeArr != null) {
            icode = String.join(",", industryCodeArr);
        }
        entity.setIndustryCode(icode);
        entity.setFrom(2);
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
        if (model.getCapital() != null) {
            entity.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
        }
        entity.setCapitalUnit(model.getCapitalUnit() != null ? model.getCapitalUnit() : "rmb");
        return customerDao.updateById(entity) > 0;
    }

    @Override
    public boolean checkCompanyName(String companyName) {
        companyName = StrUtil.removeAll(companyName, '(', ')', '\uff08', '\uff09', '\u3000', ' ');
        List< CustomerModel > model = customerDao.checkCompanyName(companyName);
        if (model != null && model.size() > 0) {
            return false;
        }
        return true;
    }

    @Autowired
    ProjectDao projectDao;

    @Override
    public PageModel< List< MyCustomerModel > > getMyCustomerList(QueryCustomerModel query, DataPermModel dataPerm) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        List< MyCustomerModel > result = customerDao.getMyCustomerList(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(result)) {
            List< Integer > customerIds = new ArrayList<>();
            Map< Integer, MyCustomerModel > cMap = new HashMap<>();
            for (MyCustomerModel customer : result) {
                customerIds.add(customer.getId());
                cMap.put(customer.getId(), customer);
                // 初始化当前客户的项目列表
                customer.setProjectList(new ArrayList<>());
            }
            // 获取当前客户列表所有项目
            List< ProjectInfoModel > projectList = projectDao.getProjectByCustomerIds(customerIds, query, dataPerm);
            if (projectList.size() > 0) {
                Map< Integer, ProjectInfoModel > pMap = new HashMap<>();
                List< Integer > projectIds = new ArrayList<>();
                for (ProjectInfoModel pInfo : projectList) {
                    projectIds.add(pInfo.getId());
                    pMap.put(pInfo.getId(), pInfo);
                    // 添加项目进客户项目列表
                    cMap.get(pInfo.getCustomerId()).getProjectList().add(pInfo);
                }
                // 获取项目所有成员
                List< ProjectMemberModel > members = projectMemberDao.getMemberList(customerIds, projectIds, null);
                for (ProjectMemberModel pm : members) {
                    if (pm.getRealName() == null) {
                        continue;
                    }
                    Integer projectId = pm.getProjectId();
                    ProjectInfoModel pInfo = pMap.get(projectId);
                    if (pm.getmType().equals(MemberTypeEnum.Tech.getId())) {
                        if (StringUtils.isEmpty(pInfo.getTechRealName())) {
                            pInfo.setTechRealName(pm.getRealName());
                        } else {
                            pInfo.setTechRealName(pInfo.getTechRealName() + "," + pm.getRealName());
                        }
                    } else if (pm.getmType().equals(MemberTypeEnum.FINANCE.getId())) {
                        if (StringUtils.isEmpty(pInfo.getFinanceRealName())) {
                            pInfo.setFinanceRealName(pm.getRealName());
                        } else {
                            pInfo.setFinanceRealName(pInfo.getFinanceRealName() + "," + pm.getRealName());
                        }
                    }
                }
            }
        }
        return PageUtils.build(page, result);
    }

    @Override
    public List< MiniModel > getCompanySelect(String customerName) {
        if (StringUtils.isEmpty(customerName)) {
            return new ArrayList<>();
        }
        return customerDao.getCompanySelect(customerName);
    }

    @Override
    public List< MiniModel > getGroupSelect(Integer groupId) {
        if (null == groupId || groupId <= 0) {
            return new ArrayList<>();
        }
        return customerDao.getGroupSelect(groupId);
    }

    @Override
    public Integer setGroup(Integer userId, Integer id) throws OwnerException {
        Customer customer = customerDao.selectById(id);
        if (null == customer) {
            throw new OwnerException("集团信息查询错误，请联系管理员");
        }
        Date date = new Date();
        customer.setLastUpdateTime(date);
        customer.setLastUpdatorId(userId);
        Integer type = customer.getCompanyType();
        CompanyGroupEntity groupEntity = new CompanyGroupEntity();
        Integer companyType = customer.getCompanyType();
        if (companyType == 1 || companyType == 3) {
            throw new OwnerException("该公司已经为集团公司");
        }
        if (companyType == 2) {
            customer.setCompanyType(3);
            groupEntity.setCompanyType(3);
        }
        if (companyType == 0) {
            customer.setCompanyType(1);
            customer.setGroupId(0);
            groupEntity.setCompanyType(1);
            //在company表有记录时赋值companyId，如果没有 则在插入后获得自增的companyId再赋值
            groupEntity.setFullPath(customer.getCompanyId() + "/");
            groupEntity.setCompanyId(customer.getCompanyId());
            groupEntity.setLevel(0);

            groupEntity.setGroupId(-1);
            groupEntity.setCreateTime(date);
            groupEntity.setMsCreatorId(userId);
        }

        groupEntity.setLastUpdateTime(date);
        groupEntity.setMsLastUpdatorId(userId);

        if (null == customer.getCompanyId() || customer.getCompanyId() == 0) {

            CompanyEntity entity = new CompanyEntity();
            BeanUtil.copyProperties(customer, entity);
            entity.setMsLastUpdatorId(userId);
            entity.setCreatorId(userId);
            entity.setLastUpdatorId(userId);
            entity.setMsCreatorId(userId);
            entity.setCreateTime(date);
            entity.setLastUpdateTime(date);
            entity.setMembers(0);
            entity.setDepts(0);
            entity.setBusinessLicense("");
            entity.setLogo("");
            entity.setStatus(0);
            entity.setId(null);
            List< CompanyEntity > list = Arrays.asList(entity);
            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                companyDao.insertBatch(list);
                CompanyEntity companyEntity = list.get(0);
                //company表插入记录后再取company的Id
                groupEntity.setCompanyId(companyEntity.getId());
                groupEntity.setFullPath(groupEntity.getCompanyId() + "/");
                companyGroupDao.insert(groupEntity);
                TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            } catch (Exception e) {
                TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
                logger.error(e.getMessage(), e);
            }
            CompanyEntity companyEntity = list.get(0);
            customer.setCompanyId(companyEntity.getId());
            customerDao.updateById(customer);
        } else {
            CompanyEntity entity = new CompanyEntity();
            entity.setId(customer.getCompanyId());
            entity.setCompanyType(1);
            entity.setMsLastUpdatorId(userId);
            entity.setLastUpdateTime(date);
            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                if (type == 2) {
                    List< CompanyGroupEntity > list = companyGroupDao.getByCompanyId(Arrays.asList(customer.getCompanyId()));
                    if (CollectionUtils.isEmpty(list)) {
                        throw new OwnerException("集团信息查询错误，请联系管理员");
                    }
                    groupEntity.setId(list.get(0).getId());
                    companyGroupDao.updateById(groupEntity);
                    entity.setCompanyType(3);
                    companyDao.updateById(entity);
                } else {
                    companyGroupDao.insert(groupEntity);
                    companyDao.updateById(entity);
                }
                TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            } catch (Exception e) {
                TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
                logger.error(e.getMessage(), e);
            }
            customerDao.updateById(customer);
        }
        return 1;
    }

    @Override
    public PageModel< List< FindCustomerModel > > findCustomerList(FindCustomerModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List< String > desc = new ArrayList<>();
            desc.add("c.creatorTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, customerDao.findCustomerList(page, query));
    }

    @Override
    public PageModel< List< FindCustomerModel > > findSonCustomerList(FindCustomerModel model) {
        Pagination page = model.getPagination();
        //查询集团下所有的子公司和子集团
        if (StringUtils.hasLength(model.getFullPath())) {
            if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
                List< String > desc = new ArrayList<>();
                desc.add("cg.createTime");
                page.setAscs(Arrays.asList("LENGTH(cg.fullPath)"));
                page.setDescs(desc);
            }
            List< FindCustomerModel > models = new ArrayList<>();
            if (null == model.getIgnoreSelf()) {
                models = companyGroupDao.getCompanyByFullPath(page, model.getFullPath(),
                        model.getCompanyId(), model.getCompanyName());
            } else {
                models = companyGroupDao.getCompanyByGroupId(page, model.getCompanyId(), model.getCompanyName());
            }
            if (!CollectionUtils.isEmpty(models)) {
                List< Integer > list = new ArrayList<>();
                List< Integer > companyIds = new ArrayList<>();
                models.forEach(item -> {
                    list.add(item.getId());
                    companyIds.add(item.getCompanyId());
                });
                List< FindCustomerModel > deptNames = customerDao.getDeptName(list);
                Map< Integer, FindCustomerModel > map = new HashMap<>();
                Map< Integer, String > roleMap = new HashMap<>();
                if (null != model.getUserId()) {
                    List< RsRoleModel > roles = rsRoledao.getRoleName(model.getUserId(), companyIds);
                    if (!CollectionUtils.isEmpty(roles)) {
                        roles.forEach(item -> {
                            roleMap.put(item.getCompanyId(), item.getRoleName());
                        });
                    }
                }
                deptNames.forEach(item -> {
                    map.put(item.getCompanyId(), item);
                });
                for (FindCustomerModel fcModel : models) {
                    Integer id = fcModel.getId();

                    if (map.containsKey(id)) {
                        FindCustomerModel findCustomerModel = map.get(id);
                        fcModel.setDeptName(findCustomerModel.getDeptName());
                        fcModel.setId(findCustomerModel.getId());
                    }
                    if (roleMap.containsKey(id)) {
                        fcModel.setRoleName(roleMap.get(id));
                    }
                    //如果公司无上一级，则所属集团显示自己
                    if (StringUtils.isEmpty(fcModel.getGroupName())) {
                        fcModel.setGroupName(fcModel.getCompanyName());
                    }
                }
            }
            return PageUtils.build(page, models);
            //添加子公司或者集团时 查询
        } else {
            if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
                List< String > desc = new ArrayList<>();
                desc.add("creatorTime");
                page.setDescs(desc);
            }
            return PageUtils.build(page, customerDao.findSonCustomerList(page, model));
        }
    }

    @Override
    public Boolean insertSonCustomer(Integer companyId, Integer userId, Integer groupId, List< Integer > customerIds) throws OwnerException {
        Date date = new Date();
        List< Customer > customers = customerDao.selectBatchIds(customerIds);
        List< CompanyGroupEntity > list = companyGroupDao.getByCompanyId(Arrays.asList(companyId));
        if (CollectionUtils.isEmpty(list)) {
            throw new OwnerException("集团信息查询错误，请联系管理员");
        }
        CompanyGroupEntity cgEntity = list.get(0);
        //更新时直接使用这个类的用户，时间信息
        cgEntity.setLastUpdateTime(date);
        cgEntity.setMsLastUpdatorId(userId);
        //company需要新增的List
        List< CompanyEntity > insertList = new ArrayList<>();
        //用于维护customerId 与companyId 的关系
        Map< Integer, Integer > map = new HashMap();

        //用于修改、新增customer和company表类型为集团的list
        List< Integer > rsUpdateGBatchIds = new ArrayList<>();
        List< Customer > updateGList = new ArrayList<>();

        //用于修改、新增customer和company表类型为公司的list
        List< Integer > rsUpdateBatchIds = new ArrayList<>();
        List< Customer > updateList = new ArrayList<>();

        //用于修改和新增company_group表的list
        List< Integer > updateGroupList = new ArrayList<>();
        List< CompanyGroupEntity > insertGroupList = new ArrayList<>();

        for (Customer bean : customers) {
            //如果type=1则修改company_group表，否则新增
            if (bean.getCompanyType() == 1) {
                updateGroupList.add(bean.getCompanyId());
                map.put(bean.getId(), bean.getCompanyId());
            } else {

                //如果companyId为0就赋值新增company表
                if (bean.getCompanyId() == null || bean.getCompanyId() == 0) {
                    CompanyEntity entity = new CompanyEntity();
                    BeanUtil.copyProperties(bean, entity);
                    entity.setMsLastUpdatorId(userId);
                    entity.setCreatorId(-1);
                    entity.setLastUpdatorId(-1);
                    entity.setMsCreatorId(userId);
                    entity.setCustomerId(bean.getId());
                    entity.setMembers(0);
                    entity.setDepts(0);
                    entity.setBusinessLicense("");
                    entity.setCreateTime(date);
                    entity.setStatus(0);
                    if (StringUtils.isEmpty(entity.getLogo())) {
                        entity.setLogo("");
                    }
                    insertList.add(entity);
                } else {
                    map.put(bean.getId(), bean.getCompanyId());
                }
            }
        }
        //批量新增并返回companyId
        if (!CollectionUtils.isEmpty(insertList)) {
            companyDao.insertBatch(insertList);
        }

        for (CompanyEntity model : insertList) {
            map.put(model.getCustomerId(), model.getId());
        }

        for (Customer bean : customers) {
            Integer id = bean.getId();
            Customer customer = new Customer();
            customer.setId(id);
            customer.setCompanyId(map.get(id));
            if (bean.getCompanyType() == 0) {
                updateList.add(customer);
                rsUpdateBatchIds.add(map.get(id));

                CompanyGroupEntity groupEntity = new CompanyGroupEntity();
                groupEntity.setCreateTime(date);
                groupEntity.setLastUpdateTime(date);
                groupEntity.setMsCreatorId(userId);
                groupEntity.setMsLastUpdatorId(userId);
                groupEntity.setCompanyId(map.get(id));
                groupEntity.setGroupId(companyId);
                groupEntity.setCompanyType(2);
                groupEntity.setFullPath(cgEntity.getFullPath() + map.get(id) + "/");
                groupEntity.setLevel(cgEntity.getLevel() + 1);
                insertGroupList.add(groupEntity);
            }
            if (bean.getCompanyType() == 1) {
                updateGList.add(customer);
                rsUpdateGBatchIds.add(map.get(id));
            }
        }
        //所有类型为子集团的公司，用于修改其子公司
        List< String > fullPaths = new ArrayList<>();
        if (!CollectionUtils.isEmpty(updateGroupList)) {
            List< CompanyGroupEntity > entities = companyGroupDao.getByCompanyId(updateGroupList);
            entities.forEach(item -> {
                fullPaths.add(item.getFullPath());
            });
        }

        TransactionStatus rsTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(rsUpdateBatchIds)) {
                companyDao.updateGroups(userId, date, 2, groupId, rsUpdateBatchIds);
            }
            if (!CollectionUtils.isEmpty(rsUpdateGBatchIds)) {
                companyDao.updateGroups(userId, date, 3, groupId, rsUpdateGBatchIds);
            }
            //company_group新增
            if (!CollectionUtils.isEmpty(insertGroupList)) {
                companyGroupDao.insertBatch(insertGroupList);
            }
            //company_group更新
            if (!CollectionUtils.isEmpty(updateGroupList)) {
                companyGroupDao.updateGroups(updateGroupList, cgEntity);
            }
            //更新company_group相关的子公司
            if (!CollectionUtils.isEmpty(fullPaths)) {
                companyGroupDao.subsidiariesDemotion(fullPaths, cgEntity);
            }
            TransactionUtils.commit(DataSourceEnum.RS, rsTransactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, rsTransactionStatus);
        }
        TransactionStatus msTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            //修改为子公司类型
            if (!CollectionUtils.isEmpty(updateList)) {
                customerDao.updateGroups(userId, date, 2, groupId, updateList);
            }
            //修改为子集团类型
            if (!CollectionUtils.isEmpty(updateGList)) {
                customerDao.updateGroups(userId, date, 3, groupId, updateGList);
            }
            TransactionUtils.commit(DataSourceEnum.MS, msTransactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, msTransactionStatus);
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public Boolean delSonCustomer(Integer userId, Integer companyId, List< Integer > customerIds) throws OwnerException {
        //查询对应的companyId
        List< Customer > customers = customerDao.selectBatchIds(customerIds);
        List< Integer > list = new ArrayList<>();

        //修改公司类型为子公司的list
        List< Integer > companyIds = new ArrayList<>();
        List< Integer > customerIdList = new ArrayList<>();

        //修改类型为子集团的list
        List< Integer > companyGroupIds = new ArrayList<>();
        List< Integer > customerGroupIds = new ArrayList<>();

        //更新和删除companyGroup的list
        List< Integer > groupIds = new ArrayList<>();
        List< Integer > delIds = new ArrayList<>();

        Date date = new Date();
        Customer customer = new Customer();
        customer.setLastUpdateTime(date);
        customer.setLastUpdatorId(userId);
        for (Customer bean : customers) {
            Integer companyType = bean.getCompanyType();
            Integer beanCompanyId = bean.getCompanyId();
            if (companyType == 3) {
                groupIds.add(beanCompanyId);
                companyGroupIds.add(beanCompanyId);
                customerGroupIds.add(beanCompanyId);
            } else if (companyType == 2) {
                companyIds.add(beanCompanyId);
                customerIdList.add(beanCompanyId);
                delIds.add(beanCompanyId);
            }
            list.add(beanCompanyId);
        }
        CompanyEntity company = new CompanyEntity();
        company.setMsLastUpdatorId(userId);
        company.setLastUpdateTime(date);

        List< CompanyGroupEntity > groups = companyGroupDao.getByCompanyId(Arrays.asList(companyId));
        if (CollectionUtils.isEmpty(groups)) {
            throw new OwnerException("集团信息查询错误，请联系管理员");
        }
        CompanyGroupEntity entity = groups.get(0);
        entity.setMsLastUpdatorId(userId);
        entity.setLastUpdateTime(date);
        List< String > fullPaths = new ArrayList<>();
        if (!CollectionUtils.isEmpty(groupIds)) {
            List< CompanyGroupEntity > entityList = companyGroupDao.getByCompanyId(groupIds);
            entityList.forEach(item -> {
                fullPaths.add(item.getFullPath());
            });
        }
        TransactionStatus msTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            if (!CollectionUtils.isEmpty(customerIdList)) {
                customerDao.upNotCustomer(customer, customerIdList, 0);
            }
            if (!CollectionUtils.isEmpty(customerGroupIds)) {
                customerDao.upNotCustomer(customer, customerGroupIds, 1);
            }
            TransactionUtils.commit(DataSourceEnum.MS, msTransactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, msTransactionStatus);
            return false;
        }

        TransactionStatus rsTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            //更新公司类型为子公司的数据
            if (!CollectionUtils.isEmpty(companyIds)) {
                companyDao.upNotCompany(company, companyIds, 0);
            }
            if (!CollectionUtils.isEmpty(delIds)) {
                companyGroupDao.delByCompanyIds(delIds);
            }

            //更新公司类型为子集团的数据
            if (!CollectionUtils.isEmpty(companyGroupIds)) {
                companyDao.upNotCompany(company, companyGroupIds, 1);
            }
            if (!CollectionUtils.isEmpty(groupIds)) {
                companyGroupDao.upNotGroup(entity, groupIds);
            }
            //更新类型为子集团下的子公司
            if (!CollectionUtils.isEmpty(fullPaths)) {
                companyGroupDao.subsidiariesUpgrade(fullPaths, entity);
            }
            List< Integer > userRoleIds = rsUserRoleDao.getGroupUserRole(customers.get(0).getGroupId(), list);
            if (!CollectionUtils.isEmpty(userRoleIds)) {
                rsUserRoleDao.deleteBatchIds(userRoleIds);
            }
            TransactionUtils.commit(DataSourceEnum.RS, rsTransactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, rsTransactionStatus);
            return false;
        }
        return true;
    }

    @Override
    public List< NameHistoryModel > getNameHistory(Integer companyId) {
        List< NameHistoryModel > nameHistoryModelList = nameHistoryDao.getNameHistoryList(companyId);
        return nameHistoryModelList;
    }

    @Override
    public Integer saveNameHistory(NameHistoryModel currentNameHistoryModel, UserInfo userInfo) throws OwnerException {
        Date currentStartDate = currentNameHistoryModel.getStartDate();
        String currentName = currentNameHistoryModel.getCompanyName();
        if (!StringUtils.hasText(currentName)) {
            throw new OwnerException("公司名称不可为空!");
        } else if (currentStartDate == null) {
            throw new OwnerException("生效时间不可为空!");
        }
        // 判断数据库存在相同生效时间或相同名字
        Integer currentId = currentNameHistoryModel.getId();
        Integer companyId = currentNameHistoryModel.getCompanyId();
        Integer hasHistory = nameHistoryDao.selectCountByIdAndSDate(currentName, companyId, currentId, currentStartDate);
        if (hasHistory > 0) {
            throw new OwnerException("存在相同生效时间或公司名称，请重新输入!");
        }

        Date now = new Date();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        RsNameHistoryEntity rsNameHistoryEntity = new RsNameHistoryEntity();
        /*for (int i = 0; i < historyListLength; i++) {
            NameHistoryModel nextHistory = nameHistoryModelList.get(i);
            Date nextStartDate = nextHistory.getStartDate();
            Integer nextId = nextHistory.getId();
            String nextName = nextHistory.getCompanyName();

            // 与其他记录重复生效时间
            if (DateUtil.compareDate(nextStartDate, currentStartDate) == 0 && !nextId.equals(currentId)) {
                String msg = "当前存在相同生效时间，请重新输入!";
                throw new OwnerException(msg);
            }
            Integer dateDiff = DateUtil.compareDate(nextStartDate, currentStartDate);
            // 判断当前记录是否为最后一条(是则更新rs-company、ms-customer表)
            if (dateDiff <= 0 && i == historyListLength - 1) {
                isFinally = true;
            }
            // 与相邻时间重复名称
            if (dateDiff >= 0) {
                if (historyListLength > 1 && i != 0) {
                    NameHistoryModel prevHistory = nameHistoryModelList.get(i - 1);
                    Date prevStartDate = prevHistory.getStartDate();
                    Integer prevId = prevHistory.getId();
                    String prevName = prevHistory.getCompanyName();
                    if (DateUtil.compareDate(prevStartDate, currentStartDate) < 0 &&
                            ((prevName.equals(currentName) && prevId.equals(currentId)) || (nextName.equals(currentName) && nextId.equals(currentId)))) {
                        String message = MessageFormat.format("当前相邻时间段存在相同公司名【{0}】，请重新输入!", currentName);
                        throw new OwnerException(message);
                    }
                } else {
                    if (nextName.equals(currentName) && !nextId.equals(currentId)) {
                        String message = MessageFormat.format("当前相邻时间段存在相同公司名【{0}】，请重新输入!", currentName);
                        throw new OwnerException(message);
                    }
                }
            }
        }*/

        try {
            Integer userId = userInfo.getId();
            // 最后一条或数据库无记录
            NameHistoryModel lastHistoryModel = nameHistoryDao.selectLastHistory(companyId);
            if (!(lastHistoryModel != null && DateUtil.compareDate(currentStartDate, lastHistoryModel.getStartDate()) < 0)) {
                companyDao.updateCompanyName(companyId, userId, currentName);

                Integer customerId = currentNameHistoryModel.getCustomerId();
                customerDao.updateCompanyName(customerId, currentName, userId);
            }
            // 判断是否为新纪录，若是，则添加，否则修改 getId() == null?
            rsNameHistoryEntity.setCompanyId(companyId);
            rsNameHistoryEntity.setCompanyName(currentName);
            rsNameHistoryEntity.setRemark(currentNameHistoryModel.getRemark());
            rsNameHistoryEntity.setStartDate(currentStartDate);
            rsNameHistoryEntity.setLastUpdateTime(now);
            rsNameHistoryEntity.setMsLastUpdatorId(userId);
            rsNameHistoryEntity.setLastUpdatorId(userId);

            if (currentId == null) {
                // 增加
                rsNameHistoryEntity.setCreateTime(now);
                rsNameHistoryEntity.setCreatorId(userId);
                rsNameHistoryEntity.setMsCreatorId(userId);
                nameHistoryDao.insert(rsNameHistoryEntity);
            } else {
                // 修改
                rsNameHistoryEntity.setId(currentId);
                nameHistoryDao.updateById(rsNameHistoryEntity);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        // 保存成功后返回 id
        return rsNameHistoryEntity.getId();
    }

    @Override
    public Integer delNameHistory(NameHistoryModel currentNameHistoryModel, UserInfo userInfo) throws OwnerException {
        // 判断删除后是否会出现相邻时间同一名称
        Integer nameId = currentNameHistoryModel.getId();
        if (nameId == null) {
            throw new OwnerException("请选择已保存的历史记录删除!");
        }
        TransactionStatus rsTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            // 删除
            RsNameHistoryEntity currHistoryModel = nameHistoryDao.selectById(nameId);
            nameHistoryDao.deleteById(nameId);
            Integer companyId = currentNameHistoryModel.getCompanyId();
            NameHistoryModel lastHistoryModel = nameHistoryDao.selectLastHistory(companyId);
            // 当前记录为最后一条则修改ms-customer和rs-company
            Date currStartDate = currHistoryModel.getStartDate();
            if (lastHistoryModel != null && DateUtil.compareDate(lastHistoryModel.getStartDate(), currStartDate) <= 0) {
                String currentName = lastHistoryModel.getCompanyName();
                Integer currentCompanyId = companyId;
                Integer customerId = currentNameHistoryModel.getCustomerId();
                customerDao.updateCompanyName(customerId, currentName, userInfo.getId());
                companyDao.updateCompanyName(currentCompanyId, userInfo.getId(), currentName);
            }

            TransactionUtils.commit(DataSourceEnum.RS, rsTransactionStatus);

        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, rsTransactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return null;
    }


}
