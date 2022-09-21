package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.RdEquipmentDao;
import com.yskc.docservice.dao.rs.init.InitEquipmentDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.init.InitEquipmentEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.InitEquipmentExcel;
import com.yskc.docservice.models.rs.rdequipment.RdEquipmentModel;
import com.yskc.docservice.service.rs.InitEquipmentService;
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
 * @CreateTime: 2019-07-23 17:20
 * @Description: 初始化项目设备列表业务实现层
 */
@Service
public class InitEquipmentServiceImpl implements InitEquipmentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private RdEquipmentDao rdEquipmentDao;

    @Override
    public boolean importInitEquipment(RsUserInfo userInfo, List<InitEquipmentExcel> data, int year, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何项目设备，请检查是否录入了数据。");
        }
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        if (null == projectEntity) {
            throw new OwnerException("当前所选项目不存在。请稍后重试！");
        }
        Map<String, InitEquipmentExcel> dataMap = new LinkedHashMap<>();
        data.forEach(item -> {
            if (dataMap.containsKey(item.getEcode())) {
                return;
            }
            dataMap.put(item.getEcode(), item);
        });
        data = new ArrayList<>(dataMap.size());
        data.addAll(dataMap.values());
        Set<String> ecodeSet = dataMap.keySet();
        List<RdEquipmentModel> rdEquipmentModels = rdEquipmentDao.getByEcodes(userInfo.getCompanyId(), ecodeSet, year);
        List<InitEquipmentEntity> initExistList = initEquipmentDao.getExistList(userInfo.getCompanyId(), projectId, ecodeSet, year);
        Map<String, Integer> ecodeTypeMap = new HashMap<>();
        Map<String, Boolean> rdExistMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(rdEquipmentModels)) {
            rdEquipmentModels.forEach(item -> {
                ecodeTypeMap.put(item.getEcode(), item.getEtype());
                rdExistMap.put(item.getEcode(), true);
            });
        }
        Map<String, Integer> initExistMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(initExistList)) {
            initExistList.forEach(item -> initExistMap.put(item.getEcode(), item.getId()));
        }
        Date now = new Date();
        List<InitEquipmentEntity> initList = new ArrayList<>();
        List<InitEquipmentEntity> updateList = new ArrayList<>();
        long projectBegin = projectEntity.getBeginDate().getTime();
        long projectEnd = projectEntity.getEndDate().getTime();
        for (int i = 0; i < data.size(); i++) {
            InitEquipmentExcel excel = data.get(i);
            if (!rdExistMap.containsKey(excel.getEcode())) {
                throw new OwnerException(MessageFormat.format("第{3,number,#}行，资产代码【{0}】，设备名称【{1}】不存在【{2,number,#}】年研发设备列表中，请先在研发设备中添加。"
                        , excel.getEcode(), excel.getEname(), year, i + 2
                ));
            }
            Integer etype = ecodeTypeMap.get(excel.getEcode());
            if (null == etype || etype <= 0) {
                throw new OwnerException(MessageFormat.format("第{3,number,#}行，资产代码【{0}】，设备名称【{1}】类型必须是设备或者仪器，请先在设备列表中设置。"
                        , excel.getEcode(), excel.getEname(), year, i + 2
                ));
            }
            if (null != excel.getEntryDate()) {
                if (excel.getEntryDate().getTime() < projectBegin || excel.getEntryDate().getTime() > projectEnd) {
                    String error = MessageFormat.format("第{0,number,#}行，进入时间【{1}】，必须在项目起止日期【{2}至{3}】以内。",
                            i + 2,
                            DateUtil.format(excel.getEntryDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)
                    );
                    throw new OwnerException(error);
                }
            }
            String ecode = excel.getEcode();
            if (!initExistMap.containsKey(ecode)) {
                initList.add(InitEquipmentEntity.build(userInfo, ecode, excel.getEffect(), projectId, now, excel.getEntryDate(), year));
            } else {
                InitEquipmentEntity equipmentEntity = InitEquipmentEntity.buildUpdate(userInfo, excel.getEffect(), now, excel.getEntryDate());
                equipmentEntity.setId(initExistMap.get(ecode));
                updateList.add(equipmentEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(initList)) {
                List<List<InitEquipmentEntity>> insertInitList = ListUtils.subList(initList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<InitEquipmentEntity> items : insertInitList) {
                    initEquipmentDao.addBatch(items);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (List<InitEquipmentEntity> updates : ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    initEquipmentDao.updateBatch(updates);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error("导入设备清单失败", e);
            throw new OwnerException("保存数据失败");
        }
    }
}
