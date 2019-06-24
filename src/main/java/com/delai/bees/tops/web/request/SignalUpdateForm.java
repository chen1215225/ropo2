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
public class SignalUpdateForm {
    @ApiModelProperty(value = "信号名")
    private String key;
    @ApiModelProperty(value = "父信号")
    private String parentName;
    @ApiModelProperty(value = "PLC Address预留")
    private String plcAddress;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "是否激活")
    private Boolean enable;
    @ApiModelProperty(value = "信号位置")
    private Integer val;
}
