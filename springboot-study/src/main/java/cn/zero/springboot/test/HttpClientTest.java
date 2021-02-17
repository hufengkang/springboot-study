package cn.zero.springboot.test;


import cn.zero.springboot.SpringbootApplication;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author guiyuoneiros
 * @since 2021/2/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class HttpClientTest {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

    @Test
    public void getRequestNoParams() {
        //创建客户端对象(类似代理浏览器)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");

        // 响应对象
        CloseableHttpResponse response = null;
        try {
            // 客户端执行(发送)请求
            response = httpClient.execute(httpGet);
            //Content-Type、Server、Date
            Header[] Date = response.getHeaders("Date");
//          Header[] allHeaders = response.getAllHeaders();

            logger.info("statusLine--->" + response.getStatusLine());
            StatusLine statusLine = response.getStatusLine();
            if (200 == statusLine.getStatusCode()) {
                logger.info("HttpClientTest.getRequestNoParams success...");
            }

            // 获得响应体对象
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {

                //EntityUtils#toString
                logger.info("responseEntity--->" + EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }


    /**
     * get请求 可设置参数 自定义配置信息
     */
    @Test
    public void getRequestWithConfig() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 参数
        StringBuilder params = new StringBuilder();
        try {
            // 若参数含有特殊字符 则进行编码处理
            params.append("redirectUrl=" + URLEncoder.encode("http://127.0.0.1:8080/app?k1=v1&k2=v2", "utf-8"));
            params.append("&");
            params.append("name=guiyuoneiros");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }

        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
//        HttpGet httpGet = new HttpGet("http://www.baidu.com" + "?" + params);

        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true)
                    .build();

            // 将配置信息添加到请求对象
            httpGet.setConfig(requestConfig);

            //run 获得响应对象
            response = httpClient.execute(httpGet);
            // 获得 responseBody
            HttpEntity responseEntity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            if (200 == statusLine.getStatusCode()) {
                logger.info("HttpClientTest.getRequestWithConfig success...");
            }
            //...


        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    /**
     * post请求 可设置参数(uriVariables、bodyVariables) 自定义配置信息
     */
    @Test
    public void PostRequestWithConfig() {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 设置 uriVariables
        StringBuilder params = new StringBuilder();
        try {
            // 若参数含有特殊字符 则进行编码处理
            params.append("redirectUrl=" + URLEncoder.encode("http://127.0.0.1:8080/app?k1=v1&k2=v2", "utf-8"));
            params.append("&");
            params.append("name=guiyuoneiros");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }

        // 创建Post请求
//        HttpPost httpPost = new HttpPost("http://www.baidu.com");
        HttpPost httpPost = new HttpPost("http://www.baidu.com" + "?" + params);

        // 1.设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 2.设置请求body参数 json字符串格式
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "guiyuoneiros");
        // 2.1 StringEntity charset
        HttpEntity httpEntity = new StringEntity(jsonObj.toString(), "utf-8");

        httpPost.setEntity(httpEntity);

        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true)
                    .build();

            // 将配置信息添加到请求对象
            httpPost.setConfig(requestConfig);

            //run 获得响应对象
            response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (200 == statusLine.getStatusCode()) {
                logger.info("HttpClientTest.PostRequestWithConfig success...");
            }

            HttpEntity responseEntity = response.getEntity();
            // client和server约定字符集编码
            EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }
}
