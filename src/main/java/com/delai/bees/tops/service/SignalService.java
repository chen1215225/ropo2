package com.delai.bees.tops.service;

import com.delai.bees.tops.dao.BitSignalConfigMapper;
import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.document.Machine;
import com.delai.bees.tops.document.MachineMode;
import com.delai.bees.tops.document.SignalConfig;
import com.delai.bees.tops.domain.BitSignal;
import com.delai.bees.tops.domain.BitSignalTop;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.entity.domain.MachineProduct;
import com.delai.bees.tops.entity.domain.MachineProduction;
import com.delai.bees.tops.entity.domain.Production;
import com.delai.bees.tops.repository.AnalogSignalRepository;
import com.delai.bees.tops.repository.MachineRepository;
import com.delai.bees.tops.repository.SignalConfigRepository;
import com.delai.bees.tops.service.helper.BitSignalConfigHelper;
import com.delai.bees.tops.service.helper.LineViewMachineProductSysnHandler;
import com.delai.bees.tops.service.helper.LineViewOverviewSysnHandler;
import com.delai.bees.tops.service.helper.LineviewHelper;
import com.delai.bees.tops.utils.BitSignalConfigUtils;
import com.delai.bees.tops.utils.BitUtils;
import com.delai.bees.tops.web.request.MachineForm;
import com.delai.bees.tops.web.request.TimeForm;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ipukr.elephant.common.function.EntityCombineHelper;
import com.ipukr.elephant.common.function.IFunction;
import com.ipukr.elephant.common.function.IPredicate;
import com.ipukr.elephant.http.config.HttpClientPoolConfig;
import com.ipukr.elephant.http.third.HttpClientPool;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.ExceptionUtils;
import com.ipukr.elephant.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/10.
 */
@Slf4j
@Service
@Transactional
public class SignalService {

    @Resource
    private BitSignalConfigMapper mBitSignalConfigMapper;
    @Resource
    private AnalogSignalRepository mAnalogSignalRepository;
    @Resource
    private SignalConfigRepository mSignalConfigRepository;
    @Resource
    private MachineRepository mMachineRepository;
    @Resource
    private AnalogSignalService mAnalogSignalService;
    @Resource
    private LineviewHelper helper;
    @Resource
    private MongoTemplate iMongoTemplate;
    @Resource
    private AnalogSignalService iAnalogSignalService;

    private HttpClientPool pool;

    private ExecutorService executor;



    @PostConstruct
    public void init() throws Exception {
        //
        HttpClientPoolConfig config = new HttpClientPoolConfig.Builder()
                .schema("http")
                .connections(10)
                .timeout(60 * 1000)
                .build();
        pool = new HttpClientPool(config);
        //
        executor = Executors.newFixedThreadPool(5);
    }


    /**
     * @param signal
     * @return
     */
    public BitSignalConfig insert(BitSignalConfig signal) {
        log.info("新增BitSignalConfig={}", signal.toString());
        mBitSignalConfigMapper.upsert(signal);
        return signal;
    }

    /**
     * 新增配置信号
     * @param gss
     * @return
     */
    public boolean batimport(List<BitSignalConfig> gss) {
        boolean bool = true;
        List<BitSignalConfig> filter = gss.stream().filter(e-> {
                boolean flag = true;
                flag = flag && e.getKey() != null && !e.getKey().equals("");
                flag = flag && e.getAddress()!=null && !e.getAddress().equals("");
                flag = flag && e.getPosition()!=null && !e.getPosition().equals("");
                flag = flag && e.getPosition() >0 && e.getPosition() <= 32;
                return flag;
            }).collect(Collectors.toList());
        log.info("批量导入数据：gss.size={}, filter.size={}", gss.size(), filter.size());
        List<BitSignalConfig> arr = new ArrayList<BitSignalConfig>();
        for (BitSignalConfig gs : filter) {
            Integer position = gs.getPosition();
            Integer val = BitUtils.convert(position).intValue();
            gs.setEnable(true);
            gs.setVal(val);
            gs.setCreateTime(DateUtils.now());
            gs.setLastUpdateTime(DateUtils.now());

            arr.add(gs);
            if (arr.size()>200) {
                bool = bool && mBitSignalConfigMapper.batupsert(arr) > 0;
                arr.clear();
            }
        }
        if (arr.size()>0) {
            bool = bool && mBitSignalConfigMapper.batupsert(arr) > 0;
        }
        return bool;
    }


