package com.delai.bees.tops.utils;

import com.ipukr.elephant.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/22.
 */
public class MongoDateHelper {

    private static final String DT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static SimpleDateFormat format = new SimpleDateFormat(DT_PATTERN);

    public static Date todate(Date date) throws ParseException {
        return format.parse(DateUtils.dateToString(date, DT_PATTERN));
    }
}
