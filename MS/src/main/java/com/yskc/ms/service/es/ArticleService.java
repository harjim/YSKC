package com.yskc.ms.service.es;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.Article.ArticleModel;
import com.yskc.ms.models.Article.QueryArticleModel;
import com.yskc.ms.models.UserInfo;

import java.util.List;

/**
 * 文章管理
 */
public interface ArticleService {
    /**
     * 查询文章列表
     */
    PageModel<List<ArticleModel>> getArtList(QueryArticleModel queryArticleModel) throws OwnerException;


    /**
     * 编辑/新增文章信息
     */
    Integer addArt(ArticleModel model, UserInfo userInfo) throws OwnerException;

    Integer editArt(ArticleModel model, UserInfo userInfo) throws OwnerException;



    /**
     * 删除文章
     */
    Integer delArt(List<Integer> ids);

}
