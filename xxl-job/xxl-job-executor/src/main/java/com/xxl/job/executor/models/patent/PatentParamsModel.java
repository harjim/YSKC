package com.xxl.job.executor.models.patent;

import com.xxl.job.executor.core.config.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-09 17:20
 * @Description: 专利参数model
 */
public class PatentParamsModel {

    private String searchQueryFormat = "F%20XX%20({0}%2FDZ)";
    private String searchParams;
    public static Map<String, String> QUERY_HEADER = new HashMap<>();
    public final static Map<String, String> LOGIN_HEADER = new HashMap<>();
    public final static Map<String,String> SIPOP_HEADER = new HashMap<>();

    StringBuilder pageParams = new StringBuilder("'Type':'CN','SourceType':'FI','rows': '" + Constant.ROW_LIMIT.toString() + "','Sort':'PD|DESC',");

    public static String getLogParams() {
        return "__VIEWSTATE=%2FwEPDwULLTIxMjczOTU5MTZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBRBJbWFnZUJ1dHRvbkxvZ2lubwV9ptrFQ6McGQvlS7KXxxrvRXYQUmvVRHxcxPZoHNs%3D&__EVENTVALIDATION=%2FwEWBALg%2B8S7CQLQ59eABwLSxeCRDwLz%2BOWrDouhzVMhAbvhgICwEZA9zOPcKN4xuHuk2z3wFLIEwx8u&TextBoxAccount=blueToWhite&Password=123456789Q&ImageButtonLogin.x=64&ImageButtonLogin.y=27";
    }

    public String getSearchParams() {
        return searchParams;
    }

    static {
//        LOGIN_PARAMS.add(new NameValuePair("__VIEWSTATE", "/wEPDwULLTIxMjczOTU5MTZkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBRBJbWFnZUJ1dHRvbkxvZ2lubwV9ptrFQ6McGQvlS7KXxxrvRXYQUmvVRHxcxPZoHNs="));
//        LOGIN_PARAMS.add(new NameValuePair("__EVENTVALIDATION", "/wEWBALg+8S7CQLQ59eABwLSxeCRDwLz+OWrDouhzVMhAbvhgICwEZA9zOPcKN4xuHuk2z3wFLIEwx8u"));
//        LOGIN_PARAMS.add(new NameValuePair("TextBoxAccount", "blueToWhite"));
//        LOGIN_PARAMS.add(new NameValuePair("Password", "1234567Q"));
//        LOGIN_PARAMS.add(new NameValuePair("imgBtnYk.x", "60"));
//        LOGIN_PARAMS.add(new NameValuePair("imgBtnYk.y", "22"));
        QUERY_HEADER.put("Content-Type", "application/json; charset=UTF-8");
        QUERY_HEADER.put("X-Requested-With", "XMLHttpRequest");
        QUERY_HEADER.put("Accept", "application/json, text/javascript, */*");
        LOGIN_HEADER.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        LOGIN_HEADER.put("Content-Type", "application/x-www-form-urlencoded");
        SIPOP_HEADER.put("Cookie","acw_tc=ac11000115984021232323766e01101b8a0a6470769997c881849224ec26d9; ssxmod_itna2=eqmhBKY5GK7IgnDz6KRx0KDQSexWu4DtYtbG8D6p+cCD0Huq03Osque+8dse2ubWqqircGh2YGF0ck2d47I2WSDr2tQp9GNX4=1hiUXeE7IYCq4TiudC8tVqtu8NvXAFyMcUUySLg87YDv+GwYWoquDrCY0UEbqIgbwAoLE=dhDrPLWLqqCiicnNohQWThhDYIfp88DeyR3wuG4NeKnfCf4IxemD2GrQ28BPIR+EN4SKKqf7CAurv=Z3p0TEmHmoh74t=TW3m5Q2vv1QUPOEK0iFHKA7sIOxO1HEqQeq2AsvOQulX/iFWkxBrz+LxoLxKok=DtoliKLKDuOBEHNnYR+Yo8K4RkEghzCWcBW3fWr+i7LN+Cr4+werF5fDfQaaOit8K1qK32PylQ80DeTiHwFoL8qbY1wYowmihvOGYKfkHOo7mQiRmgbvd=3GrQo1t+7HXBDTbq3eK+08qi6KZOoYjfZ7f1E57fYajeZqbjGSW+tWvW43eaa0Q67qKFLLY+wgn8d6An2kx6pbqRgl=u7Onxt501KbAqYxUOopdazKngoKz9IVOGP3dsDqEDDwcKDjKDeTq4D=; acw_sc__v2=5f6d41cdf3487b4a7c9e1bcfc91c78841a28ea7f; acw_sc__v3=5f6d41d2047072d4e8c1489a5364d8fa27e9c6f3; Hm_lvt_c558963ed6baaeadc348c6a317e4c44f=1600758552,1600828019,1600906010,1600995800; Hm_lpvt_c558963ed6baaeadc348c6a317e4c44f=1600995841; ssxmod_itna=CqGxBDgDnD9D2GiDXWtEwrYri=Kt0Q5TFtDlg0oxA5D8D6DQeGTbX3KebDRiju17GoGGUGA5fqei1Co4KYQ6Wv4oDU4i8DCqnt3bDenQD5xGoDPxDeDADYo6DAqiOD7k=DEDmb8DYxGAnKqDgDYQDGLPjD7QDId=Keo=V0bigWetilux5bqDM7eGXBDW4bu0i5pGlQOQDzLaDtwtgbM=Px0PzW0m1fb51DhH5375eQY5qmDeitbxiWDhx7GvxK0xP9OGClR41SFD;acw_sc__v2=6f865aa7ff90ad5d8b27861637ecf7766178b61d");
    }

    public PatentParamsModel(String query) throws UnsupportedEncodingException {
        searchParams = "{'strSearchQuery': '" + MessageFormat.format(searchQueryFormat, URLEncoder.encode(query, "utf-8")) + "','_strSdbType': 'CN','_sDoSrc': '1'}";

    }

    public void initParams(String nodeId, String itemCount) {
        pageParams.append("'ItemCount': '" + itemCount + "',").append("'NodeId': '" + nodeId + "',");
    }

    public String nextPageParams(Integer page) {
        return "{" + pageParams.toString() + "'pageindex': '" + page.toString() + "'}";
    }

}
