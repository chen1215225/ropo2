package com.delai.bees.tops.web.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class BitAnalogSignalsTopsDTO {
    @ApiModelProperty(value = "统计记录Top200")
    private List<BitAnalogSignalsStatsDTO> basssdtos;
    @ApiModelProperty(value = "设备列表")
    private Set<String> machines;
    @ApiModelProperty(value = "统计记录Top20")
    private List<BitAnalogSignalsStatsDTO> topsdtos;

}

