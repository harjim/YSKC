package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdVoucherDao;
import com.yskc.rs.dao.VoucherDao;
import com.yskc.rs.entity.RdVoucherEntity;
import com.yskc.rs.entity.VoucherEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.VoucherExcel;
import com.yskc.rs.models.voucher.QueryVoucherModel;
import com.yskc.rs.models.voucher.RdVoucherModel;
import com.yskc.rs.models.voucher.RelatedVoucherModel;
import com.yskc.rs.models.voucher.VoucherModel;
import com.yskc.rs.service.VoucherService;
import com.yskc.rs.utils.TransactionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.stream.Stream;

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
    public PageModel<List<VoucherModel>> getList(QueryVoucherModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        if (query.getVoucherMonth() != null) {
            query.setBeginDate(DateUtil.beginOfMonth(query.getVoucherMonth()));
            query.setEndDate(DateUtil.endOfMonth(query.getVoucherMonth()));
        }
        List<VoucherModel> voucherModels = voucherDao.getList(page, companyId, query);
        if (!CollectionUtils.isEmpty(voucherModels)) {
            List<String> voucherNos = voucherModels.stream().map(VoucherModel::getVoucherNo).collect(Collectors.toList());
            List<RdVoucherModel> rdVoucherModels = rdVoucherDao.getListByVoucher(voucherNos, companyId);
            Map<String, List<String>> dataMap = new HashMap<>();
            Map<String,List<Integer>> projectIdMap=new HashMap<>();
            Map<String,Integer> typeMap=new HashMap<>();
            if (!CollectionUtils.isEmpty(rdVoucherModels)) {
                for (RdVoucherModel model : rdVoucherModels) {
                    //String rd = MessageFormat.format("{0}RD{1}", String.valueOf(model.getBeginYear()), model.getRdIndex() > 9 ? model.getRdIndex() : "0" + model.getRdIndex());
                    if (dataMap.containsKey(model.getVoucherNo())) {
                        dataMap.get(model.getVoucherNo()).add(model.getRdTitle());
                        List<Integer> list=projectIdMap.get(model.getVoucherNo());
                        list.add(model.getId());
                    } else {
                        dataMap.put(model.getVoucherNo(), Stream.of(model.getRdTitle()).collect(Collectors.toList()));
                        projectIdMap.put(model.getVoucherNo(), Stream.of(model.getId()).collect(Collectors.toList()));
                    }
                    if(!typeMap.containsKey(model.getVoucherNo())){
                        typeMap.put(model.getVoucherNo(),model.getRdType());
                    }
                }
                for (VoucherModel voucher : voucherModels) {
                    if(dataMap.containsKey(voucher.getVoucherNo())) {
                        String rdIndexStrs = StringUtils.join(dataMap.get(voucher.getVoucherNo()), ",");
                        voucher.setProjectRds(rdIndexStrs);
                        voucher.setRdType(typeMap.get(voucher.getVoucherNo()));
                    }
                    if(projectIdMap.containsKey(voucher.getVoucherNo())){
                        voucher.setProjectIds(projectIdMap.get(voucher.getVoucherNo()));
                    }
                }
            }
        }
        return PageUtils.build(page, voucherModels);
    }

    @Override
    public Boolean checkVoucherNo(String voucherNo, Integer companyId) throws OwnerException {
        VoucherEntity voucherEntity = voucherDao.checkVoucherNo(voucherNo, companyId);
        if (voucherEntity != null) {
            throw new OwnerException("凭证号已存在");
        }
        return true;
    }

    @Override
    public Boolean addVoucher(VoucherModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        VoucherEntity voucherEntity = voucherDao.checkVoucherNo(model.getVoucherNo(), userInfo.getCompanyId());
        if (voucherEntity != null) {
            throw new OwnerException("凭证号已存在,不能重复添加");
        }
        VoucherEntity voucher = new VoucherEntity();
        voucher.setAmount(model.getAmount());
        voucher.setCompanyId(userInfo.getCompanyId());
        voucher.setSummary(model.getSummary());
        voucher.setVoucherNo(model.getVoucherNo());
        voucher.setVoucherDate(model.getVoucherDate());
        voucher.setCreateTime(date);
        voucher.setLastUpdateTime(date);
        voucher.setCreatorId(userInfo.getUserId());
        voucher.setLastUpdatorId(userInfo.getUserId());
        voucher.setMsCreatorId(-1);
        voucher.setMsLastUpdatorId(-1);
        Date month = DateUtil.beginOfMonth(model.getVoucherDate());
        List<RdVoucherEntity> rdVoucherEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(model.getProjectIds())) {
            for (Integer projectId : model.getProjectIds()) {
                RdVoucherEntity rdVoucherEntity = new RdVoucherEntity();
                rdVoucherEntity.setMonth(month);
                rdVoucherEntity.setCompanyId(userInfo.getCompanyId());
                rdVoucherEntity.setProjectId(projectId);
                rdVoucherEntity.setRdType(model.getRdType());
                rdVoucherEntity.setVoucherNo(model.getVoucherNo());
                rdVoucherEntity.setCreateTime(date);
                rdVoucherEntity.setCreatorId(userInfo.getUserId());
                rdVoucherEntity.setMsCreatorId(-1);
                rdVoucherEntity.setLastUpdateTime(date);
                rdVoucherEntity.setLastUpdatorId(userInfo.getUserId());
                rdVoucherEntity.setMsLastUpdatorId(-1);
                rdVoucherEntities.add(rdVoucherEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (rdVoucherEntities.size() > 0) {
                rdVoucherDao.insertList(rdVoucherEntities);
            }
            voucherDao.insert(voucher);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("添加凭证失败。");
        }
    }

    @Override
    public Boolean updateVoucher(UserInfo userInfo, VoucherModel model) throws OwnerException {
        Date date = new Date();
        VoucherEntity voucherEntity = voucherDao.selectById(model.getId());
        String voucherNo=voucherEntity.getVoucherNo();
        if (voucherEntity != null) {
            voucherEntity.setVoucherNo(model.getVoucherNo());
            voucherEntity.setSummary(model.getSummary());
            voucherEntity.setVoucherDate(model.getVoucherDate());
            voucherEntity.setAmount(model.getAmount());
            voucherEntity.setLastUpdateTime(date);
            voucherEntity.setLastUpdatorId(userInfo.getUserId());
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                voucherDao.updateById(voucherEntity);
                //无项目删除之前的项目
                if (CollectionUtils.isEmpty(model.getProjectIds())) {
                    rdVoucherDao.delByVoucherNo(voucherNo, userInfo.getCompanyId());
                }
                //有项目交集修改,model差集添加,存在rd凭证差集删除
                Map<String,List<Integer>> map=getChangeList(voucherNo,model.getProjectIds(),userInfo.getCompanyId());
                if(map.containsKey("update")){
                    List<Integer> updateProjectIds=map.get("update");
                    rdVoucherDao.updateList(model,updateProjectIds,date,userInfo.getUserId(),DateUtil.beginOfMonth(model.getVoucherDate()),voucherNo,userInfo.getCompanyId());
                }
                if(map.containsKey("del")){
                    rdVoucherDao.delByProjectIds(voucherNo,userInfo.getCompanyId(),map.get("del"));
                }
                if(map.containsKey("insert")){
                    List<RdVoucherEntity> rdVoucherEntities=new ArrayList<>();
                    for (Integer projectId:map.get("insert")) {
                        RdVoucherEntity rdVoucherEntity=new RdVoucherEntity();
                        rdVoucherEntity.setMonth(DateUtil.beginOfMonth(model.getVoucherDate()));
                        rdVoucherEntity.setVoucherNo(model.getVoucherNo());
                        rdVoucherEntity.setRdType(model.getRdType());
                        rdVoucherEntity.setProjectId(projectId);
                        rdVoucherEntity.setCompanyId(userInfo.getCompanyId());
                        rdVoucherEntity.setCreatorId(userInfo.getUserId());
                        rdVoucherEntity.setCreateTime(date);
                        rdVoucherEntity.setLastUpdatorId(userInfo.getUserId());
                        rdVoucherEntity.setLastUpdateTime(date);
                        rdVoucherEntity.setMsCreatorId(-1);
                        rdVoucherEntity.setMsLastUpdatorId(-1);
                        rdVoucherEntities.add(rdVoucherEntity);
                    }
                    if(!CollectionUtils.isEmpty(rdVoucherEntities)) {
                        rdVoucherDao.insertList(rdVoucherEntities);
                    }
                }
                TransactionUtils.commit(transactionStatus);
                return true;
            } catch (Exception e) {
                TransactionUtils.rollback(transactionStatus);
                logger.error(e.getMessage(), e);
                throw new OwnerException("修改凭证失败。");
            }
        }
        throw new OwnerException("凭证不存在,修改失败");
    }

    @Override
    public Boolean deleteById(Integer companyId, Integer id) throws OwnerException {
        VoucherEntity voucherEntity=voucherDao.selectById(id);
        if(voucherEntity!=null){
            String voucherNo=voucherEntity.getVoucherNo();
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                voucherDao.deleteById(id);
                rdVoucherDao.delByVoucherNo(voucherNo,companyId);
                TransactionUtils.commit(transactionStatus);
                return true;
            } catch (Exception e) {
                TransactionUtils.rollback(transactionStatus);
                logger.error(e.getMessage(), e);
                throw new OwnerException("删除凭证失败。");
            }

        }
        throw new OwnerException("数据不存在，删除失败");
    }

    @Override
    public boolean importVoucher(UserInfo info, List<VoucherExcel> voucherExcels) throws OwnerException {
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
        Map<String, CostEnum> costEnumMap=CostEnum.getTitleMap();
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
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
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("导入凭证失败。");
        }

    }

    /**
     * 根据要修改的项目id区分增删改操作
     *
     * @param voucherNo
     * @param projectIds
     * @param companyId
     * @return
     */
    private Map<String, List<Integer>> getChangeList(String voucherNo, List<Integer> projectIds, Integer companyId) {
        Map<String, List<Integer>> map = new HashMap<>();
        List<RdVoucherEntity> entityList = rdVoucherDao.getRdVoucherList(companyId, voucherNo);
        if (!CollectionUtils.isEmpty(entityList)) {
            List<Integer> ids = entityList.stream().map(RdVoucherEntity::getProjectId).collect(Collectors.toList());
            Collection<Integer> commonIds = CollUtil.intersection(ids, projectIds);
            Collection<Integer> insertIds = CollUtil.disjunction(projectIds, commonIds);
            Collection<Integer> delIds = CollUtil.disjunction(ids, commonIds);
            //没有交集，删除之前数据，添加修改数据
            if (CollectionUtils.isEmpty(commonIds)) {
                map.put("insert", projectIds);
                map.put("del", ids);
            } else {
                map.put("update", (List<Integer>) commonIds);
                if (!CollectionUtils.isEmpty(insertIds)) {
                    map.put("insert", (List<Integer>) insertIds);
                }
                if (!CollectionUtils.isEmpty(delIds)) {
                    map.put("del", (List<Integer>) delIds);
                }
            }

        } else {
            if(!CollectionUtils.isEmpty(projectIds)) {
                map.put("insert", projectIds);
            }
        }
        return map;
    }

    @Override
    public Boolean relatedVoucher(UserInfo userInfo, RelatedVoucherModel model) throws OwnerException {
        if(CollectionUtils.isEmpty(model.getIds())){
            return false;
        }
        List<VoucherEntity> entityList=voucherDao.selectBatchIds(model.getIds());
        List<String> voucherNos=entityList.stream().map(VoucherEntity::getVoucherNo).collect(Collectors.toList());
        List<RdVoucherEntity> rdVoucherEntities=new ArrayList<>();
        for(VoucherEntity voucherEntity:entityList){
            if(!CollectionUtils.isEmpty(model.getProjectIds())){
                if(model.getRdType()==null || model.getRdType()==0){
                    throw new OwnerException("关联指定项目需同时设置费用类型");
                }
                Date month=DateUtil.beginOfMonth(voucherEntity.getVoucherDate());
               for(Integer projectId:model.getProjectIds()){
                   RdVoucherEntity rdVoucherEntity=new RdVoucherEntity();
                   rdVoucherEntity.setProjectId(projectId);
                   rdVoucherEntity.setCompanyId(userInfo.getCompanyId());
                   rdVoucherEntity.setRdType(model.getRdType());
                   rdVoucherEntity.setVoucherNo(voucherEntity.getVoucherNo());
                   rdVoucherEntity.setMonth(month);
                   rdVoucherEntity.setCreatorId(userInfo.getUserId());
                   rdVoucherEntity.setLastUpdatorId(userInfo.getUserId());
                   rdVoucherEntity.setMsCreatorId(-1);
                   rdVoucherEntity.setMsLastUpdatorId(-1);
                   rdVoucherEntity.setCreateTime(new Date());
                   rdVoucherEntity.setLastUpdateTime(new Date());
                   rdVoucherEntities.add(rdVoucherEntity);
               }
            }else {
                throw new OwnerException("请指定要关联的项目");
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
           rdVoucherDao.delList(voucherNos,userInfo.getCompanyId());
           if(rdVoucherEntities.size()>0) {
               rdVoucherDao.insertList(rdVoucherEntities);
           }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("凭证关联项目失败。");
        }
    }
}
