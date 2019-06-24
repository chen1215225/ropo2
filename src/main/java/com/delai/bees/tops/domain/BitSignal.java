package com.delai.bees.tops.domain;

import com.delai.bees.tops.document.Comment;
import com.delai.bees.tops.document.SignalConfig;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2019/1/12.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="DigitalSignals")
@Deprecated
public class BitSignal {
    private Long id;
    private String key;
    private String parentName;
    private Integer val;
    private Date ts;
    private Long duration;



}
