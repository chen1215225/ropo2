package com.delai.bees.tops.service;

import com.delai.bees.tops.document.Machine;
import com.delai.bees.tops.repository.MachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/21.
 */
@Slf4j
@Service
public class MachineService {
    @Resource
    private MachineRepository mMachineRepository;

    /**
     * 根据主机那获取机器信息
     * @param id
     * @return
     */
    public Machine find(String id){
        return mMachineRepository.find(id);
    }
}
