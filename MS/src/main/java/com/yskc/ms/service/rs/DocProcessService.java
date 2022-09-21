package com.yskc.ms.service.rs;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.models.DocProcessModel;
import com.yskc.ms.entity.rs.models.DocProcessTemplateListModel;
import com.yskc.ms.models.UserInfo;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-05 11:46
 * @Description: 过程管理接口类
 */
public interface DocProcessService {
     /**
      * 查询文件过程以及模板文件
      * @param pageNo
      * @param pageSize
      * @param processName
      * @return
      */
     PageModel<List<DocProcessModel>> queryDocProcess(int pageNo, int pageSize, String processName);

     /**
      * 通过id查询文件模板数据
      * @param pageNo
      * @param pageSize
      * @param id
      * @return
      */
     PageModel<List<DocProcessTemplateListModel>> getDataById(int pageNo, int pageSize,int id);

     /**
      * 新增过程文件
      * @param info
      * @param model
      * @return
      */
     boolean addDocProcessAndProcessTemplate(UserInfo info,DocProcessModel model);

     /**
      * 修改过程文件
      * @param info
      * @param model
      * @return
      */
     boolean updateDocProcessAndProcessTemplate(UserInfo info,DocProcessModel model);

     /**
      * 删除操作
      * @param model
      * @return
      */
     boolean delDocProcessAndProcessTemplate(DocProcessModel model);

     /**
      * 删除模板
      * @param id
      * @return
      */
     boolean delProcessTemplate(int id);
}
