package com.yskc.rs.controller;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.IpUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 基础控制类
 *
 * @author huronghua
 */
@RestController
public class BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpServletResponse response;
    @Autowired
    public ExcelService excelService;

    /**
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 获取当前用户的会话token
     *
     * @return
     */
    public String getToken() {
        return request.getHeader(Constant.TOKEN_KEY);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() throws OwnerException {
        try {
            UserInfo userInfo = (UserInfo) request.getAttribute(Constant.USER_SESSION_KEY);
            if (userInfo == null) {
                throw new OwnerException(ErrorEnum.NOT_SESSION);
            }
            return userInfo;
        } catch (OwnerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    /**
     * @Description: 导出文件
     * @Param: [writer, fileName]
     * @return: void
     * @Author: zhangdingfu
     * @date: 2019-07-23
     */
    public OutputStream outGeneralFile(String fileName) throws IOException {
        OutputStream out = response.getOutputStream();
        response.addHeader("content-type", "text/html;charset=UTF-8");
        response.addHeader("filename", URLEncoder.encode(fileName, "UTF-8"));
        response.addHeader("Access-Control-Expose-Headers", "filename");
        return out;
    }

    /**
     * 下载文件
     *
     * @param path
     * @param fileName
     */
    public void download(String path, String fileName) throws OwnerException {
        try {
            OutputStream out = outGeneralFile(fileName);
            InputStream is = new FileInputStream(path);
            IoUtil.copy(is, out);
            is.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new OwnerException("系统找不到指定的文件，下载失败。");
        }
    }

    @Autowired
    private RsConfig rsConfig;

    /**
     * 根据模板导出excel文件
     *
     * @param data
     * @param templatePath
     * @param fileName
     * @throws OwnerException
     */
    protected void exportFileByTemplate(Map data, String templatePath, String fileName) throws OwnerException {
        try (OutputStream out = response.getOutputStream()) {
            response.addHeader("content-type", "text/html;charset=UTF-8");
            response.addHeader("filename", URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Access-Control-Expose-Headers", "filename");
            YsExcelUtils.generalReport(data, Paths.get(rsConfig.getUploadConfig().getResourcePath(), templatePath).toString(), (workbook) -> {
                if (workbook != null) {
                    try {
                        workbook.write(out);
                        workbook.close();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            out.flush();
        } catch (Exception ex) {
            throw new OwnerException("导出失败," + ex.getMessage());
        }

    }

    /**
     * 获取请求ip
     * @return
     */
    protected String getRequestIp(){
        return IpUtils.getIpAddr(request);
    }
}
