package com.delai.bees.tops.web.response;

import com.delai.bees.tops.domain.BitSignalTop;
import com.delai.bees.tops.service.constant.IConstant;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ipukr.elephant.utils.DataUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.IOException;
import java.util.ArrayList;
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
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BitSignalTop200StatsDTO {
    @ApiModelProperty(value = "唯一标识")
    private String key;
    @ApiModelProperty(value = "颜色")
    private String color;
    @ApiModelProperty(value = "计数值")
    private Long count;
    @ApiModelProperty(value = "合计")
    private Double sum;
    @ApiModelProperty(value = "最小值")
    private Double min;
    @ApiModelProperty(value = "平均值")
    private Double average;
    @ApiModelProperty(value = "最大值")
    private Double max;
    @ApiModelProperty(value = "持续时间")
    private Double duration;
    @ApiModelProperty(value = "持续时间(文本)")
    private String sduration;

    /**
     * 获取DTO对象
     *
     * @param entry
     * @param index
     * @return
     * @throws IOException
     */
    public static BitSignalTop200StatsDTO parser(Map.Entry<String, DoubleSummaryStatistics> entry, int index) throws IOException {
        BitSignalTop200StatsDTO stat = BitSignalTop200StatsDTO.builder()
                .key(entry.getKey())
                .count(entry.getValue().getCount())
                .sum(entry.getValue().getSum())
                .min(entry.getValue().getMin())
                .max(entry.getValue().getMax())
                .average(entry.getValue().getAverage())
                .duration(entry.getValue().getCount() * 5D)
                .color(IConstant.COLORS[Math.min(index++, IConstant.COLORS.length - 1 )])
                .build();
        return stat;
    }
    /**
     * 获取DTO列表对象
     *
     * @param bst
     * @return
     * @throws IOException
     */
    public static List<BitSignalTop200StatsDTO> parser(Map<String, DoubleSummaryStatistics> bst) throws IOException {
        List<BitSignalTop200StatsDTO> arrays = new ArrayList<>();
        int idx = 0;
        for(Map.Entry<String, DoubleSummaryStatistics> entry : bst.entrySet()) {
            arrays.add(parser(entry, ++idx));
        }
        return arrays;
    }

}
