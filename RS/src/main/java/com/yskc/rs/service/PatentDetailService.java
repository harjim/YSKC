package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.project.PatentFileEntity;
import com.yskc.rs.models.Patent.PatentDetailModel;
import com.yskc.rs.models.Patent.QueryPatentDetialModel;
import com.yskc.rs.models.PatentPlan.PatentFileModel;
import com.yskc.rs.models.PatentPlan.RelatedProjectModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.PatentDetailExcel;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/29 11:36
 * description:
 */
public interface PatentDetailService {
    /**
     * 检查专利号是否唯一
     *
     * @param patentNo
     * @return
     * @throws OwnerException
     */
    Boolean checkPatentSole(String patentNo, UserInfo userInfo) throws OwnerException;

    /**
     * 添加专利
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean addPatent(PatentDetailModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 修改专利
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean updatePatent(PatentDetailModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 查询专利列表
     *
     * @param query
     * @param userInfo
     * @return
     */
    PageModel<List<PatentDetailModel>> getPatentList(QueryPatentDetialModel query, UserInfo userInfo);

    /**
     * 删除专利
     *
     * @param patentDetailModel
     * @return
     */
    Boolean delPatent(PatentDetailModel patentDetailModel) throws OwnerException;

    /**
     * 导入专利
     *
     * @param userInfo
     * @param list
     * @return
     * @throws OwnerException
     */
    String importPatent(UserInfo userInfo, List<PatentDetailExcel> list) throws OwnerException;

    /**
     * 关联项目
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean relatedProject(RelatedProjectModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取权利要求内容/说明书内容
     *
     * @param id
     * @return
     * @throws OwnerException
     */
    Map<String, String> getSpecification(Integer id) throws OwnerException;

    /**
     * 获取专利附件列表
     *
     * @param patentNo
     * @return
     */
    Map<Integer, List<PatentFileModel>> getPatentFiles(String patentNo);

    /**
     * 添加专利文件
     *
     * @param fileType
     * @param filePath
     * @param originalFilename
     * @param userInfo
     * @param patentNo
     * @return
     */
    Integer addFile(Integer fileType, String filePath, String originalFilename, UserInfo userInfo, String patentNo);

    /**
     * 删除专利附件
     *
     * @param patentFile
     * @return
     */
    Boolean delPatentFile(PatentFileEntity patentFile);

    /**
     * 获取专利附件
     *
     * @param id
     * @return
     */
    PatentFileEntity getPatentFile(Integer id);
}
