package com.delai.bees.tops.controller;

import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.service.SignalService;
import com.delai.bees.tops.service.helper.ExcelHelper;
import com.delai.bees.tops.web.request.SignalCreateForm;
import com.delai.bees.tops.web.request.SignalQueryForm;
import com.delai.bees.tops.web.request.SignalUpdateForm;
import com.delai.bees.tops.web.response.BitSignalConfigDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.ImmutableMap;
import com.ipukr.elephant.common.web.http.PaginationResponseEntity;
import com.ipukr.elephant.utils.DataUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
@Slf4j
@RestController
@RequestMapping(value = "/signal")
public class SignalController {

    @Resource
    private SignalService mSignalService;

    /**
     * @param form
     * @param pageIndex
     * @param pageSize
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "查询配置信号", notes = "查询配置信号", response = BitSignalConfigDTO.class)
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public PaginationResponseEntity search(@ModelAttribute SignalQueryForm form,
                                           @RequestParam(value = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                           HttpServletRequest request) throws Exception {
        BitSignalConfig example = BitSignalConfig.builder()
                .key(form.getKey()!=null && !form.getKey().equals("")?form.getKey():null)
                .build();
        PageBounds bounds = new PageBounds(pageIndex, pageSize, true);
        List<BitSignalConfig> gss = mSignalService.search(example, bounds);
        List<BitSignalConfigDTO> dtos = BitSignalConfigDTO.parser(gss);
        return PaginationResponseEntity.ok(dtos);
    }


    /**
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "删除配置", notes = "删除配置", response = Boolean.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable(value = "id") Long id,
                                 HttpServletRequest request) throws Exception {
        BitSignalConfig signal = mSignalService.find(id);
        if (signal != null) {
            boolean ops = mSignalService.delete(signal);
            return ResponseEntity.ok(ImmutableMap.of("ops", ops));
        } else {
            return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "未找到待删除的数据记录"));
        }
    }

    /**
     * @param id
     * @param enable
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "激活信号", notes = "激活", response = Boolean.class)
    @RequestMapping(value = "/{id}/activate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity method(@PathVariable(value = "id") Long id,
                                 @RequestParam(value = "enable") Boolean enable,
                                 HttpServletRequest request) throws Exception {
        BitSignalConfig signal = mSignalService.find(id);
        if (signal != null) {
            signal.setEnable(enable);
            BitSignalConfig gs = mSignalService.update(signal);
            BitSignalConfigDTO dto = BitSignalConfigDTO.parser(gs);
            return ResponseEntity.ok(ImmutableMap.of("ops", true, "dto", dto));
        } else {
            return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "未找到待删除的数据记录"));
        }

    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "清空数据", notes = "清空数据", response = Boolean.class)
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request) throws Exception {
        boolean ops = mSignalService.clear();
        return ResponseEntity.ok(ImmutableMap.of("ops", ops));
    }

    /**
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改", notes = "修改", response = BitSignalConfigDTO.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity update(@PathVariable(value = "id") Long id,
                                 @ModelAttribute SignalUpdateForm form,
                                 HttpServletRequest request) throws Exception {
        BitSignalConfig signal = mSignalService.find(id);
        if (signal!=null) {
            DataUtils.copyPropertiesIgnoreNull(form, signal);
            BitSignalConfig gs = mSignalService.update(signal);
            BitSignalConfigDTO dto = BitSignalConfigDTO.parser(gs);
            return ResponseEntity.ok(ImmutableMap.of("ops", true, "dto", dto));
        }else {
            return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "未找到待修改的数据记录"));
        }
    }



    /**
     * @param form
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "新增数据", notes = "新增数据", response = Boolean.class)
    @RequestMapping( method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity insert(@RequestBody SignalCreateForm form,
                                 HttpServletRequest request) throws Exception {
        if (form.getItems()!=null && form.getItems().size() > 0) {
            List<BitSignalConfig> models = form.convert();

            boolean bool = mSignalService.batimport(models);
            return ResponseEntity.ok(ImmutableMap.of("ops", true));
        } else {
            return ResponseEntity.ok(ImmutableMap.of("ops", false, "msg", "提交的数据不能为空"));
        }
    }


    /**
     * @param file
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入模板", notes = "导入模板", response = Boolean.class)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity upload(@RequestParam(value="filename") MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        //判断文件是否为空
        if ( file==null ) {
            log.warn("未捕获文件");
            return ResponseEntity.ok().body(ImmutableMap.of("ops",false, "msg" ,"文件不能为空"));
        } else {
            //获取文件名
            String fileName = file.getOriginalFilename();
            //验证文件名是否合格
            if(!ExcelHelper.validate(fileName)){
                log.warn("文件类型不正确");
                return ResponseEntity.ok().body(ImmutableMap.of("ops",false, "msg" ,"文件必须是excel格式"));
            } else {
                //进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
                if (StringUtils.isEmpty(fileName) || file.getSize()==0) {
                    log.warn("文件内容为空");
                    return ResponseEntity.ok().body(ImmutableMap.of("ops",true, "msg" ,"文件不能为空"));
                } else {
                    InputStream ins = new BufferedInputStream(file.getInputStream());
                    //根据版本选择创建Workbook的方式
                    Workbook wb = null;
                    //根据文件名判断文件是2003版本还是2007版本
                    if(ExcelHelper.isExcel2007(fileName)){
                        wb = new XSSFWorkbook(ins);
                    }else{
                        wb = new HSSFWorkbook(ins);
                    }

                    log.warn("开始导入Excel数据...");
                    List<BitSignalConfig> bscs = ExcelHelper.read(wb);
                    boolean bool = mSignalService.batimport(bscs);
                    return ResponseEntity.ok().body(ImmutableMap.of("ops", bool, "msg" ,bool ? "导入数据成功" : "导入数据失败"));
                }
            }
        }
    }

}
