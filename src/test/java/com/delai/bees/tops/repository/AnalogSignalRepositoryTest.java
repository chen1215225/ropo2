package com.delai.bees.tops.repository;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.document.AnalogSignal;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
public class AnalogSignalRepositoryTest extends BootServerTest {
    @Resource
    private AnalogSignalRepository mAnalogSignalRepository;

    @Test
    public void testQuery() {
        Pageable pageable = new PageRequest(1, 10);
        List<AnalogSignal> ass = mAnalogSignalRepository.query(pageable);
        for (AnalogSignal as :
                ass) {
            System.out.println(as.toString());
        }
    }
}
