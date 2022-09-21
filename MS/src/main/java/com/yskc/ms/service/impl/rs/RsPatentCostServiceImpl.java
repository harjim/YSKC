package com.yskc.ms.service.impl.rs;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.RsPatentCostDao;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.PatentCostModel;
import com.yskc.ms.models.patent.QueryPatentCostModel;
import com.yskc.ms.service.rs.RsPatentCostService;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/2 11:07
 * description:
 */
@Service
public class RsPatentCostServiceImpl implements RsPatentCostService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsPatentCostDao rsPatentCostDao;

    @Override
    public PageModel<List<PatentCostModel>> queryPatentCost(QueryPatentCostModel query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("c.createTime");
            page.setDescs(desc);
        }
        Date startDates = null;
        Date endDates = null;
        Date now = new Date();
        if (query.getType() != null && query.getType() > 0) {
            if (query.getType() == 1) {
                startDates = DateUtil.beginOfDay(now);
            } else if (query.getType() == 2) {
                startDates = DateUtil.beginOfDay(now);
                Date endDate = com.yskc.common.utils.DateUtil.addDays(now, 15);
                endDates = DateUtil.endOfDay(endDate);
            } else if (query.getType() == 3) {
                Date startDate = com.yskc.common.utils.DateUtil.addDays(now, 16);
                Date endDate = com.yskc.common.utils.DateUtil.addDays(now, 30);
                startDates = DateUtil.beginOfDay(startDate);
                endDates = DateUtil.endOfDay(endDate);
            } else if (query.getType() == 4) {
                Date startDate = com.yskc.common.utils.DateUtil.addDays(now, 31);
                Date endDate = com.yskc.common.utils.DateUtil.addDays(now, 60);
                startDates = DateUtil.beginOfDay(startDate);
                endDates = DateUtil.endOfDay(endDate);
            } else if (query.getType() == 5) {
                Date startDate = com.yskc.common.utils.DateUtil.addDays(now, 60);
                startDates = DateUtil.endOfDay(startDate);
            }
        }
        return PageUtils.build(page, rsPatentCostDao.queryPatentCost(page,query,perm, startDates, endDates));
    }

    @Override
    public void export(QueryPatentCostModel query, UserInfo info, DataPermModel perm, OutputStream out, String path) {
        Date startDates = null;
        Date endDates = null;
        Date now = new Date();
        if (query.getType() != null && query.getType() > 0) {
            if (query.getType() == 1) {
                startDates = DateUtil.beginOfDay(now);
            } else if (query.getType() == 2) {
                startDates = DateUtil.beginOfDay(now);
                Date endDate = com.yskc.common.utils.DateUtil.addDays(now, 15);
                endDates = DateUtil.endOfDay(endDate);
            } else if (query.getType() == 3) {
                Date startDate = com.yskc.common.utils.DateUtil.addDays(now, 16);
                Date endDate = com.yskc.common.utils.DateUtil.addDays(now, 30);
                startDates = DateUtil.beginOfDay(startDate);
                endDates = DateUtil.endOfDay(endDate);
            } else if (query.getType() == 4) {
                Date startDate = com.yskc.common.utils.DateUtil.addDays(now, 31);
                Date endDate = com.yskc.common.utils.DateUtil.addDays(now, 60);
                startDates = DateUtil.beginOfDay(startDate);
                endDates = DateUtil.endOfDay(endDate);
            } else if (query.getType() == 5) {
                Date startDate = com.yskc.common.utils.DateUtil.addDays(now, 60);
                startDates = DateUtil.endOfDay(startDate);
            }
        }
        List<PatentCostModel> list = rsPatentCostDao.export(query, perm, startDates, endDates);
        for (PatentCostModel model : list) {
            model.setPayDetail(model.getPay()==null?null:model.getPay()?"是":"否");
            model.setRemindDetail(model.getRemind()==null?null:model.getRemind()?"是":"否");
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        YsExcelUtils.generalReport(dataMap, path, (workbook) -> {
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


    @Override
    public boolean updateRemark(UserInfo info, PatentCostModel model) {
        List<RsPatentCostEntity> entities = model.getRsEntityList();
        String remark = model.getRemark();
        for (int i = 0; i < entities.size(); i++) {
            RsPatentCostEntity entity = entities.get(i);
            entity.setRemark(remark);
            entity.setMsLastUpdatorId(info.getId());
            entity.setLastUpdateTime(new Date());
        }
        rsPatentCostDao.updateRemarks(entities,1);
        return true;
    }

    @Override
    public boolean updateRemindDateTime(PatentCostModel model,UserInfo info) {
        List<RsPatentCostEntity> patentCosts = model.getRsEntityList();
        Date date = model.getRemindDateTime();
        List<RsPatentCostEntity> rsPatentCostEntities=new ArrayList<>();
        for (int i = 0; i < patentCosts.size(); i++) {
            RsPatentCostEntity entity = patentCosts.get(i);
            Date limitDate = entity.getLimitDate();
            if(limitDate!=null) {
                if (date.before(limitDate)) {
                    entity.setRemindDateTime(date);
                    entity.setLastUpdateTime(new Date());
                    entity.setMsLastUpdatorId(info.getId());
                    rsPatentCostEntities.add(entity);
                }
            }
        }
        rsPatentCostDao.updateRemarks(rsPatentCostEntities,2);
        return true;
    }

    @Override
    public boolean delete(PatentCostModel model) {
        return rsPatentCostDao.deleteById(model.getId()) > 0;
    }

    @Override
    public boolean deletePatentCosts(List<RsPatentCostEntity> patentCosts) {
        List<Integer> ids = patentCosts.stream().map(RsPatentCostEntity::getId).collect(Collectors.toList());
        return rsPatentCostDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<PatentCostModel> getPayDataBypatentNo(String patentNo) {
        return rsPatentCostDao.getPayDataBypatentNo(patentNo);
    }

}
