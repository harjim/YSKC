package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.FlowInstanceReportDao;
import com.yskc.ms.dao.rs.DocListDao;
import com.yskc.ms.dao.rs.RsDocFileDao;
import com.yskc.ms.dao.rs.RsDocFileTemplateDao;
import com.yskc.ms.entity.rs.DocListEntity;
import com.yskc.ms.entity.rs.RsDocFileTemplateEntity;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.doctemplate.QueryDocModel;
import com.yskc.ms.models.rs.DocumentListModel;
import com.yskc.ms.models.rs.NewReportsModel;
import com.yskc.ms.models.rs.QueryDocListModel;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import com.yskc.ms.service.rs.DocListService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DocListServiceImpl implements DocListService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DocListDao docListDao;
    @Autowired
    private FlowInstanceReportDao flowInstanceReportDao;

    @Autowired
    private RsDocFileTemplateDao docFileTemplateDao;

    @Autowired
    private RsDocFileDao rsDocFileDao;

    @Override
    public PageModel<List<DocListModel>> queryDocList(QueryDocModel query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, docListDao.queryDocList(page, query, perm));
    }

    @Override
    public boolean del(Integer id) {
        List<SysDocumentEntity> sysDocumentEntities = docListDao.selectByListId(id);
        if (sysDocumentEntities.size() > 0) {
            return false;
        }
        return docListDao.deleteById(id) > 0;
    }

    @Override
    public Integer addDocList(UserInfo userInfo, DocListEntity entity) {
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setCreatorId(-1);
        entity.setLastUpdatorId(-1);
        entity.setMsCreatorId(userInfo.getId());
        entity.setMsLastUpdatorId(userInfo.getId());
        entity.setOptional(false);
        entity.setCompanyId(0);
        entity.setSubClassify(entity.getSubClassify() != null ? entity.getSubClassify() : "");
        entity.setDesciption(entity.getDesciption() != null ? entity.getDesciption() : "");
        docListDao.insert(entity);
        return 1;
    }

    @Override
    public Integer updateDocList(UserInfo userInfo, DocListEntity entity) {
        Date date = new Date();
        entity.setLastUpdateTime(date);
        entity.setLastUpdatorId(-1);
        entity.setMsLastUpdatorId(userInfo.getId());
        docListDao.updateById(entity);
        return 2;
    }

    @Override
    public Integer selectMaxSeq(Integer listType) {
        return docListDao.selectMaxSeq(listType) != null ? docListDao.selectMaxSeq(listType) + 1 : 1;
    }

    @Override
    public PageModel<List<NewReportsModel>> getDocList(QueryDocListModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, docListDao.getDocList(page, query.getProjectId()));
    }

    @Override
    public PageModel<List<DocumentListModel>> getDocumentList(QueryDocListModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("sdl.seq");
            ascs.add("sd.createTime");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, docListDao.getDocuments(page, query));
    }

    @Override
    public Integer insert(RsDocFileTemplateEntity entity, Integer userId) {
        Date date = new Date();
        entity.setMsCreatorId(userId);
        entity.setMsLastUpdatorId(userId);
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setCreatorId(-1);
        entity.setLastUpdatorId(-1);
        return docFileTemplateDao.insert(entity);
    }

    @Override
    public Boolean update(RsDocFileTemplateModel model, Integer userId) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            model.setMsLastUpdatorId(userId);
            model.setLastUpdateTime(new Date());
            rsDocFileDao.updateDocFile(model);
            docFileTemplateDao.updateDefault(model);
            docFileTemplateDao.updateDefaultTemplate(model);


            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("保存失败");
        }
        return true;
    }


}
