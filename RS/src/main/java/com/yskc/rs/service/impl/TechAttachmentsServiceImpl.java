package com.yskc.rs.service.impl;

import cn.hutool.core.io.FileUtil;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.tech.TechAttachmentsDao;
import com.yskc.rs.entity.tech.TechAttachmentsEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.TechAttachmentModel;
import com.yskc.rs.service.TechAttachmentsService;
import com.yskc.rs.utils.TransactionUtils;
import com.yskc.rs.utils.YsExcelUtils;
import com.yskc.rs.utils.YsPptUtils;
import com.yskc.rs.utils.YsWordUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

import static java.io.File.separatorChar;

/**
 * Created by hck
 * on 2020/8/14 13:36
 * description:技改资料清单
 */
@Service
public class TechAttachmentsServiceImpl implements TechAttachmentsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TechAttachmentsDao techAttachmentsDao;
    @Autowired
    private RsConfig rsConfig;

    @Override
    public List<TechAttachmentModel> getList(Integer projectId, UserInfo userInfo) {
        List<TechAttachmentModel> list=techAttachmentsDao.getByProjectId(projectId);
        return list;
    }

    @Override
    public Boolean add(UserInfo userInfo, TechAttachmentModel model) {
        Date date=new Date();
        TechAttachmentsEntity entity=new TechAttachmentsEntity();
        BeanUtils.copyProperties(model,entity);
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setCreatorId(userInfo.getUserId());
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setMsCreatorId(userInfo.getMsUserId());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        return techAttachmentsDao.insert(entity)>0;
    }

    @Override
    public Boolean edit(UserInfo userInfo, TechAttachmentModel model) throws OwnerException{
        TechAttachmentsEntity entity=techAttachmentsDao.selectById(model.getId());
        if(entity!=null){
            entity.setFilePath(model.getFilePath());
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setLastUpdateTime(new Date());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            return techAttachmentsDao.updateById(entity)>0;
        }
        throw new  OwnerException("未找到文件或已删除，更新失败");
    }

    @Override
    public Boolean checkFileExist(Integer id, String filePath) {
        TechAttachmentsEntity entity=techAttachmentsDao.selectById(id);
        if(entity!=null){
            String path=entity.getFilePath();
            List<String> pathList= Arrays.asList(path.split(","));
            for (String file:pathList) {
                if(file.equals(filePath)){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Object preview(String filePath) throws OwnerException {
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("无数据");
        }
        Path path = Paths.get(rsConfig.getUploadConfig().getImportPath(), filePath);
        String fullPath = path.toString();
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new OwnerException("不存在文件，请联系管理员");
        }
        try {
            if(ImageIO.read(file)!=null){
                Map<String,String> map=new HashMap<>();
                map.put("imgUrl","/import"+filePath);
                return map;
            }
        }catch (IOException e){
            logger.error("图片加载异常", e);
            e.printStackTrace();
        }
        String name = path.getFileName().toString();
        String fileName = name.substring(0, name.lastIndexOf("."));
        String targetFileName = path.getParent().toString() + separatorChar + fileName + ".html";
        File htmlFile = new File(targetFileName);
        if (htmlFile.exists()) {
            return FileUtil.readString(htmlFile, "utf-8");
        } else {
            String lowerName = name.toLowerCase();
            if (lowerName.endsWith(Constant.EXCEL_DOC)) {
                return YsWordUtils.docToHtml(fullPath);
            } else if (lowerName.endsWith(Constant.EXCEL_DOCX)) {
                return YsWordUtils.docxToHtml(fullPath);
            } else if (lowerName.endsWith(Constant.EXCEL_XLS) || lowerName.endsWith(Constant.EXCEL_XLSX)) {
                return YsExcelUtils.excelToHtml(fullPath, lowerName);
            } else if (lowerName.endsWith(Constant.EXCEL_PPT) || lowerName.endsWith(Constant.EXCEL_PPTX)) {
                return YsPptUtils.toHtml(fullPath);
            } else{
                return  filePath;
            }
        }
    }


    @Override
    public Map<String,String> upload(MultipartFile[] multipartFiles, TechAttachmentModel model,String dir,String saveName, UserInfo userInfo) throws OwnerException{
        Date date=new Date();
        Map<String,String> resultMap=new HashMap<>();
        String expertPath = rsConfig.getUploadConfig().getImportPath();
        List<String> filePaths=new ArrayList<>();
        if(multipartFiles.length>0&&multipartFiles!=null){
            for (MultipartFile file : multipartFiles) {
                String fileName = System.currentTimeMillis() +saveName;
                String filePath = dir+userInfo.getCompanyId()+"/" + fileName;
                File tempFile = new File(expertPath + filePath);
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(tempFile);
                    filePaths.add(filePath);
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    throw new OwnerException("上传失败");
                }
            }
            String path=StringUtils.join(filePaths,",");
            String result="";
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                Integer id=0;
                //更新操作
                if(model.getId()!=null&&model.getId()>0){
                    TechAttachmentsEntity entity=techAttachmentsDao.selectById(model.getId());
                    if(!StringUtils.isEmpty(entity.getFilePath())) {
                        result = MessageFormat.format("{0},{1}", entity.getFilePath(), path);
                    }else {
                        result=path;
                    }
                    entity.setFilePath(result);
                    entity.setLastUpdateTime(date);
                    entity.setLastUpdatorId(userInfo.getUserId());
                    entity.setMsLastUpdatorId(userInfo.getMsUserId());
                    techAttachmentsDao.updateById(entity);
                    id=model.getId();
                }else{
                    //添加新数据
                    TechAttachmentsEntity entity=new TechAttachmentsEntity();
                    BeanUtils.copyProperties(model,entity);
                    entity.setFilePath(path);
                    entity.setMsLastUpdatorId(userInfo.getMsUserId());
                    entity.setMsCreatorId(userInfo.getMsUserId());
                    entity.setCreateTime(date);
                    entity.setLastUpdatorId(userInfo.getUserId());
                    entity.setLastUpdateTime(date);
                    entity.setCreatorId(userInfo.getUserId());
                    result=path;
                    techAttachmentsDao.insert(entity);
                    id=entity.getId();
                }
                TransactionUtils.commit(transactionStatus);
                resultMap.put("id",id.toString());
                resultMap.put("path",result);
                return  resultMap;
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("uploadAttachment", ex);
                throw new OwnerException(ErrorEnum.FAIL);
            }

        }
        throw new OwnerException("上传文件为空，上传失败");
    }

}
