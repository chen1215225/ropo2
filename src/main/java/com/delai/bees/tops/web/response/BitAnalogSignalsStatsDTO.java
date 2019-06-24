package com.delai.bees.tops.web.response;

import com.delai.bees.tops.entity.BitAnalogSignals;
import com.delai.bees.tops.entity.domain.BitAnalogSignalsStats;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ipukr.elephant.utils.DataUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class BitAnalogSignalsStatsDTO {
    @ApiModelProperty(value = "设备名称")
    private String machine;
    @ApiModelProperty(value = "remark")
    private String remark;
    @ApiModelProperty(value = "Address")
    private String key;
    @ApiModelProperty(value = "信号主键")
    private String signalId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date begin;
    @ApiModelProperty(value = "排序值")
    private Integer sorted;
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;
    @ApiModelProperty(value = "次数")
    private Integer count;
    @ApiModelProperty(value = "持续时间")
    private Integer duration;
    @ApiModelProperty(value = "持续时间")
    private String sduration;

    public String getSduration() {
        return duration!=null?duration.toString():"";
    }

    /**
     * 获取DTO对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static BitAnalogSignalsStatsDTO parser(BitAnalogSignalsStats obj) throws IOException {
        BitAnalogSignalsStatsDTO dto = DataUtils.copyPropertiesIgnoreNull(obj, BitAnalogSignalsStatsDTO.class);
        if (obj.getSignal()!=null && obj.getSignal().getMachine()!=null) {
            dto.setMachine(obj.getSignal().getMachine().getName());
        } else {
            dto.setMachine("-");
        }
        return dto;
    }
    /**
     * 获取DTO列表对象
     *
     * @param objs
     * @return
     * @throws IOException
     */
    public static List<BitAnalogSignalsStatsDTO> parser(List<BitAnalogSignalsStats> objs) throws IOException {
        List<BitAnalogSignalsStatsDTO> arrays = new ArrayList<>();
        if(objs instanceof PageList) {
            arrays = new PageList<BitAnalogSignalsStatsDTO>(((PageList) objs).getPaginator());
        }
        int sorted = 1;
        for(BitAnalogSignalsStats obj : objs) {
            BitAnalogSignalsStatsDTO dto = parser(obj);
            dto.setSorted(sorted++);
            // FIXME 临时写死
            dto.setMachine("一体机");
            arrays.add(dto);
        }
        return arrays;
    }

}
