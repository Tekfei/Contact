package daoentity;

import java.util.Date;

/**
 * 好友申请
 */
public class ContactapplyEntity {

    private String id;
    private String user_id;//申请方
    private String group_tag;//申请方的人脉分组标签，逗号隔开
    private String contact_id;//被申请方的user_id
    private Date datetime;//申请时间

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

    public String getGroup_tag() {
        return group_tag;
    }

    public void setGroup_tag(String group_tag) {
        this.group_tag = group_tag;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
