package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.RdEmployeeDao;
import com.yskc.docservice.dao.rs.init.InitMemberDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.init.InitMemberEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.InitMemberExcel;
import com.yskc.docservice.service.rs.InitMemberService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:18
 * @Description: 初始化项目人员列表业务实现层
 */
@Service
public class InitMemberServiceImpl implements InitMemberService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitMemberDao initMemberDao;

    @Autowired
    private RdEmployeeDao rdEmployeeDao;

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Boolean importMember(RsUserInfo userInfo, List<InitMemberExcel> data, Integer year, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何项目成员，请检查是否录入了数据。");
        }
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        if (null == projectEntity) {
            throw new OwnerException("当前所选项目不存在。请稍后重试！");
        }
        Map<String, InitMemberExcel> dataMap = new LinkedHashMap<>();
        data.forEach(item -> {
            if (dataMap.containsKey(item.getEnumber())) {
                return;
            }
            dataMap.put(item.getEnumber(), item);
        });
        data = new ArrayList<>(data.size());
        data.addAll(dataMap.values());
        List<String> numberList = new ArrayList<>(dataMap.keySet());
        List<InitMemberEntity> initExistNumber = initMemberDao.getByEnumbers(userInfo.getCompanyId(), projectId, numberList, year);
        List<String> rdExistNumber = rdEmployeeDao.getByEnumbers(userInfo.getCompanyId(), year, numberList);
        Map<String, Integer> initExistMap = new HashMap<>();
        Map<String, Boolean> rdExistMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(initExistNumber)) {
            initExistNumber.forEach(item -> {
                initExistMap.put(item.getEnumber(), item.getId());
            });
        }
        if (!CollectionUtils.isEmpty(rdExistNumber)) {
            rdExistNumber.forEach(n -> {
                rdExistMap.put(n, true);
            });
        }
        List<InitMemberEntity> initList = new ArrayList<>();
        Date now = new Date();
        long projectBegin = projectEntity.getBeginDate().getTime();
        long projectEnd = projectEntity.getEndDate().getTime();
        List<InitMemberEntity> updateList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            InitMemberExcel excel = data.get(i);
            if (!rdExistMap.containsKey(excel.getEnumber())) {
                throw new OwnerException(MessageFormat.format("第{3,number,#}行，工号【{0}】，姓名【{1}】不存在【{2,number,#}】年研发人员列表中，请先在研发人员中添加。"
                        , excel.getEnumber(), excel.getEname(), year, i + 2
                ));
            }
            if (null != excel.getEntryDate()) {
                if (excel.getEntryDate().getTime() < projectBegin || excel.getEntryDate().getTime() > projectEnd) {
                    throw new OwnerException(MessageFormat.format("第{0,number,#}行，进入时间【{1}】，必须在项目起止日期【{2}至{3}】以内。",
                            i + 2,
                            DateUtil.format(excel.getEntryDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)
                    ));
                }
            }
            String enumber = excel.getEnumber();
            if (!initExistMap.containsKey(enumber)) {
                initList.add(setInitMember(userInfo, excel, projectId, now, year));
            } else {
                InitMemberEntity memberEntity = setUpdateMember(userInfo, excel, now);
                memberEntity.setId(initExistMap.get(enumber));
                updateList.add(memberEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(initList)) {
                List<List<InitMemberEntity>> insertInitList = ListUtils.subList(initList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<InitMemberEntity> items : insertInitList) {
                    initMemberDao.addbatch(items);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (List<InitMemberEntity> updates : ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    initMemberDao.updateBatch(updates);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error("导入项目成员失败", e);
            throw new OwnerException("保存数据失败");
        }
    }

    /**
     * 设置项目成员
     *
     * @param info
     * @param excel
     * @param projectId
     * @param now
     * @return
     */
    private InitMemberEntity setInitMember(RsUserInfo info, InitMemberExcel excel, Integer projectId, Date now, Integer year) {
        InitMemberEntity initMemberEntity = setUpdateMember(info, excel, now);
        initMemberEntity.setMsCreatorId(info.getMsUserId());
        initMemberEntity.setCreatorId(info.getUserId());
        initMemberEntity.setCompanyId(info.getCompanyId());
        initMemberEntity.setCreateTime(now);
        initMemberEntity.setProjectId(projectId);
        initMemberEntity.setMaster(false);
        initMemberEntity.setEnumber(excel.getEnumber());
        initMemberEntity.setYear(year);
        return initMemberEntity;
    }

    /**
     * 设置项目成员
     *
     * @param info
     * @param excel
     * @param now
     * @return
     */
    private InitMemberEntity setUpdateMember(RsUserInfo info, InitMemberExcel excel, Date now) {
        InitMemberEntity initMemberEntity = new InitMemberEntity();
        initMemberEntity.setMsLastUpdatorId(info.getMsUserId());
        initMemberEntity.setLastUpdatorId(info.getUserId());
        initMemberEntity.setCompanyId(info.getCompanyId());
        initMemberEntity.setLastUpdateTime(now);
        initMemberEntity.setRole(excel.getRole());
        initMemberEntity.setEntryDate(excel.getEntryDate());
        return initMemberEntity;
    }

}
