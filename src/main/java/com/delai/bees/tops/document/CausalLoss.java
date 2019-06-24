package com.delai.bees.tops.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * Created by ryan wu on 2018/9/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="CausalLoss")
public class CausalLoss {


    /**
     * _id : 00003a78-b19a-44eb-86d4-99a296167d74
     * On : 2018-09-18T04:12:33.187+08:00
     * Off : 2018-09-18T04:13:49.224+08:00
     * Weighting : 1.0
     * Comment : {"_id":"00000000-0000-0000-0000-000000000000","StaffId":"00000000-0000-0000-0000-000000000000","Concern":"洗瓶机进口卡瓶\r\n","Action":""}
     * MachineStopCategoryId : 00000000-0000-0000-0000-000000000000
     * MachineId : 475e6c45-318c-4ddc-a6e4-877a4b961a2a
     * LossConditionId : cb1bf0d1-fe90-460b-a24b-8e31db22e5e0
     * CriticalMachineIds : ["941c65dc-bb3e-734e-84ca-45fe64ddfec8"]
     * FirstFault : 00000000-0000-0000-0000-000000000000
     * NotAvailableId : 00000000-0000-0000-0000-000000000000
     */

    @Id
    private String id;
    @Field("On")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date on;
    @Field("Off")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date off;
    @Field("Weighting")
    private double weighting;
    @Field("Comment")
    private Comment comment;
    @Field("MachineStopCategoryId")
    private String machineStopCategoryId;
    @Field("MachineId")
    private String machineId;
    @Field("LossConditionId")
    private String lossConditionId;
    @Field("FirstFault")
    private String firstFault;
    @Field("NotAvailableId")
    private String notAvailableId;
    @Field("CriticalMachineIds")
    private List<String> criticalMachineIds;

    @Transient
    private List<DigitalSignal> afss = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getWeighting() {
        return weighting;
    }

    public void setWeighting(double weighting) {
        this.weighting = weighting;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getMachineStopCategoryId() {
        return machineStopCategoryId;
    }

    public void setMachineStopCategoryId(String machineStopCategoryId) {
        this.machineStopCategoryId = machineStopCategoryId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getLossConditionId() {
        return lossConditionId;
    }

    public void setLossConditionId(String lossConditionId) {
        this.lossConditionId = lossConditionId;
    }

    public String getFirstFault() {
        return firstFault;
    }

    public void setFirstFault(String firstFault) {
        this.firstFault = firstFault;
    }

    public String getNotAvailableId() {
        return notAvailableId;
    }

    public void setNotAvailableId(String notAvailableId) {
        this.notAvailableId = notAvailableId;
    }

    public List<String> getCriticalMachineIds() {
        return criticalMachineIds;
    }

    public void setCriticalMachineIds(List<String> criticalMachineIds) {
        this.criticalMachineIds = criticalMachineIds;
    }

    public List<DigitalSignal> getAfss() {
        return afss;
    }

    public void setAfss(List<DigitalSignal> afss) {
        this.afss = afss;
    }

    @Override
    public String toString() {
        return "CausalLoss{" +
                "id='" + id + '\'' +
                ", on='" + on + '\'' +
                ", off='" + off + '\'' +
                ", weighting=" + weighting +
                ", comment=" + comment +
                ", machineStopCategoryId='" + machineStopCategoryId + '\'' +
                ", machineId='" + machineId + '\'' +
                ", lossConditionId='" + lossConditionId + '\'' +
                ", firstFault='" + firstFault + '\'' +
                ", notAvailableId='" + notAvailableId + '\'' +
                ", criticalMachineIds=" + criticalMachineIds +
                ", afss=" + afss +
                '}';
    }
}
