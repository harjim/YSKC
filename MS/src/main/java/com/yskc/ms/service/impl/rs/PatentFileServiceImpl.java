package com.yskc.ms.service.impl.rs;

import com.yskc.ms.dao.rs.PatentFileDao;
import com.yskc.ms.dao.rs.RsPatentPlanDao;
import com.yskc.ms.entity.rs.PatentFileEntity;
import com.yskc.ms.entity.rs.RsPatentPlanEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.PatentFileModel;
import com.yskc.ms.models.patentPlan.PatentOpinionModel;
import com.yskc.ms.service.rs.PatentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 16:14
 * @Description:
 */
@Service
public class PatentFileServiceImpl implements PatentFileService {
    @Autowired
    private PatentFileDao patentFileDao;
    @Autowired
    private RsPatentPlanDao rsPatentPlanDao;

    @Override
    public Integer addFile(Integer patentId, Integer fileType, String filePath, String fileName, UserInfo userInfo, String patentNo) {
        if (!StringUtils.isEmpty(patentNo)) {
            RsPatentPlanEntity patent = rsPatentPlanDao.getPatent(null, patentNo);
            if (patent != null) {
                patentId = patent.getId();
            }
        }
        PatentFileEntity patentFile = new PatentFileEntity(patentId, fileName, filePath, fileType, userInfo, patentNo);
        patentFileDao.insert(patentFile);
        return patentFile.getId();
    }

    @Override
    public Map<Integer, Object> getPatentFiles(Integer patentId, String patentNo) {
        Map<Integer, Object> fileMap = new HashMap<>();
        RsPatentPlanEntity patentPlan;
        if (patentId != null) {
            patentPlan = rsPatentPlanDao.getPatent(patentId, null);
        } else {
            patentPlan = rsPatentPlanDao.getPatent(null, patentNo);
        }
        List<PatentFileModel> models;
        if (patentPlan != null) {
            patentId = patentPlan.getId();
            fileMap.put(7, patentPlan.getDisclosureParperPath());//交底书
            fileMap.put(8, patentPlan.getFilepath());//申请文件
            fileMap.put(9, patentPlan.getInventorInfo());//发明人信息
            models = patentFileDao.getPatentFiles(patentId, null);
        } else {
            models = patentFileDao.getPatentFiles(null, patentNo);
        }
        Map<Integer, List<PatentFileModel>> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(models)) {
            for (PatentFileModel model : models) {
                if (!dataMap.containsKey(model.getFileType())) {
                    List<PatentFileModel> list = new ArrayList<>();
                    dataMap.put(model.getFileType(), list);
                }
                dataMap.get(model.getFileType()).add(model);
            }
        }
        fileMap.putAll(dataMap);
        return fileMap;
    }

    @Override
    public Boolean delPatentFile(Integer id) {
        return patentFileDao.deleteById(id) > 0;
    }

    @Override
    public List<PatentOpinionModel> getOpinions(Integer patentId, String patentNo) {
        List<PatentOpinionModel> models = new ArrayList<>();
        RsPatentPlanEntity patentPlan = rsPatentPlanDao.getPatent(patentId, patentNo);
        if (patentPlan != null) {
            models = rsPatentPlanDao.getOpinionByPatent(patentId);
        }
        return models;
    }

    @Override
    public PatentFileEntity getById(Integer id, String patentNo) {
        return patentFileDao.selectById(id);
    }
}
