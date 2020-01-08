package daoentity;

import java.util.Date;

/**
 * 用户参与活动记录
 */
public class ParticipateEntity {

    private String id;
    private String user_id;
    private String activity_id;
    private Date datetime;//参与时间

    public ParticipateEntity(){}

    public ParticipateEntity(String user_id, String activity_id, Date datetime) {
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.datetime = datetime;
    }

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
