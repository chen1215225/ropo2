package com.delai.bees.tops.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection="CausalLossLogic")
public class CausalLossLogic {


    /**
     * _id : d57a56e3-5257-48e3-9fa9-794a1ed9f0c0
     * Name : 码垛机
     * LossConditionId : 2d6f395b-2dd5-48ad-b641-5db3cca85e98
     * Signals : [["a3da79bb-f8c4-4a19-996e-ff921f722ea8",["21ad83c3-2a70-409c-bc02-54c82a396d36"]]]
     */

    private String _id;
    private String Name;
    private String LossConditionId;
    private List<List<UUID>> Signals;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLossConditionId() {
        return LossConditionId;
    }

    public void setLossConditionId(String LossConditionId) {
        this.LossConditionId = LossConditionId;
    }

    public List<List<UUID>> getSignals() {
        return Signals;
    }

    public void setSignals(List<List<UUID>> signals) {
        Signals = signals;
    }
}
