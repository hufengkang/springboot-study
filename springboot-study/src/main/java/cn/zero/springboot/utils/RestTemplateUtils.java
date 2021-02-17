package cn.zero.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * @author guiyuoneiros
 * @since 2021/2/8
 */
@Component
public class RestTemplateUtils {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplate restTemplateForHttps;

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

    /**
     * @param url       请求路径 路径可设置占位符
     * @param request   请求对象 可添加http headers到请求
     * @param uriParams 请求路径参数
     * @return
     * @author guiyuoneiros
     */
    public String post(String url, @Nullable Object request, Object... uriParams) {
        String responseBody = "";
        try {
            //请求路径、返回类型、uriVariables
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class, uriParams);
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
     * @param urlStr   请求路径
     * @param method   请求方法类型
     * @param httpBody 请求体参数 JSON字符串格式
     * @return
     * @author guiyuoneiros
     */
    public String exchange(String urlStr, HttpMethod method, String httpBody) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置contentType
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        httpHeaders.add("", "");

//        String httpBody = "{\"name\":\"guiyuoneiros\"}";
//        请求体类型与HttpEntity类的泛型保持一致
//        GET请求时 HttpEntity的 httpBody 设为null
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);

        URI url = URI.create(urlStr);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, httpEntity, String.class);
        String responseBody = responseEntity.getBody();
        return responseBody;
    }

    /**
     * 发送https请求 GET请求时，HttpEntity的httpBody设为null
     * @param urlStr
     * @param method
     * @param httpBody
     * @return
     */
    public String httpsRequest(String urlStr, HttpMethod method, @Nullable String httpBody) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置contentType
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        httpHeaders.add("", "");

//        String httpBody = "{\"name\":\"guiyuoneiros\"}";
//        请求体类型与HttpEntity类的泛型保持一致
//
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);

        URI url = URI.create(urlStr);
        ResponseEntity<String> responseEntity = restTemplateForHttps.exchange(url, method, httpEntity, String.class);
        String responseBody = responseEntity.getBody();
        return responseBody;
    }

}
