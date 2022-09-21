package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.tech.BeianDao;
import com.yskc.rs.dao.tech.TechEquipmentDao;
import com.yskc.rs.entity.tech.BeianEntity;
import com.yskc.rs.entity.tech.TechEquipment;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.QueryEquipmentModel;
import com.yskc.rs.models.tech.TechEquipmentModel;
import com.yskc.rs.service.TechEquipmentService;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.util.*;

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
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private BeianDao beianDao;

    @Override
    public PageModel<List<TechEquipmentModel>> getList(Integer companyId, QueryEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            // 客户要求按导入顺序排
            page.setAscs(CollUtil.newArrayList("id"));
        }
        return PageUtils.build(page, techEquipmentDao.getList(page, companyId, query));
    }

    @Override
    public Boolean edit(UserInfo userInfo, TechEquipmentModel model) throws OwnerException {
        TechEquipment techEquipment = techEquipmentDao.selectById(model.getId());
        if (techEquipment == null) {
            throw new OwnerException("记录已删除或不存在，编辑失败。");
        }
        BeanUtils.copyProperties(model, techEquipment);
        techEquipment.update(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        techEquipment.setPowerUsed();
        Boolean flag = techEquipmentDao.updateById(techEquipment) > 0;
        if (flag) {
            techEquipmentDao.updateBeianTable();
        }
        return flag;
    }

    @Override
    public Boolean deletes(BatchDeleteModel deleteModel) {
        List<Integer> ids = deleteModel.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        return techEquipmentDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean add(TechEquipmentModel model, UserInfo userInfo) {
        TechEquipment equipment = new TechEquipment();
        BeanUtil.copyProperties(model, equipment);
        equipment.setCompanyId(userInfo.getCompanyId());
        equipment.setPowerUsed();
        equipment.create(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        Boolean flag = techEquipmentDao.insert(equipment) > 0;
        if (flag) {
            techEquipmentDao.updateBeianTable();
        }
        return flag;
    }

    @Override
    public Boolean importEquipment(UserInfo info, List<TechEquipmentModel> models, Integer beianId) {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        Date date = new Date();
        List<TechEquipment> techEquipments = new ArrayList<>();
        Integer companyId = info.getCompanyId();
        for (TechEquipmentModel model : models) {
            TechEquipment techEquipment = new TechEquipment();
            BeanUtils.copyProperties(model, techEquipment);
            techEquipment.create(info.getUserId(), info.getMsUserId(), date);
            techEquipment.setBeianId(beianId);
            techEquipment.setCompanyId(companyId);
            techEquipments.add(techEquipment);
        }
        return techEquipmentDao.insertList(techEquipments) > 0;
    }

    @Override
    public void exportEquipment(Integer beianId, UserInfo info, OutputStream out) throws OwnerException {
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "备案清单.xls";
        if (StrUtil.isEmpty(templatePath)) {
            throw new OwnerException("导出失败，无法获取模板。");
        }
        BeianEntity beianEntity = beianDao.selectById(beianId);
        if (beianEntity == null) {
            throw new OwnerException("备案已删除或不存在，导出失败");
        }
        List<TechEquipmentModel> models = techEquipmentDao.getEquipments(beianId);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("models", models);
        dataMap.put("companyName", info.getCompanyName());
        dataMap.put("pname", beianEntity.getPname());
        YsExcelUtils.generalReport(dataMap, templatePath, (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

    }
}
