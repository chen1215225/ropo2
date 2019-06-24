package com.delai.bees.tops.domain;

import com.delai.bees.tops.document.DigitalSignal;
import com.delai.bees.tops.entity.BitAnalogSignals;
import com.delai.bees.tops.entity.BitSignalConfig;
import lombok.*;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BitSignalTop {

    /**
     *
     */
    private Map<String, DoubleSummaryStatistics> statistics;

    @Deprecated
    private List<BitSignal> bss;

    /**
     * 配置信号
     */
    private List<BitSignalConfig> bscs;

    /**
     * 故障信号（bit）
     */
    private List<BitAnalogSignals> bass;
}
