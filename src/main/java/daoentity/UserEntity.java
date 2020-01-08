package daoentity;

import java.util.Date;

public class UserEntity {

    private String id;
    private String mail;//邮箱
    private String name;//用户名
    private Integer user_type;//0为普通用户，1为企业用户，2为管理员
    private String password;//密码
    private Integer is_activated;//是否激活
    private Date datetime;//注册时间
    private String circle_id;//圈子id
    private String interested_contacts;//推荐人脉列表

    public UserEntity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInterested_contacts() {
        return interested_contacts;
    }

    public void setInterested_contacts(String interested_contacts) {
        this.interested_contacts = interested_contacts;
    }

    public Integer getIs_activated() {
        return is_activated;
    }

    public void setIs_activated(Integer is_activated) {
        this.is_activated = is_activated;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
