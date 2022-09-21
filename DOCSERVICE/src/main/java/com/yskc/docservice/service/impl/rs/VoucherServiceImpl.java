package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.dao.rs.RdVoucherDao;
import com.yskc.docservice.dao.rs.VoucherDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.RdVoucherEntity;
import com.yskc.docservice.entity.rs.VoucherEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.enums.CostEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.VoucherExcel;
import com.yskc.docservice.service.rs.VoucherService;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/14 15:02
 * description:
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VoucherDao voucherDao;
    @Autowired
    private RdVoucherDao rdVoucherDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public boolean importVoucher(RsUserInfo info, List<VoucherExcel> voucherExcels) throws OwnerException {
        Date date=new Date();
        String message="";
        if(CollectionUtils.isEmpty(voucherExcels)){
            return true;
        }
        List<ProjectEntity> projectEntities=projectDao.getCompanyProjectList(info.getCompanyId());
        Map<String,Integer> projectMap=new HashMap<>();
        if(!CollectionUtils.isEmpty(projectEntities)){
            for (ProjectEntity project:projectEntities) {
                projectMap.put(project.getRdTitle(),project.getId());
            }
        }
        Map<String, CostEnum> costEnumMap= CostEnum.getTitleMap();
        Map<String,List<Integer>> projectIdMap=new HashMap<>();
        //校验导入数据
        for (VoucherExcel voucherExcel:voucherExcels) {
            if(!StringUtils.isEmpty(voucherExcel.getProjectRds())&& StringUtils.isEmpty(voucherExcel.getRdType())){
                message=MessageFormat.format("凭证号：{0}关联项目{1}，请设置费用类型后再导入",voucherExcel.getVoucherNo(),voucherExcel.getProjectRds());
                throw new OwnerException(message);
            }
            if(!StringUtils.isEmpty(voucherExcel.getProjectRds())){
                if(!costEnumMap.containsKey(voucherExcel.getRdType())){
                    message=MessageFormat.format("请修改凭证号为：{0}的费用类型{1}！例如：工资，五险一金等",voucherExcel.getVoucherNo(),voucherExcel.getRdType());
                    throw new OwnerException(message);
                }
                List<String> projectRds=Arrays.asList(voucherExcel.getProjectRds().split(","));
                List<String> list= projectRds.stream().distinct().collect(Collectors.toList());
                if(list.size()<projectRds.size()){
                    throw new OwnerException("凭证号为："+voucherExcel.getVoucherNo()+"存在重复的项目编号，请修改后再导入");
                }
                List<Integer> projectIds=new ArrayList<>();
                for (String projectRd:projectRds) {
                    if(!Pattern.matches("\\d{4}RD\\d{2,}",projectRd)){
                        message=MessageFormat.format("凭证号为：{0}中项目编号{1}格式不对！请修改后重新导入，例如：2020RD01",voucherExcel.getVoucherNo(),projectRd);
                        throw new OwnerException(message);
                    }
                    if(!projectMap.containsKey(projectRd)){
                        message=MessageFormat.format("凭证号为：{0}中项目编号为{1}项目不存在！请修改后重新导入",voucherExcel.getVoucherNo(),projectRd);
                        throw new OwnerException(message);
                    }
                    projectIds.add(projectMap.get(projectRd));
                }
                if(projectIdMap.containsKey(voucherExcel.getVoucherNo())){
                    throw new OwnerException("存在重复的凭证号:"+voucherExcel.getVoucherNo()+",请修改后再导入！");
                }
                projectIdMap.put(voucherExcel.getVoucherNo(),projectIds);
            }
        }
        Map<String,VoucherExcel> VoucherExcelMap=voucherExcels.stream().collect(Collectors.toMap(e->e.getVoucherNo(),e->e));
        List<String> voucherNos=voucherExcels.stream().map(VoucherExcel::getVoucherNo).collect(Collectors.toList());
        List<VoucherEntity> voucherEntities=voucherDao.getListByVoucherNos(voucherNos,info.getCompanyId());
        Map<String,VoucherEntity> voucherMap=voucherEntities.stream().collect(Collectors.toMap(e->e.getVoucherNo(),e->e));
        List<VoucherEntity> insertVouchers=new ArrayList<>();//插入凭证列表
        List<VoucherEntity> updateVouchers=new ArrayList<>();//更新凭证列表
        List<RdVoucherEntity> insertRdVouchers=new ArrayList<>();//插入研发凭证列表
        for (String voucherNo:voucherNos) {
            VoucherExcel voucherExcel=VoucherExcelMap.get(voucherNo);
            if(voucherMap.containsKey(voucherNo)){
                //更新凭证
                VoucherEntity voucherEntity=voucherMap.get(voucherNo);
                voucherEntity.setLastUpdateTime(date);
                voucherEntity.setLastUpdatorId(info.getUserId());
                voucherEntity.setSummary(voucherExcel.getSummary());
                voucherEntity.setAmount(voucherExcel.getAmount());
                voucherEntity.setVoucherDate(voucherExcel.getVoucherDate());
                updateVouchers.add(voucherEntity);
            }else{
                //新增凭证
                VoucherEntity voucher = new VoucherEntity();
                voucher.setAmount(voucherExcel.getAmount());
                voucher.setCompanyId(info.getCompanyId());
                voucher.setSummary(voucherExcel.getSummary());
                voucher.setVoucherNo(voucherNo);
                voucher.setVoucherDate(voucherExcel.getVoucherDate());
                voucher.setCreateTime(date);
                voucher.setLastUpdateTime(date);
                voucher.setCreatorId(info.getUserId());
                voucher.setLastUpdatorId(info.getUserId());
                voucher.setMsCreatorId(-1);
                voucher.setMsLastUpdatorId(-1);
                insertVouchers.add(voucher);
            }
            if(projectIdMap.containsKey(voucherNo)){
                Date month = DateUtil.beginOfMonth(voucherExcel.getVoucherDate());
                Integer type=costEnumMap.get(voucherExcel.getRdType()).getType();
                List<Integer> projectIds=projectIdMap.get(voucherNo);
                for (Integer projectId:projectIds) {
                    RdVoucherEntity rdVoucherEntity=new RdVoucherEntity();
                    rdVoucherEntity.setProjectId(projectId);
                    rdVoucherEntity.setCompanyId(info.getCompanyId());
                    rdVoucherEntity.setRdType(type);
                    rdVoucherEntity.setVoucherNo(voucherNo);
                    rdVoucherEntity.setMonth(month);
                    rdVoucherEntity.setCreateTime(date);
                    rdVoucherEntity.setLastUpdateTime(date);
                    rdVoucherEntity.setCreatorId(info.getUserId());
                    rdVoucherEntity.setLastUpdatorId(info.getUserId());
                    rdVoucherEntity.setMsLastUpdatorId(-1);
                    rdVoucherEntity.setMsCreatorId(-1);
                    insertRdVouchers.add(rdVoucherEntity);
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            //删除凭证列表数据
            if(voucherNos.size()>0){
                rdVoucherDao.delList(voucherNos,info.getCompanyId());
            }
            //插入凭证
            if(insertVouchers.size()>0){
                voucherDao.insertList(insertVouchers);
            }
            //更新凭证
           if(updateVouchers.size()>0){
               voucherDao.updateList(updateVouchers);
           }
           //插入研发凭证
           if(insertRdVouchers.size()>0){
               rdVoucherDao.insertList(insertRdVouchers);
           }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("导入凭证失败。");
        }

    }
}
