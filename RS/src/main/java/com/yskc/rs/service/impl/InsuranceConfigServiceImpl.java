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
//        // ??????
//        if (model.getId() == null) {
//            model.setCreateTime(new Date());
//            model.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//            model.setMsCreatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//            // ???????????????????????????????????????????????????
//            List<InsuranceConfigModel> containStartDate = insuranceConfigDao.queryStartDate(startMonth, companyId,
//                    deptId, enumber, 0);
//            if (containStartDate.size() != 0) {
//                throw new OwnerException(ErrorEnum.INSURANCECONFIG_STARTMONTH_EXSIT);
//            }
//            // ???????????????????????????/??????/?????????????????????
//            List<InsuranceConfigEntity> insuranceConfigList = insuranceConfigDao
//                    .queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(companyId, deptId, enumber);
//            if (insuranceConfigList.size() == 0) {
//                insuranceConfigDao.insert(model);
//                updateInsurance(model);
//            } else if (insuranceConfigList.size() == 1) {
//                InsuranceConfigEntity insuranceConfigModel = insuranceConfigList.get(0);
//                // ????????????????????????????????????
//                if (insuranceConfigModel.getStartMonth().compareTo(model.getStartMonth()) == -1) {
//                    // ????????????????????????,?????????'????????????????????????????????????'
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(model.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    // ????????????????????????????????????(???????????????-1),???update
//                    insuranceConfigModel.setEndMonth(cal.getTime());
//                    insuranceConfigDao.updateEndMonth(insuranceConfigModel);
//
//                    // ????????????model??????????????????null????????????????????????
//                    model.setEndMonth(null);
//                    insuranceConfigDao.insert(model);
//                    updateInsurance(model);
//                    updateInsurance(insuranceConfigModel);
//                } else {
//                    // ????????????????????????????????????
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(insuranceConfigModel.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    // ???????????????????????????(???????????????-1)
//                    model.setEndMonth(cal.getTime());
//                    insuranceConfigDao.insert(model);
//                    updateInsurance(model);
//                }
//            } else {
//                // ???????????????????????????????????????????????????????????????
//                List<InsuranceConfigEntity> containStartAndEnd = insuranceConfigDao.queryContainStartAndEnd(startMonth,
//                        companyId, deptId, enumber, 0);
//                // ????????????
//                if (containStartAndEnd.size() != 0) {
//                    InsuranceConfigEntity insuranceConfigEntity = containStartAndEnd.get(0);
//                    // ??????????????????????????????????????????????????????
//                    model.setEndMonth(insuranceConfigEntity.getEndMonth());
//                    // ???????????????-1??????????????????????????????
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(model.getStartMonth());
//                    cal.add(Calendar.MONTH, -1);
//                    insuranceConfigEntity.setEndMonth(cal.getTime());
//                    insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                    insuranceConfigDao.insert(model);
//                    updateInsurance(insuranceConfigEntity);
//                    updateInsurance(model);
//                } else {
//                    // ???????????????,??????????????????????????????,?????????????????????
//                    InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList.get(0);
//                    // ????????????????????????????????????,??????????????????????????????????????????,??????????????????????????????
//                    if (insuranceConfigEntity.getStartMonth().compareTo(model.getStartMonth()) == -1) {
//                        // ??????????????????(??????????????????)
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
//                        // ??????????????????????????????,??????????????????????????????
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
//            // ??????
//            // ???????????????????????????????????????????????????
//            List<InsuranceConfigModel> containStartDate = insuranceConfigDao.queryStartDate(startMonth, companyId,
//                    deptId, enumber, model.getId());
//            if (containStartDate.size() != 0) {
//                throw new OwnerException(ErrorEnum.INSURANCECONFIG_STARTMONTH_EXSIT);
//            }
//            // ????????????????????????/??????/?????????????????????
//            List<InsuranceConfigEntity> insuranceConfigList = insuranceConfigDao
//                    .queryInsuranceConfigByCompanyIdAndDeptIdAndEnumber(companyId, deptId, enumber);
//            // ?????????????????????????????????????????????(???????????????????????????????????????,???????????????)
//            if (model.getId().equals(insuranceConfigList.get(0).getId())) {
//                // ????????????????????????????????????????????????
//                // ?????????????????????????????????
//                Date endM = new Date();
//                if (model.getEndMonth() != null) {
//                    endM = model.getEndMonth();
//                }
//
//                if (model.getStartMonth().compareTo(endM) == -1) {
//                    insuranceConfigDao.updateEndMonth(model);
//                    updateInsurance(model);
//                } else {
//                    // ?????????????????????????????????
//                    // ??????????????????????????????????????????
//                    List<InsuranceConfigEntity> containStartAndEnd = insuranceConfigDao
//                            .queryContainStartAndEnd(startMonth, companyId, deptId, enumber, model.getId());
//                    // ??????list?????????0,??????????????????????????????
//                    if (containStartAndEnd.size() == 0) {
//                        InsuranceConfigEntity insuranceConfigEntity = insuranceConfigList
//                                .get(insuranceConfigList.size() - 1);
//                        // ???????????????????????????????????????
//                        model.setEndMonth(insuranceConfigEntity.getEndMonth());
//                        insuranceConfigEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//                        insuranceConfigEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
//                        insuranceConfigEntity.setLastUpdateTime(new Date());
//                        // ???????????????-1????????????????????????
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(model.getStartMonth());
//                        cal.add(Calendar.MONTH, -1);
//                        insuranceConfigEntity.setEndMonth(cal.getTime());
//                        insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//                        insuranceConfigDao.updateEndMonth(model);
//                        updateInsurance(insuranceConfigEntity);
//                        updateInsurance(model);
//                    } else {
//                        // ?????????????????????0( ?????????????????????????????????,??????get(0) )
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
//                // ???????????????????????????
//                // ????????????????????????????????????????????????????????????
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
//                    // ???????????????????????????????????????
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
//                // ????????????????????????????????????(?????????????????????????????????????????????,
//                InsuranceConfigEntity selectById = insuranceConfigDao.selectById(model.getId());
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(selectById.getStartMonth());
//                cal.add(Calendar.MONTH, -1);
//                Date endMonthDate = cal.getTime();
//                InsuranceConfigEntity insuranceConfigEntity = new InsuranceConfigEntity();
//                // ??????list,?????????????????????"?????????"
//                for (int i = 0; i < insuranceConfigList.size(); i++) {
//                    if (endMonthDate.compareTo(insuranceConfigList.get(i).getEndMonth()) == 0) {
//                        insuranceConfigEntity = insuranceConfigList.get(i);
//                        break;
//                    }
//                }
//                // ?????????????????????????????????????????????
//                insuranceConfigEntity.setEndMonth(model.getEndMonth());
//                TransactionStatus transactionStatus = TransactionUtils.newTransaction();
//                try {
//                    insuranceConfigDao.updateEndMonth(insuranceConfigEntity);
//
//                    // ????????????????????????????????????????????????,??????????????????????????? "?????????~?????????-1????????????~?????????" ??????
//                    Date editStartMonth = model.getStartMonth();
//                    List<InsuranceConfigEntity> queryContainStartAndEnd = insuranceConfigDao
//                            .queryContainStartAndEnd(editStartMonth, companyId, deptId, enumber, model.getId());
//                    InsuranceConfigEntity entity = new InsuranceConfigEntity();
//                    // ??????size!=0,??????????????????,endMonth??????null
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
//                        // ????????????
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
//                            // ?????????
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