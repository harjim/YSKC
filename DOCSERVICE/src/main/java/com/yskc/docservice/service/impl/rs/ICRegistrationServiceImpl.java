package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.dao.rs.ICRegistrationDao;
import com.yskc.docservice.entity.rs.ICRegistrationEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ICRegistrationExcel;
import com.yskc.docservice.service.rs.ICRegistrationService;
import com.yskc.docservice.utils.TransactionUtils;
import fr.opensagres.xdocreport.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @DateTime: 2021/11/2 14:44
 * @Description:
 * @author: hsx
 */
@Service
public class ICRegistrationServiceImpl implements ICRegistrationService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICRegistrationDao iCRegistrationDao;

    @Override
    public Boolean importICRegistration(RsUserInfo info, List<ICRegistrationExcel> list) throws OwnerException{
        Date date = new Date();
        String message = "";
        if (list.size() > 0) {
            //判断要导入的数据中是否存在重复的登记号
            Map<String, ICRegistrationEntity> registerNoMap = new HashMap<>();
            Map<String, ICRegistrationEntity> certificateNoMap = new HashMap<>();
            for (ICRegistrationExcel excel : list) {
                String source = excel.getSource();
                if (!(Objects.equals(source,"自主") || Objects.equals(source,"购买"))) {
                    message = MessageFormat.format("来源：{0} 填写错误，请按格式填写", source);
                    throw new OwnerException(message);
                }
                String registerNo = excel.getRegisterNo();
                if (registerNoMap.containsKey(registerNo)) {
                    message = MessageFormat.format("存在重复登记号：{0}，请修改后导入", registerNo);
                    throw new OwnerException(message);
                }
                registerNoMap.put(registerNo,null);
                String certificateNo = excel.getCertificateNo();
                if (StringUtils.isEmpty(certificateNo)) {
                    continue;
                }
                if (certificateNoMap.containsKey(certificateNo)) {
                    message = MessageFormat.format("存在重复证书号：{0}，请修改后导入", certificateNo);
                    throw new OwnerException(message);
                }
                certificateNoMap.put(certificateNo,null);
            }
            List<ICRegistrationEntity> entities = iCRegistrationDao.getListByCompany(info.getCompanyId());
            registerNoMap.clear();
            certificateNoMap.clear();
            if (!CollectionUtils.isEmpty(entities)) {
                registerNoMap = entities.stream().collect(Collectors.toMap(e -> e.getRegisterNo(), e -> e));
                certificateNoMap = entities.stream().filter(a ->
                    (!StringUtils.isEmpty(a.getCertificateNo()))).collect(Collectors.toMap(e -> e.getCertificateNo(), e -> e));
            }
            List<ICRegistrationEntity> updateList = new ArrayList<>();
            List<ICRegistrationEntity> insertList = new ArrayList<>();
            //查询公司计算机软件著作,根据导入数据中登记号是否存在判断做更新操作还是修改操作
            for (ICRegistrationExcel excel : list) {
                ICRegistrationEntity entity = registerNoMap.get(excel.getRegisterNo());
                String certificateNo = excel.getCertificateNo();
                String registerNo = excel.getRegisterNo();
                if (entity != null) {
                    //更新计算机软件著作表(不更新登记号)
                    ICRegistrationEntity icRegistration = certificateNoMap.get(certificateNo);
                    if (null != icRegistration && !icRegistration.getRegisterNo().equals(registerNo)) {
                        message = MessageFormat.format("证书号: {0} 在系统中已存在，请修改后导入！", certificateNo);
                        throw new OwnerException(message);
                    }
                    entity.update(info.getId(), info.getMsUserId(), date);
                    convertExcel2Entity(excel,entity);
                    updateList.add(entity);
                } else {
                    //添加计算机软件著作
                    if (null != certificateNo && certificateNoMap.containsKey(certificateNo)) {
                        message = MessageFormat.format("证书号: {0} 在系统中已存在，请修改后导入！", certificateNo);
                        throw new OwnerException(message);
                    }
                    entity = new ICRegistrationEntity();
                    convertExcel2Entity(excel,entity);
                    entity.create(info.getId(),info.getMsUserId(),date);
                    insertList.add(entity);
                }
            }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                if (insertList.size() > 0) {
                    iCRegistrationDao.insertICs(insertList, info.getCompanyId());
                }
                if (updateList.size() > 0) {
                    iCRegistrationDao.updateICs(updateList, info.getCompanyId());
                }
                TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
                logger.error("importICRegistration", ex);
            }
        } else {
            message = "文件上传失败,请重新上传";
            throw new OwnerException(message);
        }
        return true;
    }
    /**
     * 将excel类转换为存入数据库的电路设计实体类
     * @param excel
     * @param entity
     */
    private void convertExcel2Entity(ICRegistrationExcel excel, ICRegistrationEntity entity) {
        entity.setDesginName(excel.getDesginName());
        entity.setOwnerName(excel.getOwnerName());
        entity.setOwnerAddress(excel.getOwnerAddress());
        entity.setApplyDate(excel.getApplyDate());
        entity.setIssueDate(excel.getIssueDate());
        entity.setCertificateNo(excel.getCertificateNo());
        entity.setRegisterNo(excel.getRegisterNo());
        if (Objects.equals(excel.getSource(),"自主")) {
            entity.setSource(0);
        }
        if (Objects.equals(excel.getSource(),"购买")) {
            entity.setSource(1);
        }
    }
}
