package com.delai.bees.tops.service.helper;

import com.ipukr.elephant.http.config.HttpClientPoolConfig;
import com.ipukr.elephant.http.third.HttpClientPool;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/10/13.
 */
@Component
public class LineviewHelper {

    private HttpClientPool pool;

    @Value("${setting.lineview.server.enable:false}")
    private Boolean enable;
    @Value("${setting.lineview.server.schema:http}")
    private String schema;
    @Value("${setting.lineview.server.host:localhost}")
    private String host;
    @Value("${setting.lineview.server.port:80}")
    private Integer port;
    @Value("${setting.lineview.server.username:Administrator}")
    private String username;
    @Value("${setting.lineview.server.password:Default}")
    private String password;


    private static final String RequestVerificationToken = "fnrExrHAI3RT3tXT7leLJfdTHhYkri4PuzQ-qp19CUcjuxkDezCRmfyHv2r3uYfzT2HJ7oTT8BLp4sRwbUNIUrWQDorXMmw-b8FaUtQwcBs1" ;
    /**
     */
    @PostConstruct
    public void init() throws Exception {
        HttpClientPoolConfig config = new HttpClientPoolConfig.Builder()
                .schema(schema)
                .build();
        pool = new HttpClientPool(config);
    }

    /**
     * 登录系统
     * @throws Exception
     */
    public void login() throws Exception {
        if (enable) {
            URI URI = new URIBuilder()
                    .setScheme(schema)
                    .setHost(host)
                    .setPort(port)
                    .setPath("/Admin/Account/Login")
                    .setCharset(Consts.UTF_8)
                    .build();
            HttpUriRequest request = RequestBuilder.get(URI)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addParameter("UserName", username)
                    .addParameter("Password", password)
                    .addParameter("RememberMe", "false")
                    .addParameter("__RequestVerificationToken", RequestVerificationToken)
                    .build();

            HttpResponse response = pool.getConnection().execute(request);

            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    /**
     * 登出系统
     * @throws Exception
     */
    public void logout() throws Exception {
        if (enable) {
            URI URI3 = new URIBuilder()
                    .setScheme(schema)
                    .setHost(host)
                    .setPort(port)
                    .setPath("/Admin/Account/LogOff")
                    .setCharset(Consts.UTF_8)
                    .build();

            HttpUriRequest request3 = RequestBuilder.get(URI3)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addParameter("__RequestVerificationToken", RequestVerificationToken)
                    .build();
            HttpResponse response3 = pool.getConnection().execute(request3);

            String content3 = EntityUtils.toString(response3.getEntity(), "UTF-8");
        }
    }

    /**
     * 重置Data Collector
     * @throws Exception
     */
    public void resetDataCollector() throws Exception {
        if (enable) {
            URI URI = new URIBuilder()
                    .setScheme(schema)
                    .setHost(host)
                    .setPort(port)
                    .setPath("/Admin/Home/ResetDataCollector")
                    .setCharset(Consts.UTF_8)
                    .build();

            HttpUriRequest request3 = RequestBuilder.post(URI)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addParameter("__RequestVerificationToken", RequestVerificationToken)
                    .build();
            HttpResponse response3 = pool.getConnection().execute(request3);

            String content3 = EntityUtils.toString(response3.getEntity(), "UTF-8");
        }
    }
}
