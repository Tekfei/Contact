package daoentity;

import java.util.Date;

public class ActivityEntity {

    private String id;
    private String circle_id;
    private String topic_id;
    private Integer status;//状态：0为进行中，1为已结束
    private Integer popularize_state;//推广状态，0为未推广，1为已推广，2为申请推广中
    private String popularize_theme_id;//推广的主题广场
    private Date start_time;//活动开始时间
    private String address;//活动地点

    public ActivityEntity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPopularize_state() {
        return popularize_state;
    }

    public void setPopularize_state(Integer popularize_state) {
        this.popularize_state = popularize_state;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPopularize_theme_id() {
        return popularize_theme_id;
    }

    public void setPopularize_theme_id(String popularize_theme_id) {
        this.popularize_theme_id = popularize_theme_id;
    }
}
