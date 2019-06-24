package com.delai.bees.tops.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="MachineModes")
public class MachineMode {
    @Id
    private String id;
    @Version
    private Integer version;
    @Field("On")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date on;
    @Field("Off")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date off;
    @Field("Weighting")
    private Double weighting;
    @Field("Comment")
    private String comment;
    @Field("MachineId")
    private String machineId;
    @Field("ModeId")
    private String modeId;
    @Field("MachineProduct")
    @DBRef
    private Machine machine;
    @Field("Mode")
    @DBRef
    private Mode mode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getOn() {
        return on;
    }

    public void setOn(Date on) {
        this.on = on;
    }

    public Date getOff() {
        return off;
    }

    public void setOff(Date off) {
        this.off = off;
    }

    public Double getWeighting() {
        return weighting;
    }

    public void setWeighting(Double weighting) {
        this.weighting = weighting;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "MachineMode{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", on=" + on +
                ", off=" + off +
                ", weighting=" + weighting +
                ", comment='" + comment + '\'' +
                ", machineId='" + machineId + '\'' +
                ", modeId='" + modeId + '\'' +
                ", machine=" + machine +
                ", mode=" + mode +
                '}';
    }
}
