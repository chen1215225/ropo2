package com.delai.bees.tops.web.utils;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/16.
 */
public class DateHelper {
    /**
     * 毫秒转时间
     * @param millis
     * @return
     */
    public static String parser(Long millis) {
        Long hour = TimeUnit.MILLISECONDS.toHours(millis);
        Long minute = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        Long second = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        if (hour!=0) {
            return String.format("%d时%d分%d秒", hour, minute, second);
        } else {
            if (minute!=0) {
                return String.format("%d分%d秒", minute, second);
            } else {
                return String.format("%d秒", second);
            }
        }
    }
}
