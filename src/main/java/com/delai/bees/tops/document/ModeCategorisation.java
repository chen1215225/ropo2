package com.delai.bees.tops.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/9/20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "ModeCategorisation")
public class ModeCategorisation {

    /**
     * _id : c5c2a6d9-a383-4061-9019-b83f992ea1c4
     * ModeType : 3
     * ParentId : 00000000-0000-0000-0000-000000000000
     * IsRooElement : true
     * IsElementMode : false
     * ElementId : 00000000-0000-0000-0000-000000000000
     */
    @Id
    private String id;
    @Field("ModeType")
    private int modeType;
    @Field("ParentId")
    private String parentId;
    @Field("IsRooElement")
    private boolean isRooElement;
    @Field("IsElementMode")
    private boolean isElementMode;
    @Field("ElementId")
    private String elementId;

    private Mode mode;

    List<ModeCategorisation> children = new ArrayList<>();

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isRooElement() {
        return isRooElement;
    }

    public void setRooElement(boolean rooElement) {
        isRooElement = rooElement;
    }

    public boolean isElementMode() {
        return isElementMode;
    }

    public void setElementMode(boolean elementMode) {
        isElementMode = elementMode;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<ModeCategorisation> getChildren() {
        return children;
    }

    public void setChildren(List<ModeCategorisation> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "ModeCategorisation{" +
                "id='" + id + '\'' +
                ", modeType=" + modeType +
                ", parentId='" + parentId + '\'' +
                ", isRooElement=" + isRooElement +
                ", isElementMode=" + isElementMode +
                ", elementId='" + elementId + '\'' +
                ", children=" + children +
                '}';
    }
}
