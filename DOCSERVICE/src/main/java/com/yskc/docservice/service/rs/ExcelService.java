package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 报表服务
 * @author huronghua
 */
public interface ExcelService {
    /**
     * 根据上传文件，获取数据
     * @param tempPath
     * @param multipartFile
     * @param beanType
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> List<T> getExcelList(String tempPath, MultipartFile multipartFile, Class<T> beanType) throws OwnerException;

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
    <T> List<T> getExcelList(String tempPath, MultipartFile multipartFile, Class<T> beanType, TableField tableField) throws OwnerException;

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
     <T> ExcelResult<T> getExcelResult(String tempPath, MultipartFile multipartFile, Class<T> beanType, TableField tableField) throws OwnerException;

}
