package cn.zero.springboot.test;

import cn.zero.springboot.SpringbootApplication;
import cn.zero.springboot.utils.SpringHttpUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private SpringHttpUtils springHttpUtils;

    @Test
    public void testGet() {
        String url = "http://www.baidu.com?name={key1}&age={key2}";
        Map<String, Object> uriVariableMap = new HashMap<>(4);
        uriVariableMap.put("key1", "guiyu");
        uriVariableMap.put("key2", "oneiros");
        String responseStr = springHttpUtils.get(url, uriVariableMap);
        System.out.println("------------end---------------");

    }

    @Test
    public void testPost() {
        String url = "http://www.baidu.com?name={1}&age={2}";
        String responseStr = springHttpUtils.post(url, null, "zhangsan", 18);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("bodyKey1", "value1");
        jsonObj.put("bodyKey2", "value2");
        responseStr = springHttpUtils.post(url, jsonObj.toJSONString(), "zhangsan", 18);
        System.out.println("------------end---------------");

    }
}
