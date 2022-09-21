package com.xxl.job.executor.service.jobhandler;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.H3Config;
import com.xxl.job.executor.dao.ms.*;
import com.xxl.job.executor.dao.rs.CompanyDao;
import com.xxl.job.executor.dao.rs.ReportDao;
import com.xxl.job.executor.entity.ms.*;
import com.xxl.job.executor.entity.rs.CompanyEntity;
import com.xxl.job.executor.entity.rs.Report;
import com.xxl.job.executor.models.customer.CustomerStatusModel;
import com.xxl.job.executor.models.h3.*;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class H3Job {
    private static Logger logger = LoggerFactory.getLogger(H3Job.class);

    @Autowired
    H3Config h3Config;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DeptDao deptDao;
    @Autowired
    UserDeptDao userDeptDao;
    @Autowired
    CustomerTraceDao customerTraceDao;
    static Map<String, String> biMap = new HashMap<>();
    static {
        biMap.put("人", "rmb");
        biMap.put("美", "usd");
        biMap.put("港", "hkd");
        biMap.put("欧", "eur");
        biMap.put("日", "jpy");
        biMap.put("英", "gbp");
        biMap.put("韩", "krw");
        biMap.put("澳", "aud");
        biMap.put("加", "cad");
    }
    // 错误客户名称
    Set ignoreCustomers = new HashSet() {
        {
            add("ITTest01");
            add("刈钣车件上海有限公司");
            add("东莞益海嘉里赛瑞淀粉科创有限公司");
            add("东莞巨正源科技");
            add("上海东方航空食品他投资有限公司");
            add("东莞市东实新能源有限公司客户公司名称");
            add("东莞市艾尔发自动化科技有限公司东莞珈码数控科技有限公司"); // 东莞市艾尔发自动化科技有限公司（东莞珈码数控科技有限公司）
            add("日照盈德气体有限");
            add("深圳市同晖珠宝首饰有限");
            add("东莞日精电子有");
            add("浙江惠中制衣有限公");
            add("东莞市兴开泰电子科技有限公");
            add("广东惠州平海发电厂有限公");
            add("宁波世林电器有限公");
            add("天津荣程联合集团-合同");
            add("临沂大林食品");
            add("客户公司名称佳化化学滨州有限公司"); //客户公司名称佳化化学(滨州)有限公司
            add("客户威海昌星电子有限公司公司名称");
            add("竹内工程机械青岛有限公司客户公司名称"); //竹内工程机械(青岛)有限公司客户公司名称
            add("东莞市东实新能源有限公司客户公司名称");
            add("艾福迈汽车系统上海有限公司名称"); //艾福迈汽车系统（上海）有限公司名称
            add("客户公司名称东莞创机电业制品有限公司");

        }
    };

    Map<String, Integer> customerIdMap = new HashMap<>();
    Map<String,Integer> deptIdMap = new HashMap<>();
    Map<Integer,Integer> userIdDeptMap = new HashMap<>();
    Map<String,Integer> userIdMap = new HashMap<>();

    private Integer getCustomerId(String customerName){
        Integer customerId = customerIdMap.computeIfAbsent(customerName, k -> {
            List<Customer> customerModels = customerDao.selectList(new EntityWrapper<Customer>().where("REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(companyName,'(',''),')',''),'（',''),'）',''),' ','')={0}",k));
            if (customerModels.size() > 0) {
                return customerModels.get(0).getId();
            }
            return null;
        });
        return customerId;
    }
    private Integer getDeptId(String deptname){
        Integer deptId = deptIdMap.computeIfAbsent(deptname, k -> {

            List<Dept> depts = deptDao.selectList(new EntityWrapper<Dept>().eq("fullname", k.replace("东莞市优赛科创企业管理咨询有限公司/","")));
            if (depts.size() > 0) {
                return depts.get(0).getId();
            }
            return null;
        });
        return deptId;
    }

    private Integer getDeptIdByUserId(Integer userId){
        Integer deptId = userIdDeptMap.computeIfAbsent(userId, k -> {
            List<UserDept> userDepts = userDeptDao.selectList(new EntityWrapper<UserDept>().eq("userId",userId));
            if (userDepts.size() > 0) {
                return userDepts.get(0).getDepId();
            }
            return null;
        });
        return deptId;
    }

    private Integer getUserId(String realName){
        Integer userId = userIdMap.computeIfAbsent(realName, k -> {
            List<UserEntity> users = userDao.selectList(new EntityWrapper<UserEntity>().eq("realName", k));
            if (users.size() > 0) {
                return users.get(0).getId();
            }
            return null;
        });
        return userId;
    }

    private void updateCustomer(Map cMap) {
        String companyName = (String) cMap.get("Name");
        if (StringUtils.isEmpty(companyName))
            return;
        // 对有括号的公司,将全角括号替换成半角括号
        String customerName = companyName.replace(" ", "").replace("（", "(").replace("）", ")");
        //去除各种符号的客户名称用来检测
        companyName = companyName.replaceAll("[（|）|\\(|\\)|\\s]", ""); //.replace(" ", "").replace("（", "(").replace("）", ")");
        if (ignoreCustomers.contains(companyName)) {
            return;
        }
        Customer customer = new Customer();
        customer.setCompanyName(customerName);
        customer.setInvoiceTitle(customerName);
        String addr = (String) cMap.get("Addr");
        if (!StringUtils.isEmpty(addr)) {
            // '{"adcode":"440000","adname":"广东省 ","Detail":"广州市南沙区黄阁镇乌洲山北路1号"}'
            try {
                Map addrMap = JsonUtils.jsonToPojo(addr, Map.class);
                String adcode = (String) addrMap.get("adcode");
                if (!StringUtils.isEmpty(adcode) && adcode.length() == 6) {
                    String addrCode;
                    if (adcode.substring(2, 6) == "0000") {
                        addrCode = adcode;
                    } else if (adcode.substring(4, 6) == "00") {
                        addrCode = adcode.substring(0, 2) + "00," + adcode;
                    } else {
                        addrCode = adcode.substring(0, 2) + "0000," + adcode.substring(0, 4) + "00," + adcode;
                    }
                    customer.setAddressCode(addrCode);
                }
                String detail = (String) addrMap.get("Detail");
                if (!StringUtils.isEmpty(detail)) {
                    customer.setCompanyAddress(detail);
                }
            } catch (Exception ex) {
            }
        }
        String taxId = (String) cMap.get("F0000022");
        if (!StringUtils.isEmpty(taxId)) {
            customer.setTaxpayerId(taxId);
            customer.setCreditCode(taxId);
        }
        String linkMan = (String) cMap.get("ContactName");
        if (!StringUtils.isEmpty(linkMan)) {
            customer.setLinkMan(linkMan);
        }
        String mobile = (String) cMap.get("Mobile");
        if (!StringUtils.isEmpty(mobile)) {
            customer.setLinkTel(mobile);
        }

        String capital = (String) cMap.get("F0000003");
        if (!StringUtils.isEmpty(capital)) {
            String unit = "rmb";
            for (String k :
                    biMap.keySet()) {
                if (capital.contains(k)) {
                    unit = biMap.get(k);
                    break;
                }
            }
            try {
                String c = capital.replaceAll("[^\\d|^.]", "");
                if (!StringUtils.isEmpty(c)) {
                    Double ci = Double.parseDouble(c) * 10000;
                    customer.setCapital(ci.intValue());
                }
                customer.setCapitalUnit(unit);
            } catch (Exception ex) {
                XxlJobLogger.log(ex);
            }
        }

        String owner = (String) cMap.get("OwnerId");
        if (!StringUtils.isEmpty(owner)) {
            customer.setOwerId(getUserId(owner));
        }
        String ownerDeptId = (String) cMap.get("OwnerDeptId");
        if (!StringUtils.isEmpty(ownerDeptId)) {
            Integer deptId = getDeptId(ownerDeptId);
            customer.setDeptId(deptId);
        }

        String sl = (String) cMap.get("StrategicLevel");
        if (!StringUtils.isEmpty(sl)) {
            String companyLevel = null;
            switch (sl){
                case "5dd0945d-62a9-41f0-976f-2993d673f183":
                    companyLevel = "A";
                    break;
                case "00000000-0000-0000-0005-000000000002":
                    companyLevel = "B";
                    break;
                case "c5cac326-d2e4-4930-b345-9d4c881f0fd9":
                    companyLevel = "C";
                    break;
                case "7451ea59-161d-4aa3-ab35-0ee6807ffe61":
                    companyLevel = "A+";
                    break;
            }
            customer.setCompanyLevel(companyLevel);
        }

        Integer customerId = getCustomerId(companyName);
        if (customerId != null) {
            customer.setId(customerId);
            customer.setLastUpdatorId(1);
            customer.setLastUpdateTime(LocalDateTime.now());
            // 设置成已签状态
            customer.setStatus(30);
            customerDao.updateById(customer);
        } else {
            if (customer.getAddressCode() == null)
                customer.setAddressCode("");
            if (customer.getTaxpayerId() == null)
                customer.setTaxpayerId("");
            if (customer.getCreditCode() == null)
                customer.setCreditCode("");
            customer.setOwner("");
            customer.setRegisterTime(LocalDateTime.of(2000,1,1,0,0));
            customer.setHasDevAccount(false);
            customer.setHighTec(false);
            customer.setFrom(0);
            // 设置成已签状态
            customer.setStatus(30);
            customer.setCreatorId(1);
            customer.setLastUpdatorId(1);
            customer.setCreatorTime(LocalDateTime.now());
            customer.setLastUpdateTime(LocalDateTime.now());
            customerDao.insert(customer);
        }

    }

    /**
     * 返回一个H3Matcher的Item对象
     * @param itemName 条件列名,要匹配的项
     * @param operator 运算符：0 =大于，1=大于等于，2=等于，3=小于等于，4=小于，5=不等于，6=在某个范围内，7=不在某个范围内。
     * @param value 要匹配的值
     * @return H3Matcher对象
     */
    private H3Matcher getItemMatcher(String itemName, int operator, String value){
        H3Matcher h3Matcher = new H3Matcher();
        h3Matcher.setType("Item");
        h3Matcher.setName(itemName);
        h3Matcher.setOperator(operator);
        h3Matcher.setValue(value);
        return  h3Matcher;
    }
    /**
     * 同步客户数据
     */
    @XxlJob("customerHandler")
    public ReturnT<String> customerHandler(String param) throws Exception {

        Date now = new Date();
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime lastDate = dateTime.minusDays(11);

        Integer spiltCount = h3Config.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName("LoadBizObjects");
        request.setSchemaCode("D000001Client");
        H3Matcher andMatcher = new H3Matcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());
        andMatcher.getMatchers().add(getItemMatcher("Status",2,"1"));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",3,dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        H3Filter chuanYunFilter = new H3Filter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;
        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(JsonUtils.objectToJson(chuanYunFilter));
            H3Response<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful() && null != response.getReturnData()) {
                totalCount = response.getReturnData().getTotalCount();
                List<Map> customerList = response.getReturnData().getBizObjectArray();
                for (Map cMap : customerList
                ) {
                    updateCustomer(cMap);
                    cMap.put("synDataDateTime", new Date());
                    List<String> f0000038s = (List<String>) cMap.get("F0000038");
//                    if (approvers != null && approvers.size() > 0) {
//                        amap.put("ApproverName", approvers.get(0));
//                    }
                    cMap.put("SalesAssistant", null);
                    if (f0000038s != null && f0000038s.size() > 0) {
                        cMap.put("F0000038Name", f0000038s.get(0));
                    }
                }
                customerDao.InsertH3Customers(customerList);
                fromRowNum += spiltCount;
            } else {
                XxlJobLogger.log("未请求到氚云数据,返回结果:"+ response.getSuccessful());
                break;
            }
        }

        return ReturnT.SUCCESS;
    }
    @XxlJob("updateCustomerStatusJob")
    public ReturnT<String> updateCustomerStatusJob(String param) throws Exception{
        Date date = new Date();
        //param 格式为：“1,2” (邀约，拜访)
        if (null==param){
            param ="1,2";
        }
        String[] split = param.split(",");
        if (null==split[0]){
            split[0] ="1";
        }
        if (null==split[1]){
            split[1] ="2";
        }
        Date inviteDate = ToolUtils.offsetDay(split[0],date);
        Date visitDate = ToolUtils.offsetDay(split[1],date);
        TransactionStatus transactionStatus = null;
        String info = "逾期未跟进";
        try{
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            customerTraceDao.insertInfoList(inviteDate,visitDate, info);
            customerDao.updateCustomer(inviteDate,visitDate);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
            return ReturnT.SUCCESS;
        }catch (Exception e){
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            logger.error(e.getMessage());
            return ReturnT.FAIL;
        }
    }
    @XxlJob("projectAuditHandler")
    public ReturnT<String> projectAuditHandler(String param) throws Exception
    {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime lastDate = dateTime.minusDays(7);

        Integer spiltCount = h3Config.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName("LoadBizObjects");
        request.setSchemaCode("D00051276af3ce6d80e444b903a2b7b087425a8");

        H3Matcher andMatcher = new H3Matcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());
        andMatcher.getMatchers().add(getItemMatcher("Status",2,"1"));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",3,dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        // andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,"2021-09-1"));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        H3Filter chuanYunFilter = new H3Filter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;

        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(com.yskc.common.utils.JsonUtils.objectToJson(chuanYunFilter));
            H3Response<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful() && response.getReturnData()!=null) {
                totalCount = response.getReturnData().getTotalCount();
                List<Map> projectList = response.getReturnData().getBizObjectArray();
                for (Map pMap : projectList
                ) {
                    UpdateNewProject(pMap);
                }
                fromRowNum += spiltCount;
            } else {
                XxlJobLogger.log("未请求到氚云数据,返回结果:"+ response.getSuccessful());
                break;
            }
        }
        return ReturnT.SUCCESS;
    }
    Map<String,Customer> objectIdCustomerMap = new HashMap<>();
    private void UpdateNewProject(Map projectMap) throws Exception {
        // 客户ID
        String objectId = (String) projectMap.get("F0000001");
        if (StringUtils.isEmpty(objectId)) {
            return;
        }
        Customer customerInfo = objectIdCustomerMap.computeIfAbsent(objectId,k->{
            List<Customer> customers = customerDao.selectList(new EntityWrapper<Customer>().where("REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(companyName,'(',''),')',''),'（',''),'）',''),' ','') in (select REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(clientName,'(',''),')',''),'（',''),'）',''),' ','') from h3_customer where objectId={0})",k));
            if (customers.size()>0){
                Customer customer = customers.get(0);
                if (customer.getCompanyId()==null || customer.getCompanyId()==0){
                    try {
                        CompanyEntity companyEntity = new CompanyEntity();
                        companyEntity.setCompanyName(customer.getCompanyName());
                        companyEntity.setCompanyAddress(customer.getCompanyAddress());
                        companyEntity.setAddressCode(customer.getAddressCode());
                        companyEntity.setTaxpayerId(customer.getTaxpayerId());
                        companyEntity.setCreditCode(customer.getCreditCode());
                        companyEntity.setInvoiceTitle(customer.getInvoiceTitle());
                        companyEntity.setIndustryCode(customer.getIndustryCode());
                        companyEntity.setMainIndustry(customer.getMainIndustry());
                        companyEntity.setLinkTel(customer.getLinkTel());
                        companyEntity.setDomain("");
                        companyEntity.setEmail(customer.getEmail());
                        companyEntity.setOwner(customer.getOwner());
                        companyEntity.setCapital(customer.getCapital());
                        companyEntity.setMembers(0);
                        companyEntity.setDepts(0);
                        companyEntity.setRegisterTime(customer.getRegisterTime());
                        companyEntity.setFirstDevFee(customer.getFirstDevFee());
                        companyEntity.setAccountSystem(customer.getAccountSystem());
                        companyEntity.setTaxAuthorities(customer.getTaxAuthorities());
                        companyEntity.setRealTaxAuthorities(customer.getRealTaxAuthorities());
                        companyEntity.setHasDevAccount(customer.getHasDevAccount());
                        companyEntity.setHighTec(customer.getHighTec());
                        companyEntity.setBusinessLicense("");
                        companyEntity.setLogo("");
                        companyEntity.setCreateTime(LocalDateTime.now());
                        companyEntity.setCreatorId(-1);
                        companyEntity.setLastUpdateTime(LocalDateTime.now());
                        companyEntity.setLastUpdatorId(-1);
                        companyEntity.setFrom(3);
                        companyEntity.setStatus(1);
                        companyEntity.setLinkMan(customer.getLinkMan());
                        companyEntity.setRemark(customer.getRemark());
                        companyEntity.setMsCreatorId(1);
                        companyEntity.setMsLastUpdatorId(1);
                        companyEntity.setCapitalUnit(customer.getCapitalUnit());
                        companyDao.insert(companyEntity);
                        // 开通平台,并更新对应的companyId
                        customer.setCompanyId(companyEntity.getId());
                        customerDao.updateById(customer);
                    } catch (Exception ex){
                        XxlJobLogger.log(ex.getMessage());
                    }
                    // CompanyDao
                }
                return customer;
            }
            return null;
        });
        if (customerInfo==null){
            return;
        }
        Integer customerId = customerInfo.getId();
        Product productInfo = null;
        Object y = null;
        y = projectMap.get("F0000005");
        if (y == null) {
            return;
        }
        try{
            String productName = (String) projectMap.get("F0000025");
            if (!StringUtils.isEmpty(productName)) {
                productName = productName.trim();
                productInfo = productMap.computeIfAbsent(productName, k -> {
                    List<Product> products = productDao.selectList(new EntityWrapper<Product>().eq("productName", k));
                    if (products.size() > 0) {
                        return products.get(0);
                    } else {
                        Product product = new Product();
                        product.setLastUpdateTime(LocalDateTime.now());
                        product.setLastUpdatorId(1);
                        product.setProductType(0);
                        product.setCreateTime(LocalDateTime.now());
                        product.setCreatorId(1);
                        product.setProductName(k);
                        productDao.insert(product);
                        return product;
                    }
                });
            } else {
                return;
            }
            Integer productId = productInfo.getId();
            // 规划数量和规划研发费
            Integer cnt =(Integer)projectMap.get("F0000015");
            Integer rdFee =(Integer)projectMap.get("F0000016");
            if (cnt!=null && rdFee!=null) {
                Map<String, Object> reportMap = new HashMap<String, Object>();
                reportMap.put("ryear", y);
                reportMap.put("companyId", customerInfo.getCompanyId());
                List<Report> reportList = reportDao.selectList(new EntityWrapper<Report>().allEq(reportMap));
                Report report = new Report();
                report.setCnt(cnt);
                report.setRdFee(rdFee);
                if (reportList.size() > 0) {
                    report.setId(reportList.get(0).getId());
                    report.setLastUpdateTime(LocalDateTime.now());
                    report.setMsLastUpdatorId(-1);
                    reportDao.updateById(report);
                } else {
                    report.setCompanyId(customerInfo.getCompanyId());
                    report.setCreateTime(LocalDateTime.now());
                    report.setCreatorId(-1);
                    report.setLastUpdatorId(-1);
                    report.setLastUpdateTime(LocalDateTime.now());
                    report.setRname("");
                    report.setTechIntro("");
                    report.setMsCreatorId(-1);
                    report.setMsLastUpdatorId(-1);
                    report.setRyear(Integer.parseInt((String)y));
                    report.setRemark("");
                    reportDao.insert(report);
                }
            }

            Project project = new Project();
            String desc =  (String)projectMap.get("F0000013");
            project.setRemark(desc);
            String sales = (String) projectMap.get("OwnerId");
            if (!StringUtils.isEmpty(sales)) {
                project.setOwerId(getUserId(sales));
                if (project.getOwerId()!=null)
                    project.setDeptId(getDeptIdByUserId(project.getOwerId()));
            }
            if (project.getOwerId()==null){
                return;
            }
            List<String> busIds = (List<String>) projectMap.get("F0000018");
            if (busIds != null && busIds.size() > 0) {
                project.setBusinessId(getUserId(busIds.get(0)));
            }
            if (project.getDeptId()==null) {
                String OwnerDeptId = (String) projectMap.get("OwnerDeptId");
                if (!StringUtils.isEmpty(OwnerDeptId)) {
                    project.setDeptId(getDeptId(OwnerDeptId));
                } else {
                    return;
                }
            }

            Map<String, Object> pMap = new HashMap<String, Object>();
            pMap.put("productId", productId);
            pMap.put("year", y);
            pMap.put("customerId", customerId);
            List<Project> projects = projectDao.selectList(new EntityWrapper<Project>().allEq(pMap));
            project.setCustomerId(customerId);
            project.setProductId(productId);
            project.setYear(Integer.parseInt(y.toString()));

            project.setLastUpdateTime(LocalDateTime.now());
            project.setLastUpdatorId(1);
            Integer projectId = null;
            if (projects.size() > 0) {
                // 2020.5.28 调整项目人员分配规则,如果已经存在创新系统的项目,则不再进行同步. 由创新系统中去重新分配人员
                return;
                // projectId = projects.get(0).getId();
//            project.setId(projectId);
//            projectDao.updateById(project);
            } else {
                project.setCreateTime(LocalDateTime.now());
                project.setCreatorId(1);
                projectDao.insert(project);
                projectId = project.getId();
            }
            String tecName = (String) projectMap.get("F0000023");

            List<ProjectMemberModel> projectMembers = new ArrayList<>();
            // 技术人员
            Integer techId = -1;
            if (!StringUtils.isEmpty(tecName)) {
                Integer userId = getUserId(tecName);
                if (userId != null) {
                    ProjectMemberModel projectMember = new ProjectMemberModel();
                    projectMember.setProjectId(projectId);
                    projectMember.setCreateTime(LocalDateTime.now());
                    projectMember.setCreatorId(1);
                    projectMember.setLastUpdateTime(LocalDateTime.now());
                    projectMember.setLastUpdatorId(1);
                    projectMember.setMemberId(userId);
                    projectMember.setmType(1);
                    projectMember.setCustomerId(customerId);
                    projectMembers.add(projectMember);
                    techId = userId;
                }
            }
            String finName = (String) projectMap.get("F0000024");
            //财务人员
            if (!StringUtils.isEmpty(finName)) {
                Integer userId = getUserId(finName);
                if (userId != null) {
                    ProjectMemberModel projectMember = new ProjectMemberModel();
                    projectMember.setProjectId(projectId);
                    projectMember.setCreateTime(LocalDateTime.now());
                    projectMember.setCreatorId(1);
                    projectMember.setLastUpdateTime(LocalDateTime.now());
                    projectMember.setLastUpdatorId(1);
                    projectMember.setMemberId(userId);
                    projectMember.setmType(2);
                    projectMember.setCustomerId(customerId);
                    projectMembers.add(projectMember);
                    // projectMemberDao.insert(projectMember);
                }
            }
            // 业务员
            if (project.getOwerId()!=null){
                ProjectMemberModel projectMember = new ProjectMemberModel();
                projectMember.setProjectId(projectId);
                projectMember.setCreateTime(LocalDateTime.now());
                projectMember.setCreatorId(1);
                projectMember.setLastUpdateTime(LocalDateTime.now());
                projectMember.setLastUpdatorId(1);
                projectMember.setMemberId(project.getOwerId());
                projectMember.setmType(3);
                projectMember.setCustomerId(customerId);
                projectMembers.add(projectMember);
            }
            //谈判经理
            if (project.getBusinessId()!=null){
                ProjectMemberModel projectMember = new ProjectMemberModel();
                projectMember.setProjectId(projectId);
                projectMember.setCreateTime(LocalDateTime.now());
                projectMember.setCreatorId(1);
                projectMember.setLastUpdateTime(LocalDateTime.now());
                projectMember.setLastUpdatorId(1);
                projectMember.setMemberId(project.getBusinessId());
                projectMember.setmType(4);
                projectMember.setCustomerId(customerId);
                projectMembers.add(projectMember);
            }
            if (projectMembers.size()>0){
                List<Integer> mTypes = new ArrayList<>();
                mTypes.add(1);
                mTypes.add(2);
                mTypes.add(3);
                mTypes.add(4);
                projectDao.batchUpdateMembers(projectId,mTypes,projectMembers);
            }

            // 专利撰写，添加进专利撰写需求。
            if (productId==103){
                projectDao.insertPatentDemand(customerId,project.getYear(),project.getOwerId(),techId,desc);
            }
            // 创新项目合并成一条记录
            if (productInfo.getProductType()>0){
                MergeInnProject(customerId,project.getYear(),productId,project.getDeptId(),projectMembers);
            }
        }
        catch (Exception ex){
            XxlJobLogger.log(ex);
        }
    }

    @Autowired
    InnovationProjectDao innovationProjectDao;
    @Autowired
    InnovationMemberDao innovationMemberDao;
    /**
     * 合并进创新项目
     * @param customerId
     * @param pYear
     * @param productId
     * @return
     */
    private void MergeInnProject(Integer customerId,Integer pYear,Integer productId,Integer deptId,List<ProjectMemberModel> projectMembers){
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("customerId", customerId);
        pMap.put("year", pYear);
        List<InnovationProject> iprojects = innovationProjectDao.selectList(new EntityWrapper<InnovationProject>().allEq(pMap));
        InnovationProject innovationProject = null;
        List<InnovationMember> innovationMembers = new ArrayList<>();
        if (iprojects.size()>0){
            innovationProject = iprojects.get(0);
            String proId = ","+ productId.toString() + ",";
            String proIds = ","+ innovationProject.getTypes() + ",";
            if (!proIds.contains(proId)){
                innovationProject.setTypes(innovationProject.getTypes() + "," + productId.toString());
                innovationProjectDao.updateById(innovationProject);
            }
            List<InnovationMember> members = innovationMemberDao.selectList(new EntityWrapper<InnovationMember>().eq("innovationId",innovationProject.getId()));
            if (projectMembers.size()>0){
                for (ProjectMemberModel pMember:
                     projectMembers) {
                    for (InnovationMember iMember:
                         members) {
                        if (pMember.getmType()==iMember.getmType()){
                            if (pMember.getMemberId()!=iMember.getMemberId()){
                                iMember.setMaster(false);
                                innovationMembers.add(iMember);
                            }
                        }
                    }
                    InnovationMember innovationMember = getInnovationMember(innovationProject, pMember);
                    innovationMembers.add(innovationMember);
                }
            }
        } else {
            innovationProject = new InnovationProject();
            innovationProject.setCustomerId(customerId);
            innovationProject.setYear(pYear);
            innovationProject.setDeptId(deptId);
            innovationProject.setTypes(productId.toString());
            innovationProject.setCreatorId(-1);
            innovationProject.setCreateTime(LocalDateTime.now());
            innovationProject.setLastUpdatorId(-1);
            innovationProject.setLastUpdateTime(LocalDateTime.now());
            innovationProjectDao.insert(innovationProject);
            if (projectMembers.size()>0){
                for (ProjectMemberModel pMember:
                        projectMembers) {
                    InnovationMember innovationMember = getInnovationMember(innovationProject, pMember);
                    innovationMembers.add(innovationMember);
                }
            }
        }
        if (innovationMembers.size()>0){
            innovationMemberDao.insertOrUpdate(innovationMembers);
        }
    }

    private InnovationMember getInnovationMember(InnovationProject innovationProject, ProjectMemberModel pMember) {
        InnovationMember innovationMember = new InnovationMember();
        innovationMember.setMaster(true);
        innovationMember.setCreateTime(LocalDateTime.now());
        innovationMember.setCreatorId(-1);
        innovationMember.setInnovationId(innovationProject.getId());
        innovationMember.setmType(pMember.getmType());
        innovationMember.setMemberId(pMember.getMemberId());
        return innovationMember;
    }

    /**
     * 同步项目数据
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("projectHandler")
    public ReturnT<String> projecthandler(String param) throws Exception
    {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime lastDate = dateTime.minusDays(1);

        Integer spiltCount = h3Config.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName("LoadBizObjects");
        request.setSchemaCode("yrb9f3we4x4wfzqsa60ylv0h6");

        H3Matcher andMatcher = new H3Matcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());

        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",3,dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        // andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,"2020-07-01"));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        H3Filter chuanYunFilter = new H3Filter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;

        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(com.yskc.common.utils.JsonUtils.objectToJson(chuanYunFilter));
            H3Response<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful() && response.getReturnData()!=null) {
                totalCount = response.getReturnData().getTotalCount();
                List<Map> projectList = response.getReturnData().getBizObjectArray();
                for (Map pMap : projectList
                ) {
                    pMap.put("F0000534", pMap.get("Name"));
                    if (pMap.get("ProjectYear") == "") {
                        pMap.put("ProjectYear", 0);
                    }
                    List<String> F0000523s = (List<String>) pMap.get("F0000523");
                    List<String> F0000524s = (List<String>) pMap.get("F0000524");
                    if (F0000523s != null && F0000523s.size() > 0) {
                        pMap.put("F0000523Name", F0000523s.get(0));
                    }
                    if (F0000524s != null && F0000524s.size() > 0) {
                        pMap.put("F0000524Name", F0000524s.get(0));
                    }
                    UpdateProject(pMap);
                }
                customerDao.insertH3Projects(projectList);
                // cyCustomerDao.insertH3Project(projectList);
                fromRowNum += spiltCount;
            } else {
                XxlJobLogger.log("未请求到氚云数据,返回结果:"+ response.getSuccessful());
                break;
            }
        }
        return ReturnT.SUCCESS;
    }
    @Autowired
    ProductDao productDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    ReportDao reportDao;
    @Autowired
    CompanyDao companyDao;
    Map<String,Integer> objectIdCustomerIdMap = new HashMap<>();
    Map<String, Product> productMap = new HashMap<>();
    private void UpdateProject(Map projectMap) throws Exception {
        String objectId = (String) projectMap.get("F0000006");
        if (StringUtils.isEmpty(objectId)) {
            return;
        }
        Integer customerId = objectIdCustomerIdMap.computeIfAbsent(objectId,k->{
            List<Customer> customers = customerDao.selectList(new EntityWrapper<Customer>().where("REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(companyName,'(',''),')',''),'（',''),'）',''),' ','') in (select REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(clientName,'(',''),')',''),'（',''),'）',''),' ','') from h3_customer where objectId={0})",k));
            if (customers.size()>0){
                Customer customer = customers.get(0);
                if (customer.getCompanyId()==null || customer.getCompanyId()==0){
                    try {
                        CompanyEntity companyEntity = new CompanyEntity();
                        companyEntity.setCompanyName(customer.getCompanyName());
                        companyEntity.setCompanyAddress(customer.getCompanyAddress());
                        companyEntity.setAddressCode(customer.getAddressCode());
                        companyEntity.setTaxpayerId(customer.getTaxpayerId());
                        companyEntity.setCreditCode(customer.getCreditCode());
                        companyEntity.setInvoiceTitle(customer.getInvoiceTitle());
                        companyEntity.setIndustryCode(customer.getIndustryCode());
                        companyEntity.setMainIndustry(customer.getMainIndustry());
                        companyEntity.setLinkTel(customer.getLinkTel());
                        companyEntity.setDomain("");
                        companyEntity.setEmail(customer.getEmail());
                        companyEntity.setOwner(customer.getOwner());
                        companyEntity.setCapital(customer.getCapital());
                        companyEntity.setMembers(0);
                        companyEntity.setDepts(0);
                        companyEntity.setRegisterTime(customer.getRegisterTime());
                        companyEntity.setFirstDevFee(customer.getFirstDevFee());
                        companyEntity.setAccountSystem(customer.getAccountSystem());
                        companyEntity.setTaxAuthorities(customer.getTaxAuthorities());
                        companyEntity.setRealTaxAuthorities(customer.getRealTaxAuthorities());
                        companyEntity.setHasDevAccount(customer.getHasDevAccount());
                        companyEntity.setHighTec(customer.getHighTec());
                        companyEntity.setBusinessLicense("");
                        companyEntity.setLogo("");
                        companyEntity.setCreateTime(LocalDateTime.now());
                        companyEntity.setCreatorId(-1);
                        companyEntity.setLastUpdateTime(LocalDateTime.now());
                        companyEntity.setLastUpdatorId(-1);
                        companyEntity.setFrom(3);
                        companyEntity.setStatus(1);
                        companyEntity.setLinkMan(customer.getLinkMan());
                        companyEntity.setRemark(customer.getRemark());
                        companyEntity.setMsCreatorId(1);
                        companyEntity.setMsLastUpdatorId(1);
                        companyEntity.setCapitalUnit(customer.getCapitalUnit());
                        companyDao.insert(companyEntity);
                        // 开通平台,并更新对应的companyId
                        customer.setCompanyId(companyEntity.getId());
                        customerDao.updateById(customer);
                    } catch (Exception ex){
                        XxlJobLogger.log(ex.getMessage());
                    }
                    // CompanyDao
                }
                return customer.getId();
            }
            return null;
        });
        if (customerId==null){
            return;
        }
        Product productInfo = null;
        Object y = null;
        y = projectMap.get("ProjectYear");
        if (y == null) {
            return;
        }
        try{
        String productName = (String) projectMap.get("F0000534");
        if (!StringUtils.isEmpty(productName)) {
            productName = productName.trim();
            productInfo = productMap.computeIfAbsent(productName, k -> {
                List<Product> products = productDao.selectList(new EntityWrapper<Product>().eq("productName", k));
                if (products.size() > 0) {
                    return products.get(0);
                } else {
                    Product product = new Product();
                    product.setLastUpdateTime(LocalDateTime.now());
                    product.setLastUpdatorId(1);
                    product.setProductType(0);
                    product.setCreateTime(LocalDateTime.now());
                    product.setCreatorId(1);
                    product.setProductName(k);
                    productDao.insert(product);
                    return product;
                }
            });
        } else {
            return;
        }

        Integer productId = productInfo.getId();
        Project project = new Project();

        String sales = (String) projectMap.get("Sales");
        if (!StringUtils.isEmpty(sales)) {
            project.setOwerId(getUserId(sales));
            if (project.getOwerId()!=null)
                project.setDeptId(getDeptIdByUserId(project.getOwerId()));
        }
        if (project.getOwerId()==null){
            return;
        }
        String busId = (String) projectMap.get("F0000524Name");
        if (!StringUtils.isEmpty(busId)) {
            project.setBusinessId(getUserId(busId));
        }
        if (project.getDeptId()==null) {
            String OwnerDeptId = (String) projectMap.get("OwnerDeptId");
            if (!StringUtils.isEmpty(OwnerDeptId)) {
                project.setDeptId(getDeptId(OwnerDeptId));
            } else {
                return;
            }
        }

        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("productId", productId);
        pMap.put("year", y);
        pMap.put("customerId", customerId);
        List<Project> projects = projectDao.selectList(new EntityWrapper<Project>().allEq(pMap));
        project.setCustomerId(customerId);
        project.setProductId(productId);
        project.setYear(Integer.parseInt(y.toString()));

        project.setLastUpdateTime(LocalDateTime.now());
        project.setLastUpdatorId(1);
        Integer projectId = null;
        if (projects.size() > 0) {
            // 2020.5.28 调整项目人员分配规则,如果已经存在创新系统的项目,则不再进行同步. 由创新系统中去重新分配人员
            return;
//            projectId = projects.get(0).getId();
//            project.setId(projectId);
//            projectDao.updateById(project);
        } else {
            project.setCreateTime(LocalDateTime.now());
            project.setCreatorId(1);
            projectDao.insert(project);
            projectId = project.getId();
        }
        String tecName = (String) projectMap.get("F0000112");

        List<ProjectMemberModel> projectMembers = new ArrayList<>();
        // 技术人员
        if (!StringUtils.isEmpty(tecName)) {
            Integer userId = getUserId(tecName);
            if (userId != null) {
                ProjectMemberModel projectMember = new ProjectMemberModel();
                projectMember.setProjectId(projectId);
                projectMember.setCreateTime(LocalDateTime.now());
                projectMember.setCreatorId(1);
                projectMember.setLastUpdateTime(LocalDateTime.now());
                projectMember.setLastUpdatorId(1);
                projectMember.setMemberId(userId);
                projectMember.setmType(1);
                projectMember.setCustomerId(customerId);
                projectMembers.add(projectMember);
            }
        }
        String finName = (String) projectMap.get("F0000115");
        //财务人员
        if (!StringUtils.isEmpty(finName)) {
            Integer userId = getUserId(finName);
            if (userId != null) {
                ProjectMemberModel projectMember = new ProjectMemberModel();
                projectMember.setProjectId(projectId);
                projectMember.setCreateTime(LocalDateTime.now());
                projectMember.setCreatorId(1);
                projectMember.setLastUpdateTime(LocalDateTime.now());
                projectMember.setLastUpdatorId(1);
                projectMember.setMemberId(userId);
                projectMember.setmType(2);
                projectMember.setCustomerId(customerId);
                projectMembers.add(projectMember);
                // projectMemberDao.insert(projectMember);
            }
        }
        // 业务员
        if (project.getOwerId()!=null){
            ProjectMemberModel projectMember = new ProjectMemberModel();
            projectMember.setProjectId(projectId);
            projectMember.setCreateTime(LocalDateTime.now());
            projectMember.setCreatorId(1);
            projectMember.setLastUpdateTime(LocalDateTime.now());
            projectMember.setLastUpdatorId(1);
            projectMember.setMemberId(project.getOwerId());
            projectMember.setmType(3);
            projectMember.setCustomerId(customerId);
            projectMembers.add(projectMember);
        }
        //谈判经理
        if (project.getBusinessId()!=null){
            ProjectMemberModel projectMember = new ProjectMemberModel();
            projectMember.setProjectId(projectId);
            projectMember.setCreateTime(LocalDateTime.now());
            projectMember.setCreatorId(1);
            projectMember.setLastUpdateTime(LocalDateTime.now());
            projectMember.setLastUpdatorId(1);
            projectMember.setMemberId(project.getBusinessId());
            projectMember.setmType(4);
            projectMember.setCustomerId(customerId);
            projectMembers.add(projectMember);
        }
        if (projectMembers.size()>0){
            List<Integer> mTypes = new ArrayList<>();
            mTypes.add(1);
            mTypes.add(2);
            mTypes.add(3);
            mTypes.add(4);
            projectDao.batchUpdateMembers(projectId,mTypes,projectMembers);
        }
        }
        catch (Exception ex){
            XxlJobLogger.log(ex);
        }
    }

    /**
     * 同步专利需求数据
     * @param param
     * @return
     * @throws Exception
     */
    @XxlJob("patentHandler")
    public ReturnT<String> patentHandler(String param) throws Exception
    {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime lastDate = dateTime.minusDays(1);

        Integer spiltCount = h3Config.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName("LoadBizObjects");
        request.setSchemaCode("D0005120563eec4aa9a4c8ba2a53a1f5803296d");

        H3Matcher andMatcher = new H3Matcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());
        andMatcher.getMatchers().add(getItemMatcher("Status",2,"1"));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",3,dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        // andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,"2020-07-01"));
        andMatcher.getMatchers().add(getItemMatcher("ModifiedTime",1,lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        H3Filter chuanYunFilter = new H3Filter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;

        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(com.yskc.common.utils.JsonUtils.objectToJson(chuanYunFilter));
            H3Response<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful() && response.getReturnData()!=null) {
                totalCount = response.getReturnData().getTotalCount();
                List<Map> projectList = response.getReturnData().getBizObjectArray();
                for (Map pMap : projectList
                ) {
                    UpdatePatent(pMap);
                }
                // customerDao.insertH3Projects(projectList);
                fromRowNum += spiltCount;
            } else {
                XxlJobLogger.log("未请求到氚云数据,返回结果:"+ response.getSuccessful());
                break;
            }
        }
        return ReturnT.SUCCESS;
    }

    @Autowired
    PatentDemandDao patentDemandDao;
    private void UpdatePatent(Map patentMap) throws Exception {
        String objectId = (String) patentMap.get("Customer");
        if (StringUtils.isEmpty(objectId)) {
            return;
        }
        Integer customerId = objectIdCustomerIdMap.computeIfAbsent(objectId,k->{
            List<Customer> customers = customerDao.selectList(new EntityWrapper<Customer>().where("REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(companyName,'(',''),')',''),'（',''),'）',''),' ','') in (select REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(clientName,'(',''),')',''),'（',''),'）',''),' ','') from h3_customer where objectId={0})",k));
            if (customers.size()>0){
                Customer customer = customers.get(0);
                if (customer.getCompanyId()==null || customer.getCompanyId()==0){
                    try {
                        CompanyEntity companyEntity = new CompanyEntity();
                        companyEntity.setCompanyName(customer.getCompanyName());
                        companyEntity.setCompanyAddress(customer.getCompanyAddress());
                        companyEntity.setAddressCode(customer.getAddressCode());
                        companyEntity.setTaxpayerId(customer.getTaxpayerId());
                        companyEntity.setCreditCode(customer.getCreditCode());
                        companyEntity.setInvoiceTitle(customer.getInvoiceTitle());
                        companyEntity.setIndustryCode(customer.getIndustryCode());
                        companyEntity.setMainIndustry(customer.getMainIndustry());
                        companyEntity.setLinkTel(customer.getLinkTel());
                        companyEntity.setDomain("");
                        companyEntity.setEmail(customer.getEmail());
                        companyEntity.setOwner(customer.getOwner());
                        companyEntity.setCapital(customer.getCapital());
                        companyEntity.setMembers(0);
                        companyEntity.setDepts(0);
                        companyEntity.setRegisterTime(customer.getRegisterTime());
                        companyEntity.setFirstDevFee(customer.getFirstDevFee());
                        companyEntity.setAccountSystem(customer.getAccountSystem());
                        companyEntity.setTaxAuthorities(customer.getTaxAuthorities());
                        companyEntity.setRealTaxAuthorities(customer.getRealTaxAuthorities());
                        companyEntity.setHasDevAccount(customer.getHasDevAccount());
                        companyEntity.setHighTec(customer.getHighTec());
                        companyEntity.setBusinessLicense("");
                        companyEntity.setLogo("");
                        companyEntity.setCreateTime(LocalDateTime.now());
                        companyEntity.setCreatorId(-1);
                        companyEntity.setLastUpdateTime(LocalDateTime.now());
                        companyEntity.setLastUpdatorId(-1);
                        companyEntity.setFrom(3);
                        companyEntity.setStatus(1);
                        companyEntity.setLinkMan(customer.getLinkMan());
                        companyEntity.setRemark(customer.getRemark());
                        companyEntity.setMsCreatorId(1);
                        companyEntity.setMsLastUpdatorId(1);
                        companyEntity.setCapitalUnit(customer.getCapitalUnit());
                        companyDao.insert(companyEntity);
                        // 开通平台,并更新对应的companyId
                        customer.setCompanyId(companyEntity.getId());
                        customerDao.updateById(customer);
                    } catch (Exception ex){
                        XxlJobLogger.log(ex.getMessage());
                    }
                    // CompanyDao
                }
                return customer.getId();
            }
            return null;
        });
        if (customerId==null){
            return;
        }
        String demandNo = (String)patentMap.get("SeqNo");
        List<PatentDemand> demands = patentDemandDao.selectList(new EntityWrapper<PatentDemand>().eq("demandNo",demandNo));
        if (demands.size()==0){
            PatentDemand patentDemand = new PatentDemand();
            patentDemand.setDemandNo(demandNo);
            patentDemand.setCustomerId(customerId);
            patentDemand.setYear(Integer.parseInt((String)patentMap.get("Year")));
            patentDemand.setCreateTime(new Date());
            patentDemand.setPlanSubmitDate(new Date((String)patentMap.get("PlanDate")));
            patentDemand.setAppearanceDesignNum((Integer) patentMap.get("F0000003"));
            patentDemand.setInventionNum((Integer)patentMap.get("F0000001"));
            patentDemand.setUtilityNum((Integer)patentMap.get("F0000002"));
            patentDemand.setSoftRightNum((Integer)patentMap.get("F0000009"));
            patentDemand.setTotal((Integer) patentMap.get("F0000004"));
            patentDemand.setRemark((String)patentMap.get("Remark"));
            patentDemand.setTechId(0);
            patentDemand.setOwnerId(0);
            Integer dtype = patentMap.get("DType").equals("撰写")?1:0;
            patentDemand.setType(dtype);

            patentDemandDao.insert(patentDemand);
            Integer demandId = patentDemand.getId();

            List<PatentDemandMember> members = new ArrayList<>();
            //3
            String saleName = (String) patentMap.get("CreatedBy");
            //业务人员
            if (!StringUtils.isEmpty(saleName)) {
                PatentDemandMember pdm = new PatentDemandMember();
                pdm.setMemberId( getUserId(saleName));
                pdm.setmType(3);
                pdm.setDeamndId(demandId);
                members.add(pdm);
            }
            // 1
            List<String> techNames = (List<String>) patentMap.get("F0000008");
            if (techNames!=null) {
                for (String tName :
                        techNames) {
                    PatentDemandMember pdm = new PatentDemandMember();
                    pdm.setMemberId(getUserId(tName));
                    pdm.setmType(1);
                    pdm.setDeamndId(demandId);
                    members.add(pdm);
                }
            }
            if (members.size()>0){
               patentDemandDao.insertMembers(members);
            }


        }
    }
    private RestTemplate restTemplate = new RestTemplate();
    /**
     * 批量查询业务数据
     *
     * @param request
     * @return LoadBizObjectRequest
     */
    public <T> H3Response<LoadBizObjectsData<T>> loadBizObjects(LoadBizObjectsRequest request) {
        try {
            ParameterizedTypeReference<H3Response<LoadBizObjectsData<T>>> typeReference = new ParameterizedTypeReference<H3Response<LoadBizObjectsData<T>>>() {
            };
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            headers.add("EngineCode", h3Config.getEngineCode());
            headers.add("EngineSecret", h3Config.getEngineSecret());
            // 发送请求
            HttpEntity<LoadBizObjectsRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<H3Response<LoadBizObjectsData<T>>> resultEntity = restTemplate.exchange(h3Config.getUrl(), HttpMethod.POST, entity, typeReference);
            return resultEntity.getBody();
        } catch (Exception ex) {
            logger.error("", ex.getMessage());
            return null;
        }
    }

    public  void  uploadFile(){
        // D0005129cd8e5eda54f4d6f8416c3cf32b85b33&BizObjectId=7913c173-07f1-450d-b1ee-5f897b0e6852
        String urlTransfer = "https://www.h3yun.com/OpenApi/UploadAttachment?SchemaCode=D0005129cd8e5eda54f4d6f8416c3cf32b85b33&FilePropertyName=F0000010&BizObjectId=7913c173-07f1-450d-b1ee-5f897b0e6852";
        HttpHeaders headers = new HttpHeaders();
        // headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("EngineCode", h3Config.getEngineCode());
        headers.add("EngineSecret", h3Config.getEngineSecret());
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        //headers.set("Cookie", cookies);//session用于验证身份
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        FileSystemResource fileSystemResource = new FileSystemResource("d:\\work\\sshda.txt");
        form.add("file", fileSystemResource);
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        HttpEntity<String> responseEntity = restTemplate.postForEntity(urlTransfer, files, String.class);//发送
        //此处要添加错误处理
        String result = responseEntity.getBody().toString();
    }
}
