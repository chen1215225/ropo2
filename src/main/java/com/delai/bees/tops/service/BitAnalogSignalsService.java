package com.delai.bees.tops.service;

import com.delai.bees.tops.dao.BitAnalogSignalsMapper;
import com.delai.bees.tops.dao.BitSignalConfigMapper;
import com.delai.bees.tops.document.AnalogSignal;
import com.delai.bees.tops.document.Machine;
import com.delai.bees.tops.document.SignalConfig;
import com.delai.bees.tops.entity.BitAnalogSignals;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.entity.domain.BitAnalogSignalsStats;
import com.delai.bees.tops.repository.AnalogSignalRepository;
import com.delai.bees.tops.repository.MachineRepository;
import com.delai.bees.tops.repository.SignalConfigRepository;
import com.delai.bees.tops.service.helper.BitSignalConfigHelper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/18.
 */
@Slf4j
@Service
@Transactional
public class BitAnalogSignalsService {
    @Resource
    private BitSignalConfigMapper mBitSignalConfigMapper;
    @Resource
    private MachineRepository mMachineRepository;
    @Resource
    private AnalogSignalRepository mAnalogSignalRepository;
    @Resource
    private BitAnalogSignalsMapper mBitAnalogSignalsMapper;
    @Resource
    private SignalConfigRepository mSignalConfigRepository;

    /**
     * Tops 统计
     * @param begin
     * @param end
     * @return
     */
    public List<BitAnalogSignalsStats> tops(Date begin, Date end, PageBounds bounds) {
        // 统计信号数据
        List<BitAnalogSignalsStats> basss = mBitSignalConfigMapper.statsTopsBitAnalogSignalsAccordingTimeRange(begin, end, bounds);

        if (basss.size() > 0) {
            // 获取信号配置
            Set<String> addresses = basss.stream().map(e -> e.getAddress()).collect(Collectors.toSet());
            List<SignalConfig> scs = mSignalConfigRepository.findByAddresses(addresses);
            // 获取机器信息
            List<Machine> machines = mMachineRepository.find();
            BitSignalConfigHelper.combine(scs, machines);

            for (BitAnalogSignalsStats bass : basss) {
                for (SignalConfig sc : scs) {
                    if (sc.getAddress().equals(bass.getAddress())) {
                        bass.setSignal(sc);
                    }
                }
            }
        }
        return basss;
    }

    /**
     * 定时任务 同步信号
     * @param begin
     * @param end
     */
    public void schedule(Date begin, Date end) {
        List<BitSignalConfig> bscs = mBitSignalConfigMapper.query(BitSignalConfig.builder()
                .enable(true)
                .build());

        // 根据Address获取MongoDB信号配置
        Set<String> addresses = bscs.stream().map(e->e.getAddress()).collect(Collectors.toSet());
        List<SignalConfig> scs = mSignalConfigRepository.findByAddresses(addresses);
        // 根据信号主键获取MongoDB模拟信号数据
        List<String> ids = scs.stream().map(e->e.getId()).collect(Collectors.toList());
        List<AnalogSignal> ass = mAnalogSignalRepository.ins4(ids, begin.getTime(), end.getTime());
        log.info("执行定时同步信号任务：bscs.size={}, addresses.size={}, scs.size={}, ass.siza={}", bscs.size(), addresses.size(), scs.size(), ass.size());
        List<BitAnalogSignals> bass = new ArrayList<>();
        for (AnalogSignal as : ass) {
            BitAnalogSignals bas = BitAnalogSignals.builder()
                    .id(as.getId())
                    .name(as.getName())
                    .val(as.getVal())
                    .signalId(as.getSignalId())
                    .ts(as.getTs())
                    .build();
            bass.add(bas);
            if (bass.size() > 1000) {
                log.info("执行定时同步信号任务：批量插入信号数据，bass.siza={}", bass.size());
                mBitAnalogSignalsMapper.batupsert(bass);
                bass.clear();
            }
        }
        if (bass.size() > 0) {
            log.info("执行定时同步信号任务：批量插入信号数据，bass.siza={}", bass.size());
            mBitAnalogSignalsMapper.batupsert(bass);
        }
    }
}
//</editor-fold>