    public Iterable<BitSignalConfig> findAll() {
        return mBitSignalConfigMapper.findAll();
    }


    /**
     * @param bscs
     * @param begin
     * @param end
     * @return
     */
    @Deprecated
    public List<BitSignal> doservice(List<BitSignalConfig> bscs, Date begin, Date end) throws Exception {
        // 获取组信号-映射
        Set<String> addresses = bscs.stream().filter(e->e.getAddress()!=null && !e.getAddress().equals("")).map(e->e.getAddress()).collect(Collectors.toSet());

        // 获取组信号
        List<SignalConfig> scc = mSignalConfigRepository.findByAddresses(addresses);
        // 拼接信号
        EntityCombineHelper.combine(bscs, scc, new IPredicate<BitSignalConfig, SignalConfig>() {
            @Override
            public Boolean test(BitSignalConfig bitSignalConfig, SignalConfig signalConfig) {
                return bitSignalConfig.getAddress().equals(signalConfig.getAddress());
            }
        }, new IFunction<BitSignalConfig, SignalConfig>() {
            @Override
            public void apply(BitSignalConfig bitSignalConfig, SignalConfig signalConfig) {
                bitSignalConfig.setSignal(signalConfig);
            }
        });

        List<String> filter = scc.stream().filter( e -> e.getId()!=null && addresses.contains(e.getAddress())).map(e->e.getId()).collect(Collectors.toList());
        log.debug("doservice method: filter.size={}, d={}", filter.size(), DateUtils.now().getTime());

        Date d1 = DateUtils.now();
        List<AnalogSignal> ass = mAnalogSignalRepository.ins4(filter, begin.getTime(), end.getTime());
        Date d2 = DateUtils.now();
        Long doffset = d2.getTime() - d1.getTime();
        log.debug("doservice method：ins consume={}", doffset / 1000);

        EntityCombineHelper.combine(ass, scc, new IPredicate<AnalogSignal, SignalConfig>() {
            @Override
            public Boolean test(AnalogSignal signal, SignalConfig signalConfig) {
                return signal.getSignalId().equals(signalConfig.getId());
            }
        }, new IFunction<AnalogSignal, SignalConfig>() {
            @Override
            public void apply(AnalogSignal signal, SignalConfig signalConfig) {
                signal.setSignal(signalConfig);
            }
        });

        log.debug("doservice method: ass.size={}, d={}",ass.size(),  DateUtils.now().getTime());
        List<BitSignal> collections = new ArrayList<>();
        for (AnalogSignal as: ass) {
            // 解析信号
            List<BitSignal> bss = BitSignalConfigUtils.flat(bscs, as);
            if (!bss.isEmpty()) {
                collections.addAll(bss);
            }
        }
        return collections;
    }

    /**
     * @param begin
     * @param end
     * @return
     */
    public Map<String, DoubleSummaryStatistics> stats(Date begin, Date end) throws Exception {
        List<BitSignalConfig> bscs = mBitSignalConfigMapper.findAll();
        List<BitSignal> bss = doservice(bscs, begin, end);
        Map<String, DoubleSummaryStatistics> g = bss.stream().collect(Collectors.groupingBy(
                BitSignal::getKey, Collectors.summarizingDouble(BitSignal::getDuration)));
        for (Map.Entry<String, DoubleSummaryStatistics> entry : g.entrySet()) {

        }

        return g;
    }

