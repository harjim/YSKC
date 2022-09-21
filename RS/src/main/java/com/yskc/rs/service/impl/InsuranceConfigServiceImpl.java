package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.rs.dao.SalaryDao;
import com.yskc.rs.entity.SalaryEntity;
import com.yskc.rs.service.InsuranceConfigService;
import org.springframework.stereotype.Service;

@Service
public class InsuranceConfigServiceImpl extends ServiceImpl<SalaryDao, SalaryEntity> implements InsuranceConfigService {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private InsuranceConfigDao insuranceConfigDao;
//
//    @Autowired
//    private SalaryDao salaryDao;
//
//    @Autowired
//    private DeptDao deptDao;
//    @Autowired
//    private EmployeeDao employeeDao;
//    @Autowired
//    private InsuranceDao insuranceDao;
//
//    @Override
//    public boolean saveInsuranceConfig(UserInfo userInfo, InsuranceConfigEntity model) throws OwnerException {
//
//        Integer companyId = model.getCompanyId();
//        Integer deptId = model.getDeptId();
//        String enumber = model.getEnumber();
//        Date startMonth = model.getStartMonth();
//        Date endMonth = model.getEndMonth();
//
//        BigDecimal oneHundred = new BigDecimal(100);
//
//        model.setEndowment(model.getEndowment().divide(oneHundred));
//        model.setMedical(model.getMedical().divide(oneHundred));
//        model.setUnemployment(model.getUnemployment().divide(oneHundred));
//        model.setInjury(model.getInjury().divide(oneHundred));
//        model.setMaternity(model.getMaternity().divide(oneHundred));
//        model.setHouse(model.getHouse().divide(oneHundred));
//        model.setEndowmentOfCom(model.getEndowmentOfCom().divide(oneHundred));
//        model.setMedicalOfCom(model.getMedicalOfCom().divide(oneHundred));
//        model.setUnemploymentOfCom(model.getUnemploymentOfCom().divide(oneHundred));
//        model.setInjuryOfCom(model.getInjuryOfCom().divide(oneHundred));
//        model.setMaternityOfCom(model.getMaternityOfCom().divide(oneHundred));
//        model.setHouseOfCom(model.getHouseOfCom().divide(oneHundred));
//        model.setLastUpdateTime(new Date());
//        model.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//        model.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//        // 新增
//        if (model.getId() == null) {
//            model.setCreateTime(new Date());
//            model.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//            model.setMsCreatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//            // 新增时查询是否已存在相同的开始月份
//            List<InsuranceConfigModel> containStartDate = insuranceConfigDao.queryStartDate(startMonth, companyId,
//                    deptId, enumber, 0);
//            if (containStartDate.size() != 0) {
//                throw new OwnerException(ErrorEnum.INSURANCECONFIG_STARTMONTH_EXSIT);
//            }
//            // 查询是否存在该员工/部门/公司的社保规则
//            List<InsuranceConfigEntity> insuranceConfigList = insuranceConfigDao
//                    .queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(companyId, deptId, enumber);
//            if (insuranceConfigList.size() == 0) {
//                insuranceConfigDao.insert(model);
//                updateInsurance(model);
//            } else if (insuranceConfigList.size() == 1) {
//                InsuranceConfigEntity insuranceConfigModel = insuranceConfigList.get(0);
//                // 如果新增的月份比原有的大
//                if (insuranceConfigModel.getStartMonth().compareTo(model.getStartMonth()) == -1) {
//                    // 将新增的开始月份,设置给'查询出来的数据的结束月份'
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(model.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    // 设置好原有的规则结束日期(新增的月份-1),并update
//                    insuranceConfigModel.setEndMonth(cal.getTime());
//                    insuranceConfigDao.updateEndMonth(insuranceConfigModel);
//
//                    // 将新增的model结束日期设为null表示至今然后插入
//                    model.setEndMonth(null);
//                    insuranceConfigDao.insert(model);
//                    updateInsurance(model);
//                    updateInsurance(insuranceConfigModel);
//                } else {
//                    // 如果新增的月份比原有的小
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(insuranceConfigModel.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    // 设置新增的结束日期(原有的月份-1)
//                    model.setEndMonth(cal.getTime());
//                    insuranceConfigDao.insert(model);
//                    updateInsurance(model);
//                }
//            } else {
//                // 通过新增的月份查询是否存在原有的月份区间内
//                List<InsuranceConfigEntity> containStartAndEnd = insuranceConfigDao.queryContainStartAndEnd(startMonth,
//                        companyId, deptId, enumber, 0);
//                // 如果存在
//                if (containStartAndEnd.size() != 0) {
//                    InsuranceConfigEntity insuranceConfigEntity = containStartAndEnd.get(0);
//                    // 将原有的结束月份设置给新增的结束月份
//                    model.setEndMonth(insuranceConfigEntity.getEndMonth());
//                    // 新增的月份-1设置给原有的结束月份
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(model.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    insuranceConfigEntity.setEndMonth(cal.getTime());
//                    insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                    insuranceConfigDao.insert(model);
//                    updateInsurance(insuranceConfigEntity);
//                    updateInsurance(model);
//                } else {
//                    // 如果不存在,将查询出来的所有规则,取第一条作比较
//                    InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList.get(0);
//                    // 如果新增的月份比第一条大,则该新增月份为最大的新增月份,否则是最小的新增月份
//                    if (insuranceConfigEntity.getStartMonth().compareTo(model.getStartMonth()) == -1) {
//                        // 取出最大一条(排序最后一条)
//                        InsuranceConfigEntity lastEntity = insuranceConfigList.get(insuranceConfigList.size() - 1);
//                        model.setEndMonth(lastEntity.getEndMonth());
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(model.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        lastEntity.setEndMonth(cal.getTime());
//                        insuranceConfigDao.updateEndMonth(lastEntity);
//                        insuranceConfigDao.insert(model);
//                        updateInsurance(lastEntity);
//                        updateInsurance(model);
//                    } else {
//                        // 将原有最小的新增月份,设置为新增的结束月份
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(insuranceConfigEntity.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        model.setEndMonth(cal.getTime());
//                        insuranceConfigDao.insert(model);
//                        updateInsurance(model);
//                    }
//                }
//            }
//
//        } else {
//            // 修改
//            // 修改时查询是否已存在相同的开始月份
//            List<InsuranceConfigModel> containStartDate = insuranceConfigDao.queryStartDate(startMonth, companyId,
//                    deptId, enumber, model.getId());
//            if (containStartDate.size() != 0) {
//                throw new OwnerException(ErrorEnum.INSURANCECONFIG_STARTMONTH_EXSIT);
//            }
//            // 查询出所有该员工/部门/公司的社保规则
//            List<InsuranceConfigEntity> insuranceConfigList = insuranceConfigDao
//                    .queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(companyId, deptId, enumber);
//            // 如果修改的开始月份是最小的那条(查询出来的月份排序从小到大,第一条最小)
//            if (model.getId().equals(insuranceConfigList.get(0).getId())) {
//                // 判断修改的开始月份有没大于结束月
//                // 修改的开始月小于结束月
//                Date endM = new Date();
//                if (model.getEndMonth() != null) {
//                    endM = model.getEndMonth();
//                }
//
//                if (model.getStartMonth().compareTo(endM) == -1) {
//                    insuranceConfigDao.updateEndMonth(model);
//                    updateInsurance(model);
//                } else {
//                    // 修改的开始月大于结束月
//                    // 查询修改的开始月在哪个区间内
//                    List<InsuranceConfigEntity> containStartAndEnd = insuranceConfigDao
//                            .queryContainStartAndEnd(startMonth, companyId, deptId, enumber, model.getId());
//                    // 如果list长度为0,则新增的月份为最大月
//                    if (containStartAndEnd.size() == 0) {
//                        InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList
//                                .get(insuranceConfigList.size() - 1);
//                        // 最大结束月给到修改的结束月
//                        model.setEndMonth(insuranceConfigEntity.getEndMonth());
//                        insuranceConfigEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                        insuranceConfigEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                        insuranceConfigEntity.setLastUpdateTime(new Date());
//                        // 修改开始月-1给到最大的结束月
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(model.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        insuranceConfigEntity.setEndMonth(cal.getTime());
//                        insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                        insuranceConfigDao.updateEndMonth(model);
//                        updateInsurance(insuranceConfigEntity);
//                        updateInsurance(model);
//                    } else {
//                        // 如果长度不等于0( 其实只会查询一条符合的,所以get(0) )
//                        InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList.get(0);
//                        model.setEndMonth(insuranceConfigEntity.getEndMonth());
//                        insuranceConfigEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                        insuranceConfigEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                        insuranceConfigEntity.setLastUpdateTime(new Date());
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(model.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        insuranceConfigEntity.setEndMonth(cal.getTime());
//                        insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                        insuranceConfigDao.updateEndMonth(model);
//                        updateInsurance(insuranceConfigEntity);
//                        updateInsurance(model);
//                    }
//                }
//            } else if (model.getId().equals(insuranceConfigList.get(insuranceConfigList.size() - 1).getId())) {
//                // 修改月份最大的那条
//                // 如果修改的开始月份比倒数第二条的开始月大
//                if (model.getStartMonth()
//                        .compareTo(insuranceConfigList.get(insuranceConfigList.size() - 2).getStartMonth()) == 1) {
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(model.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList
//                            .get(insuranceConfigList.size() - 2);
//                    insuranceConfigEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                    insuranceConfigEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                    insuranceConfigEntity.setLastUpdateTime(new Date());
//                    insuranceConfigEntity.setEndMonth(cal.getTime());
//                    insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                    insuranceConfigDao.updateEndMonth(model);
//                    updateInsurance(insuranceConfigEntity);
//                    updateInsurance(model);
//                } else {
//                    // 修改的开始月别倒数第二条小
//                    InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList
//                            .get(insuranceConfigList.size() - 2);
//                    insuranceConfigEntity.setEndMonth(model.getEndMonth());
//                    insuranceConfigEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                    insuranceConfigEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                    insuranceConfigEntity.setLastUpdateTime(new Date());
//                    List<InsuranceConfigEntity> containStartAndEnd = insuranceConfigDao
//                            .queryContainStartAndEnd(startMonth, companyId, deptId, enumber, model.getId());
//                    if (containStartAndEnd.size() > 0) {
//                        InsuranceConfigEntity entity = containStartAndEnd.get(0);
//                        model.setEndMonth(entity.getEndMonth());
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(model.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        entity.setEndMonth(cal.getTime());
//                        entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                        entity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                        entity.setLastUpdateTime(new Date());
//                        insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                        insuranceConfigDao.updateEndMonth(entity);
//                        insuranceConfigDao.updateEndMonth(model);
//                        updateInsurance(insuranceConfigEntity);
//                        updateInsurance(entity);
//                        updateInsurance(model);
//                    } else {
//                        InsuranceConfigEntity firstEntity = insuranceConfigList.get(0);
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(firstEntity.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        model.setEndMonth(cal.getTime());
//                        insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                        insuranceConfigDao.updateEndMonth(model);
//                        updateInsurance(insuranceConfigEntity);
//                        updateInsurance(model);
//                    }
//                }
//            } else {
//                // 如果修改的不是最小那一条(把修改的结束月份给到它的上一条,
//                InsuranceConfigEntity selectById = insuranceConfigDao.selectById(model.getId());
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(selectById.getStartMonth());
//                cal.add(Calendar.MONTH, -1);
//                Date endMonthDate = cal.getTime();
//                InsuranceConfigEntity insuranceConfigEntity = new InsuranceConfigEntity();
//                // 遍历list,查出需要修改的"上一条"
//                for (int i = 0; i < insuranceConfigList.size(); i++) {
//                    if (endMonthDate.compareTo(insuranceConfigList.get(i).getEndMonth()) == 0) {
//                        insuranceConfigEntity = insuranceConfigList.get(i);
//                        break;
//                    }
//                }
//                // 把修改的结束月份给到它的上一条
//                insuranceConfigEntity.setEndMonth(model.getEndMonth());
//                TransactionStatus transactionStatus = TransactionUtils.newTransaction();
//                try {
//                    insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//
//                    // 通过修改的开始月去查询在哪个区间,将查出来的区间改为 "查开始~改开始-1，改开始~查结束" 两条
//                    Date editStartMonth = model.getStartMonth();
//                    List<InsuranceConfigEntity> queryContainStartAndEnd = insuranceConfigDao
//                            .queryContainStartAndEnd(editStartMonth, companyId, deptId, enumber, model.getId());
//                    InsuranceConfigEntity entity = new InsuranceConfigEntity();
//                    // 如果size!=0,不是最后一条,endMonth不为null
//                    if (queryContainStartAndEnd.size() != 0) {
//                        entity = queryContainStartAndEnd.get(0);
//                        entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                        entity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                        entity.setLastUpdateTime(new Date());
//                        model.setEndMonth(entity.getEndMonth());
//                        Calendar cal2 = Calendar.getInstance();
//                        cal2.setTime(model.getStartMonth());
//                        cal2.add(Calendar.MONTH, -1);
//                        entity.setEndMonth(cal2.getTime());
//                        insuranceConfigDao.updateEndMonth(entity);
//                        insuranceConfigDao.updateEndMonth(model);
//                        updateInsurance(insuranceConfigEntity);
//                        updateInsurance(entity);
//                        updateInsurance(model);
//                    } else {
//                        // 最后一条
//                        if (editStartMonth.compareTo(insuranceConfigList.get(0).getStartMonth()) == 1) {
//                            List<InsuranceConfigEntity> newList = insuranceConfigDao
//                                    .queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(companyId, deptId, enumber);
//                            entity = newList.get(newList.size() - 1);
//                            model.setEndMonth(entity.getEndMonth());
//                            Calendar cal2 = Calendar.getInstance();
//                            cal2.setTime(model.getStartMonth());
//                            cal2.add(Calendar.MONTH, -1);
//                            entity.setEndMonth(cal2.getTime());
//                            entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                            entity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                            entity.setLastUpdateTime(new Date());
//                            insuranceConfigDao.updateEndMonth(entity);
//                            insuranceConfigDao.updateEndMonth(model);
//                            updateInsurance(entity);
//                            updateInsurance(model);
//                        } else {
//                            // 第一条
//                            InsuranceConfigEntity firstEntity = insuranceConfigList.get(0);
//                            Calendar cal2 = Calendar.getInstance();
//                            cal2.setTime(firstEntity.getStartMonth());
//                            cal2.add(Calendar.MONTH, -1);
//                            model.setEndMonth(cal2.getTime());
//                            insuranceConfigDao.updateEndMonth(model);
//                            updateInsurance(model);
//                        }
//                    }
//
//                    TransactionUtils.commit(transactionStatus);
//                } catch (Exception ex) {
//                    TransactionUtils.rollback(transactionStatus);
//                    throw new OwnerException(ErrorEnum.FAIL);
//                }
//
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public List<InsuranceConfigEntity> queryInsuranceConfig(UserInfo userInfo, Integer companyId, Integer deptId,
//                                                            String enumber) {
//
//        BigDecimal oneHundred = new BigDecimal(100);
//
//        List<InsuranceConfigEntity> insuranceConfigList = insuranceConfigDao
//                .queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(companyId, deptId, enumber);
//        for (int i = 0; i < insuranceConfigList.size(); i++) {
//            insuranceConfigList.get(i).setEndowment(insuranceConfigList.get(i).getEndowment().multiply(oneHundred));
//            insuranceConfigList.get(i).setMedical(insuranceConfigList.get(i).getMedical().multiply(oneHundred));
//            insuranceConfigList.get(i)
//                    .setUnemployment(insuranceConfigList.get(i).getUnemployment().multiply(oneHundred));
//            insuranceConfigList.get(i).setInjury(insuranceConfigList.get(i).getInjury().multiply(oneHundred));
//            insuranceConfigList.get(i).setMaternity(insuranceConfigList.get(i).getMaternity().multiply(oneHundred));
//            insuranceConfigList.get(i).setHouse(insuranceConfigList.get(i).getHouse().multiply(oneHundred));
//            insuranceConfigList.get(i)
//                    .setEndowmentOfCom(insuranceConfigList.get(i).getEndowmentOfCom().multiply(oneHundred));
//            insuranceConfigList.get(i)
//                    .setMedicalOfCom(insuranceConfigList.get(i).getMedicalOfCom().multiply(oneHundred));
//            insuranceConfigList.get(i)
//                    .setUnemploymentOfCom(insuranceConfigList.get(i).getUnemploymentOfCom().multiply(oneHundred));
//            insuranceConfigList.get(i).setInjuryOfCom(insuranceConfigList.get(i).getInjuryOfCom().multiply(oneHundred));
//            insuranceConfigList.get(i)
//                    .setMaternityOfCom(insuranceConfigList.get(i).getMaternityOfCom().multiply(oneHundred));
//            insuranceConfigList.get(i).setHouseOfCom(insuranceConfigList.get(i).getHouseOfCom().multiply(oneHundred));
//        }
//
//        return insuranceConfigList;
//    }
}