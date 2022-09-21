package com.yskc.ms.service.impl.rs;


import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.dao.rs.RsDocFileTemplateDao;
import com.yskc.ms.entity.rs.RsDocFileTemplateEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import com.yskc.ms.models.rs.RsDocFilesModel;
import com.yskc.ms.service.rs.RsDocFileTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RsDocFileTemplateServiceImpl implements RsDocFileTemplateService {
    @Autowired
    private RsDocFileTemplateDao rsDocFileTemplateDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    @Override
    public RsDocFileTemplateModel getData(Integer docFileTemplateId) {
        return rsDocFileTemplateDao.getData(docFileTemplateId);
    }


    @Override
    public Boolean saveData(UserInfo userInfo, RsDocFileTemplateModel rsDocFileTemplateModel, Boolean isHave) {
        RsDocFileTemplateEntity rsDocFileTemplateEntity = new RsDocFileTemplateEntity();
        Date now = new Date();
        rsDocFileTemplateEntity.setLastUpdateTime(now);
        rsDocFileTemplateEntity.setLastUpdatorId(-1);
        rsDocFileTemplateEntity.setMsLastUpdatorId(userInfo.getId());
        rsDocFileTemplateEntity.setDocFileId(rsDocFileTemplateModel.getDocFileId());
        rsDocFileTemplateEntity.setTemplateName(rsDocFileTemplateModel.getTemplateName());
        //isHave true  更新操作  false  插入操作
        if (isHave) {
            rsDocFileTemplateEntity.setId(rsDocFileTemplateModel.getId());
            return rsDocFileTemplateDao.updateById(rsDocFileTemplateEntity) > 0;
        }
        rsDocFileTemplateEntity.setCreateTime(now);
        rsDocFileTemplateEntity.setCreatorId(-1);
        rsDocFileTemplateEntity.setMsCreatorId(userInfo.getId());
        return rsDocFileTemplateDao.insert(rsDocFileTemplateEntity) > 0;
    }

    @Override
    public PageModel<List<RsDocFilesModel>> getDocFilesList(int pageNo, int pageSize, String docName) {
        Pagination page = new Pagination(pageNo, pageSize);
        List<RsDocFilesModel> list = rsDocFileTemplateDao.getDocFileList(page, docName);//获取所有模板
        List<Integer> ids = new ArrayList<>();
        if (list.size() > 0 && list != null) {
            for (RsDocFilesModel rsDocFilesModel : list) {
                ids.add(rsDocFilesModel.getId());//获取所有模板id
            }
            List<RsDocFileTemplateModel> rsDocFileTemplateModels = rsDocFileTemplateDao.getDataList(ids);
            Map<Integer, List<RsDocFileTemplateModel>> map1 = new HashMap<>();
            List<RsDocFileTemplateModel> list1;
            for (RsDocFileTemplateModel rs : rsDocFileTemplateModels) {
                if (map1.get(rs.getDocFileId()) != null) {
                    list1 = map1.get(rs.getDocFileId());
                    list1.add(rs);
                    map1.put(rs.getDocFileId(), list1);
                } else {
                    list1 = new ArrayList<>();
                    list1.add(rs);
                    map1.put(rs.getDocFileId(), list1);
                }
            }
            for (RsDocFilesModel rsDocFilesModel : list) {//
                rsDocFilesModel.setRsDocFileTemplateModels(map1.get(rsDocFilesModel.getId()));
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public Boolean delTemplate(Integer id) {
        RsDocFileTemplateEntity rsDocFileTemplateEntity = rsDocFileTemplateDao.selectById(id);
        //默认模板不能删除
        if (rsDocFileTemplateEntity.getDefaultTemplate()) {
            return false;
        }
        Integer num = projectDocFileDao.queryFileById(id);
        //该模板有在使用的情况下不能删除
        if (num > 0) {
            return false;
        }
        return rsDocFileTemplateDao.delTemplate(id) > 0;

    }

    @Override
    public List<RsDocFileTemplateEntity> findDocFileId(Integer docFileId) {
        return rsDocFileTemplateDao.findDocFileId(docFileId);
    }

}
