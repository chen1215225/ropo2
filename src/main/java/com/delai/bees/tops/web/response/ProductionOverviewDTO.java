package com.delai.bees.tops.web.response;

import com.delai.bees.tops.entity.domain.MachineProduction;
import com.delai.bees.tops.entity.domain.Production;
import com.ipukr.elephant.utils.DataUtils;
import com.ipukr.elephant.utils.StringUtils;
import lombok.*;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductionOverviewDTO implements java.io.Serializable {
    private String oee;
    private String eff;
    private String product;
    private String rejected;
    private String loss;
    private String time;
    /**
     * 表现
     */
    private String performance = "0";
    /**
     * 质量
     */
    private String quality = "0";
    /**
     * 注入机
     */
    private Long implanterProducts = 0L;
    /**
     * 吹瓶机
     */
    private Long bottleBlowingProducts = 0L;
    /**
     * 码垛机
     */
    private Long stackerProducts;

    private Long lossLevel1;

    private Long lossLevel2;

    public void setTime(Long time) {
        Long hour = time / 60L;
        Long minute = time % 60L;
        this.time = "".concat(hour>0?hour+"小时":"").concat(minute+"分钟");
    }

    public Long getLossLevel1() {
        return getBottleBlowingProducts()  - getImplanterProducts();
    }

    public Long getLossLevel2() {
        return getImplanterProducts() - getStackerProducts();
    }

    public Long getImplanterProducts() {
        // 临时NULL异常兼容
        return implanterProducts != null ? implanterProducts : 0;
    }

    public Long getBottleBlowingProducts() {
        // 临时NULL异常兼容
        return bottleBlowingProducts !=null ? bottleBlowingProducts : 0;
    }

    public Long getStackerProducts() {
        // 临时NULL异常兼容
        return stackerProducts !=null ? stackerProducts : 0;
    }

    /**
     * 获取DTO对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static ProductionOverviewDTO parser(Production obj) throws IOException {
        ProductionOverviewDTO dto = DataUtils.copyPropertiesIgnoreNull(obj, ProductionOverviewDTO.class);
        return dto;
    }
}
