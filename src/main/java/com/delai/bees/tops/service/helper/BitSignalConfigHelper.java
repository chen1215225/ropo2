package com.delai.bees.tops.service.helper;

import com.delai.bees.tops.document.*;
import com.delai.bees.tops.entity.BitSignalConfig;
import com.delai.bees.tops.service.constant.IConstant;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/10/12.
 */
public class BitSignalConfigHelper {


    /**
     * 根据信号，获取机器
     * @param signalId
     * @param machines
     * @return
     */
    public static Machine getMachineAccordingSignalId(String signalId, List<Machine> machines) {
        // 拼接机器
        for (Machine machine : machines) {
            Machine.CoreSignals core = machine.getCoreSignals();
            if (core.getLACK() != null && core.getLACK().toString().equals(signalId)) {
                return machine;
            } else if (core.getSPEED() != null && core.getSPEED().toString().equals(signalId)) {
                return machine;
            } else if (core.getUNAVAILABLE() != null && core.getUNAVAILABLE().toString().equals(signalId)) {
                return machine;
            } else if (core.getBACK() != null && core.getBACK().toString().equals(signalId)) {
                return machine;
            } else if (core.getPRODUCED() != null && core.getPRODUCED().toString().equals(signalId)) {
                return machine;
            } else if (core.getSECONDARY() != null && core.getSECONDARY().toString().equals(signalId)) {
                return machine;
            } else {
                for (Machine.AssociatedSignals as : machine.getAssociatedSignals()) {
                    if (signalId.equals(as.getSignalId())) {
                        return machine;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 结合
     * @param dss
     * @param scs
     * @param machines
     * @return
     */
    @Deprecated
    public static List<BitSignalConfig> combine(List<BitSignalConfig> dss , List<SignalConfig> scs, List<Machine> machines) {
        combine(scs, machines);
        for (SignalConfig sc : scs ) {
            // 拼接机器
            // 拼接信号
            for (BitSignalConfig ds : dss) {
                if (sc.getAddress().equals(ds.getAddress())) {
                    ds.setSignal(sc);
                }
            }
        }
        return dss;
    }


    /**
     * @param scs
     * @param machines
     */
    public static void combine(List<SignalConfig> scs, List<Machine> machines) {
        for (SignalConfig sc : scs ) {
            // 拼接机器
            for (Machine machine : machines) {
                Machine.CoreSignals core = machine.getCoreSignals();
                if (core.getLACK() != null && core.getLACK().toString().equals(sc.getId())) {
                    sc.setCtype(IConstant.CTYPE_LACK);
                    sc.setDtype(IConstant.DTYPE_CORESIGNAL);
                    // 如果缺料，必然备注
                    sc.setDname("Inlet conveyor bottles gate close");
                    sc.setMachine(machine);
                } else if (core.getSPEED() != null && core.getSPEED().toString().equals(sc.getId())) {
                    sc.setCtype(IConstant.CTYPE_SPEED);
                    sc.setDtype(IConstant.DTYPE_CORESIGNAL);
                    sc.setMachine(machine);
                } else if (core.getUNAVAILABLE() != null && core.getUNAVAILABLE().toString().equals(sc.getId())) {
                    sc.setCtype(IConstant.CTYPE_UNAVAILABLE);
                    sc.setDtype(IConstant.DTYPE_CORESIGNAL);
                    sc.setMachine(machine);
                } else if (core.getBACK() != null && core.getBACK().toString().equals(sc.getId())) {
                    sc.setCtype(IConstant.CTYPE_BACK);
                    sc.setDtype(IConstant.DTYPE_CORESIGNAL);
                    // 如果后堵，必然备注
                    sc.setDname("Outlet conveyor full of bottles");
                    sc.setMachine(machine);
                } else if (core.getPRODUCED() != null && core.getPRODUCED().toString().equals(sc.getId())) {
                    sc.setCtype(IConstant.CTYPE_PRODUCED);
                    sc.setDtype(IConstant.DTYPE_CORESIGNAL);
                    sc.setMachine(machine);
                } else if (core.getSECONDARY() != null && core.getSECONDARY().toString().equals(sc.getId())) {
                    sc.setCtype(IConstant.CTYPE_SECONDARY);
                    sc.setDtype(IConstant.DTYPE_CORESIGNAL);
                    sc.setMachine(machine);
                } else {
                    for (Machine.AssociatedSignals as : machine.getAssociatedSignals()) {
                        if (sc.getId().equals(as.getSignalId())) {
                            sc.setDtype(IConstant.DTYPE_ASSOCIATEDSIGNAL);
                            sc.setDname(as.getName());
                            sc.setMachine(machine);
                        }
                    }
                }
            }
        }
    }


    /**
     * 区分信号 / 自动备注
     * @param dss
     * @return
     */
    @Deprecated
    public static List<DigitalSignal> divided(List<DigitalSignal> dss, String implanterId) {
        // Core信号
        List<DigitalSignal> cores = dss.stream().filter(e->e.getSignal().getDtype()!=null && e.getSignal().getDtype().equals(IConstant.DTYPE_CORESIGNAL)).collect(Collectors.toList());

        // Associated信号
        List<DigitalSignal> faults = dss.stream().filter(e->e.getSignal().getDtype()!=null && e.getSignal().getDtype().equals(IConstant.DTYPE_ASSOCIATEDSIGNAL)).collect(Collectors.toList());

        // 匹配 CoreSignals 与 AssociatedSignals
        for (DigitalSignal core : cores) {
            // 自动绑定错误信号至 DigitalSignal
            for (DigitalSignal fault : faults) {
                boolean bool = true;
                // 筛选
                bool = bool && core.getSignal()!=null
                        && core.getSignal().getMachine()!=null
                        && fault.getOff()!=null
                        && fault.getSignal()!=null
                        && fault.getSignal().getMachine()!=null
                        && fault.getSignal().getMachine()!=null;
                // 匹配
                bool = bool && core.getSignal().getMachine().getId().equals(fault.getSignal().getMachine().getId());
                // 时间范围交叉匹配
                bool = bool && ( core.getOff() == null || core.getOff().getTime()  > fault.getOn().getTime());
                bool = bool && ( core.getOn().getTime() <= fault.getOff().getTime());
                if ( bool ) {
                    core.getAfss().add(fault);
                }
            }
            // Fault信号，按时间排序
            Collections.sort(core.getAfss(), new Comparator<DigitalSignal>() {
                @Override
                public int compare(DigitalSignal o1, DigitalSignal o2) {
                    return o1.getOn().compareTo(o2.getOn());
                }
            });
            if ( core.getSignal().getCtype().equals(IConstant.CTYPE_LACK) || core.getSignal().getCtype().equals(IConstant.CTYPE_BACK) ) {
                // FIXME 临时特殊处理
                if (core.getSignal().getMachine().getId().equals(implanterId)) {
                    String concern = String.format("@%s", core.getSignal().getDname());
                    core.setComment(new Comment(concern));
                }
            } else {
                // Fault信号，捕获第一个进行备注
                if (core.getAfss().size() > 0) {
                    if (core.getComment() == null) {
                        String concern = String.format("@%s", core.getAfss().get(0).getSignal().getDname());
                        core.setComment(new Comment(concern));
                    }
                }
            }
        }
        return cores;
    }

    /**
     * 自动备注
     * @param cls
     * @param dss
     * @return
     */
    public static List<CausalLoss> divided(List<CausalLoss> cls, List<DigitalSignal> dss) {
        // Associated信号
        List<DigitalSignal> faults = dss.stream().filter(e->e.getSignal().getDtype()!=null && e.getSignal().getDtype().equals(IConstant.DTYPE_ASSOCIATEDSIGNAL)).collect(Collectors.toList());
        for(CausalLoss cl : cls) {
            // 自动绑定错误信号至 CausalLoss
            for (DigitalSignal fault : faults) {
                // 绑定条件：信号所属机器相同 && 状态信号开始时间 < 错误信号开始时间 && （状态、错误信号一个为空 || 错误信号 < 状态信号 ）
                boolean bool = true;
                bool = bool && fault.getSignal()!=null && fault.getSignal().getMachine()!=null;
                bool = bool && cl.getMachineId().equals(fault.getSignal().getMachine().getId()) && cl.getFirstFault().equals(fault.getSignal().getId());
                bool = bool && ( cl.getOff() == null || cl.getOff().getTime()  > fault.getOn().getTime());
                bool = bool && ( cl.getOn().getTime() <= fault.getOff().getTime());
                if ( bool ) {
                    cl.getAfss().add(fault);
                }
            }
            // Fault信号，按时间排序
            Collections.sort(cl.getAfss(), new Comparator<DigitalSignal>() {
                @Override
                public int compare(DigitalSignal o1, DigitalSignal o2) {
                    return o1.getOn().compareTo(o2.getOn());
                }
            });
            // Fault信号，捕获第一个进行备注
            if( cl.getAfss().size() > 0 ) {
                if (cl.getComment()==null) {
                    String concern = String.format("@%s", cl.getAfss().get(0).getSignal().getDname());
                    cl.setComment(new Comment(concern));
                }
            }
        }
        return cls;
    }
}
