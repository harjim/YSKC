package com.yskc.rs.service.impl;

import com.yskc.rs.dao.FileDao;
import com.yskc.rs.entity.FileEntity;
import com.yskc.rs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileDao fileDao;

    @Override
    public boolean insertFile(FileEntity entity) {
        FileEntity fileEntity = fileDao.selectByProjectId(entity.getProjectId());
        if(fileEntity==null){
            fileDao.insert(entity);
        }else {
            fileEntity.setFileName(entity.getFileName());
            fileEntity.setFilePath(entity.getFilePath());
            fileEntity.setLastUpdateTime(entity.getLastUpdateTime());
            fileEntity.setLastUpdatorId(entity.getLastUpdatorId());
            fileDao.updateById(fileEntity);
        }
        return true;
    }

    @Override
    public FileEntity queryFilePath(int companyId, Integer projectId) {
        FileEntity fileEntity = fileDao.queryFilePath(companyId, projectId);
//        String path = "";
//        if(fileEntity!=null){
//            path = fileEntity.getFilePath();
//        }
        return fileEntity;
    }

    @Override
    public Boolean delFile(FileEntity entity) {
        return fileDao.deleteByProjectId(entity);
    }
}
