package com.delai.bees.tops.utils;

import com.delai.bees.tops.web.response.ProductionOverviewDTO;
import com.delai.bees.tops.web.utils.DateHelper;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.JsonUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/16.
 */
public class DateUtilsTest {
    @Test
    public void testParser() {
        String ds = DateHelper.parser(584*1000L);
        System.out.println(ds);
    }

    @Test
    public void name() {
        ProductionOverviewDTO dto = ProductionOverviewDTO.builder().build();
        dto.setBottleBlowingProducts(0L);
        dto.setStackerProducts(0L);
        dto.setImplanterProducts(0L);
        dto.setTime(1770L);
        System.out.println(dto.toString());
        dto.setTime(10L);
        System.out.println(dto.toString());
    }
}
