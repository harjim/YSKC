package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.DocListEntity;
import com.yskc.ms.entity.rs.RsDocFileTemplateEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.doctemplate.QueryDocModel;
import com.yskc.ms.models.rs.DocumentListModel;
import com.yskc.ms.models.rs.NewReportsModel;
import com.yskc.ms.models.rs.QueryDocListModel;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;

import java.util.List;

public interface DocListService {
    PageModel<List<DocListModel>> queryDocList(QueryDocModel query, DataPermModel perm);

    boolean del(Integer id);

    Integer addDocList(UserInfo userInfo, DocListEntity entity);

    Integer updateDocList(UserInfo userInfo, DocListEntity entity);

    Integer selectMaxSeq(Integer listType);

    /**
     * 获取查新报告列表
     *
     * @param model
     * @return
     */
    PageModel<List<NewReportsModel>> getDocList(QueryDocListModel model);

    /**
     * 创新体系上传文件列表
     *
     * @param model
     * @return
     */
    PageModel<List<DocumentListModel>> getDocumentList(QueryDocListModel model);

    Integer insert(RsDocFileTemplateEntity entity, Integer userId);

    Boolean update(RsDocFileTemplateModel model, Integer userId) throws OwnerException;
}
