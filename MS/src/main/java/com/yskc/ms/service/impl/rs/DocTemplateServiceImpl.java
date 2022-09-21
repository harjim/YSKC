package com.yskc.ms.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.DocTemplateDao;
import com.yskc.ms.entity.rs.DocTemplateEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.doctemplate.DocTemplateModel;
import com.yskc.ms.service.rs.DocTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-08-02 16:08
 * @Description: 文档模板业务管理层
 */
@Service
public class DocTemplateServiceImpl implements DocTemplateService {
    @Autowired
    private DocTemplateDao docTemplateDao;

    @Override
    public PageModel<List<DocTemplateEntity>> queryDocTemplateList(int pageNo, int pageSize, String docName) {
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, docTemplateDao.queryDocTemplate(page, docName));
    }

    @Override
    public boolean addDocTemplate(UserInfo info, DocTemplateModel model) {
        DocTemplateEntity docTemplateEntity = new DocTemplateEntity();
        BeanUtil.copyProperties(model, docTemplateEntity);
        docTemplateEntity.setCreateTime(new Date());
        docTemplateEntity.setMsCreatorId(info.getId());
        docTemplateEntity.setLastMsUpdatorId(info.getId());
        docTemplateEntity.setLastUpdateTime(new Date());
        return docTemplateDao.insert(docTemplateEntity) > 0;
    }

    @Override
    public boolean updateDocTemplate(UserInfo info, DocTemplateModel model) {
        DocTemplateEntity docTemplateEntity = new DocTemplateEntity();
        BeanUtil.copyProperties(model, docTemplateEntity);
        docTemplateEntity.setLastMsUpdatorId(info.getId());
        docTemplateEntity.setLastUpdateTime(new DateTime());
        return docTemplateDao.updateById(docTemplateEntity) > 0;
    }

    @Override
    public boolean delDocTemplate(DocTemplateModel model) {
        return docTemplateDao.deleteById(model.getId()) > 0;
    }
}
