package entity;

import daoentity.EncounterEntity;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 相遇历史
 * 可以自行创建
 * 参与活动时判断参与者中有好友的话，自动创建两条相遇历史，分别属于双方
 */
public class Encounter {

    private String id;
    private String userId;//记录方
    private String contactId;//该相遇的对象人脉user_id
    private Date encounterTime;//相遇时间
    private String address;//相遇地址
    private String note;//用户对相遇的记录
    private String activityId;//该相遇对应的活动id
    private Date datetime;

    public static List<Encounter> transformToEncounterEntity(List<EncounterEntity> encounterEntityList){
        List<Encounter> encounterList = new ArrayList<>();
        if(CollectionUtils.isEmpty(encounterEntityList)){
            return encounterList;
        }
        for(EncounterEntity encounterEntity : encounterEntityList) {
            Encounter encounter = new Encounter();
            encounter.setId(encounterEntity.getId());
            encounter.setUserId(encounterEntity.getUser_id());
            encounter.setContactId(encounterEntity.getContact_id());
            encounter.setEncounterTime(encounterEntity.getEncounter_time());
            encounter.setAddress(encounterEntity.getAddress());
            encounter.setNote(encounterEntity.getNote());
            encounter.setActivityId(encounterEntity.getActivity_id());
            encounter.setDatetime(encounterEntity.getDatetime());
            encounterList.add(encounter);
        }
        return encounterList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Date getEncounterTime() {
        return encounterTime;
    }

    public void setEncounterTime(Date encounterTime) {
        this.encounterTime = encounterTime;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
