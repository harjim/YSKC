package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docList.QueryDocListModel;
import com.yskc.rs.models.sysDocument.DocListModel;
import com.yskc.rs.models.sysDocument.SysDocumentModel;

import java.util.List;

/**
 * @author Administrator
 */
public interface DocListService {
    /**
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean addDocList(UserInfo userInfo, DocListModel entity);

    /**
     *
     * @param id
     * @return
     * @throws OwnerException
     */
    boolean del(Integer id) throws OwnerException;

    /**
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean editOperators(UserInfo userInfo, DocListModel entity);

    /**
     * 根据listId查出listType
     * @param listId
     * @return
     */
    int getListType(Integer listId);

    /**
     * 批量新增文档
     *
     * @param info
     * @param sourceYear
     * @param targetYear
     * @return
     */
    boolean importFiles(UserInfo info, Integer sourceYear,Integer targetYear);

    /**
     * 查询建设事项下对应年份的document附件
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<SysDocumentModel>> getDocuments(QueryDocListModel query, Integer companyId) throws OwnerException;

    /**
     * 查询建设事项 存在文件的之前的年份
     * @param year
     * @param companyId
     * @return
     */
    List<Integer> getYear(Integer year, Integer companyId);
}
