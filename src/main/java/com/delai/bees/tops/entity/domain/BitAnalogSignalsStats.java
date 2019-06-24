package com.delai.bees.tops.entity.domain;

import com.delai.bees.tops.document.Machine;
import com.delai.bees.tops.document.SignalConfig;
import lombok.Data;

import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/3/18.
 */
@Data
public class BitAnalogSignalsStats {

    private String key;
    private String remark;
    private String address;
    private Date begin;
    private Date end;
    private Integer count;
    private Integer duration;

    private SignalConfig signal;

}
