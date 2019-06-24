package com.delai.bees.tops.entity.domain;

import lombok.Data;
import lombok.ToString;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/24.
 */
@Data
@ToString
public class LineProdcutTimeCrossingSum {
    private Long productsSum;
    private Long stackerProductSum;
    private Long bottleBlowingProductSum;
    private Long implanterProductSum;
    private Long timeSum;
}
