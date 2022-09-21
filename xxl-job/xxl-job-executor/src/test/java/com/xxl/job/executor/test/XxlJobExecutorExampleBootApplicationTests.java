package com.xxl.job.executor.test;

import com.xxl.job.executor.utils.DingUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XxlJobExecutorExampleBootApplicationTests {

    @Autowired
    private DingUtils dingUtils;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
//        Map<String, String> head = new HashMap<>();
//        head.put("bgcolor", "FFCCCCCC");
//        head.put("text", "项目审核");
//        List<Map<String, String>> form = new ArrayList<>();
//        Map<String, String> m1 = new HashMap<>();
//        m1.put("key", "发起人：");
//        m1.put("value", "ZDF");
//        Map<String, String> m2 = new HashMap<>();
//        m2.put("key", "项目：");
//        m2.put("value", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx公司2020【模块】审核");
//        Map<String, String> m3 = new HashMap<>();
//        m3.put("key", "进度：");
//        m3.put("value", "【xxxxx】已【xxx】");
//        form.add(m1);
//        form.add(m2);
//        form.add(m3);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String msg = DingMsgModel.buildToUser(302821533L, DingMsgUtils.buildOAMsg(head, form, "http://project.gdyskc.com:9000/customer/ProjectProgress"), "082409276331017544,266266601624152722,1586257494384882,15862575344515900");
//        HttpEntity<String> request = new HttpEntity<>(msg, headers);
//        String token = dingUtils.getAccessToken();
//        Object obj = restTemplate.exchange(MessageFormat.format(Constant.DING_MSG_URL, token), HttpMethod.POST, request, Object.class);
//        System.out.println(obj);

    }
}