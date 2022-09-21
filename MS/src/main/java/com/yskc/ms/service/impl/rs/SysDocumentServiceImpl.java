package com.yskc.ms.service.impl.rs;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.hutool.core.io.FileUtil;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.rs.RdDeptEntity;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.SysDocumentModel;
import com.yskc.ms.models.rs.buildconfig.BuildConfigDocModel;
import com.yskc.ms.service.rs.SysDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @DateTime: 2021/9/6 16:46
 * @Description:成果管理/机构建设事项
 */
@Service
public class SysDocumentServiceImpl implements SysDocumentService {
    @Autowired
    private SysDocumentDao sysDocumentDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private BuildConfigDao buildConfigDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Override
    public List<DocListModel> queryAppendixDocList(Integer listType, Integer companyId, String patentNo, Integer year) {
        List<DocListModel> docListModels = sysDocumentDao.queryDocList(listType, companyId);
        if (docListModels.size() == 0) {
            return null;
        }

        List<Integer> ids = docListModels.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(DocListModel::getId).collect(Collectors.toList());
        List<SysDocumentModel> sysDocumentModels = sysDocumentDao.queryDocForDocList(companyId, ids, 0, patentNo, listType==2001?0:year);
        for (int i = 0; i < docListModels.size(); i++) {
            List<SysDocumentModel> docModels = new ArrayList<>();
            for (SysDocumentModel model : sysDocumentModels) {
                if (docListModels.get(i).getId().equals(model.getListId())) {
                    docModels.add(model);
                }
            }
            docListModels.get(i).setDocList(docModels);
        }
        return docListModels;
    }

    @Override
    public SysDocumentEntity getFileById(Integer id) {
        return sysDocumentDao.selectById(id);
    }

    @Override
    public Integer updateFile(SysDocumentEntity entity) {
        return sysDocumentDao.updateById(entity);
    }

    @Override
    public Map<String, Object> getBuildFileData(Integer year,String companyName,Integer companyId,String path) {
        Map<String, Object> result = new HashMap<>();
        BuildConfigDocModel techConfig = new BuildConfigDocModel();
        BuildConfigDocModel financeConfig = new BuildConfigDocModel();
        //公司名称
        result.put("companyName",companyName);
        RdDeptEntity parentNode = employeeDao.getParentNode(year, companyId);
        if (null != parentNode && !StringUtils.isEmpty(parentNode.getDeptName())) {
            result.put("deptName", parentNode.getDeptName());
        } else {
            result.put("deptName", "研发部门");
        }
        List<BuildConfigDocModel> list = buildConfigDao.getConfigList(companyId,year);
        //机构事项配置
        Set enumbers = new HashSet();
        if(list.size()>0){
            techConfig = list.get(0);
            financeConfig = list.get(1);
        }
        result.put("techConfig",techConfig);
        result.put("financeConfig",financeConfig);
        //公司logo
        String companyLogoPath = companyDao.getLogo(companyId);
        String s = path + companyLogoPath;
        result.put("logo", !StringUtils.isEmpty(companyLogoPath) && FileUtil.exist(s) ?
                new ImageEntity(s, 120, 120) : "");
        return result;
    }

    @Override
    public List<SysDocumentEntity> getReportByProject(Integer projectId) {
        return sysDocumentDao.getReportByProject(projectId);
    }
}
