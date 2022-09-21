package com.yskc.ms.service.impl.es;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.es.CooperationDao;
import com.yskc.ms.entity.es.CooperationEntity;
import com.yskc.ms.models.newexpert.cooperation.CooperationModel;
import com.yskc.ms.models.newexpert.cooperation.QueryCooperationModel;
import com.yskc.ms.models.newexpert.cooperation.CooperationInfoModel;
import com.yskc.ms.service.es.CooperationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @DateTime: 2021/11/12 8:54
 * @Description:
 * @author: hsx
 */
@Service
public class CooperationServiceImpl implements CooperationService {

    @Autowired
    private CooperationDao cooperationDao;

    @Override
    public Boolean update(CooperationModel model,Integer id) {
        CooperationEntity cooperation = new CooperationEntity();
        BeanUtils.copyProperties(model,cooperation);
        cooperation.update(new Date(),id);
        return cooperationDao.updateCooperation(cooperation)>0;
    }

    @Override
    public PageModel<List<CooperationModel>> getExpertList(QueryCooperationModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(Arrays.asList("c.createTime"));
        }
        return PageUtils.build(page,cooperationDao.getExpertList(page,model));
    }

    @Override
    public PageModel<List<CooperationInfoModel>> getList(QueryCooperationModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(Arrays.asList("c.createTime"));
        }
        return PageUtils.build(page,cooperationDao.getList(page, model));
    }

    @Override
    public CooperationModel getData(Integer id) {
        CooperationModel model = cooperationDao.getData(id);
        String information = model.getInformation();
        String remark = model.getRemark();
        if (StringUtils.hasLength(information)) {
            model.setInformation((String) CommonUtils.strSpecialTransfer(information,false));
        }
        if (StringUtils.hasLength(remark)) {
            model.setRemark((String) CommonUtils.strSpecialTransfer(remark,false));
        }
        return model;
    }

    @Override
    public Integer getCooperationStatus(Integer intentionId) {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        Integer status = new Integer(0);
        List<CooperationEntity> cooperationEntity = cooperationDao.selectList(new EntityWrapper<CooperationEntity>().eq("intentionId",intentionId).eq("type",2).in("status",list));
        if(cooperationEntity.size()>0) {
            status = cooperationEntity.get(0).getStatus();
        }
        return status;
    }
}
