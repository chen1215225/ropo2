package com.delai.bees.tops.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/18.
 */
@Document(collection="Machines")
public class Machine implements java.io.Serializable {
    @Id
    private String id;
    private int MachineType;
    private int WatchdogFrequency;
    private String RatedSpeedSignalId;
    private String Name;
    private String Group;
    private int SpeedThresholdPercentage;
    private boolean ContinuousRunning;
    private List<AssociatedSignals> AssociatedSignals = new ArrayList<>();
    private CoreSignals CoreSignals;

    private List<SignalConfig> css;
    private List<SignalConfig> ass;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMachineType() {
        return MachineType;
    }

    public void setMachineType(int machineType) {
        MachineType = machineType;
    }

    public int getWatchdogFrequency() {
        return WatchdogFrequency;
    }

    public void setWatchdogFrequency(int watchdogFrequency) {
        WatchdogFrequency = watchdogFrequency;
    }

    public List<Machine.AssociatedSignals> getAssociatedSignals() {
        return AssociatedSignals;
    }

    public void setAssociatedSignals(List<Machine.AssociatedSignals> associatedSignals) {
        AssociatedSignals = associatedSignals;
    }

    public String getRatedSpeedSignalId() {
        return RatedSpeedSignalId;
    }

    public void setRatedSpeedSignalId(String ratedSpeedSignalId) {
        RatedSpeedSignalId = ratedSpeedSignalId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public int getSpeedThresholdPercentage() {
        return SpeedThresholdPercentage;
    }

    public void setSpeedThresholdPercentage(int speedThresholdPercentage) {
        SpeedThresholdPercentage = speedThresholdPercentage;
    }

    public boolean isContinuousRunning() {
        return ContinuousRunning;
    }

    public void setContinuousRunning(boolean continuousRunning) {
        ContinuousRunning = continuousRunning;
    }

    public Machine.CoreSignals getCoreSignals() {
        return CoreSignals;
    }

    public void setCoreSignals(Machine.CoreSignals coreSignals) {
        CoreSignals = coreSignals;
    }


    public List<SignalConfig> getCss() {
        return css;
    }

    public void setCss(List<SignalConfig> css) {
        this.css = css;
    }

    public List<SignalConfig> getAss() {
        return ass;
    }

    public void setAss(List<SignalConfig> ass) {
        this.ass = ass;
    }

    public class AssociatedSignals {
        @Id
        private String id;
        private String Name;
        private String Type;
        private String SignalId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
        public String getName() {
            return Name;
        }

        public void setType(String Type) {
            this.Type = Type;
        }
        public String getType() {
            return Type;
        }

        public void setSignalId(String SignalId) {
            this.SignalId = SignalId;
        }
        public String getSignalId() {
            return SignalId;
        }

    }

    public class CoreSignals {
        @Field(value = "BUILD_BACK")
        private UUID BACK;
        @Field(value = "NOT_AVAILABLE")
        private UUID UNAVAILABLE;
        @Field(value = "SPEED")
        private UUID SPEED;
        @Field(value = "UNITS_PRODUCED")
        private UUID PRODUCED;
        @Field(value = "LACK")
        private UUID LACK;
        @Field(value = "SECONDARY")
        private UUID SECONDARY;

        public UUID getUNAVAILABLE() {
            return UNAVAILABLE;
        }

        public void setUNAVAILABLE(UUID UNAVAILABLE) {
            this.UNAVAILABLE = UNAVAILABLE;
        }

        public UUID getBACK() {
            return BACK;
        }

        public void setBACK(UUID BACK) {
            this.BACK = BACK;
        }

        public UUID getSPEED() {
            return SPEED;
        }

        public void setSPEED(UUID SPEED) {
            this.SPEED = SPEED;
        }

        public UUID getPRODUCED() {
            return PRODUCED;
        }

        public void setPRODUCED(UUID PRODUCED) {
            this.PRODUCED = PRODUCED;
        }

        public UUID getLACK() {
            return LACK;
        }

        public void setLACK(UUID LACK) {
            this.LACK = LACK;
        }

        public UUID getSECONDARY() {
            return SECONDARY;
        }

        public void setSECONDARY(UUID SECONDARY) {
            this.SECONDARY = SECONDARY;
        }
    }

    @Override
    public String toString() {
        return "MachineProduct{" +
                "id='" + id + '\'' +
                ", MachineType=" + MachineType +
                ", WatchdogFrequency=" + WatchdogFrequency +
                ", AssociatedSignals=" + AssociatedSignals +
                ", RatedSpeedSignalId='" + RatedSpeedSignalId + '\'' +
                ", Name='" + Name + '\'' +
                ", Group='" + Group + '\'' +
                ", SpeedThresholdPercentage=" + SpeedThresholdPercentage +
                ", ContinuousRunning=" + ContinuousRunning +
                ", CoreSignals=" + CoreSignals +
                '}';
    }
}
