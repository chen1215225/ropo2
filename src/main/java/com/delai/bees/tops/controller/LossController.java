package com.delai.bees.tops.controller;

import com.delai.bees.tops.entity.LineProduct;
import com.delai.bees.tops.entity.domain.LineProdcutTimeCrossingSum;
import com.delai.bees.tops.entity.domain.MachineProduct;
import com.delai.bees.tops.entity.domain.MachineProduction;
import com.delai.bees.tops.entity.domain.Production;
import com.delai.bees.tops.service.LineProductService;
import com.delai.bees.tops.service.SignalService;
import com.delai.bees.tops.service.constant.IConstant;
import com.delai.bees.tops.web.response.ProductionOverviewDTO;
import com.delai.bees.tops.web.utils.DigitalUtils;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.ExcelUtils;
import com.ipukr.elephant.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/20.
 */
@RestController
@RequestMapping(value = "/loss")
public class LossController {

    @Resource
    private SignalService mSignalService;
    @Resource
    private LineProductService mLineProductService;

    @ApiOperation(value = "获取整线概况", response = ProductionOverviewDTO.class)
    @GetMapping("/overview")
    public ResponseEntity<ProductionOverviewDTO> overview(@ApiParam(defaultValue = "2018-03-29 09:05:00") @RequestParam(value = "begin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
                                                          @ApiParam(defaultValue = "2018-03-29 10:05:00") @RequestParam(value = "end", required = true)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end) throws Exception {

        // 发布任务
        Future<Production> pfu = mSignalService.synsOverview(begin, end);

        // 获取结果
        ProductionOverviewDTO podto = ProductionOverviewDTO.parser(pfu.get());

        LineProdcutTimeCrossingSum sum = mLineProductService.calproduct(begin, end);

        if ( sum!=null ) {
            podto.setImplanterProducts(sum.getImplanterProductSum());
            podto.setStackerProducts(sum.getStackerProductSum());
            podto.setBottleBlowingProducts(sum.getBottleBlowingProductSum());
            podto.setTime(sum.getTimeSum()!=null?sum.getTimeSum():0L);
        }

        return ResponseEntity.ok(podto);
    }


    @ApiOperation(value = "获取整线概况", response = ProductionOverviewDTO.class)
    @GetMapping("/overview/test")
    public ResponseEntity<ProductionOverviewDTO> testoverview(@ApiParam(defaultValue = "2018-03-29 09:05:00") @RequestParam(value = "begin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
                                                              @ApiParam(defaultValue = "2018-03-29 10:05:00") @RequestParam(value = "end", required = true)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end) throws Exception {

        ProductionOverviewDTO podto = ProductionOverviewDTO.builder()
                .eff("93%")
                .oee("91%")
                .product("187561")
                .rejected("2312")
                .loss("7312")
                .time("-")
                .bottleBlowingProducts(64543120L)
                .stackerProducts(63458640L)
                .implanterProducts(6308026L)
                .build();
        return ResponseEntity.ok(podto);
    }



    @ApiOperation(value = "获取整线概况", response = ProductionOverviewDTO.class)
    @GetMapping("/overview/download")
    public void download(@ApiParam(defaultValue = "2019-03-01 10:05:00") @RequestParam(value = "begin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date begin,
                         @ApiParam(defaultValue = "2019-03-23 10:05:00") @RequestParam(value = "end", required = true)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end,
                         HttpServletResponse response) throws Exception {
        List<LineProduct> products = mLineProductService.query(begin, end);

        ClassPathResource resource = new ClassPathResource("template".concat("/").concat(IConstant.TEMPLATE_FILENAME));
        InputStream ins = resource.getInputStream();


        // 设置为下载 application/x-download
        response.setHeader("Content-disposition", "attachment;filename="+IConstant.TEMPLATE_FILENAME);
        response.setContentType("application/x-download");

        Workbook wb = WorkbookFactory.create(ins);

        Sheet sheet = wb.getSheet(IConstant.TEMPLATE_FILENAME_SHEET);
        sheet.setForceFormulaRecalculation(true);

        int rowoffset = 1;
        for (LineProduct product : products) {
            int coloffset = 0;
            Row row = sheet.createRow(rowoffset);

            /**
             * 编号
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getId());
            }
            /**
             * 开始时间
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(DateUtils.dateToString(product.getOn()));
            }
            /**
             * 结束时间
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(DateUtils.dateToString(product.getOff()));
            }
            /**
             * OEE
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(product.getOee());
            }
            /**
             * EFF
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(product.getEff());
            }
            /**
             * 整线产量
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getProducts());
            }

            /**
             * 整线剔除
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getRejected());
            }
            /**
             * 整线损失
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getLoss());
            }
            /**
             * 声场时间
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(product.getTime());
            }

            /**
             * 码垛机产量
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getStackerProduct());
            }
            /**
             * 吹瓶机产量
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getBottleBlowingProduct());
            }
            /**
             * 核心机产量
             * */
            {
                Cell cell = row.createCell(coloffset++);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(product.getImplanterProduct());
            }

            rowoffset ++ ;
        }

        OutputStream ous = response.getOutputStream();
        wb.write(ous);
        ous.flush();
        ous.close();

    }

}
