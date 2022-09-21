package com.yskc.ms.service.impl.es;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.es.ArticleDao;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.entity.es.ArticleEntity;
import com.yskc.ms.models.Article.ArticleModel;
import com.yskc.ms.models.Article.QueryArticleModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.es.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文章管理
 *
 * @author Administrator
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;

    @Override
    public PageModel<List<ArticleModel>> getArtList(QueryArticleModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("top","createTime"));
        }
        List<ArticleModel> list = articleDao.getArtList(page, query);
        if (!CollectionUtils.isEmpty(list)) {
            List<MiniUserModel> users = userDao.getUsers(list.stream().map(ArticleModel::getMsCreatorId).collect(Collectors.toSet()));
            if (!CollectionUtils.isEmpty(users)) {
                Map<Integer, String> userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), b -> b.getRealName(), (o, n) -> n));
                list.forEach(item -> {
                    item.setRealName(userMap.get(item.getMsCreatorId()));
                });
            }
        }

        //inner join ys_user ys on ys.id=a.creatorId
        return PageUtils.build(page, list);
    }

    @Override
    public Integer addArt(ArticleModel model, UserInfo userInfo) throws OwnerException {
        ArticleEntity art = new ArticleEntity();
        BeanUtils.copyProperties(model, art);
        Date now = new Date();
        Integer msUserId = userInfo.getId();
        art.setCreateTime(now);
        art.setLastUpdateTime(now);
        art.setMsCreatorId(msUserId);
        art.setMsLastUpdatorId(msUserId);
        return articleDao.insert(art);

    }

    @Override
    public Integer editArt(ArticleModel model, UserInfo userInfo) throws OwnerException {
        ArticleEntity art = new ArticleEntity();
        BeanUtils.copyProperties(model, art);
        art.setLastUpdateTime(new Date());
        art.setMsLastUpdatorId(userInfo.getId());
        return articleDao.updateById(art);
    }

    @Override
    public Integer delArt(List<Integer> ids) {
        return articleDao.deleteBatchIds(ids);
    }

}
