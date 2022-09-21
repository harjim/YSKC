package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.SolutionDao;
import com.yskc.ms.dao.ms.SolutionShareDao;
import com.yskc.ms.entity.ms.SolutionEntity;
import com.yskc.ms.entity.ms.SolutionShareEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.solution.QuerySolutionModel;
import com.yskc.ms.models.solution.SolutionModel;
import com.yskc.ms.models.solution.SolutionShareModel;
import com.yskc.ms.service.ms.SolutionService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:24
 * @Description: 知识库业务实现层
 */
@Service
public class SolutionServiceImpl implements SolutionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SolutionDao solutionDao;

    @Autowired
    private SolutionShareDao solutionShareDao;

    @Override
    public PageModel<List<SolutionModel>> getList(QuerySolutionModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if(CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())){
            page.setDescs(CollUtil.newArrayList("s.createTime"));
        }
        return PageUtils.build(page,solutionDao.getList(page,query,dataPerm));
    }

    @Override
    public PageModel<List<SolutionModel>> getShareList(QuerySolutionModel query, UserInfo userInfo) {
        Pagination page = query.getPagination();
        if(CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())){
            page.setDescs(CollUtil.newArrayList("rate","s.createTime"));
        }
        return PageUtils.build(page,solutionDao.getShareList(page,query,userInfo.getId(),userInfo.getDeptFullPaths()));
    }

    @Override
    public Boolean add(SolutionModel solution, UserInfo userInfo) {
        SolutionEntity entity = new SolutionEntity();
        BeanUtils.copyProperties(solution,entity);
        entity.create(userInfo.getId(),new Date());
        return solutionDao.insert(entity) > 0;
    }

    @Override
    public Boolean edit(SolutionModel solution, UserInfo userInfo) {
        SolutionEntity entity = new SolutionEntity();
        BeanUtils.copyProperties(solution,entity);
        entity.update(userInfo.getId(),new Date());
        return solutionDao.updateSolution(entity) > 0;
    }

    @Override
    public Boolean delete(List<Integer> ids)throws OwnerException {
        if(CollUtil.isEmpty(ids)){
            return true;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            solutionDao.deleteBatchIds(ids);
            solutionShareDao.deleteShare(ids);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            throw new OwnerException("删除失败");
        }
    }

    @Override
    public List<SolutionShareModel> getShareInfo(Integer solutionId) {
        return solutionShareDao.getShareInfo(solutionId);
    }

    @Override
    public Boolean share(List<SolutionShareEntity> share, UserInfo userInfo)throws OwnerException {
        if(CollectionUtils.isEmpty(share)){
            throw new OwnerException("分享失败");
        }
        Integer userId = userInfo.getId();
        Date now = new Date();
        share.forEach(item->item.create(userId,now));
        return solutionShareDao.inertOrUpdate(share) > 0;
    }

    @Override
    public Boolean deleteShare(List<Integer> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return true;
        }
        return solutionShareDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean rate(SolutionEntity solution,Integer userId) {
        return solutionDao.updateRate(solution.getRate(),solution.getId(),userId,new Date()) > 0;
    }

}