    /**
     * 数据统计
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    public BitSignalTop statistics(Date begin, Date end) throws Exception {
        log.info("top 200 statistics begin,");
        Date d1 = DateUtils.now();
        {
            // @FIXME 调整机制后，不需要在使用此方法
            log.info("reset data collector");
            helper.login();
            helper.resetDataCollector();
            helper.logout();
        }
        Date d2 = DateUtils.now();
        log.info("top 200 statistics d2={}", d2.getTime());
        // TODO
        List<BitSignalConfig> bscs = mBitSignalConfigMapper.findAll();

        List<BitSignal> bss = doservice(bscs, begin, end);

        Map<String, DoubleSummaryStatistics> statistics = bss.stream().collect(Collectors.groupingBy(
                BitSignal::getKey, Collectors.summarizingDouble(BitSignal::getDuration)));
        Date d3 = DateUtils.now();
        log.info("top 200 statistics d3={}, bss.size={}", d3.getTime(), bss.size());

        Date d4 = DateUtils.now();
        log.info("top 200 statistics d4={}, bscs.size={}", d4.getTime(), bscs.size());
        // 获取信号配置
        Set<String> parents = bscs.stream().filter(e->e.getAddress()!=null && !e.getAddress().equals("")).map(e->e.getAddress()).collect(Collectors.toSet());
        List<SignalConfig> scc = mSignalConfigRepository.findByAddresses(parents);

        Date d5 = DateUtils.now();
        log.info("top 200 statistics d5={}, scc.size={}", d5.getTime(), scc.size());
        // 获取机器配置
        List<BitSignalConfig> filter = bscs.stream().filter(
                e-> bss.stream().filter(x->x.getKey().equals(e.getKey())).count()>0
        ).collect(Collectors.toList());

        List<Machine> machines = mMachineRepository.find();
        BitSignalConfigHelper.combine(filter, scc, machines);
        Date d6 = DateUtils.now();
        log.info("top 200 statistics end, d1={}, d2={}, d3={}, d4={}, d5={}, d6={}, filter.size={}, scc.size={}, machines.size={}",
                d1.getTime(),
                d2.getTime(),
                d3.getTime(),
                d4.getTime(),
                d5.getTime(),
                d6.getTime(),
                filter.size(),
                scc.size(),
                machines.size());

        return BitSignalTop.builder()
                .statistics(statistics)
                .bss(bss)
                .bscs(filter)
                .build();
    }

    /**
     * 搜索配置信号
     * @param example
     * @param bounds
     * @return
     */
    public List<BitSignalConfig> search(BitSignalConfig example, PageBounds bounds) {
        List<BitSignalConfig> signals = mBitSignalConfigMapper.search(example, bounds);
        if (signals.size()>0) {
            Set<String> addresses = signals.stream().map(e->e.getAddress()).collect(Collectors.toSet());
            List<SignalConfig> scc = mSignalConfigRepository.findByAddresses(addresses);
            List<Machine> machines = mMachineRepository.find();
            BitSignalConfigHelper.combine(signals, scc, machines);
        }
        return signals;
    }

    /**
     * 删除配置信号
     * @param signal
     * @return
     */
    public boolean delete(BitSignalConfig signal) {
        return mBitSignalConfigMapper.deleteByPrimaryKey(signal.getId()) > 0;
    }

    /**
     * 清空配置信号
     * @return
     */
    public boolean clear() {
        return mBitSignalConfigMapper.clear() >= 0;
    }

    /**
     * 更新配置信号
     * @param signal
     * @return
     */
    public BitSignalConfig update(BitSignalConfig signal) {
        signal.setLastUpdateTime(DateUtils.now());
        mBitSignalConfigMapper.updateByPrimaryKeySelective(signal);
        return signal;
    }

    /**
     * 获取配置信号
     * @param id
     * @return
     */
    public BitSignalConfig find(Long id) {
        return mBitSignalConfigMapper.selectByPrimaryKey(id);
    }


    @Value("${setting.lineview.implanter.id:}")
    private String implanterId;
    @Value("${setting.lineview.implanter.product.id:}")
    private String implanterProductId;
    @Value("${setting.lineview.bottle_blowing.product.id:}")
    private String bottleBlowingProductId;
    @Value("${setting.lineview.stacker.product.id:}")
    private String stackerProductId;

