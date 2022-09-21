package com.yskc.ms.chuanyun;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.chuanyun.model.*;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.models.customer.CustomerModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 氚云工具
 *
 * @author huronghua
 */
@Component
public class ChuanYunService {
    private Logger logger = LoggerFactory.getLogger(ChuanYunService.class);
    @Autowired
    private ChuanYunConfig chuanYunConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CyCustomerDao cyCustomerDao;
    private static ThreadPoolExecutor poolExecutor = getPoolExecutor();

    /**
     * 创建这样一个线程池：
     * 核心线程为20，最大100；
     * 多于核心线程时，线程存货时间为1分
     * 缓存队列是有界阻塞队列，最大的缓存数为30
     *
     * @return
     */
    private static ThreadPoolExecutor getPoolExecutor() {
        if (poolExecutor != null) {
            return poolExecutor;
        }
        return new ThreadPoolExecutor(20, 100, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(30));
    }

    /**
     * 批量查询业务数据
     *
     * @param request
     * @return LoadBizObjectRequest
     */
    public <T> ChuanYunResponse<LoadBizObjectsData<T>> loadBizObjects(LoadBizObjectsRequest request) {
        try {
            ParameterizedTypeReference<ChuanYunResponse<LoadBizObjectsData<T>>> typeReference = new ParameterizedTypeReference<ChuanYunResponse<LoadBizObjectsData<T>>>() {
            };
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType(ChuanYunConstant.APPLICATION_JSON_CHARSET_UTF_8);
            headers.setContentType(type);
            headers.add(ChuanYunConstant.ACCEPT, MediaType.APPLICATION_JSON.toString());
            headers.add(ChuanYunConstant.ENGINE_CODE, chuanYunConfig.getEngineCode());
            headers.add(ChuanYunConstant.ENGINE_SECRET, chuanYunConfig.getEngineSecret());
            // 发送请求
            HttpEntity<LoadBizObjectsRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<ChuanYunResponse<LoadBizObjectsData<T>>> resultEntity = restTemplate.exchange(chuanYunConfig.getUrl(), HttpMethod.POST, entity, typeReference);
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
        headers.add("EngineCode", chuanYunConfig.getEngineCode());
        headers.add("EngineSecret", chuanYunConfig.getEngineSecret());
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        //headers.set("Cookie", cookies);//session用于验证身份
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        FileSystemResource fileSystemResource = new FileSystemResource("d:\\work\\sshda.txt");

        form.add("file", fileSystemResource);
        //form.add("pass", finalpass);;//用于验证身份
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        HttpEntity<String> responseEntity = restTemplate.postForEntity(urlTransfer, files, String.class);//发送
        //此处要添加错误处理
        String result = responseEntity.getBody().toString();
    }

    public void download() throws IOException {
        String urlTransfer = "https://www.h3yun.com/Api/DownloadBizObjectFile";
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        form.add("EngineCode", chuanYunConfig.getEngineCode());
        form.add("attachmentId","eeb4d29a-b6df-49e8-950d-2bb4217737cc");
        headers.add("EngineCode", chuanYunConfig.getEngineCode());
        headers.add("EngineSecret", chuanYunConfig.getEngineSecret());
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        HttpEntity<Resource> responseEntity = restTemplate.postForEntity(urlTransfer,files, Resource.class);//发送

        InputStream is = responseEntity.getBody().getInputStream();
//        if (response.getStatusCodeValue() != 200) {//异常处理
//
//        }
//        //写入本地
        try {
            File file = new File("d:/test.txt");
            FileOutputStream fos = new FileOutputStream(file);
            // fos.write(responseEntity.getBody());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    ProjectDao projectDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ProjectMemberDao projectMemberDao;


    Map<String, String> cyCustomerMap = new HashMap<>();
    Map<String, Integer> productIdMap = new HashMap<>();

    // 1.项目中没有业务员的数据不导
    // 2.项目名不在客户表单中的不导
    private void UpdateProject(Map projectMap) {
        String objectId = (String) projectMap.get("F0000006");
        if (StringUtils.isEmpty(objectId)) {
            return;
        }
        String customerName = cyCustomerMap.computeIfAbsent(objectId, k -> {
            List<CyCustomer> cyCustomers = cyCustomerDao.selectList(new EntityWrapper<CyCustomer>().eq("objectId", k));
            if (cyCustomers.size() > 0) {
                return cyCustomers.get(0).getName().replaceAll("[（|）|\\(|\\)|\\s]", "");
            }
            return null;
        });
        if (StringUtils.isEmpty(customerName)) {
            return;
        }
        // 检测是否为现有客户数据
        Integer customerId = customerIdMap.computeIfAbsent(customerName, k -> {
            List<CustomerModel> customerModels = customerDao.checkCompanyName(k);
            if (customerModels.size() > 0) {
                return customerModels.get(0).getId();
            }
            return null;
        });
        Integer productId = null;
        Object y = null;
        if (customerId == null) {
            return;
        }
        String productName = (String) projectMap.get("F0000534");
        if (!StringUtils.isEmpty(productName)) {
            productName = productName.trim();
            productId = productIdMap.computeIfAbsent(productName, k -> {
                List<Product> products = productDao.selectList(new EntityWrapper<Product>().eq("productName", k));
                if (products.size() > 0) {
                    return products.get(0).getId();
                } else {
                    Product product = new Product();
                    product.setLastUpdateTime(DateTime.now());
                    product.setLastUpdatorId(1);
                    product.setCreateTime(DateTime.now());
                    product.setCreatorId(1);
                    product.setProductName(k);
                    productDao.insert(product);
                    return product.getId();
                }
            });
        } else {
            return;
        }
        y = projectMap.get("ProjectYear");
        if (y == null) {
            return;
        }

        Project project = new Project();

        String sales = (String) projectMap.get("Sales");
        if (!StringUtils.isEmpty(sales)) {
            User s = getUser(sales);
            if (s != null) {
                project.setOwerId(s.getId());
            } else {
                System.out.println(sales);
                return;
            }
        } else {
            System.out.println(sales);
            System.out.println(customerName);
            return;
        }
        String busId = (String) projectMap.get("F0000524Name");
        if (!StringUtils.isEmpty(busId)) {
            User s = getUser(busId);
            if (s != null) {
                project.setBusinessId(s.getId());
            }
        }
        String OwnerDeptId = (String) projectMap.get("OwnerDeptId");
        if (!StringUtils.isEmpty(OwnerDeptId)) {
            Dept s = getDetp(OwnerDeptId);
            if (s != null) {
                project.setDeptId(s.getId());
            }
        }
        if (project.getDeptId() == null) {
            System.out.println(OwnerDeptId);
            System.out.println(customerName);
            return;
        }
//        project.setfDeptId();
//        project.setfDistrict();
//        project.settDeptId();
//        project.settDistrict();

        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("productId", productId);
        pMap.put("year", y);
        pMap.put("customerId", customerId);
        List<Project> projects = projectDao.selectList(new EntityWrapper<Project>().allEq(pMap));
        project.setCustomerId(customerId);
        project.setProductId(productId);
        project.setYear(Integer.parseInt(y.toString()));

        project.setLastUpdateTime(DateTime.now());
        project.setLastUpdatorId(1);
        Integer projectId = null;
        if (projects.size() > 0) {
            projectId = projects.get(0).getId();
            project.setId(projectId);
            projectDao.updateById(project);
        } else {
            project.setCreateTime(DateTime.now());
            project.setCreatorId(1);
            projectDao.insert(project);
            projectId = project.getId();
        }
        String tecName = (String) projectMap.get("F0000112");

        // projectMemberDao.deleteByProjectId(projectId);
        List<ProjectMemberModel> projectMembers = new ArrayList<>();
        // 技术人员
        if (!StringUtils.isEmpty(tecName)) {
            User s = getUser(tecName);
            if (s != null) {
                ProjectMemberModel projectMember = new ProjectMemberModel();
                projectMember.setProjectId(projectId);
                projectMember.setCreateTime(DateTime.now());
                projectMember.setCreatorId(1);
                projectMember.setLastUpdateTime(DateTime.now());
                projectMember.setLastUpdatorId(1);
                projectMember.setMemberId(s.getId());
                projectMember.setmType(1);
                projectMember.setCustomerId(customerId);
                projectMembers.add(projectMember);
                // projectMemberDao.insert(projectMember);
            }
        }
        String finName = (String) projectMap.get("F0000115");
        //财务人员
        if (!StringUtils.isEmpty(finName)) {
            User s = getUser(finName);
            if (s != null) {
                ProjectMemberModel projectMember = new ProjectMemberModel();
                projectMember.setProjectId(projectId);
                projectMember.setCreateTime(DateTime.now());
                projectMember.setCreatorId(1);
                projectMember.setLastUpdateTime(DateTime.now());
                projectMember.setLastUpdatorId(1);
                projectMember.setMemberId(s.getId());
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
            projectMember.setCreateTime(DateTime.now());
            projectMember.setCreatorId(1);
            projectMember.setLastUpdateTime(DateTime.now());
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
            projectMember.setCreateTime(DateTime.now());
            projectMember.setCreatorId(1);
            projectMember.setLastUpdateTime(DateTime.now());
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
            projectMemberDao.batchUpdateMembers(projectId,mTypes,projectMembers);
        }
    }

    Map<String,User> userMap = new HashMap<>();
    User getUser(String realName) {
        if (!StringUtils.isEmpty(realName)) {
            return userMap.computeIfAbsent(realName,k->{
                List<User> users = userDao.selectList(new EntityWrapper<User>().eq("realName", k));
                if (users.size() > 0) {
                    return users.get(0);
                }
                return null;
            });
        }
        return null;
    }
    Map<String,Dept> deptMap = new HashMap<>();
    Dept getDetp(String deptName) {
        if (!StringUtils.isEmpty(deptName)) {
            deptName = deptName.replaceAll("东莞市优赛科创企业管理咨询有限公司/","");
            return deptMap.computeIfAbsent(deptName,k->{
                List<Dept> depts = deptDao.selectList(new EntityWrapper<Dept>().eq("fullname", k));
                if (depts.size() > 0) {
                    return depts.get(0);
                }
                return null;
            });
        }
        return null;
    }
    String createdTime="2020-05-11 08:30:00";
    public void synProject() {
        // D000001Agreement
        Integer spiltCount = chuanYunConfig.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName(ChuanYunConstant.ACTION_NAME_LOAD_BIZ_OBJECTS);
        request.setSchemaCode("yrb9f3we4x4wfzqsa60ylv0h6");

        ChuanYunMatcher andMatcher = new ChuanYunMatcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());

        ChuanYunMatcher chuanYunMatcher = new ChuanYunMatcher();
        chuanYunMatcher.setType("Item");
        chuanYunMatcher.setName("CreatedTime");
        chuanYunMatcher.setOperator(4); //Operator运算符：0 =大于，1=大于等于，2=等于，3=小于等于，4=小于，5=不等于，6=在某个范围内，7=不在某个范围内。
        chuanYunMatcher.setValue(createdTime);
        andMatcher.getMatchers().add(chuanYunMatcher);

        ChuanYunFilter chuanYunFilter = new ChuanYunFilter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;
        cyCustomerDao.deleteH3Project();
        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(JsonUtils.objectToJson(chuanYunFilter));
            ChuanYunResponse<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful()) {
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
                cyCustomerDao.insertH3Project(projectList);
                fromRowNum += spiltCount;
            } else {
                break;
            }
        }
    }

    public void synContract() {
        // D000001Agreement
        Integer spiltCount = chuanYunConfig.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName(ChuanYunConstant.ACTION_NAME_LOAD_BIZ_OBJECTS);
        request.setSchemaCode("D000001Agreement");
        ChuanYunMatcher andMatcher = new ChuanYunMatcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());

        ChuanYunFilter chuanYunFilter = new ChuanYunFilter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;
        cyCustomerDao.deleteH3Agreement();
        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(JsonUtils.objectToJson(chuanYunFilter));
            ChuanYunResponse<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful()) {
                totalCount = response.getReturnData().getTotalCount();
                List<Map> agreeMap = response.getReturnData().getBizObjectArray();
                for (Map amap : agreeMap
                ) {
                    amap.put("clientName", amap.get("Name").toString()
                            .replace(" ", "")
                            .replace("（", "(")
                            .replace("）", ")"));
                    List<String> approvers = (List<String>) amap.get("Approvers");
                    List<String> f0000022s = (List<String>) amap.get("F0000022");
                    if (approvers != null && approvers.size() > 0) {
                        amap.put("ApproverName", approvers.get(0));
                    }
                    if (f0000022s != null && f0000022s.size() > 0) {
                        amap.put("f0000022Name", f0000022s.get(0));
                    }
                }
                cyCustomerDao.insertH3Agreement(agreeMap);
                fromRowNum += spiltCount;
            } else {
                break;
            }
        }
    }

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

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DeptDao deptDao;

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

    private void updateCustomer(Map cMap) {
        String companyName = (String) cMap.get("Name");
        if (StringUtils.isEmpty(companyName))
            return;
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
                System.out.println(ex);
            }
        }

        String owner = (String) cMap.get("OwnerId");
        if (!StringUtils.isEmpty(owner)) {
            List<User> users = userDao.selectList(new EntityWrapper<User>().eq("realName", owner));
            if (users.size() > 0) {
                customer.setOwerId(users.get(0).getId());
            }
        }
        String ownerDeptId = (String) cMap.get("OwnerDeptId");
        if (!StringUtils.isEmpty(ownerDeptId)) {
            List<Dept> depts = deptDao.selectList(new EntityWrapper<Dept>().eq("fullname", ownerDeptId));
            if (depts.size() > 0) {
                customer.setDeptId(depts.get(0).getId());
            }
        }
        Integer customerId = customerIdMap.computeIfAbsent(companyName, k -> {
            List<CustomerModel> customerModels = customerDao.checkCompanyName(k);
            if (customerModels.size() > 0) {
                return customerModels.get(0).getId();
            }
            return null;
        });
        if (customerId != null) {
            customer.setId(customerId);
            customer.setLastUpdatorId(1);
            customer.setLastUpdateTime(DateTime.now());
            customerDao.updateById(customer);
        } else {
            if (customer.getAddressCode() == null)
                customer.setAddressCode("");
            if (customer.getTaxpayerId() == null)
                customer.setTaxpayerId("");
            if (customer.getCreditCode() == null)
                customer.setCreditCode("");
            customer.setOwner("");
            customer.setRegisterTime(new Date(2000 - 1900, 1 - 1, 1));
            customer.setHasDevAccount(false);
            customer.setHighTec(false);
            customer.setFrom(0);
            customer.setCreatorId(1);
            customer.setLastUpdatorId(1);
            customer.setCreatorTime(DateTime.now());
            customer.setLastUpdateTime(DateTime.now());
            customerDao.insert(customer);
        }

    }

    /**
     * 同步客户信息
     */
    public void synCustomer() {

        Integer spiltCount = chuanYunConfig.getSpiltCount();
        LoadBizObjectsRequest request = new LoadBizObjectsRequest();
        request.setActionName(ChuanYunConstant.ACTION_NAME_LOAD_BIZ_OBJECTS);
        request.setSchemaCode("D000001Client");

        ChuanYunMatcher andMatcher = new ChuanYunMatcher();
        andMatcher.setType("And");
        andMatcher.setMatchers(new ArrayList<>());

        ChuanYunMatcher chuanYunMatcher = new ChuanYunMatcher();
        chuanYunMatcher.setType("Item");
        chuanYunMatcher.setName("Status");
        chuanYunMatcher.setOperator(2);
        chuanYunMatcher.setValue("1");
        andMatcher.getMatchers().add(chuanYunMatcher);

        chuanYunMatcher = new ChuanYunMatcher();
        chuanYunMatcher.setType("Item");
        chuanYunMatcher.setName("CreatedTime");
        chuanYunMatcher.setOperator(4); //Operator运算符：0 =大于，1=大于等于，2=等于，3=小于等于，4=小于，5=不等于，6=在某个范围内，7=不在某个范围内。
        chuanYunMatcher.setValue(createdTime);
        andMatcher.getMatchers().add(chuanYunMatcher);

        ChuanYunFilter chuanYunFilter = new ChuanYunFilter();
        chuanYunFilter.setMatcher(andMatcher);
        chuanYunFilter.setReturnItems(new ArrayList<>());
        chuanYunFilter.setSortByCollection(new ArrayList<>());
        chuanYunFilter.setRequireCount(true);

        Integer totalCount = spiltCount;
        Integer fromRowNum = 0;
        cyCustomerDao.deleteH3Customer();
        while (fromRowNum < totalCount) {
            chuanYunFilter.setFromRowNum(fromRowNum);
            chuanYunFilter.setToRowNum(fromRowNum + spiltCount);
            request.setFilter(JsonUtils.objectToJson(chuanYunFilter));
            ChuanYunResponse<LoadBizObjectsData<Map>> response = loadBizObjects(request);
            if (response.getSuccessful()) {
                totalCount = response.getReturnData().getTotalCount();
                List<Map> customerList = response.getReturnData().getBizObjectArray();
                for (Map cMap : customerList
                ) {
                    updateCustomer(cMap);
                    cMap.put("synDataDateTime", new Date());
//                    amap.put("clientName", amap.get("Name").toString()
//                            .replace(" ", "")
//                            .replace("（", "(")
//                            .replace("）", ")"));
//                    List<String> approvers = (List<String>) amap.get("Approvers");
                    List<String> f0000038s = (List<String>) cMap.get("F0000038");
//                    if (approvers != null && approvers.size() > 0) {
//                        amap.put("ApproverName", approvers.get(0));
//                    }
                    cMap.put("SalesAssistant", null);
                    if (f0000038s != null && f0000038s.size() > 0) {
                        cMap.put("F0000038Name", f0000038s.get(0));
                    }
                }
                cyCustomerDao.addList(customerList);
                fromRowNum += spiltCount;
            } else {
                break;
            }
        }
    }
}
