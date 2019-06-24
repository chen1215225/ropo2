package com.delai.bees.tops.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="LossCondition")
public class LossCondition {

    /**
     * _id : 101c934b-b674-4371-b804-868133a68c51
     * Name : 码垛机
     * MachineId : 00000000-0000-0000-0000-000000000000
     * Active : true
     */
    @Id
    private String id;
    private String Name;
    private String MachineId;
    private Boolean Active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMachineId() {
        return MachineId;
    }

    public void setMachineId(String machineId) {
        MachineId = machineId;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    @Override
    public String toString() {
        return "LossCondition{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", MachineId='" + MachineId + '\'' +
                ", Active=" + Active +
                '}';
    }
}
