package daoentity;

import java.util.Date;

/**
 * 相遇历史
 */
public class EncounterEntity {

    private String id;
    private String user_id;
    private String contact_id;//该相遇所属的人脉user_id
    private Date encounter_time;//相遇时间
    private String address;//相遇地址
    private String note;//用户对相遇的记录
    private String activity_id;//该相遇对应的活动id
    private Date datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public Date getEncounter_time() {
        return encounter_time;
    }

    public void setEncounter_time(Date encounter_time) {
        this.encounter_time = encounter_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
