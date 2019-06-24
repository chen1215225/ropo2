package com.delai.bees.tops.datafix;

import com.delai.bees.tops.BootServer;
import com.delai.bees.tops.dao.BitAnalogSignalsMapper;
import com.delai.bees.tops.dao.BitSignalConfigMapper;
import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.entity.BitAnalogSignals;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.repository.AnalogSignalRepository;
import com.delai.bees.tops.utils.BitUtils;
import com.delai.bees.tops.web.response.ProductionOverviewDTO;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/8.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootServer.class)
public class DataFixHelper {

    @Resource
    private BitSignalConfigMapper mBitSignalConfigMapper;
    @Resource
    private BitAnalogSignalsMapper mBitAnalogSignalsMapper;
    @Resource
    private AnalogSignalRepository mAnalogSignalRepository;

    @Test
    public void fix1() {
        List<BitSignalConfig> bscs = mBitSignalConfigMapper.findAll();
        for (BitSignalConfig bsc :  bscs) {
            int position = bsc.getPosition();
            bsc.setVal(BitUtils.convert(position).intValue());
            mBitSignalConfigMapper.updateByPrimaryKey(bsc);
        }
    }

    @Test
    public void fix2() throws ParseException {
        List<BitSignalConfig> bscs = mBitSignalConfigMapper.findAll();
        List<String> ids = bscs.stream().map(e->e.getAddress()).collect(Collectors.toList());
        Date begin = DateUtils.stringToDate("2018-02-28 09:05:00");
        Date end = DateUtils.stringToDate("2018-03-29 09:06:00");
        List<AnalogSignal> ass = mAnalogSignalRepository.ins(ids, begin, end);
        List<BitAnalogSignals> bass = new ArrayList<BitAnalogSignals>();
        for (AnalogSignal as : ass) {
            BitAnalogSignals bas = BitAnalogSignals.builder()
                    .id(as.getId())
                    .signalId(as.getSignalId())
                    .name(as.getName())
                    .val(as.getVal())
                    .ts(as.getTs())
                    .build();
            bass.add(bas);
            if (bass.size() > 1000) {
                log.info("批量插入数, 插入队列 bass.size={}", bass.size());
                mBitAnalogSignalsMapper.batupsert(bass);
                bass.clear();
                log.info("批量插入数, 清空队列 bass.size={}", bass.size());
            }
        }
        if (bass.size() > 0) {
            log.info("批量插入数, 插入队列 bass.size={}", bass.size());
            mBitAnalogSignalsMapper.batupsert(bass);
        }
    }

    @Test
    public void test() {
        ProductionOverviewDTO dto = ProductionOverviewDTO.builder().build();
        System.out.println(JsonUtils.parserObj2String(dto));
    }
}
