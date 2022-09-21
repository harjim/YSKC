package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.Article.ArticleModel;
import com.yskc.ms.models.Article.QueryArticleModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.service.es.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hsx
 */

@Api(tags = "文章类接口", value = "文章类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/getArtList")
    @PermissionRequired(perms = "sys:article:search")
    @ApiOperation(notes = "获取文章列表", value = "获取文章列表")
    public PageModel<List<ArticleModel>> getArtList(QueryArticleModel query) throws OwnerException{
       return articleService.getArtList(query);
    }

    @PostMapping("/addArt")
    @SystemLog(description = "新增文章", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:article:add")
    @ApiOperation(notes = "新增文章", value = "新增文章")
    public Integer addArt(@RequestBody @Validated ArticleModel model) throws OwnerException{
        return articleService.addArt(model,getUserInfo());
    }

    @PostMapping("/editArt")
    @SystemLog(description = "编辑文章", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:article:edit")
    @ApiOperation(notes = "编辑文章", value = "编辑文章")
    public Integer editArt(@RequestBody @Validated ArticleModel model) throws OwnerException{
        return articleService.editArt(model,getUserInfo());
    }

    @PostMapping("/delArt")
    @SystemLog(description = "删除文章", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:article:del")
    @ApiOperation(notes = "删除文章", value = "删除文章")
    public Integer delArt(@RequestBody BatchDeleteModel deleteModel) {
        return articleService.delArt(deleteModel.getIds());
    }
}
