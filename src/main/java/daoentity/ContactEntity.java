package daoentity;

import java.util.Date;

/**
 *  人脉
 */
public class ContactEntity {

    private String id;
    private String user_id;
    private String group_tag;//user_id方的人脉分组标签，逗号隔开
    private String contact_id;//被申请方的user_id
    private Date datetime;//添加时间
    private String contact_group_tag;//contact方的人脉分组标签，逗号隔开
    private Integer intimacy;//亲密度

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

    public String getContact_group_tag() {
        return contact_group_tag;
    }

    public void setContact_group_tag(String contact_group_tag) {
        this.contact_group_tag = contact_group_tag;
    }

    public Integer getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(Integer intimacy) {
        this.intimacy = intimacy;
    }
}
