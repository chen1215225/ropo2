package com.delai.bees.tops.controller;

import com.delai.bees.tops.entity.domain.MachineProduction;
import com.delai.bees.tops.service.BitAnalogSignalsService;
import com.delai.bees.tops.service.LineProductService;
import com.delai.bees.tops.service.SignalService;
import com.ipukr.elephant.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/14.
 */
@Controller
public class ActionController {

    @Resource
    private SignalService mSignalService;
    @Resource
    private BitAnalogSignalsService mBitAnalogSignalsService;
    @Resource
    private LineProductService mLineProductService;

    @RequestMapping(value = "/")
    public String index(){
        return "/views/index.html";
    }


    @ApiOperation(value = "故障信号重跑", notes = "同步故障信号Top200")
    @GetMapping(value = "/op/{offset}")
    public ResponseEntity op(@PathVariable Integer offset) {
        Date d = DateUtils.now();
        Date begin = DateUtils.dateWithOffset(d, -offset , Calendar.DAY_OF_YEAR);
        Date end = DateUtils.dateWithOffset(d, 0, Calendar.DAY_OF_YEAR);
        mBitAnalogSignalsService.schedule(begin, end);
        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/op/products")
    public ResponseEntity products(@ApiParam(defaultValue = "2018-03-29 09:05:00") @RequestParam(value = "begin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
                                   @ApiParam(defaultValue = "2018-03-29 10:05:00") @RequestParam(value = "end", required = true)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end) {
        MachineProduction products = mSignalService.getMachineProductsAccordingTimeRange(begin, end);
        return ResponseEntity.ok(products);
    }

    /**
     * @param offset
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "产量数据重跑", notes = "同步LineView产量")
    @GetMapping(value = "/syns/{offset}")
    public ResponseEntity syns(@RequestParam(value = "location", defaultValue = "0") Integer location,
                               @PathVariable Integer offset) throws Exception {
        Date d = DateUtils.now();
        Date begin = DateUtils.dateWithOffset(d, -offset , Calendar.DAY_OF_YEAR);
        Date end = DateUtils.dateWithOffset(d, -location, Calendar.DAY_OF_YEAR);
        mLineProductService.syns(begin, end);
        return ResponseEntity.ok("success");
    }


}
