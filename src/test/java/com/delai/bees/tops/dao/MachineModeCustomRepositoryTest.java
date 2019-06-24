package com.delai.bees.tops.dao;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.document.MachineMode;
import com.delai.bees.tops.repository.MachineModeCustomRepository;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.JsonUtils;
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
 * Created by ryan wu on 2019/3/20.
 */
public class MachineModeCustomRepositoryTest extends BootServerTest {
    @Resource
    private MachineModeCustomRepository mMachineModeCustomRepository;

    @Test
    public void testFetchAccordingMachineIdsAndTs() throws ParseException {
        List<String> ids = Arrays.asList("0c96ff33-b260-4134-8021-70202b9d6b0a","17a6085b-f0f8-4510-a02e-97fbe759c87d","2d4eee90-5706-4ddd-af87-a6cdf2891532");
        Date ts = DateUtils.stringToDate("2018-03-02 09:59:59");
        List<MachineMode> mms = mMachineModeCustomRepository.fetchAccordingMachineIdsAndTs(ids, ts);
        System.out.println(JsonUtils.parserObj2String(mms));
    }
}
