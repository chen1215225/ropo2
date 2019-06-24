package com.delai.bees.tops.service;

import com.delai.bees.tops.BootServerTest;
import com.delai.bees.tops.entity.LineProduct;
import com.delai.bees.tops.entity.domain.LineProdcutTimeCrossingSum;
import com.ipukr.elephant.utils.DateUtils;
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
 * Created by ryan wu on 2019/3/24.
 */
public class LineProductServiceTest extends BootServerTest {
    @Resource
    private LineProductService mLineProductService;

    @Test
    public void testQuery() throws ParseException {
        Date begin = DateUtils.stringToDate("2019-03-23 00:00:00");
        Date end = DateUtils.stringToDate("2019-03-24 00:00:00");
        List<LineProduct> products = mLineProductService.query(begin, end);
        for (LineProduct product: products) {
            System.out.println(product.toString());
        }
    }

    @Test
    public void testCalproduct() throws ParseException {
        Date begin = DateUtils.stringToDate("2019-03-23 00:00:00");
        Date end = DateUtils.stringToDate("2019-03-24 23:00:00");
        LineProdcutTimeCrossingSum sum = mLineProductService.calproduct(begin, end);
        System.out.println(sum.toString());
    }
}
