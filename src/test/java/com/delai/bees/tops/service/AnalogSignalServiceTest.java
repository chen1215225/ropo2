package com.delai.bees.tops.service;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.document.AnalogSignal;
import com.ipukr.elephant.utils.DateUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/30.
 */
public class AnalogSignalServiceTest extends BootServerTest {
    @Resource
    private AnalogSignalService mAnalogSignalService;

    @Test
    public void testIns() throws Exception {
        Date begin = DateUtils.stringToDate("2018-03-28 09:05:00");
        Date end = DateUtils.stringToDate("2018-03-29 10:06:00");
        Date d1 = DateUtils.now();
        List<String> signals = Arrays.asList("1e35a2ef-e77b-4e90-8799-4ce14b02e56e");
        List<AnalogSignal> ass = mAnalogSignalService.ins(signals, begin, end);
        Date d2 = DateUtils.now();
        Long offset = d2.getTime()-d1.getTime();

        for (AnalogSignal as : ass) {
            System.out.println(as.toString());
        }
        System.out.println("offset" + offset / 1000);
    }
}
