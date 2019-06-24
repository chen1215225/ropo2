package com.delai.bees.tops.dao;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.repository.AnalogSignalCustomRepository;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.JsonUtils;
import org.junit.Test;
import org.springframework.data.domain.Sort;

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
 * Created by ryan wu on 2019/3/20.
 */
public class AnalogSignalCustomRepositoryTest extends BootServerTest {
    @Resource
    private AnalogSignalCustomRepository mAnalogSignalCustomRepository;

    @Test
    public void testFetchFirstRecordAccordingSignalsIdsAndTsRange() throws ParseException {
        Date begin = DateUtils.stringToDate("2018-07-07 00:00:00");
        Date end = DateUtils.stringToDate("2018-07-07 23:59:59");
        List<String> ids = Arrays.asList("cc1f533d-a3f0-4e94-884d-5cfb0a7f3089", "f0023513-7849-4f88-9fa4-4e591e2f497e");
        List<AnalogSignal> ass = mAnalogSignalCustomRepository.fetchFirstRecordAccordingSignalsIdsAndTsRange(ids, begin, end, Sort.Direction.DESC);
        System.out.println(JsonUtils.parserObj2String(ass));
    }
}
