package daoentity;

import java.util.Date;

/**
 * 点赞
 */
public class LikeEntity {

    private String id;
    private String user_id;//点赞者user_id
    private String topic_id;//点赞的帖子id
    private Date datetime;//点赞时间

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

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
