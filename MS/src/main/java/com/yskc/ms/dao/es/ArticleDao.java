package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.ArticleEntity;
import com.yskc.ms.models.Article.ArticleModel;
import com.yskc.ms.models.Article.QueryArticleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends BaseMapper<ArticleEntity> {

    List<ArticleModel> getArtList(@Param("page") Pagination page , @Param("query") QueryArticleModel query);
}
