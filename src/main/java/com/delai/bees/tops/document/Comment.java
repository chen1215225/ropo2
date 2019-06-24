package com.delai.bees.tops.document;

/**
 * 请描述类 <br>
 *
 * @author ryan wu
 * <p>
 * Created by ryan wu on 2018/10/14.
 */
public class Comment {
    private String id;
    private String StaffId;
    private String Concern;
    private String Action;

    public Comment() {
    }

    public Comment(String concern) {
        this.id = "00000000-0000-0000-0000-000000000000";
        this.StaffId = "00000000-0000-0000-0000-000000000000";
        Concern = concern;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getConcern() {
        return Concern;
    }

    public void setConcern(String concern) {
        Concern = concern;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", StaffId='" + StaffId + '\'' +
                ", Concern='" + Concern + '\'' +
                ", Action='" + Action + '\'' +
                '}';
    }
}
