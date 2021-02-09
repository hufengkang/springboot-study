package cn.zero.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author guiyuoneiros
 * @since 2021/2/8 23:22
 */
@RestController
@RequestMapping("/restTemplate")
public class RestTemplateController {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateController.class);

    //restTemplate 可变参 占位符 http get请求
    @RequestMapping("/getForEntityWithParams")
    public String getForEntityWithParams() {
        String url = "http://domain?param1={1}&param2={2}";
        String param1 = "param1";
        String param2 = "param2";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, param1, param2);

//        HttpStatus responseStatusCode = responseEntity.getStatusCode();
        //响应码
        int statusCodeValue = responseEntity.getStatusCodeValue();
        //响应头
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        //响应体
        String responseBody = responseEntity.getBody();
        logger.info("statusCodeValue--->{}", statusCodeValue);

        Set<String> keySet = responseHeaders.keySet();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("\n");
        for (String key : keySet) {
            strBuilder.append(key)
                    .append(":")
                    .append(responseHeaders.get(key))
                    .append("\n");
        }
        logger.info("responseHeaders--->{}", strBuilder.toString());

        return "success";
    }

    //占位符中的key和map中的key一一对应
    @RequestMapping("/getForEntityWithParamsMap")
    public String getForEntityWithParamsMap() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("param1Key", "param1Value");
        paramsMap.put("param2Key", "param2Value");
        String domain = "";
        String url = "http://" + domain + "?param1Key={param1Key}&param2Key={param2Key}";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, paramsMap);
        int statusCode = responseEntity.getStatusCodeValue();
        logger.info("statusCode--->{}", statusCode);
        if (200 != statusCode) {
            //....
            return "false";
        }

        return "success";
    }

}
