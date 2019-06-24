package com.delai.bees.tops.dao;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.entity.domain.BitAnalogSignalsStats;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.JsonUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
public class BitSignalConfigMapperTest extends BootServerTest {
    @Resource
    private BitSignalConfigMapper mBitSignalConfigMapper;
    @Test
    public void testFindAll() {
        List<BitSignalConfig> gss = mBitSignalConfigMapper.findAll();
        for (BitSignalConfig gs :
                gss) {
            System.out.println(gs.toString());
        }
    }

    @Test
    public void testQueryBitAnalogSignalsExpandAccordingTimeRange() throws ParseException {
        Date begin = DateUtils.stringToDate("2018-03-10 00:00:00");
        Date end = DateUtils.stringToDate("2018-03-11 00:00:00");
        List<BitAnalogSignalsStats> bases = mBitSignalConfigMapper.statsTopsBitAnalogSignalsAccordingTimeRange(begin, end);
        if (bases.size()>0) {
            System.out.println(JsonUtils.parserObj2String(bases));
        }
//        for (BitAnalogSignalsExpand base : bases) {
//            System.out.println(base.toString());
//        }
    }
}
