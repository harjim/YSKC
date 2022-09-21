package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.DeptDao;
import com.yskc.docservice.entity.rs.Dept;
import com.yskc.docservice.entity.rs.EquipmentEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;
import com.yskc.docservice.dao.rs.EquipmentDao;
import com.yskc.docservice.service.rs.EquipmentService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:22
 * @Description: 设备业务层实现
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquipmentDao equipmentDao;
    @Autowired
    private DeptDao deptDao;

    public static String trimBothEndsChars(String srcStr, String splitter) {
        String regex = "^" + splitter + "*|" + splitter + "*$";
        return srcStr.replaceAll(regex, "");
    }

    /**
     * @param info
     * @param equipmentExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public String importEquipment(RsUserInfo info, List<EquipmentExcel> equipmentExcels) throws OwnerException {
        if (CollectionUtils.isEmpty(equipmentExcels)) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        EquipmentExcel equipmentExcel;
        Date now = new Date();
        StringBuffer errorRow = new StringBuffer();
        List<EquipmentEntity> equipmentEntities = new ArrayList<>();

        Dept parentDept = deptDao.selectParentDept(info.getCompanyId());
        if (parentDept==null){
            throw new OwnerException("该公司尚无组织架构，请先导入组织架构!");
        }
        List<Dept> depts = deptDao.queryByCompanyId(info.getCompanyId());
        Map<String, Integer> deptMap = depts.stream().collect(Collectors.toMap(Dept::getFullname, Dept::getId,(o,n)->o));

        for (int i = 0; i < equipmentExcels.size(); i++) {
            equipmentExcel = equipmentExcels.get(i);
            boolean checkRate = null != equipmentExcel.getDepreciationRate() && (equipmentExcel.getDepreciationRate().doubleValue() > 1 || equipmentExcel.getDepreciationRate().doubleValue() < 0);
            if (checkRate) {
                errorRow.append("第" + (i + 2) + "行 【月折旧率不能大于1且不能小于0】,");
                continue;
            }
            if (equipmentExcel.getPurchaseDate() != null && equipmentExcel.getDepreciationDate() != null && equipmentExcel.getPurchaseDate().getTime() > equipmentExcel.getDepreciationDate().getTime()) {
                errorRow.append("第" + (i + 2) + "行 【折旧日期不能小于开始计提日期】,");
                continue;
            }
            String fullname = equipmentExcel.getFullname();
            fullname = fullname.replaceAll(parentDept.getDeptName()+"/", "");
            fullname = trimBothEndsChars(fullname,"/");
            if (null == deptMap.get(fullname)){
                throw new OwnerException("第" + (i + 2) + "行，部门【"+equipmentExcel.getFullname()+"】不在组织架构部门中！");
            }
            equipmentExcel.setFullname(fullname);
            Integer deptId = deptMap.get(fullname);
            equipmentEntities.add(EquipmentEntity.build(equipmentExcel, BigDecimal.ZERO, now, info, deptId));
        }

        // 分批次插入 // 分批次插入
        List<List<EquipmentEntity>> equipments = ListUtils.subList(equipmentEntities, Constant.MAX_INSERT_OR_UPDATE_COUNT);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            for (List<EquipmentEntity> equipmentList : equipments) {
                equipmentDao.insertOrUpdate(equipmentList);
            }
            logger.info("设备导入失败行：" + errorRow.toString());
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            if (errorRow.length() > 0) {
                errorRow.delete(errorRow.length() - 1, errorRow.length());
            }
            return errorRow.toString();
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            String error = e.getMessage();
            logger.error(error, e);
            throw new OwnerException(
                    MessageFormat.format("导入数据失败{0}",
                            error.contains("Data too long") ? "，部分列字数过长，请检查" : "")
            );
        }
    }

}
