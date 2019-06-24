package com.delai.bees.tops.entity.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/20.
 */
@Data
@Builder
@ToString
public class Production {

    private String oee;
    private String eff;
    private String product;
    private String rejected;
    private String loss;
    private String time;
//    private String performance;
//    private String quality;

}
