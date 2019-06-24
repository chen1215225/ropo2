package com.delai.bees.tops.service;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.entity.domain.MachineProduction;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ipukr.elephant.utils.DateUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/10.
 */
public class SignalServiceTest extends BootServerTest {

    @Resource
    private SignalService mSignalService;


    @Test
    public void testStats() throws Exception {
        Date begin = DateUtils.stringToDate("2018-03-29 09:05:00");
        Date end = DateUtils.stringToDate("2018-03-29 09:06:00");
        Map<String, DoubleSummaryStatistics> group = mSignalService.stats(begin, end);
        for (Map.Entry<String, DoubleSummaryStatistics> entry : group.entrySet()) {
            System.out.println(String.format("key=%s, value=%s",entry.getKey(), entry.getValue().toString()));
        }
    }

    @Test
    public void testQuery() {
        PageBounds bounds = new PageBounds(1, 10, true);
        List<BitSignalConfig> gss = mSignalService.search(BitSignalConfig.builder().build(), bounds);
        gss.stream().forEach(s -> System.out.println(s.toString()));
    }

    @Test
    public void testDelete() {
        BitSignalConfig gs = mSignalService.find(40L);
        mSignalService.delete(gs);
    }

    @Test
    public void testUpdate() {
        BitSignalConfig gs = mSignalService.find(39L);
        gs.setVal(18);
        mSignalService.update(gs);
    }



    @Test
    public void testGetMachineProductsAccordingTimeRange() throws Exception {
        Date begin = DateUtils.stringToDate("2018-07-07 00:00:00");
        Date end = DateUtils.stringToDate("2018-07-07 23:59:59");
//        List<String> ids = Arrays.asList("cc1f533d-a3f0-4e94-884d-5cfb0a7f3089", "f0023513-7849-4f88-9fa4-4e591e2f497e");
        MachineProduction pl = mSignalService.getMachineProductsAccordingTimeRange(begin, end);
        System.out.println(pl.toString());
    }
}
