package com.yskc.ms.service.impl.rs;

import com.yskc.ms.dao.rs.ExtraDao;
import com.yskc.ms.entity.rs.ExtraEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.ExtraModel;
import com.yskc.ms.service.rs.ExtraService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 14:40
 * description:
 */
@Service
public class ExtraServiceImpl implements ExtraService {

    @Autowired
    private ExtraDao extraDao;

    @Override
    public ExtraModel getExtraInfo(Integer companyId) {
        ExtraEntity extra=extraDao.getByCompany(companyId);
        ExtraModel model = new ExtraModel();
        if(extra!=null) {
             model = new ExtraModel();
             BeanUtils.copyProperties(extra,model);
        }
        return model;
    }

    @Override
    public Integer editInfo(ExtraModel model, UserInfo userInfo) {
        Date date=new Date();
        ExtraEntity extra=extraDao.getByCompany(model.getCompanyId());
        if(extra!=null){
            BeanUtils.copyProperties(model,extra);
            extra.setLastUpdateTime(date);
            extra.setLastUpdatorId(-1);
            extra.setMsLastUpdatorId(userInfo.getId());
             extraDao.updateById(extra);
        }else {
            extra=new ExtraEntity();
            BeanUtils.copyProperties(model,extra);
            extra.setCreateTime(date);
            extra.setCreatorId(-1);
            extra.setMsLastUpdatorId(userInfo.getId());
            extra.setMsCreatorId(userInfo.getId());
            extra.setLastUpdatorId(-1);
            extra.setLastUpdateTime(date);
             extraDao.insert(extra);
        }
        return extra.getId();
    }
}
