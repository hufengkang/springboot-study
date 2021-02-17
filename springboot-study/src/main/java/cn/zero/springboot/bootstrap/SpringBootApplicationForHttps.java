package cn.zero.springboot.bootstrap;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * 指定http端口
 *
 * @author guiyuoneiros
 * @since 2021/2/14
 */
@SpringBootApplication(scanBasePackages = "cn.zero.springboot")
public class SpringBootApplicationForHttps {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationForHttps.class, args);
    }

    @Value("${http.port}")
    private Integer httpPort;

    @Bean
    public ServletWebServerFactory servletContainer(Connector createHTTPConnector) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createHTTPConnector);
        return tomcat;
    }

    @Bean
    public Connector createHTTPConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(httpPort);
        return connector;
    }
}
