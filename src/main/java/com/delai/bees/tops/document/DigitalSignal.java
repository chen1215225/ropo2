package com.delai.bees.tops.document;

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
 * Created by ryan wu on 2018/9/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection="DigitalSignals")
public class DigitalSignal {
    @Id
    private String id;
    @Field("On")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date on;
    @Field("Off")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date off;
    @Field("Weighting")
    private Float weighting;
    @Field("Comment")
    private Comment comment;
    @Field("SignalId")
    private String signalId;
    @Field("MachineStopCategoryId")
    private String machineStopCategoryId;
    @Field("Name")
    private String name;
    @Field("Db")
    private String db;
    @Field("Confidence")
    private Integer confidence;
    @Transient
    private SignalConfig signal;
    @Transient
    private List<DigitalSignal> afss = new ArrayList<>();

}
