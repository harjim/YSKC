package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ICRegistrationDao;
import com.yskc.rs.entity.ICRegistrationEntity;
import com.yskc.rs.models.ICRegistrationModel;
import com.yskc.rs.models.QueryICRegistrationModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ICRegistrationExcel;
import com.yskc.rs.service.ICRegistrationService;
import com.yskc.rs.utils.TransactionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    public Boolean importICRegistration(UserInfo info, List<ICRegistrationExcel> list) throws OwnerException{
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
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                if (insertList.size() > 0) {
                    iCRegistrationDao.insertICs(insertList, info.getCompanyId());
                }
                if (updateList.size() > 0) {
                    iCRegistrationDao.updateICs(updateList, info.getCompanyId());
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("importICRegistration", ex);
            }
        } else {
            message = "文件上传失败,请重新上传";
            throw new OwnerException(message);
        }
        return true;
    }

    @Override
    public PageModel<List<ICRegistrationModel>> getList(QueryICRegistrationModel model,Integer companyId) {
        model.setCompanyId(companyId);
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("s.createTime");
            page.setDescs(desc);
        }
        List<ICRegistrationModel> list = iCRegistrationDao.getList(page, model);
        list.forEach(item->{
            String filepath = item.getFilepath();
            if (null != filepath && filepath != "") {
                item.setFileList(Arrays.asList(filepath.split(",")));
            }
        });
        return PageUtils.build(page,list);
    }

    @Override
    public Boolean del(List<Integer> ids) {
        return iCRegistrationDao.deleteBatchIds(ids)>0;
    }

    @Override
    public Boolean add(ICRegistrationModel model,UserInfo info) throws OwnerException{
        checkTheOnlyNo(model, info.getCompanyId(),null);
        ICRegistrationEntity entity = new ICRegistrationEntity();
        BeanUtils.copyProperties(model,entity);
        if (!CollectionUtils.isEmpty(model.getFileList())) {
            entity.setFilepath(StringUtils.join(model.getFileList(),','));
        } else {
            entity.setFilepath(null);
        }
        entity.create(info.getUserId(), info.getMsUserId(), new Date());
        entity.setCompanyId(info.getCompanyId());
        return iCRegistrationDao.insert(entity)>0;
    }

    @Override
    public Boolean edit(ICRegistrationModel model,UserInfo info) throws OwnerException{
        checkTheOnlyNo(model, info.getCompanyId(),model.getId());
        ICRegistrationEntity entity = new ICRegistrationEntity();
        BeanUtils.copyProperties(model,entity);
        if (!CollectionUtils.isEmpty(model.getFileList())) {
            entity.setFilepath(StringUtils.join(model.getFileList(), ','));
        } else {
            entity.setFilepath(null);
        }
        entity.update(info.getUserId(), info.getMsUserId(), new Date());
        return iCRegistrationDao.editIC(entity)>0;
    }

    @Override
    public ICRegistrationModel getText(Integer id) {
        return iCRegistrationDao.getText(id);
    }

    /**
     * 校验证书号和登记号是否唯一
     * @param model
     * @param companyId
     * @param id
     * @throws OwnerException
     */
    public void checkTheOnlyNo(ICRegistrationModel model, Integer companyId, Integer id) throws OwnerException{
        List<String> registerNos = iCRegistrationDao.checkRegisterNo(companyId, model.getRegisterNo(), id);
        if (!CollectionUtils.isEmpty(registerNos)) {
            throw new OwnerException("存在相同登记号，请重新输入！");
        }
        if (null != model.getCertificateNo()) {
            List<String> certificateNos = iCRegistrationDao.checkCertificateNo(companyId, model.getCertificateNo(), id);
            if (!CollectionUtils.isEmpty(certificateNos)) {
                throw new OwnerException("存在相同证书号，请重新输入！");
            }
        }
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