    /**
     * 获取时间范围内产量
     * @param begin
     * @param end
     * @return
     */
    public MachineProduction getMachineProductsAccordingTimeRange(Date begin, Date end) {
        List<String> products = Arrays.asList(implanterProductId, bottleBlowingProductId, stackerProductId);
        // 聚合查询最近非零
        MatchOperation match = Aggregation.match(
                new Criteria().andOperator(new Criteria("SignalId").in(products), new Criteria("Ts").gt(begin).lt(end), new Criteria("Val").gt(0))
        );
        GroupOperation group = Aggregation.group("SignalId")
                .first("$SignalId").as("SignalId")
                .first("$Ts").as("Ts")
                .first("$Name").as("Name").first("$Val").as("Val");

        SortOperation sorted = Aggregation.sort(Sort.Direction.DESC, "Ts");

        Aggregation aggregation = Aggregation.newAggregation(match, group, sorted);
        AggregationResults<AnalogSignal> results = iMongoTemplate.aggregate(aggregation, "AnalogSignals", AnalogSignal.class);
        List<AnalogSignal> ass = results.getMappedResults();
        log.debug("获取时间范围内产量：ass={}", JsonUtils.parserObj2String(ass));
        Double implanterProducts = ass.stream().filter(e -> e.getSignalId().equals(implanterProductId)).map(e -> e.getVal()).reduce(0D, Double::max);
        Double bottleBlowingProducts = ass.stream().filter(e -> e.getSignalId().equals(bottleBlowingProductId)).map(e -> e.getVal()).reduce(0D, Double::max);
        Double stackerProducts = ass.stream().filter(e -> e.getSignalId().equals(stackerProductId)).map(e -> e.getVal()).reduce(0D, Double::max);

        // TODO 顺延机制
        Date stackerTs = ass.stream().filter(e->e.getSignalId().equals(stackerProductId)).map(e->e.getTs()).max((a,b )-> a.compareTo(b)).orElse(null);

        if (stackerTs!=null) {

        }

        return MachineProduction.builder()
                .implanterProducts(implanterProducts.longValue())
                .bottleBlowingProducts(bottleBlowingProducts.longValue())
                .stackerProducts(stackerProducts.longValue())
                .build();
    }

