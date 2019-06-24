package com.delai.bees.tops.repository;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.document.SignalConfig;
import org.assertj.core.util.Sets;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
public class SignalConfigRepositoryTest extends BootServerTest {
    @Resource
    private SignalConfigRepository mSignalConfigRepository;

    @Test
    public void testFindByAddresses() {
        Set<String> addresses = Sets.newTreeSet("DB1.DBD12", "DB120.DBX2.3");
        List<SignalConfig> scs = mSignalConfigRepository.findByAddresses(addresses);
        for (SignalConfig sc:
             scs) {
            System.out.println(sc.toString());
        }
    }
}
