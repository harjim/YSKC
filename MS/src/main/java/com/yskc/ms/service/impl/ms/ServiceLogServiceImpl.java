package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.ServiceLogDao;
import com.yskc.ms.dao.ms.ServiceUserDao;
import com.yskc.ms.entity.ms.QueryServiceModel;
import com.yskc.ms.entity.ms.ServiceLog;
import com.yskc.ms.entity.ms.ServiceUser;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.OtherUsersModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.servicelog.*;
import com.yskc.ms.service.ms.ServiceLogService;
import com.yskc.ms.utils.TransactionUtils;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.util.*;

/**
 * Created by hck
 * on 2020/5/5 14:46
 */
@Service
public class ServiceLogServiceImpl implements ServiceLogService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ServiceLogDao serviceLogDao;
    @Autowired
    private ServiceUserDao serviceUserDao;
    @Autowired
    private MsConfig msConfig;

    @Override
    public PageModel<List<ServiceInfoModel>> queryByParams(QueryServiceModel model, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("sL.createTime");
        }
        List<ServiceInfoModel> list = serviceLogDao.queryByParams(page, model, dataPerm);
        loadData(list, false);
        return PageUtils.buildPageResult(page, list, serviceLogDao.getStat(model, dataPerm));
    }


    @Override
    public Boolean addServiceLog(ServiceAddLogModel modal, Integer id) throws OwnerException {
        Date date = new Date();
        ServiceLog serviceLog = new ServiceLog();
        BeanUtil.copyProperties(modal, serviceLog);
        serviceLog.setCreateTime(date);
        serviceLog.setCreatorId(id);
        serviceLog.setLastUpdateTime(date);
        serviceLog.setLastUpdatorId(id);
        if (modal.getChangeStatus() == 1) {
            serviceLog.setStatus(1);
            serviceLog.setSubmitDate(date);
        } else {
            serviceLog.setStatus(0);
        }
        if (CollectionUtils.isEmpty(modal.getFilePaths())) {
            throw new OwnerException("请上传服务记录附件！");
        }
        serviceLog.setFilePath(String.join(",", modal.getFilePaths()));
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            //插入主要服务人员
            serviceLogDao.insert(serviceLog);
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.setServiceId(serviceLog.getId());
            serviceUser.setCreateTime(date);
            serviceUser.setCreatorId(id);
            serviceUser.setLastUpdateTime(date);
            serviceUser.setLastUpdatorId(id);
            serviceUser.setUserId(modal.getMainStaffId() != null ? modal.getMainStaffId() : 0);
            serviceUser.setmType(0);
            serviceUserDao.insert(serviceUser);
            List<ServiceUser> list = getUsers(modal, id, serviceLog.getId());
            if (list != null && list.size() > 0) {
                serviceUserDao.insertServiceUsers(list);
            }
            TransactionUtils.commit(DataSourceEnum.MS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());

        }
    }

    @Override
    public Boolean editServiceLog(ServiceLogModel serviceLogModel, Integer id) throws OwnerException {
        ServiceLog serviceLog = serviceLogDao.selectById(serviceLogModel.getId());
        Date date = new Date();
        if (serviceLog != null) {
            BeanUtil.copyProperties(serviceLogModel, serviceLog);
            serviceLog.setLastUpdateTime(date);
            serviceLog.setLastUpdatorId(id);
            TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
            try {
                serviceLogDao.updateById(serviceLog);
                serviceUserDao.delByServiceId(serviceLogModel.getId());
                //插入主要服务人员
                ServiceUser serviceUser = new ServiceUser();
                serviceUser.setServiceId(serviceLog.getId());
                serviceUser.setCreateTime(date);
                serviceUser.setCreatorId(id);
                serviceUser.setLastUpdateTime(date);
                serviceUser.setLastUpdatorId(id);
                serviceUser.setUserId(serviceLogModel.getMainStaffId());
                serviceUser.setmType(0);
                serviceUserDao.insert(serviceUser);
                if (!StringUtils.isEmpty(serviceLogModel.getStaffIds())) {
                    List<ServiceUser> list = new ArrayList<>();
                    List<String> list1 = Arrays.asList(serviceLogModel.getStaffIds().split(","));
                    for (String otherId : list1) {
                        ServiceUser serviceUser1 = new ServiceUser();
                        serviceUser1.setServiceId(serviceLog.getId());
                        serviceUser1.setCreateTime(date);
                        serviceUser1.setCreatorId(id);
                        serviceUser1.setLastUpdateTime(date);
                        serviceUser1.setLastUpdatorId(id);
                        serviceUser1.setUserId(Integer.parseInt(otherId));
                        serviceUser1.setmType(1);
                        list.add(serviceUser1);
                    }
                    serviceUserDao.insertServiceUsers(list);
                }
                TransactionUtils.commit(DataSourceEnum.MS, status);
                return true;
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.MS, status);
                throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());

            }
        }
        return false;
    }

    @Override
    public Boolean delServiceLog(Integer id) throws OwnerException {
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            serviceLogDao.deleteById(id);
            serviceUserDao.delByServiceId(id);
            TransactionUtils.commit(DataSourceEnum.MS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());

        }

    }

    private List<ServiceUser> getUsers(ServiceAddLogModel modal, Integer id, Integer serviceId) {
        List<ServiceUser> list = new ArrayList<>();
        Date date = new Date();
        if (!StringUtils.isEmpty(modal.getStaffIds())) {
            List<String> list1 = Arrays.asList(modal.getStaffIds().split(","));
            for (String otherId : list1) {

                ServiceUser serviceUser = new ServiceUser();
                serviceUser.setServiceId(serviceId);
                serviceUser.setCreateTime(date);
                serviceUser.setCreatorId(id);
                serviceUser.setLastUpdateTime(date);
                serviceUser.setLastUpdatorId(id);
                serviceUser.setUserId(Integer.parseInt(otherId));
                serviceUser.setmType(1);
                list.add(serviceUser);
            }
            return list;
        }
        return null;
    }

    @Override
    public Boolean updateStatus(ServiceLogModel model, Integer id) throws OwnerException {
        ServiceLog serviceLog = serviceLogDao.selectById(model.getId());
        if (serviceLog != null && model.getChangeStatus() != null) {
            String str = !StringUtils.isEmpty(model.getAuditOpinion()) ? model.getAuditOpinion() : "";
            switch (model.getChangeStatus()) {
                case 0://保存草稿
                    model.setStatus(0);
                    return updateServiceLog(model, id);

                case 1://提交
                    model.setSubmitDate(new Date());
                    model.setStatus(1);
                    return updateServiceLog(model, id);
                case 2://不通过
                    serviceLog.setStatus(2);
                    serviceLog.setAuditOpinion(str);
                    serviceLog.setLastUpdatorId(id);
                    serviceLog.setLastUpdateTime(new Date());
                    return serviceLogDao.updateById(serviceLog) > 0;
                case 3://通过
                    serviceLog.setStatus(3);
                    serviceLog.setLastUpdatorId(id);
                    serviceLog.setAuditOpinion(str);
                    serviceLog.setLastUpdateTime(new Date());
                    return serviceLogDao.updateById(serviceLog) > 0;
                default:
                    return true;
            }

        }
        throw new OwnerException("请先添加或保存记录后再操作");
    }

    private Boolean updateServiceLog(ServiceLogModel serviceLogModel, Integer id) throws OwnerException {
        ServiceLog serviceLog = serviceLogDao.selectById(serviceLogModel.getId());
        Date date = new Date();
        if (serviceLog != null) {
            BeanUtil.copyProperties(serviceLogModel, serviceLog);
            if (CollectionUtils.isEmpty(serviceLogModel.getFilePaths())) {
                throw new OwnerException("请上传服务记录附件！");
            }
            serviceLog.setFilePath(String.join(",", serviceLogModel.getFilePaths()));
            serviceLog.setLastUpdateTime(date);
            serviceLog.setLastUpdatorId(id);
            TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
            try {
                serviceLogDao.updateById(serviceLog);
                serviceUserDao.delByServiceId(serviceLogModel.getId());
                //插入主要服务人员
                ServiceUser serviceUser = new ServiceUser();
                serviceUser.setServiceId(serviceLog.getId());
                serviceUser.setCreateTime(date);
                serviceUser.setCreatorId(id);
                serviceUser.setLastUpdateTime(date);
                serviceUser.setLastUpdatorId(id);
                serviceUser.setUserId(serviceLogModel.getMainStaffId());
                serviceUser.setmType(0);
                serviceUserDao.insert(serviceUser);
                if (!StringUtils.isEmpty(serviceLogModel.getStaffIds())) {
                    List<ServiceUser> list = new ArrayList<>();
                    List<String> list1 = Arrays.asList(serviceLogModel.getStaffIds().split(","));
                    for (String otherId : list1) {
                        ServiceUser serviceUser1 = new ServiceUser();
                        serviceUser1.setServiceId(serviceLog.getId());
                        serviceUser1.setCreateTime(date);
                        serviceUser1.setCreatorId(id);
                        serviceUser1.setLastUpdateTime(date);
                        serviceUser1.setLastUpdatorId(id);
                        serviceUser1.setUserId(Integer.parseInt(otherId));
                        serviceUser1.setmType(1);
                        list.add(serviceUser1);
                    }
                    serviceUserDao.insertServiceUsers(list);
                }
                TransactionUtils.commit(DataSourceEnum.MS, status);
                return true;
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.MS, status);
                throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());

            }
        }
        return false;
    }

    @Override
    public ServiceLog getLogById(Integer id) {
        return serviceLogDao.selectById(id);
    }

    @Override
    public void exportServiceLog(OutputStream out, QueryServiceModel model, DataPermModel dataPerm) throws OwnerException {
        List<ExportServiceInfoModel> list = serviceLogDao.getExportData(model, dataPerm);
        loadData(list, true);
        Map<String, String> serviceTypeMap = new HashMap<>();
        serviceTypeMap.put("0", "现场对接");
        serviceTypeMap.put("1", "电话沟通");
        serviceTypeMap.put("2", "其他");
        list.forEach(item -> item.setServiceTypeStr(serviceTypeMap.get(item.getServiceType())));
        Map<String, Object> data = new HashMap<>(1);
        data.put("list", list);
        YsExcelUtils.generalReport(data, msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "客户服务记录模板.xlsx", workbook -> {
            try {
                workbook.write(out);
                workbook.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 装载数据
     *
     * @param list   数据源
     * @param export 是否导出
     */
    private void loadData(List<? extends ServiceInfoModel> list, Boolean export) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<Integer> serviceIds = new ArrayList<>();
        list.forEach(item -> {
            if (!export && !StringUtils.isEmpty(item.getFilePath())) {
                item.setFilePaths(Arrays.asList(item.getFilePath().split(",")));
            }
            serviceIds.add(item.getId());
        });

        List<ServiceUserModel> userList = serviceUserDao.getUserByServiceIds(serviceIds);
        Map<Integer, List<ServiceUserModel>> userMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userList)) {
            userList.forEach(item -> {
                Integer key = item.getServiceId();
                if (!userMap.containsKey(key)) {
                    userMap.put(key, new ArrayList<>());
                }
                userMap.get(key).add(item);
            });
        }
        list.forEach(item -> {
            List<ServiceUserModel> tempUserList = userMap.get(item.getId());
            String ownUser = "";
            Integer mainId = null;
            List<String> otherUser = new ArrayList<>();
            List<OtherUsersModel> list1 = new ArrayList<>();
            if (!CollectionUtils.isEmpty(tempUserList)) {
                for (ServiceUserModel tempUser : tempUserList) {
                    if (tempUser.getmType().equals(0)) {
                        ownUser = tempUser.getRealName();
                        mainId = tempUser.getId();

                    } else {
                        if (export) {
                            OtherUsersModel otherUsersModel = new OtherUsersModel();
                            otherUsersModel.setId(tempUser.getId());
                            otherUsersModel.setRealName(tempUser.getRealName());
                            otherUsersModel.setUserName(tempUser.getUserName());
                            list1.add(otherUsersModel);
                        }

                        otherUser.add(tempUser.getRealName());
                    }
                }
                item.setMainUserId(mainId);
                item.setOtherUsersModels(list1);
                item.setMainStaffName(ownUser);
                item.setOtherStaffName(String.join(",", otherUser));
            }
        });
    }

}
