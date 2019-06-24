package com.delai.bees.tops.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection="SignalConfig")
public class SignalConfig {

    /**
     * _id : 0015f848-db63-456b-9200-a899a14c14ba
     * Name : 装箱机 - Communications Error
     * ScaleFactor : 1.0
     * SignalType : COMMS_STATE
     * Address : SYSTEM
     * DataType : BOOL
     * CommsId : 9f6a59c5-3d0d-42b7-ac6c-babfdd1c5ddb
     * Invalid : false
     * DerivedType : DERIVED_TYPE
     * BaseSignalId : 00000000-0000-0000-0000-000000000000
     */
    @Id
    private String id;
    @Version
    private Integer version;
    @Field("Name")
    private String name;
    @Field("ScaleFactor")
    private Double scaleFactor;
    @Field("SignalType")
    private String signalType;
    @Field("Address")
    private String address;
    @Field("DataType")
    private String dataType;
    @Field("CommsId")
    private String commsId;
    @Field("Invalid")
    private Boolean invalid;
    @Field("DerivedType")
    private String derivedType;
    @Field("BaseSignalId")
    private String baseSignalId;
    // 信号类型 Core / Associated两种
    @Transient
    private String dtype;
    @Transient
    private String dname;
    // 信号类型 Core Children Type : BACK、 UNAVAILABLE、 SPEED、 PRODUCED、 LACK、 SECONDAR
    @Transient
    private String ctype;
    @Transient
    private Machine machine;

}
