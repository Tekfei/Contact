package daoentity;

import java.util.Date;

/**
 * 帖子
 */
public class TopicEntity {

    private String id;
    private String title;//标题
    private String content;//正文
    private Integer belong_type = 0;//所属类型：0为圈子帖，1为主题帖
    private String belong_id;//所属圈子/主题id
    private String user_id;//作者id
    private Date datetime;//发帖时间
    private String user_name;//用户姓名，冗余减少查询
    private Integer comment_num;//评论数，冗余减少查询
    private Integer like_num;//点赞数，冗余减少查询
    private int topic_type;//帖子类型，0为帖子，1为活动

    public TopicEntity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBelong_type() {
        return belong_type;
    }

    public void setBelong_type(Integer belong_type) {
        this.belong_type = belong_type;
    }

    public String getBelong_id() {
        return belong_id;
    }

    public void setBelong_id(String belong_id) {
        this.belong_id = belong_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getComment_num() {
        return comment_num;
    }

    public void setComment_num(Integer comment_num) {
        this.comment_num = comment_num;
    }

    public Integer getLike_num() {
        return like_num;
    }

    public void setLike_num(Integer like_num) {
        this.like_num = like_num;
    }

    public int getTopic_type() {
        return topic_type;
    }

    public void setTopic_type(int topic_type) {
        this.topic_type = topic_type;
    }
}
