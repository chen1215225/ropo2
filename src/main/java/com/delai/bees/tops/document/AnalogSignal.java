package com.delai.bees.tops.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="AnalogSignals")
public class AnalogSignal implements Serializable {
    @Id
    private String id;
    @Version
    private Integer version;
    @Field("Ts")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date ts;
    @Field("SignalId")
    @Indexed
    private String signalId;
    @Field("Name")
    private String name;
    @Field("Val")
    private Double val;
    @Deprecated
    private SignalConfig signal;
    @Field("Config")
    @DBRef
    private SignalConfig config;

}
