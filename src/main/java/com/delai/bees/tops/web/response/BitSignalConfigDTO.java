package com.delai.bees.tops.web.response;

import com.delai.bees.tops.entity.BitSignalConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ipukr.elephant.utils.DataUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
 * Created by ryan wu on 2019/1/12.
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BitSignalConfigDTO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "信号值")
    private String key;
    @ApiModelProperty(value = "父信号")
    private String parentName;
    private String plcAddress;
    @ApiModelProperty(value = "是否激活")
    private Boolean enable;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "位置")
    private Integer val;
    @ApiModelProperty(value = "位置")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "设备名")
    private String machine;

    /**
     * 获取DTO对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static BitSignalConfigDTO parser(BitSignalConfig obj) throws IOException {
        BitSignalConfigDTO dto = DataUtils.copyPropertiesIgnoreNull(obj, BitSignalConfigDTO.class);
        if (obj.getSignal()!=null && obj.getSignal().getMachine()!=null) {
            dto.setMachine(obj.getSignal().getMachine().getName());
        } else {
            dto.setMachine("未知");
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
    public static List<BitSignalConfigDTO> parser(List<BitSignalConfig> objs) throws IOException {
        List<BitSignalConfigDTO> arrays = new ArrayList<>();
        if(objs instanceof PageList) {
            arrays = new PageList<BitSignalConfigDTO>(((PageList) objs).getPaginator());
        }
        for(BitSignalConfig obj : objs) {
            arrays.add(parser(obj));
        }
        return arrays;
    }

}
