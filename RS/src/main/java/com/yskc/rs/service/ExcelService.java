package com.yskc.rs.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.excel.ExcelResult;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.function.Consumer;

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
    <T> List<T> getExcelList(String tempPath ,MultipartFile multipartFile, Class<T> beanType) throws OwnerException;

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
    <T> List<T> getExcelList(String tempPath ,MultipartFile multipartFile, Class<T> beanType,TableField tableField) throws OwnerException;

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
