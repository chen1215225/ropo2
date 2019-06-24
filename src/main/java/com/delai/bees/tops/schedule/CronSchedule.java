package com.delai.bees.tops.schedule;

import com.delai.bees.tops.dao.BitAnalogSignalsMapper;
import com.delai.bees.tops.dao.BitSignalConfigMapper;
import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.entity.BitAnalogSignals;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.repository.AnalogSignalRepository;
import com.delai.bees.tops.service.BitAnalogSignalsService;
import com.delai.bees.tops.service.DigitalSignalService;
import com.delai.bees.tops.service.LineProductService;
import com.ipukr.elephant.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/14.
 */
@Slf4j
@Component
@EnableScheduling
public class CronSchedule {

    @Resource
    private BitAnalogSignalsService mBitAnalogSignalsService;
    @Resource
    private LineProductService mLineProductService;
    @Resource
    private DigitalSignalService mDigitalSignalService;

//    @Scheduled(cron = "0 * * * * ?")
//    public void tst() {
//        Date d = DateUtils.now();
//        log.info("测试定时同步信号任务：d={}", DateUtils.dateToString(d));
//    }

    /**
     * 每小时同步
     * 同步 AnalogSignal 信号数据（模拟信号/故障信号）至MySQL数据库
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void sysnByHour() {
        Date d = DateUtils.now();
        Date begin = DateUtils.dateWithOffset(d, -1 , Calendar.HOUR_OF_DAY);
        log.info("执行定时同步信号任务：d={}", DateUtils.dateToString(d));
        mBitAnalogSignalsService.schedule(begin, d);
    }


    /**
     * 每小时同步
     * 同步生产线产量数据
     * @throws Exception
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void syns() throws Exception {
        Date d = DateUtils.now();
        Date begin = DateUtils.dateWithOffset(d, -1 , Calendar.HOUR_OF_DAY);
        log.info("执行定时同步产量任务：d={}", DateUtils.dateToString(d));
        mLineProductService.syns(begin, d);
    }


    /**
     * 每半小时定时任务
     * 自动故障备注
     */
    @Scheduled(cron="0 */30 * * * ?")
    public void autocomm() {
        Date end = DateUtils.now();
        // 获取时间范围内的信号
        Date begin = DateUtils.dateWithOffset(end, -4, Calendar.HOUR);
        log.info("执行定时备注任务：当前执行点：{}，range.begin={}. range.end={}",
                DateUtils.dateToString(end),
                DateUtils.dateToString(begin),
                DateUtils.dateToString(end));
        // 自动备注
        mDigitalSignalService.autocomm(begin, end);
    }

}
