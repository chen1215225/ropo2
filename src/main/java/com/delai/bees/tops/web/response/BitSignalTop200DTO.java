package com.delai.bees.tops.web.response;

import com.delai.bees.tops.domain.BitSignal;
import com.delai.bees.tops.domain.BitSignalTop;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.service.constant.IConstant;
import com.delai.bees.tops.web.utils.DateHelper;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ipukr.elephant.utils.DataUtils;
import com.ipukr.elephant.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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
public class BitSignalTop200DTO {
    @ApiModelProperty(value = "统计相关")
    List<BitSignalTop200StatsDTO> statistics;
    @ApiModelProperty(value = "列表数据")
    List<BitSignalTop200RecordDTO> records;
    @ApiModelProperty(value = "设备列表")
    List<String> machines;
    /**
     * 获取DTO对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static BitSignalTop200DTO parser(BitSignalTop obj) throws IOException {
        BitSignalTop200DTO dto = DataUtils.copyPropertiesIgnoreNull(obj, BitSignalTop200DTO.class);
        // 统计数据
        List<BitSignalTop200StatsDTO> stats = new ArrayList<>();
//        int index = 0;
        DecimalFormat df = new DecimalFormat("#.00");
        for (Map.Entry<String, DoubleSummaryStatistics> entry : obj.getStatistics().entrySet()) {

            Double duration = Math.round(entry.getValue().getCount() * 5D / 60D * 100D) / 100D;
            String sduration = DateHelper.parser(entry.getValue().getCount() * 5L * 1000L);

            BitSignalTop200StatsDTO stat = BitSignalTop200StatsDTO.builder()
                    .key(entry.getKey())
                    .count(entry.getValue().getCount())
                    .sum(entry.getValue().getSum())
                    .min(entry.getValue().getMin())
                    .max(entry.getValue().getMax())
                    .average(entry.getValue().getAverage())
                    .duration(duration)
                    .sduration(sduration)
                    .build();
            stats.add(stat);
        }
        // 统计数据排序
        Collections.sort(stats, new Comparator<BitSignalTop200StatsDTO>() {
            @Override
            public int compare(BitSignalTop200StatsDTO o1, BitSignalTop200StatsDTO o2) {
                return o2.getCount().compareTo(o1.getCount());
            }
        });

        // 列表数据
        List<BitSignalTop200RecordDTO> records = new ArrayList<>();
        for (BitSignalConfig bsc : obj.getBscs()) {
            DoubleSummaryStatistics dss = obj.getStatistics().get(bsc.getKey());
            Optional<BitSignal> begin = obj.getBss().stream().filter(e->e.getKey().equals(bsc.getKey())).min((o1, o2) -> o1.getTs().compareTo(o2.getTs()));
            Optional<BitSignal> end = obj.getBss().stream().filter(e->e.getKey().equals(bsc.getKey())).max((o1, o2) -> o1.getTs().compareTo(o2.getTs()));
            BitSignalTop200RecordDTO record = BitSignalTop200RecordDTO.builder()
                    .id(bsc.getId())
                    .key(bsc.getKey())
                    .parentName(bsc.getAddress())
                    .val(bsc.getVal())
                    .begin(begin.isPresent()? DateUtils.dateToString(begin.get().getTs()):"-")
                    .end(end.isPresent()? DateUtils.dateToString(end.get().getTs()):"-")
                    .duration(dss==null?0:dss.getCount()*5L)
                    .machine(bsc.getSignal()!=null && bsc.getSignal().getMachine()!=null ? bsc.getSignal().getMachine().getName():"未知")
                    .count(dss==null?0:dss.getCount())
                    .build();
            records.add(record);
        }
        // 列表数据排序
        Collections.sort(records, new Comparator<BitSignalTop200RecordDTO>() {
            @Override
            public int compare(BitSignalTop200RecordDTO o1, BitSignalTop200RecordDTO o2) {
                return o2.getCount().compareTo(o1.getCount());
            }
        });
        List<String> machines = records.stream().map(e->e.getMachine()).distinct().collect(Collectors.toList());

        // Append 前端颜色
        List<BitSignalTop200StatsDTO> topstats = stats.subList(0, Math.min(stats.size(), 20));
        int index = 0;
        for (BitSignalTop200StatsDTO topstat : topstats) {
            topstat.setColor(IConstant.COLORS[Math.min(index++, IConstant.COLORS.length - 1 )]);
        }

        dto.setStatistics(topstats);

        List<BitSignalTop200RecordDTO> rdtos = records.subList(0, Math.min(records.size(), 200));
        int sort = 0;
        for (BitSignalTop200RecordDTO d : rdtos) {
            d.setIndex(++sort);
        }

        dto.setRecords(rdtos);
        dto.setMachines(machines);
        return dto;
    }

    /**
     * 获取DTO列表对象
     *
     * @param objs
     * @return
     * @throws IOException
     */
    public static List<BitSignalTop200DTO> parser(List<BitSignalTop> objs) throws IOException {
        List<BitSignalTop200DTO> arrays = new ArrayList<>();
        if(objs instanceof PageList) {
            arrays = new PageList<BitSignalTop200DTO>(((PageList) objs).getPaginator());
        }
        for(BitSignalTop obj : objs) {
            arrays.add(parser(obj));
        }
        return arrays;
    }

}
