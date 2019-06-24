package com.delai.bees.tops.utils;

import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.document.DigitalSignal;
import com.delai.bees.tops.domain.BitSignal;
import com.delai.bees.tops.entity.BitSignalConfig;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
public class BitSignalConfigUtils {
    /**
     * @param gss
     * @param as
     * @return
     */
    public static List<BitSignal> flat(List<BitSignalConfig> gss, AnalogSignal as) {
        List<BitSignal> dss = new ArrayList<>();
        BitSet asbit = BitUtils.convert(as.getVal().longValue());
        for (BitSignalConfig gs : gss) {
            BitSet gsbit = new BitSet(32);
            // 判断是否超出32位
            if (gs.getVal() > 32) {
                continue;
            }
            gsbit.set(gs.getVal().intValue() - 1);
            gsbit.and(asbit);
            if (!gsbit.isEmpty()) {
                BitSignal ds = BitSignal.builder()
                        .id(gs.getId())
                        .key(gs.getKey())
                        .parentName(as.getSignal().getAddress())
                        .val(gs.getVal())
                        .ts(as.getTs())
                        .duration(5L)
                        .build();
                dss.add(ds);
            }
        }
        return dss;
    }
}
