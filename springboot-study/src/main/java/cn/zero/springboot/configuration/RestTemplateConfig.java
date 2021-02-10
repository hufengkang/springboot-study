package cn.zero.springboot.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author guiyuoneiros
 * @since 2021/2/10
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate() {

        //将4XX、5XX的响应也返回客户端
        RestTemplate restTemplate = new RestTemplate();
        //自定义异常处理器
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {

                //返回false表示 不管response的statusCode是多少 都正常返回 不抛异常
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

                //可以实现你自己遇到了Error进行合理的处理
            }
        });

        return restTemplate;
    }


}
