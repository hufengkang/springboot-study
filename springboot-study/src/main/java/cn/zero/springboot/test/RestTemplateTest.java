package cn.zero.springboot.test;

import cn.zero.springboot.SpringbootApplication;
import cn.zero.springboot.utils.RestTemplateUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guiyuoneiros
 * @since 2021/2/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class RestTemplateTest {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Test
    public void testGet() {
        String url = "http://www.baidu.com?name={key1}&age={key2}";
        Map<String, Object> uriVariableMap = new HashMap<>(4);
        uriVariableMap.put("key1", "guiyu");
        uriVariableMap.put("key2", "oneiros");
        String responseStr = restTemplateUtils.get(url, uriVariableMap);
        System.out.println("------------end---------------");

    }

    @Test
    public void testPost() {
//        -----------------------------------------------------------------------
        String url = "http://www.baidu.com?name={1}&age={2}";
        String responseStr = restTemplateUtils.post(url, null, "zhangsan", 18);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("bodyKey1", "value1");
        jsonObj.put("bodyKey2", "value2");
        responseStr = restTemplateUtils.post(url, jsonObj.toJSONString(), "zhangsan", 18);
//        -----------------------------------------------------------------------
        url = "http://www.baidu.com";
        Map<String, Object> requestBodyMap = new HashMap<>();
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBodyMap, httpHeaders);

        //若不需要uriVariable时 设置 ""
        restTemplateUtils.post(url, httpEntity, "");
        System.out.println("------------end---------------");


    }

    @Test
    public void testHttpsRequest() {
        String url = "http://www.baidu.com";
        url = "https://www.baidu.com";
        url = "https://localhost:8443/app/restTemplate/postForEntityWithParams";
        String responseBody = restTemplateUtils.httpsRequest(url, HttpMethod.GET, null);


        System.out.println("------------end---------------");

    }
}
