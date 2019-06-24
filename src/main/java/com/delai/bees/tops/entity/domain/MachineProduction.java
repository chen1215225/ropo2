package com.delai.bees.tops.entity.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 机器产量 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/20.
 */
@Data
@Builder
public class MachineProduction {
    /**
     * 注入机
     */
    private Long implanterProducts;
    /**
     * 吹瓶机
     */
    private Long bottleBlowingProducts;
    /**
     * 码垛机
     */
    private Long stackerProducts;
}
