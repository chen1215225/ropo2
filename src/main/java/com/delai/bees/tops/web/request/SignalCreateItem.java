package com.delai.bees.tops.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class SignalCreateItem {
    @ApiModelProperty(value = "信号名")
    private String key;
    @ApiModelProperty(value = "PLC Address预留", required = false)
    private String plcAddress;
    @ApiModelProperty(value = "备注", required = false)
    private String remark;
    @ApiModelProperty(value = "信号位置")
    private Integer val;
}
