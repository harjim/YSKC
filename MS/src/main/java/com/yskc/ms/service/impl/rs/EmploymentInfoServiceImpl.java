package com.yskc.ms.service.impl.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.rs.EmploymentInfoDao;
import com.yskc.ms.entity.rs.EmploymentInfoEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.EmploymentInfoModel;
import com.yskc.ms.service.rs.EmploymentInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 14:43
 * description:
 */
@Service
public class EmploymentInfoServiceImpl implements EmploymentInfoService {

    @Autowired
    private EmploymentInfoDao employmentInfoDao;

    @Override
    public EmploymentInfoModel getEmploymentInfo(int companyId, int year) {
        EmploymentInfoModel model=employmentInfoDao.getInfo(companyId,year);
        if(model==null){
            model=new EmploymentInfoModel();
        }
        return model;
    }

    @Override
    public Integer editEmploymentInfo(EmploymentInfoModel model, UserInfo userInfo) throws OwnerException {
        if(model==null){
           throw new OwnerException("请求参数为空。请联系管理员！");
        }
        Date date=new Date();
        Integer userId=userInfo.getId();
        EmploymentInfoEntity entity;
        if(model.getId()!=null){
            entity=employmentInfoDao.selectById(model.getId());
            if(entity==null){
                throw new OwnerException("信息已删除或不存在，编辑失败！");
            }
            BeanUtils.copyProperties(model,entity);
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(-1);
            entity.setMsLastUpdatorId(userId);
             employmentInfoDao.updateById(entity);
        }else {
            entity=new EmploymentInfoEntity();
            BeanUtils.copyProperties(model,entity);
            entity.setCreateTime(date);
            entity.setCreatorId(-1);
            entity.setMsCreatorId(userId);
            entity.setLastUpdatorId(-1);
            entity.setLastUpdateTime(date);
            entity.setMsLastUpdatorId(userId);
             employmentInfoDao.insert(entity);
        }
        return entity.getId();
    }
}
