package com.delai.bees.tops.service;

import com.delai.bees.tops.document.CausalLoss;
import com.delai.bees.tops.document.DigitalSignal;
import com.delai.bees.tops.document.Machine;
import com.delai.bees.tops.document.SignalConfig;
import com.delai.bees.tops.repository.CausalLossRepository;
import com.delai.bees.tops.repository.DigitalSignalRepository;
import com.delai.bees.tops.repository.MachineRepository;
import com.delai.bees.tops.repository.SignalConfigRepository;
import com.delai.bees.tops.service.helper.DigitalSignalHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/4/3.
 */
@Slf4j
@Service
public class DigitalSignalService {

    @Resource
    private DigitalSignalRepository mDigitalSignalRepository;
    @Resource
    private SignalConfigRepository mSignalConfigRepository;
    @Resource
    private MachineRepository mMachineRepository;

    @Resource
    private CausalLossRepository mCausalLossRepository;


    public List<DigitalSignal> range(Date from, Date to) {
        List<DigitalSignal> dss = mDigitalSignalRepository.range(from, to);
        List<SignalConfig> scs = mSignalConfigRepository.find();
        List<Machine> machines = mMachineRepository.find();
        DigitalSignalHelper.combine(dss, scs, machines);
        return dss;
    }


    @Value("${setting.lineview.implanter.id:}")
    private String implanterId;

    /**
     * @param begin
     * @param end
     */
    public void autocomm(Date begin, Date end) {
        List<DigitalSignal> dss = range(begin, end);
        if (dss.size()>0) {
            // 核心信号自动备注
            List<DigitalSignal> cores = DigitalSignalHelper.divided(dss, implanterId);
            for (DigitalSignal signal : cores) {
                // 保存命中信号
                if (signal.getComment() != null
                        && signal.getComment().getConcern().startsWith("@")
                        && !signal.getComment().getConcern().endsWith(".")) {
                    signal.getComment().setConcern(signal.getComment().getConcern() + ".");
                    log.info("命中错误信号，更新状态信号备注 _id={}, comment={}", signal.getId(), signal.getComment());
                    mDigitalSignalRepository.save(signal);
                }
            }
            // CauseLoss信号自动备注
            List<CausalLoss> cls = mCausalLossRepository.range(begin, end);
            DigitalSignalHelper.divided(cls, dss);
            for (CausalLoss cl : cls) {
                // 自动备注规则，匹配中后追加备注信息 @+备注信息，如果
                if (cl.getComment()!=null
                        && cl.getComment().getConcern().startsWith("@")
                        && !cl.getComment().getConcern().endsWith(".")) {
                    cl.getComment().setConcern(cl.getComment().getConcern() + ".");
                    log.info("命中错误信号，更新CauseLoss备注 _id={}, comment={}", cl.getId(), cl.getComment());
                    mCausalLossRepository.save(cl);
                }
            }
        }
    }

}
