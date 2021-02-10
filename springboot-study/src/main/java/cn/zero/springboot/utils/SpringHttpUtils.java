package cn.zero.springboot.utils;

import cn.zero.springboot.controller.RestTemplateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author guiyuoneiros
 * @since 2021/2/8
 */
@Component
public class SpringHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateController.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 占位符中的key和map中的key一一对应
     *
     * @param url 请求路径 路径可设置占位符
     * @return
     * @author guiyuoneiros
     */
    public String get(String url, Map<String, Object> uriParams) {
        String responseBody = "";
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, uriParams);
            int statusCode = responseEntity.getStatusCodeValue();
            logger.info("statusCode--->{}", statusCode);
            if (200 != statusCode) {
                logger.warn("服务响应异常");
                return "";
            }

            responseBody = responseEntity.getBody();
            logger.info("responseBody--->", responseBody);
        } catch (Exception e) {
            logger.error("", e);
        }

        return responseBody;
    }

    /**
     * @param url               请求路径 路径可设置占位符
     * @param requestBodyParams 请求体参数 JSON字符串格式
     * @param uriParams         请求路径参数
     * @return
     * @author guiyuoneiros
     */
    public String post(String url, @Nullable String requestBodyParams, Object... uriParams) {
        String responseBody = "";
        try {
            //请求路径、请求体参数、返回类型、uriVariables
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestBodyParams, String.class, uriParams);
            int statusCode = responseEntity.getStatusCodeValue();
            logger.info("statusCode--->{}", statusCode);
            if (200 != statusCode) {
                logger.warn("服务响应异常");
                return "";
            }

            responseBody = responseEntity.getBody();
            logger.info("responseBody--->", responseBody);

        } catch (Exception e) {
            logger.error("", e);
        }

        return responseBody;
    }

}
