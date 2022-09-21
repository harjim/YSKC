package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.PageUtils;
import com.yskc.docservice.dao.rs.PatentDetailDao;
import com.yskc.docservice.entity.rs.PatentDetailEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.PatentDetailExcel;
import com.yskc.docservice.service.rs.PatentDetailService;
import com.yskc.docservice.utils.TransactionUtils;
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
 * Created by hck
 * on 2020/6/29 11:37
 * description:
 */
@Service
public class PatentDetailServiceImpl implements PatentDetailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatentDetailDao patentDetailDao;

    @Override
    public String importPatent(RsUserInfo userInfo, List<PatentDetailExcel> list) throws OwnerException {
        Date date = new Date();
        String message = "";
        if (list.size() > 0) {
            //判断要导入的数据中是否存在重复的专利号
            Map<String, PatentDetailExcel> patentNoMap = new HashMap<>();
            for (PatentDetailExcel patentDetailExcel : list) {
                if (patentNoMap.containsKey(patentDetailExcel.getPatentNo())) {
                    message = MessageFormat.format("存在重复专利号：{0}，请修改后导入", patentDetailExcel.getPatentNo());
                    throw new OwnerException(message);
                }
                patentNoMap.put(patentDetailExcel.getPatentNo(), patentDetailExcel);
                String mainType = patentDetailExcel.getMainType();
                if (!mainType.equals("发明专利") && !mainType.equals("实用新型") && !mainType.equals("外观设计")) {
                    message = MessageFormat.format("专利号：{0}的类型不存在，请从中选择：发明专利、实用新型、外观设计，例如：发明专利", patentDetailExcel.getPatentNo());
                    throw new OwnerException(message);
                }
            }
            List<PatentDetailEntity> patentDetailEntities = patentDetailDao.getListByPatentNos(userInfo.getCompanyId());
            Map<String, PatentDetailEntity> patentDetailMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(patentDetailEntities)) {
                patentDetailMap = patentDetailEntities.stream().collect(Collectors.toMap(e -> e.getPatentNo(), e -> e));
            }
            List<PatentDetailEntity> updatePatents = new ArrayList<>();
            List<PatentDetailEntity> insertPatents = new ArrayList<>();
            //查询公司专利,根据导入数据中专利号是否存在判断做更新操作还是修改操作
            for (PatentDetailExcel patentDetailExcel : list) {
                PatentDetailEntity patentDetailEntity = patentDetailMap.get(patentDetailExcel.getPatentNo());
                if (patentDetailEntity != null) {
                    //更新专利表(暂不更新专利号和专利名称)
                    patentDetailEntity.setInventor(patentDetailExcel.getInventor());
                    patentDetailEntity.setMainType(patentDetailExcel.getMainType());
                    patentDetailEntity.setApplyDateTime(patentDetailExcel.getApplyDateTime());
                    patentDetailEntity.setClaimContent(patentDetailExcel.getClaimContent());
                    patentDetailEntity.setClaimNum(patentDetailExcel.getClaimNum());
                    updatePatents.add(patentDetailEntity);
                } else {
                    //添加专利
                    patentDetailEntity = new PatentDetailEntity();
                    BeanUtils.copyProperties(patentDetailExcel, patentDetailEntity);
                    patentDetailEntity.setCreateTime(date);
                    patentDetailEntity.setCreatorId(userInfo.getUserId());
                    patentDetailEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                    patentDetailEntity.setMsCreatorId(userInfo.getMsUserId());
                    patentDetailEntity.setCompanyId(userInfo.getCompanyId());
                    patentDetailEntity.setSource(0);
                    insertPatents.add(patentDetailEntity);
                }
                patentDetailEntity.setAuthDate(patentDetailExcel.getAuthDate());
                patentDetailEntity.setSpecification(patentDetailExcel.getSpecification());
                patentDetailEntity.setUsedCnt(patentDetailExcel.getUsedCnt());
                patentDetailEntity.setLastUpdatorId(userInfo.getUserId());
                patentDetailEntity.setLastUpdateTime(date);
            }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                if (insertPatents.size() > 0) {
                    patentDetailDao.insertPatents(insertPatents, userInfo.getCompanyId());
                }
                if (updatePatents.size() > 0) {
                    patentDetailDao.updatePatents(updatePatents, userInfo.getCompanyId());
                }
                TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
                logger.error("importPatent", ex);
            }
        } else {
            message = "文件上传失败,请重新上传";
            throw new OwnerException(message);
        }
        return "";
    }
}
