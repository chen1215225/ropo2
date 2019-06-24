package com.delai.bees.tops.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="Modes")
public class Mode implements java.io.Serializable {

    /**
     * _id : 05d1fa70-9739-4d17-a5a5-0a4316256ecb
     * ModeType : 3
     * Name : Brewing_酿造部/Brewing Tank change 换罐
     * Target : null
     * EndsJob : false
     * Active : true
     */
    @Id
    private String id;
    @Field("ModeType")
    private int modeType;
    @Field("Name")
    private String name;
    @Field("EndsJob")
    private boolean endsJob;
    @Field("Active")
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEndsJob() {
        return endsJob;
    }

    public void setEndsJob(boolean endsJob) {
        this.endsJob = endsJob;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Mode{" +
                "id='" + id + '\'' +
                ", modeType=" + modeType +
                ", name='" + name + '\'' +
                ", endsJob=" + endsJob +
                ", active=" + active +
                '}';
    }
}