    /**
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    public Production synsProductSafely(Date begin, Date end) {
        try {
            TimeForm form = TimeForm.builder()
                    .datefrom(DateUtils.dateToString(begin, "yyyy-MM-dd"))
                    .timefrom(DateUtils.dateToString(begin, "HH:mm:ss"))
                    .dateto(DateUtils.dateToString(end, "yyyy-MM-dd"))
                    .timeto(DateUtils.dateToString(end, "HH:mm:ss"))
                    .build();
            return sysProduct(form);
        } catch (Exception e) {
            log.error(ExceptionUtils.castEx2Str(e));
            return null;
        }
    }

    public HttpResponse init(URI URI) throws Exception {
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
        return response;
    }

    @Value("${setting.lineview.server.host:127.0.0.1}")
    private String host;

    /**
     * 多线程异步爬数据
     * @param ids
     * @param begin
     * @param end
     * @return
     * @throws InterruptedException
     */
    @Deprecated
    public List<Future<MachineProduct>> sysMachine(List<String> ids, Date begin, Date end) throws InterruptedException {
        if (ids.size() > 0) {
            List<Callable<MachineProduct>> partitions = new ArrayList<Callable<MachineProduct>>();
            for (String id : ids) {
                MachineForm form = MachineForm.builder()
                        .machineId(id)
                        .datefrom(DateUtils.dateToString(begin, "yyyy-MM-dd"))
                        .timefrom(DateUtils.dateToString(begin, "HH:mm:ss"))
                        .dateto(DateUtils.dateToString(end, "yyyy-MM-dd"))
                        .timeto(DateUtils.dateToString(end, "HH:mm:ss"))
                        .host(host)
                        .build();
                partitions.add(new LineViewMachineProductSysnHandler(pool, form));
            }
            List<Future<MachineProduct>> futures = executor.invokeAll(partitions, 60, TimeUnit.SECONDS);
            return futures;
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * 异步爬数据
     * @param begin
     * @param end
     * @return
     */
    @Deprecated
    public Future<Production> synsOverview(Date begin, Date end) {
        TimeForm form = TimeForm.builder()
                .datefrom(DateUtils.dateToString(begin, "yyyy-MM-dd"))
                .timefrom(DateUtils.dateToString(begin, "HH:mm:ss"))
                .dateto(DateUtils.dateToString(end, "yyyy-MM-dd"))
                .timeto(DateUtils.dateToString(end, "HH:mm:ss"))
                .host(host)
                .build();
        return executor.submit(new LineViewOverviewSysnHandler(pool, form));
    }

    /**
     * 同步机器产量
     * @param machineId
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    @Deprecated
    public MachineProduct sysMachine(String machineId, Date begin, Date end) throws Exception {
        MachineForm form = MachineForm.builder()
                .machineId(machineId)
                .datefrom(DateUtils.dateToString(begin, "yyyy-MM-dd"))
                .timefrom(DateUtils.dateToString(begin, "HH:mm:ss"))
                .dateto(DateUtils.dateToString(end, "yyyy-MM-dd"))
                .timeto(DateUtils.dateToString(end, "HH:mm:ss"))
                .build();
        return sysMachine(form);
    }
    /**
     * 同步机器产量
     * @param machine
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    @Deprecated
    public MachineProduct sysMachine(Machine machine, Date begin, Date end) throws Exception {
        MachineForm form = MachineForm.builder()
                .machineId(machine.getId())
                .datefrom(DateUtils.dateToString(begin, "yyyy-MM-dd"))
                .timefrom(DateUtils.dateToString(begin, "HH:mm:ss"))
                .dateto(DateUtils.dateToString(end, "yyyy-MM-dd"))
                .timeto(DateUtils.dateToString(end, "HH:mm:ss"))
                .build();
        return sysMachine(form);
    }

    /**
     * 抓取页面数据
     * @param form
     * @throws Exception
     */
    @Deprecated
    public Production sysProduct(TimeForm form) throws Exception {

        URI URI = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPath("/LineView")
                .setPort(80)
                .setCharset(Consts.UTF_8)
                .addParameter("type","fromto")
                .addParameter("datefrom",form.getDatefrom())
                .addParameter("timefrom",form.getTimefrom())
                .addParameter("dateto",form.getDateto())
                .addParameter("timeto",form.getTimeto())
                .addParameter("timezone","-00:00")
                .build();

        HttpResponse response = init(URI);
        String content = EntityUtils.toString(response.getEntity(), "UTF-8");
        log.debug("html content={}",content);

        // 采用Jsoup解析
        Document doc = Jsoup.parse(content);
        // 获取html标签中的内容
        String oee = doc.select("div[id=OEE]").text(); //移动率
        String eff = doc.select("div[id=Eff]").text(); //生产效率
        String product = doc.select("div[id=UnitsProduced]").text();
        String rejected = doc.select("div[id=Rejected]").text();
        String loss = doc.select("div[id=UnitsLost]").text();
        String time = doc.select("div[id=TimeLost]").text();
        log.info("oee={}, eff={}, product={}, rejected={}, loss={}, time={}",oee, eff, product, rejected, loss, time);

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


    /**
     * 同步机器产量
     * @param form
     * @return
     * @throws Exception
     */
    @Deprecated
    public MachineProduct sysMachine(MachineForm form) throws Exception {
        URI URI = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPath("/LineView/Machine/"+form.getMachineId())
                .setPort(80)
                .setCharset(Consts.UTF_8)
                .addParameter("type","fromto")
                .addParameter("datefrom",form.getDatefrom())
                .addParameter("timefrom",form.getTimefrom())
                .addParameter("dateto",form.getDateto())
                .addParameter("timeto",form.getTimeto())
                .addParameter("timezone","-00:00")
                .build();

        HttpResponse response = init(URI);
        String content = EntityUtils.toString(response.getEntity(), "UTF-8");
        log.info("html content={}",content);

        Document doc = Jsoup.parse(content);
        String availability = doc.select("div[id=Availability]").text();
        String performance = doc.select("div[id=Performance]").text();
        String quality = doc.select("div[id=Quality]").text();
        String bottlesProduced = doc.select("div[id=UnitsProduced]").text();
        String bottlesRejected = doc.select("div[id=UnitsRejected]").text();
        String bottlesLost = doc.select("div[id=UnitsLost]").text();
        String timeLost = doc.select("div[id=TimeLost]").text();
        log.info("availability={},performance={},quality={},bottlesProduced={},bottlesRejected={},bottlesLost={},timeLost={}",
                availability,performance,quality,bottlesProduced,bottlesRejected,bottlesLost,timeLost);

        MachineProduct machineProduct = MachineProduct.builder()
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
