package com.delai.bees.tops.service.helper;

import com.delai.bees.tops.entity.domain.MachineProduct;
import com.delai.bees.tops.entity.domain.Production;
import com.delai.bees.tops.web.request.TimeForm;
import com.ipukr.elephant.http.third.HttpClientPool;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.Callable;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/21.
 */
public class LineViewOverviewSysnHandler implements Callable<Production> {

    private static final Logger logger = LoggerFactory.getLogger(LineViewOverviewSysnHandler.class);

    private TimeForm form;

    private HttpClientPool pool;

    public LineViewOverviewSysnHandler(HttpClientPool pool, TimeForm form) {
        this.form = form;
        this.pool = pool;
    }

    @Override
    public Production call() throws Exception {
        URI URI = new URIBuilder()
                .setScheme("http")
                .setHost(form.getHost())
                .setPath("/LineView")
                .setPort(80)
                .setCharset(Consts.UTF_8)
                .addParameter("type","fromto")
                .addParameter("datefrom",form.getDatefrom())
                .addParameter("timefrom",form.getTimefrom())
                .addParameter("dateto",form.getDateto())
                .addParameter("timeto",form.getTimeto())
                .addParameter("timezone","+08:00")
                .build();

        HttpUriRequest request = RequestBuilder.get(URI)
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Accept-Language","en-US,en;q=0.9")
                .addHeader("Connection","keep-alive")
                .addHeader("Upgrade-Insecure-Requests","1")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.3")
                .build();

        HttpClient client  = pool.getConnection();
        HttpResponse response = client.execute(request);

        String content = EntityUtils.toString(response.getEntity(), "UTF-8");

        // 采用Jsoup解析
        Document doc = Jsoup.parse(content);
        // 获取html标签中的内容
        String oee = doc.select("div[id=OEE]").text(); //移动率
        String eff = doc.select("div[id=Eff]").text(); //生产效率
        String product = doc.select("div[id=UnitsProduced]").text();
        String rejected = doc.select("div[id=Rejected]").text();
        String loss = doc.select("div[id=UnitsLost]").text();
        String time = doc.select("div[id=TimeLost]").text();
        logger.info("LineView Overview Sysn：同步结果 oee={}, eff={}, product={}, rejected={}, loss={}, time={}",oee, eff, product, rejected, loss, time);

        Production production = Production.builder()
                .eff(eff)
                .oee(oee)
                .loss(loss)
                .product(product)
                .rejected(rejected)
                .time(time)
                .build();

        return production;
    }
}
