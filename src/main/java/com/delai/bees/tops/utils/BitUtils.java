package com.delai.bees.tops.utils;

import java.util.BitSet;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
public class BitUtils {

    public static BitSet convert(Long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    public static Long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    /**
     * @param position
     * @return
     */
    public static Long convert(Integer position) {
        BitSet bits = new BitSet(32);
        bits.set(position, true);
        return convert(bits);
    }
}

