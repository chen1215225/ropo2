package com.delai.bees.tops.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/24.
 */
@Data
public class AnalogSignalGRP implements java.io.Serializable {
    @Id
    private String id;
    @Field(value = "Max")
    private Long max;
    @Field(value = "Min")
    private Long min;
}
