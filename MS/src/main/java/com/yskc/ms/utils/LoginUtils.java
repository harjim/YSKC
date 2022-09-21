package com.yskc.ms.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.stereotype.Component;
@Component
public class LoginUtils {
     WebClient webClient;
    public   LoginUtils(){
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.setJavaScriptTimeout(5000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setDoNotTrackEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(30000);
    }
    public  void  login(){
        String userName="18503080134";
        String password="Aps_100914@";
        String loginUrl="http://cpquery.cnipa.gov.cn/";
        try {
            final HtmlPage page = webClient.getPage(loginUrl);
            Thread.sleep(30000);
            System.out.println(page.getWebResponse().getContentAsString());
            //获取账号输入框结点，然后点击账号输入框，最后输入
            HtmlTextInput inputUserName= (HtmlTextInput) page.getElementById("username1");
            inputUserName.click();
            inputUserName.setText(userName);

            HtmlPasswordInput inputPasswd= (HtmlPasswordInput) page.getElementById("password1");
            inputPasswd.click();
            inputPasswd.setText(password);
            HtmlSpan htmlSpan=(HtmlSpan)page.getElementById("selectyzm_text");
            String aa=htmlSpan.getTextContent();
            HtmlButtonInput btnLogin= (HtmlButtonInput) page.getElementById("publiclogin");
            btnLogin.click();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}