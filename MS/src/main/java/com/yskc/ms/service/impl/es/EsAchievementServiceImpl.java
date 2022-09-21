package com.yskc.ms.service.impl.es;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.es.EsAchievementDao;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.models.CountModel;
import com.yskc.ms.models.newexpert.achievement.AchievementLogModel;
import com.yskc.ms.models.newexpert.achievement.AchievementModel;
import com.yskc.ms.models.newexpert.achievement.QueryAchievementModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.es.EsAchievementService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @DateTime: 2021/11/12 9:29
 * @Description:
 * @author: hsx
 */
@Service
public class EsAchievementServiceImpl implements EsAchievementService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsAchievementDao esAchievementDao;

    @Autowired
    private UserDao userDao;

    @Override
    public PageModel<List<AchievementModel>> getList(QueryAchievementModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(Arrays.asList("a.lastUpdateTime"));
        }
        List<AchievementModel> list = esAchievementDao.getList(page,model);
        return PageUtils.build(page,list);
    }

    @Override
    public AchievementModel getData(Integer id) {
        AchievementModel achievement = esAchievementDao.getData(id);
        String keywords = achievement.getKeywords();
        if (StringUtils.hasLength(keywords)) {
            achievement.setKeywords(keywords.substring(1,keywords.length()-1));
        }
        String description = achievement.getDescription();
        achievement.setDescription((String) CommonUtils.strSpecialTransfer(description,false));
        String innovation = achievement.getInnovation();
        achievement.setInnovation((String) CommonUtils.strSpecialTransfer(innovation,false));
        return achievement;
    }

    @Override
    public Boolean audit(AchievementLogModel model) {
        model.setCreateTime(new Date());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.ES);
        try {
            esAchievementDao.editStatus(model);
            esAchievementDao.insertLog(model);
            TransactionUtils.commit(DataSourceEnum.ES, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.ES,transactionStatus);
            return false;
        }
    }

    @Override
    public List<AchievementLogModel> getLogs(Integer id) {
        return esAchievementDao.getLogs(id);
    }

    @Override
    public Map<String, Integer> getCount() {
        List<CountModel> countModels = esAchievementDao.getCount();
        Map<String, Integer> map = new HashMap<>();
        Integer sum = 0;
        for (CountModel countModel : countModels) {
            Integer count = countModel.getCount();
            sum += count;
            map.put(countModel.getValue(), count);
        }
        //全部状态统计
        map.put("-1", sum);
        return map;
    }
}
