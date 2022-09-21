package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.BuildConfigDao;
import com.yskc.rs.dao.DocListDao;
import com.yskc.rs.dao.DocOperatorDao;
import com.yskc.rs.dao.SysDocumentDao;
import com.yskc.rs.entity.BuildConfigEntity;
import com.yskc.rs.entity.DocListEntity;
import com.yskc.rs.entity.DocOperatorEntity;
import com.yskc.rs.entity.SysDocumentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.buildconfig.BuildConfigModel;
import com.yskc.rs.models.docList.QueryDocListModel;
import com.yskc.rs.models.sysDocument.DocListModel;
import com.yskc.rs.models.sysDocument.SysDocumentModel;
import com.yskc.rs.service.DocListService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DocListServiceImpl implements DocListService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DocListDao docListDao;
    @Autowired
    private SysDocumentDao sysDocumentDao;
    @Autowired
    private DocOperatorDao docOperatorDao;
    @Autowired
    private BuildConfigDao buildConfigDao;
    /**
     *
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean addDocList(UserInfo userInfo, DocListModel model) {
        Date date = new Date();
        DocListEntity entity = new DocListEntity();
        BeanUtil.copyProperties(model, entity);
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setOptional(false);
        entity.setCompanyId(userInfo.getCompanyId());

        DocOperatorEntity docOperatorEntity = new DocOperatorEntity();
        docOperatorEntity.setCreateTime(date);
        docOperatorEntity.setLastUpdateTime(date);
        docOperatorEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        docOperatorEntity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        docOperatorEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        docOperatorEntity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        docOperatorEntity.setCompanyId(userInfo.getCompanyId());
        docOperatorEntity.setOperators(model.getOperators() !=null?model.getOperators():"");
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            docListDao.insert(entity);
            docOperatorEntity.setListId(entity.getId());
            docOperatorDao.insert(docOperatorEntity);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addDocList", ex);
        }
        return true;
    }

    /**
     * 批量新增文档
     * @param info
     * @param year
     * @param targetYear
     * @return
     */
    @Override
    public boolean importFiles(UserInfo info, Integer year,Integer targetYear){
        List<SysDocumentEntity> entities = sysDocumentDao.selectByYearAndComId(year, info.getCompanyId());
        Date date = new Date();
        for (int i = 0; i < entities.size(); i++) {
            SysDocumentEntity entity = entities.get(i);
            entity.setCreatorId(info.getUserId());
            entity.setLastUpdatorId(info.getUserId());
            entity.setMsCreatorId(info.getMsUserId());
            entity.setMsLastUpdatorId(info.getMsUserId());
            entity.setCreateTime(date);
            entity.setYear(targetYear);
            entity.setLastUpdateTime(date);
            entity.setDownloadTimes(0);
        }
        List<BuildConfigModel> list = buildConfigDao.getList(info.getCompanyId(), year);
        if(list.size()>0) {
            for (BuildConfigModel model : list) {
                model.setId(null);
                model.setYear(targetYear);
            }
            buildConfigDao.batchSave(list);
        }
        sysDocumentDao.insertDocuments(entities);
        return true;
    }

    /**
     * 查询建设事项下对应年份的document附件
     * @param query
     * @param companyId
     * @return
     */
    @Override
    public PageModel<List<SysDocumentModel>> getDocuments(QueryDocListModel query, Integer companyId) throws OwnerException{
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            asc.add("createTime");
            page.setAscs(asc);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        List<SysDocumentModel> sysDocumentModelList = sysDocumentDao.selectFileNameAndId(page,query.getDocListId(), query.getYear(), companyId);
        if (CollectionUtil.isEmpty(sysDocumentModelList)) {
            throw new OwnerException("该建设事项所选年份下暂无附件。");
        }
        return PageUtils.build(page,sysDocumentModelList);
    }

    /**
     * 查询建设事项 存在文件的之前的年份
     * @param year
     * @param companyId
     * @return
     */
    @Override
    public List<Integer> getYear(Integer year,Integer companyId) {
        return sysDocumentDao.getYear(year, companyId);
    }

    /**
     *
     * @param id
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean del(Integer id) throws OwnerException {
        List<SysDocumentEntity> sysDocumentEntities = sysDocumentDao.selectByListId(id);
        if (sysDocumentEntities.size() > 0) {
            return false;
//            throw new OwnerException("所选文档项已上传文件，不能删除!");
        }
        return docListDao.deleteById(id) > 0;
    }

    /**
     *
     * @param userInfo
     * @param entity
     * @return
     */
    @Override
    public boolean editOperators(UserInfo userInfo, DocListModel entity) {
        Date date = new Date();
        DocOperatorEntity docOperatorEntity = docOperatorDao.queryOperator(userInfo.getCompanyId(),entity.getId());
        if(docOperatorEntity!=null){
            docOperatorEntity.setOperators(entity.getOperators());
            docOperatorEntity.setLastUpdateTime(date);
            docOperatorEntity.setLastUpdatorId(userInfo.getUserId());
            docOperatorEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            docOperatorDao.updateById(docOperatorEntity);
        }else{
            docOperatorEntity = new DocOperatorEntity();
            docOperatorEntity.setCompanyId(userInfo.getCompanyId());
            docOperatorEntity.setListId(entity.getId());
            docOperatorEntity.setOperators(entity.getOperators());
            docOperatorEntity.setCreateTime(date);
            docOperatorEntity.setCreatorId(userInfo.getUserId());
            docOperatorEntity.setMsCreatorId(userInfo.getMsUserId());
            docOperatorEntity.setLastUpdateTime(date);
            docOperatorEntity.setLastUpdatorId(userInfo.getUserId());
            docOperatorEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            docOperatorDao.insert(docOperatorEntity);
        }
        if(null != entity.getCompanyId() && entity.getCompanyId() > 0){
            docListDao.updateInfo(entity,userInfo.getUserId(),userInfo.getMsUserId(),date);
        }
        return true;
    }

    @Override
    public int getListType(Integer listId) {
        return docListDao.getListType(listId);
    }
}
