package com.delai.bees.tops.web.utils;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/21.
 */
public class DigitalUtils {

    /**
     * 字符转数值
     * @param text
     * @return
     */
    public static Long convert(String text) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
