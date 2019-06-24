package com.delai.bees.tops.controller;

import com.delai.bees.tops.domain.BitSignalTop;
import com.delai.bees.tops.entity.domain.BitAnalogSignalsStats;
import com.delai.bees.tops.service.BitAnalogSignalsService;
import com.delai.bees.tops.service.SignalService;
import com.delai.bees.tops.web.response.BitAnalogSignalsStatsDTO;
import com.delai.bees.tops.web.response.BitAnalogSignalsTopsDTO;
import com.delai.bees.tops.web.response.BitSignalTop200DTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.ImmutableMap;
import com.ipukr.elephant.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Top 200报表接口 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/10.
 */
@Slf4j
@RestController
public class TopsController {

    @Resource
    private SignalService mSignalService;
    @Resource
    private BitAnalogSignalsService mBitAnalogSignalsService;

    /**
     * top 200 统计
     *
     * @param begin
     * @param end
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询Top 200统计", notes = "查询Top 200统计", response = BitSignalTop200DTO.class)
    @RequestMapping(value = "/top200", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity tops(@ApiParam(defaultValue = "2018-03-29 09:05:00") @RequestParam(value = "begin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
                               @ApiParam(defaultValue = "2018-03-29 10:05:00") @RequestParam(value = "end", required = true)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end,
                               HttpServletRequest request) throws Exception {
        if (begin == null || end == null) {
            // 给定默认值
            end = DateUtils.now();
            begin = DateUtils.dateWithOffset(end, -1, Calendar.HOUR_OF_DAY);
        } else {
            if(begin.after(end)) {
                return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "开始时间不能大于结束时间"));
            } else {
                if (DateUtils.dateWithOffset(begin, 7).before(end)) {
                    return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "查询时间范围不能超过7天"));
                }
            }
        }

        log.info("Request Top 200, begin={}, end={}", begin.toString(), end.toString());
        BitSignalTop dst200 = mSignalService.statistics(begin, end);
        BitSignalTop200DTO dto = BitSignalTop200DTO.parser(dst200);

        return ResponseEntity.ok(dto);
    }


    /**
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation(value = "查询Top 200统计", notes = "查询Top 200统计", response = BitAnalogSignalsTopsDTO.class)
    @GetMapping(value = "/tops")
    public ResponseEntity tops(@ApiParam(defaultValue = "2018-03-29 09:05:00") @RequestParam(value = "begin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
                               @ApiParam(defaultValue = "2018-03-29 10:05:00") @RequestParam(value = "end", required = true)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end) throws IOException {
        if (begin == null || end == null) {
            // 给定默认值
            end = DateUtils.now();
            begin = DateUtils.dateWithOffset(end, -1, Calendar.HOUR_OF_DAY);
        } else {
            if(begin.after(end)) {
                return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "开始时间不能大于结束时间"));
            } else {
                if (DateUtils.dateWithOffset(begin, 7).before(end)) {
                    return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "查询时间范围不能超过7天"));
                }
            }
        }
        // 基础数据
        PageBounds bounds = new PageBounds(1, 200, false);
        List<BitAnalogSignalsStats> basss = mBitAnalogSignalsService.tops(begin, end, bounds);
        List<BitAnalogSignalsStatsDTO> basssdtos = BitAnalogSignalsStatsDTO.parser(basss);


        List<BitAnalogSignalsStats> tops = basss.stream().sorted(new Comparator<BitAnalogSignalsStats>() {
            @Override
            public int compare(BitAnalogSignalsStats o1, BitAnalogSignalsStats o2) {
                return o2.getCount().compareTo(o1.getCount());
            }
        }).limit(20).collect(Collectors.toList());
        List<BitAnalogSignalsStatsDTO> topsdtos = BitAnalogSignalsStatsDTO.parser(tops);


        Set<String> machines = basss.stream().filter(e->e.getSignal()!=null && e.getSignal().getMachine()!=null).map(e->e.getSignal().getMachine().getName()).collect(Collectors.toSet());

        BitAnalogSignalsTopsDTO bastd = BitAnalogSignalsTopsDTO.builder()
                .basssdtos(basssdtos)
                .topsdtos(topsdtos)
                .machines(machines)
                .build();

        return ResponseEntity.ok(bastd);
    }


}
