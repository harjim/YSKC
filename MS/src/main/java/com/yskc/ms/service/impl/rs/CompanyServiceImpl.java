package com.yskc.ms.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.MsUserInfo;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.ProductMtype;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.common.utils.TokenGenerator;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.CustomerDao;
import com.yskc.ms.dao.ms.CustomerTraceDao;
import com.yskc.ms.dao.ms.ProjectMemberDao;
import com.yskc.ms.dao.rs.CompanyDao;
import com.yskc.ms.dao.rs.RsDeptDao;
import com.yskc.ms.dao.rs.RsUserDao;
import com.yskc.ms.entity.ms.Customer;
import com.yskc.ms.entity.rs.CompanyEntity;
import com.yskc.ms.entity.rs.Dept;
import com.yskc.ms.entity.rs.User;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.entity.rs.models.role.RsRoleModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.CompanyInfoModel;
import com.yskc.ms.models.company.QueryCompanyModel;
import com.yskc.ms.models.customer.QueryFollowModel;
import com.yskc.ms.models.params.CompanyParams;
import com.yskc.ms.service.rs.CompanyService;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, CompanyEntity> implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private RsUserDao rsUserDao;
    @Autowired
    private RsDeptDao rsDeptDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    ProjectMemberDao projectMemberDao;
    @Autowired
    private CustomerTraceDao customerTraceDao;

    @Override
    public PageModel<List<CompanyModel>> getCompanyList(CompanyParams params) {

        Pagination pagination = params.getPagination();
        List<CompanyModel> queryCompanyList = companyDao.getCompanyList(pagination, params);
        if (CollectionUtils.isEmpty(queryCompanyList)) {
            return PageUtils.build(pagination, queryCompanyList);
        }
        List<Integer> companyIds = new ArrayList<>();
        for (CompanyModel companyModel : queryCompanyList) {
            companyIds.add(companyModel.getId());
        }
        List<RsRoleModel> roleModels = companyDao.queryRoles(companyIds);
        for (CompanyModel company : queryCompanyList) {
            for (RsRoleModel rolemodel : roleModels) {
                if (company.getId().equals(rolemodel.getCompanyId())) {
                    List<String> roles = company.getRoleNames() != null ? company.getRoleNames() : new ArrayList<>();
                    roles.add(rolemodel.getRoleName());
                    company.setRoleNames(roles);
                }
            }
        }

        for (int i = 0; i < queryCompanyList.size(); i++) {
            CompanyModel companyModel = queryCompanyList.get(i);
            String industryCode = companyModel.getIndustryCode();
            if (industryCode != null) {
                String[] industryCodeArr = industryCode.split(",");
                companyModel.setIndustryCodeArr(industryCodeArr);
            }

            String addressCode = companyModel.getAddressCode();
            if (addressCode != null) {
                String[] addressCodeArr = addressCode.split(",");
                companyModel.setAddressCodeArr(addressCodeArr);
            }

            List<String> roleNames = companyModel.getRoleNames();
            String roles = "";
            if (roleNames != null) {
                for (int j = 0; j < roleNames.size(); j++) {
                    if (j != roleNames.size() - 1) {
                        roles += roleNames.get(j) + ",";
                    } else {
                        roles += roleNames.get(j);
                    }
                }
            }
            companyModel.setRoles(roles);
        }

        return PageUtils.build(pagination, queryCompanyList);
    }

    @Override
    public PageModel<List<CompanyModel>> queryCompanyList(QueryCompanyModel query) {
        Pagination pagination = query.getPagination();
        if (CollectionUtils.isEmpty(pagination.getDescs()) && CollectionUtils.isEmpty(pagination.getAscs())) {
            pagination.setDescs(new ArrayList<>());
            pagination.getDescs().add("createTime");
        }
        List<CompanyModel> queryCompanyList = this.baseMapper.queryCompanyList(pagination, query);
        for (int i = 0; i < queryCompanyList.size(); i++) {
            CompanyModel companyModel = queryCompanyList.get(i);
            String industryCode = companyModel.getIndustryCode();
            if (industryCode != null) {
                String[] industryCodeArr = industryCode.split(",");
                companyModel.setIndustryCodeArr(industryCodeArr);
            }

            String addressCode = companyModel.getAddressCode();
            if (addressCode != null) {
                String[] addressCodeArr = addressCode.split(",");
                companyModel.setAddressCodeArr(addressCodeArr);
            }
        }
        return PageUtils.build(pagination, queryCompanyList);
    }

    @Override
    public Integer saveCompany(CompanyModel model, UserInfo userInfo) {
        Date date = new Date();
        Integer userId = userInfo.getId();
        CompanyEntity entity = new CompanyEntity();
        BeanUtil.copyProperties(model, entity);
        String[] addressCode = model.getAddressCodeArr();
        String addStr = "";
        for (int i = 0; i < addressCode.length; i++) {
            if (i == addressCode.length - 1) {
                addStr += addressCode[i];
            } else {
                addStr += addressCode[i] + ",";
            }
        }
        entity.setAddressCode(addStr);
        String icode = "";
        String[] industryCodeArr = model.getIndustryCodeArr();
        if (industryCodeArr != null) {
            for (int i = 0; i < industryCodeArr.length; i++) {
                if (i == industryCodeArr.length - 1) {
                    icode += industryCodeArr[i];
                } else {
                    icode += industryCodeArr[i] + ",";
                }
            }
        }
        entity.setIndustryCode(icode);
        if (model.getCapital() != null) {
            entity.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
        }
        entity.setCapitalUnit(model.getCapitalUnit() != null ? model.getCapitalUnit() : "rmb");
        entity.setMembers(0);
        entity.setDepts(0);
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

        entity.setBusinessLicense("");
        entity.setLogo("");
        entity.setLastUpdateTime(date);
        entity.setMsLastUpdatorId(userId);

        if (entity.getId() != null) {
            companyDao.updateById(entity);
            if (entity.getFrom() == 0 && entity.getStatus() == 1) {
                Customer customer = new Customer();
                BeanUtil.copyProperties(entity, customer);
                customer.setCompanyId(entity.getId());
                customer.setCreatorId(userId);
                customer.setLastUpdatorId(userId);
                customer.setCreatorTime(date);
                customer.setLastUpdateTime(date);
                customer.setDeptId(model.getDeptId());
                customerDao.insert(customer);
            }
            return entity.getStatus();
        }
        return -1;

    }

    @Override
    public boolean addCustomer(CompanyModel model, Integer userId) throws OwnerException {

        User userInfo = rsUserDao.getUserInfo(model.getUserName());
        if (userInfo != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        }

        String companyName = model.getCompanyName();
        CompanyEntity companyInfo = companyDao.getCompanyInfo(companyName, model.getId());
        if (companyInfo != null) {
            throw new OwnerException(ErrorEnum.COMPANYNAME_EXSIT);
        }

        Date date = new Date();
        User user = new User();
        user.setUserName(model.getUserName());
        user.setPassword(Md5Util.encrypt(MessageFormat.format("{0}{1}", model.getUserName(), model.getPassword())));
        user.setRealName(model.getCompanyName());
        user.setGender(0);
        user.setTel("");
        user.setStatus(0);
        user.setCreatorId(-1);
        user.setCreateTime(date);
        user.setLastUpdatorId(-1);
        user.setLastUpdateTime(date);
        user.setMsCreatorId(userId);
        user.setMsLastUpdatorId(userId);
        user.setType(0);

        CompanyEntity entity = new CompanyEntity();
        entity.setCompanyName(model.getCompanyName());
        entity.setCompanyAddress(model.getCompanyAddress());
        String[] addressCode = model.getAddressCodeArr();
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
        String[] industryCodeArr = model.getIndustryCodeArr();
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
        entity.setEmail(model.getEmail());
        entity.setOwner(model.getOwner());
        if (model.getCapital() != null) {
            entity.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
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
        entity.setCreateTime(new Date());
        entity.setCreatorId(-1);
        entity.setLastUpdateTime(new Date());
        entity.setLastUpdatorId(-1);
        entity.setMsCreatorId(userId);
        entity.setMsLastUpdatorId(userId);
        entity.setBusinessLicense("");
        entity.setLogo("");
        entity.setStatus(1);
        entity.setRemark(model.getRemark());
        entity.setFrom(2);
        entity.setCapitalUnit(model.getCapitalUnit() != null ? model.getCapitalUnit() : "rmb");

        Dept dept = new Dept();
        dept.setDeptName(companyName);
        dept.setParentId(-1);
        dept.setLevel(0);
        dept.setIdentity("");
        dept.setRemark("");
        dept.setCreatorId(-1);
        dept.setCreateTime(date);

        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            companyDao.insert(entity);
            dept.setCompanyId(entity.getId());
            rsDeptDao.insert(dept);
            dept.setFullPath(dept.getId() + Constant.PATH_SEPARATOR);
            rsDeptDao.updateById(dept);
            user.setCompanyId(entity.getId());
            rsUserDao.insert(user);
            TransactionUtils.commit(DataSourceEnum.RS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }

    }

    @Override
    public boolean editCustomer(CompanyModel model, Integer userId) throws OwnerException {
        User userInfo = rsUserDao.getUserInfoByCompanyId(model.getUserName(), model.getId());
        if (userInfo != null) {
            throw new OwnerException(ErrorEnum.USERNAME_EXSIT);
        }

        String companyName = model.getCompanyName();
        CompanyEntity companyInfo = companyDao.getCompanyInfo(companyName, model.getId());
        if (companyInfo != null) {
            throw new OwnerException(ErrorEnum.COMPANYNAME_EXSIT);
        }

        CompanyEntity entity = new CompanyEntity();
        entity.setId(model.getId());
        entity.setCompanyName(model.getCompanyName());
        entity.setCompanyAddress(model.getCompanyAddress());
        String[] addressCode = model.getAddressCodeArr();
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
        String[] industryCodeArr = model.getIndustryCodeArr();
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
//		entity.setDomain(model.getDomain());
        entity.setEmail(model.getEmail());
        entity.setOwner(model.getOwner());
        if (model.getCapital() != null) {
            entity.setCapital(model.getCapital().multiply(new BigDecimal(10000)).intValue());
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
        entity.setBusinessLicense("");
        entity.setLogo("");
        entity.setLastUpdateTime(new Date());

        entity.setStatus(model.getStatus());
        entity.setRemark(model.getRemark());

        companyDao.updateById(entity);
        return true;
    }


    @Override
    public String jump(MyCustomerModel model, UserInfo userInfo, DataPermModel dataPerm) throws OwnerException {
        // 获取当前用户在当前客户的所有项目的成员类型,在RS端通过成员类型获取相应的权限.
        List<ProductMtype> productMtypes = projectMemberDao.getMTypesByCustomerId(model.getId(), dataPerm);
        if (CollectionUtils.isEmpty(productMtypes)) {
            throw new OwnerException("没有此公司权限，请联系管理员");
        }
        String mtypes = userInfo.getMtypes();
        if (StringUtils.hasLength(mtypes)) {
            String[] mtypesArr = mtypes.split(",");
            List<Integer> mtypeList = new ArrayList<>();
            for (String mtypeStr : mtypesArr) {
                mtypeList.add(Integer.valueOf(mtypeStr));
            }
            List<Integer> productTypes = productMtypes.stream().map(a -> a.getProductType() == 2 ? 1 : a.getProductType()).distinct().collect(Collectors.toList());
            productMtypes = new ArrayList<>();
            for (Integer productType : productTypes) {
                for (Integer mtype : mtypeList) {
                    ProductMtype current = new ProductMtype();
                    current.setProductType(productType);
                    current.setmType(mtype);
                    productMtypes.add(current);
                }
            }

        } else {   // // TODO: 20/10/27 zdf productType 为2（高新），转换为1（创新项目）
            productMtypes.forEach(item -> {
                if (item.getProductType() == 2) {
                    item.setProductType(1);
                }
            });
        }
        MsUserInfo msUserInfo = new MsUserInfo();
        msUserInfo.setProductMtypes(productMtypes);
        msUserInfo.setUserId(userInfo.getId());
        msUserInfo.setCurrentCompanyId(model.getCompanyId());
        msUserInfo.setRealName(userInfo.getRealName());
        msUserInfo.setUserName(userInfo.getUserName());
        String token = TokenGenerator.generateValue();
        //临时跳转token,3分钟后过期，用过之后需要立即清除
        redisUtils.set("tempToken:" + token, msUserInfo, 180);
        return MessageFormat.format(msConfig.getLogin().getJumpUrl(), token);
    }

    @Override
    public boolean updateCompany(CompanyModel model, UserInfo userInfo) {
        CompanyEntity companyEntity = new CompanyEntity();
        BeanUtils.copyProperties(model, companyEntity);
        companyEntity.setLastUpdatorId(userInfo.getId());
        companyEntity.setLastUpdateTime(new Date());
        companyEntity.setStatus(3);
        companyEntity.setRemark("已存在该公司名的客户");
        companyDao.updateById(companyEntity);
        return true;
    }

    @Override
    public CompanyInfoModel getInfo(Integer companyId) {
        CompanyInfoModel model = companyDao.getInfo(companyId);
        CompanyInfoModel entity = customerDao.getByCompany(companyId);
        if (entity != null) {
            model.setCustomerId(entity.getCustomerId());
            model.setDeptId(entity.getDeptId());
            model.setOwerId(entity.getOwerId());
            model.setDeptName(entity.getDeptName());
            model.setOwnerName(entity.getOwnerName());
            model.setCompanyLevel(entity.getCompanyLevel());
            model.setStatus(entity.getStatus());
        }
        return model;
    }

    @Override
    public Boolean editInfo(CompanyInfoModel model, UserInfo userInfo) {
        Date date = new Date();
        Customer customer = customerDao.selectById(model.getCustomerId());
        customer.setDeptId(model.getDeptId());
        customer.setOwerId(model.getOwerId());
        customer.setIndustryCode(model.getIndustryCode());
        customer.setLastUpdateTime(date);
        customer.setLastUpdatorId(userInfo.getId());
        customer.setAddressCode(model.getAddressCode());
        customer.setStatus(model.getStatus());
        customer.setCompanyLevel(model.getCompanyLevel());
        CompanyEntity companyEntity = companyDao.selectById(model.getCompanyId());
        BeanUtils.copyProperties(model, companyEntity);
        companyEntity.setLastUpdateTime(date);
        companyEntity.setLastUpdatorId(-1);
        companyEntity.setMsLastUpdatorId(userInfo.getId());
        QueryFollowModel query = new QueryFollowModel();
        if (customerDao.updateById(customer) > 0) {
            query.setInfo("修改项目信息");
            query.setStatus(5);
            query.setCustomerId(model.getCustomerId());
            query.setCreateTime(new Date());
            query.setCreatorId(userInfo.getId());
            customerTraceDao.insertInfo(query);
            return companyDao.updateById(companyEntity) > 0;
        }
        return false;
    }
}
