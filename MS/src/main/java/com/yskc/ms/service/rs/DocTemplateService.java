package com.yskc.ms.service.rs;


import com.yskc.ms.entity.rs.DocTemplateEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.doctemplate.DocTemplateModel;
import com.yskc.common.model.PageModel;
import java.util.List;


/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-08-02 16:04
 * @Description: DocTemplateService
 */
public interface DocTemplateService {
    /**
     * 获取文档模板
     * @param pageNo
     * @param pageSize
     * @param docName
     * @return
     */
    PageModel<List<DocTemplateEntity>> queryDocTemplateList(int pageNo, int pageSize, String docName);

    /**
     * 新增文档模板
     * @param info
     * @param model
     * @return
     */
    boolean addDocTemplate(UserInfo info, DocTemplateModel model);

    /**
     * 修改文档模板
     * @param info
     * @param model
     * @return
     */
    boolean updateDocTemplate(UserInfo info,DocTemplateModel model);

    /**
     * 删除文档模板
     * @param model
     * @return
     */
    boolean delDocTemplate(DocTemplateModel model);

}
