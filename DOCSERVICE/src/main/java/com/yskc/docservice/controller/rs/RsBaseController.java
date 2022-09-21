package com.yskc.docservice.controller.rs;

import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.IpUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.service.rs.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基础控制类
 *
 * @author huronghua
 */
@RestController
public class RsBaseController {
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
    public RsUserInfo getUserInfo() throws OwnerException {
        try {
            RsUserInfo userInfo = (RsUserInfo) request.getAttribute(Constant.USER_SESSION_KEY);
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
     * 获取请求ip
     *
     * @return
     */
    protected String getRequestIp() {
        return IpUtils.getIpAddr(request);
    }
}
