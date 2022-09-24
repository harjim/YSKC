package com.yskc.docservice.service.impl.rs;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.dao.rs.TechEquipmentDao;
import com.yskc.docservice.entity.rs.tech.TechEquipment;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.TechEquipmentModel;
import com.yskc.docservice.service.rs.TechEquipmentService;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 11:11
 * @Description:
 */
@Service
public class TechEquipmentServiceImpl implements TechEquipmentService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TechEquipmentDao techEquipmentDao;

    @Override
    public Boolean importEquipment(RsUserInfo info, List<TechEquipmentModel> models, Integer beianId) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        BigDecimal temp = new BigDecimal("10.00");
        Date date = new Date();
        List<TechEquipment> techEquipments = new ArrayList<>();
        Integer companyId = info.getCompanyId();
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getLoadFactor().compareTo(temp) == 1) {
                throw new OwnerException(MessageFormat.format("第【{0}】行负荷系数应小于10", i+2));
            }
            if (models.get(i).getRunRate().compareTo(temp) == 1) {
                throw new OwnerException(MessageFormat.format("第【{0}】行运转率应小于10", i+2));
            }
            TechEquipment techEquipment = new TechEquipment();
            BeanUtils.copyProperties(models.get(i), techEquipment);
            techEquipment.create(info.getUserId(), info.getMsUserId(), date);
            techEquipment.setBeianId(beianId);
            techEquipment.setCompanyId(companyId);
            techEquipment.setPowerUsed();
            techEquipments.add(techEquipment);
        }
        Boolean flag = techEquipmentDao.insertList(techEquipments) > 0;
        if (flag) {
            techEquipmentDao.updateBeianTable();
        }
        return flag;
    }
}
