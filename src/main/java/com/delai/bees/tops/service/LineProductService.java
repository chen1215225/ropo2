package com.delai.bees.tops.service;

import com.delai.bees.tops.dao.LineProductMapper;
import com.delai.bees.tops.document.MachineMode;
import com.delai.bees.tops.domain.AnalogSignalGRP;
import com.delai.bees.tops.entity.LineProduct;
import com.delai.bees.tops.entity.domain.LineProdcutTimeCrossingSum;
import com.delai.bees.tops.entity.domain.Production;
import com.delai.bees.tops.repository.AnalogSignalCustomRepository;
import com.delai.bees.tops.repository.MachineModeCustomRepository;
import com.delai.bees.tops.service.constant.IConstant;
import com.delai.bees.tops.web.utils.DigitalUtils;
import com.ipukr.elephant.common.mybatis.utils.ConditionHelper;
import com.ipukr.elephant.utils.DateUtils;
import com.ipukr.elephant.utils.JsonUtils;
import com.ipukr.elephant.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/24.
 */
@Slf4j
@Service
public class LineProductService {
    @Resource
    private MachineModeCustomRepository mMachineModeCustomRepository;
    @Resource
    private AnalogSignalCustomRepository mAnalogSignalCustomRepository;
    @Resource
    private LineProductMapper mLineProductMapper;
    @Resource
    private SignalService mSignalService;

    @Value("${setting.lineview.implanter.id:}")
    private String implanterId;
    @Value("${setting.lineview.implanter.product.id:}")
    private String implanterProductId;
    @Value("${setting.lineview.bottle_blowing.product.id:}")
    private String bottleBlowingProductId;
    @Value("${setting.lineview.stacker.product.id:}")
    private String stackerProductId;


    /**
     * 根据开始时间、结束时间同步数据
     * @param begin
     * @param end
     * @throws Exception
     */
    public void syns(Date begin, Date end) throws Exception {
        // 获取所有设备状态
        List<String> mids = Arrays.asList(implanterId);
        List<MachineMode> mms = mMachineModeCustomRepository.fetchProducingAccordingMachineIdsAndTimeRange(mids, begin, end);
        log.info("同步LineProduct: mms.szie={}", mms.size());
        for (MachineMode mm : mms) {
            // 同步机器产量
            List<String> sids = Arrays.asList(implanterProductId, bottleBlowingProductId, stackerProductId);

            List<AnalogSignalGRP> asgrps = mAnalogSignalCustomRepository.fetchMaxAndMinValAccordingSignalsIdsAndTsRange(sids, mm.getOn(), mm.getOff());
            log.info("同步LineProduct: asgrps={}", JsonUtils.parserObj2String(asgrps));
            LineProduct.LineProductBuilder builder = LineProduct.builder();
            builder.on(mm.getOn());
            builder.off(mm.getOff());

            for (AnalogSignalGRP asgrp : asgrps) {
                if (asgrp.getId().equals(implanterProductId)) {
                    builder.implanterProduct(asgrp.getMax() - asgrp.getMin());
                } else if (asgrp.getId().equals(bottleBlowingProductId)) {
                    builder.bottleBlowingProduct(asgrp.getMax() - asgrp.getMin());
                } else if (asgrp.getId().equals(stackerProductId)) {
                    builder.stackerProduct(asgrp.getMax() - asgrp.getMin());
                }
            }
            // 同步生产线效率
            Production production = mSignalService.synsProductSafely(mm.getOn(), mm.getOff());
            log.info("同步LineProduct {}-{}:, production={}",
                    DateUtils.dateToString(mm.getOn()),
                    DateUtils.dateToString(mm.getOff()),
                    production);
            if (production!=null) {
                builder.eff(production.getEff());
                builder.oee(production.getOee());
                builder.products(DigitalUtils.convert(production.getProduct()));
                builder.rejected(DigitalUtils.convert(production.getRejected()));
                builder.loss(DigitalUtils.convert(production.getLoss()));
                builder.time(production.getTime());
            }
            LineProduct lineProduct = builder.build();
            log.info("同步LineProduct {}-{}: line_product={}",
                    DateUtils.dateToString(mm.getOn()),
                    DateUtils.dateToString(mm.getOff()),
                    lineProduct);
            mLineProductMapper.upsert(lineProduct);

        }
    }

    /**
     * 根据开始时间、结束时间查询数据
     * @param begin
     * @param end
     * @return
     */
    public List<LineProduct> query(Date begin, Date end) {
        List<LineProduct> productions = mLineProductMapper.condition(
                StringUtils.easyAppend("`Off` > '{}' AND `On` < '{}'",
                        DateUtils.dateToString(begin),
                        DateUtils.dateToString(end)
                )
        );
        return productions;
    }

    public LineProdcutTimeCrossingSum calproduct(Date begin, Date end){
        return mLineProductMapper.calProductsAccordingTimeCrossing(begin, end);

    }
}
