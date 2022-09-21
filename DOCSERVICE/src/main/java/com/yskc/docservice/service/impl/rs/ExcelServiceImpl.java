package com.yskc.docservice.service.impl.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.utils.ImportExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 报表服务
 * @author huronghua
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    private static Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Autowired
    private DocServiceConfig docServiceConfig;
    /**
     * 根据上传文件，获取数据
     * 并保存文件留底
     * @param tempPath
     * @param multipartFile
     * @param beanType
     * @param <T>
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<T> getExcelList(String tempPath , MultipartFile multipartFile, Class<T> beanType) throws OwnerException {
        if (multipartFile.isEmpty()) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        String tempFileName = multipartFile.getOriginalFilename().replace(".xls", System.currentTimeMillis() + ".xls");
        File tempFile = new File(new File(tempPath).getAbsolutePath() + "/" + tempFileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("transferTo",ex);
        }
        return ImportExcelUtils.getList(tempFile, beanType);
    }

    /**
     * 根据上传文件，获取数据
     * @param tempPath
     * @param multipartFile
     * @param beanType
     * @param tableField
     * @param <T>
     * @return
     * @throws OwnerException
     */
    @Override
    public <T> List<T> getExcelList(String tempPath, MultipartFile multipartFile, Class<T> beanType, TableField tableField) throws OwnerException {
        if (multipartFile.isEmpty()) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        String tempFileName = multipartFile.getOriginalFilename().replace(".xls", System.currentTimeMillis() + ".xls");
        File tempFile = new File(new File(tempPath).getAbsolutePath() + "/" + tempFileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("transferTo",ex);
        }
        return ImportExcelUtils.getList(tempFile,beanType, tableField);
    }

    /**
     * 根据上传文件，获取数据
     * @param tempPath
     * @param multipartFile
     * @param beanType
     * @param tableField
     * @param <T>
     * @return
     * @throws OwnerException
     */
    @Override
    public <T> ExcelResult<T> getExcelResult(String tempPath, MultipartFile multipartFile, Class<T> beanType, TableField tableField) throws OwnerException {
        if (multipartFile.isEmpty()) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        String tempFileName = multipartFile.getOriginalFilename().replace(".xls", System.currentTimeMillis() + ".xls");
        File tempFile = new File(new File(tempPath).getAbsolutePath() + "/" + tempFileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("transferTo",ex);
        }
        return ImportExcelUtils.getExcelResult(tempFile,beanType, tableField,docServiceConfig.getMaxRow());
    }

}
