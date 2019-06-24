package com.delai.bees.tops.service.helper;

import com.delai.bees.tops.entity.domain.MachineProduct;
import com.delai.bees.tops.web.request.MachineForm;
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
public class LineViewMachineProductSysnHandler implements Callable<MachineProduct> {

    private static final Logger logger = LoggerFactory.getLogger(LineViewMachineProductSysnHandler.class);

    private HttpClientPool pool;
    private MachineForm form;

    public LineViewMachineProductSysnHandler(HttpClientPool pool, MachineForm form) {
        this.pool = pool;
        this.form = form;
    }

    @Override
    public MachineProduct call() throws Exception {

        URI URI = new URIBuilder()
                .setScheme("http")
                .setHost(form.getHost())
                .setPath("/LineView/Machine/"+form.getMachineId())
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
        logger.debug("LineView Machine Product Sysn：获取页面结果 html content={}", content);

        Document doc = Jsoup.parse(content);
        String availability = doc.select("div[id=Availability]").text();
        String performance = doc.select("div[id=Performance]").text();
        String quality = doc.select("div[id=Quality]").text();
        String bottlesProduced = doc.select("div[id=UnitsProduced]").text();
        String bottlesRejected = doc.select("div[id=UnitsRejected]").text();
        String bottlesLost = doc.select("div[id=UnitsLost]").text();
        String timeLost = doc.select("div[id=TimeLost]").text();

        logger.info("LineView Machine Product Sysn：获取页面数据 availability={},performance={},quality={},bottlesProduced={},bottlesRejected={},bottlesLost={},timeLost={}",
                availability,performance,quality,bottlesProduced,bottlesRejected,bottlesLost,timeLost);
        MachineProduct machineProduct = MachineProduct.builder()
                .machineId(form.getMachineId())
                .availability(availability)
                .performance(performance)
                .quality(quality)
                .bottlesProduced(bottlesProduced)
                .bottlesRejected(bottlesRejected)
                .bottlesLost(bottlesLost)
                .timeLost(timeLost)
                .build();
        return machineProduct;
    }
}
