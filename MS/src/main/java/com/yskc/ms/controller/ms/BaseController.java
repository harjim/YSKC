package com.yskc.ms.controller.ms;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.IpUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基础控制类
 *
 * @author huronghua
 */
@RestController
public class BaseController {
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpServletResponse response;

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

    public DataPermModel getDataPerm() throws OwnerException {
        try {
            DataPermModel dataPerm = (DataPermModel) request.getAttribute(Constant.DATA_PERMISSION_KEY);
            return dataPerm;
        } catch (Exception ex) {
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    /**
     * @Description: 导出文件
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

    /**
     * 获取请求ip
     * @return
     */
    protected String getRequestIp(){
        return IpUtils.getIpAddr(request);
    }
}
