package com.delai.bees.tops.web.request;

import com.delai.bees.tops.entity.BitSignalConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
public class SignalCreateForm {
    @ApiModelProperty(value = "组信号名称")
    private String parentName;
    @ApiModelProperty(value = "数据项")
    List<SignalCreateItem> items;


    /**
     * @return
     */
    public List<BitSignalConfig> convert() {
        List<BitSignalConfig> gss = new ArrayList<BitSignalConfig>();
        for(SignalCreateItem item : items) {
            BitSignalConfig gs = BitSignalConfig.builder()
                    .key(item.getKey())
                    .address(parentName)
                    .remark(item.getRemark())
                    .val(item.getVal())
                    .build();
            gss.add(gs);
        }
        return gss;
    }
}
