package entity;

import daoentity.ActivityEntity;
import daoentity.TopicEntity;
import daoentity.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Activity extends UserId implements Serializable {
    private String id;
    private String title;
    private String content;
    private Date datetime;
    private String userName;//用户名称，冗余到Topic表中
    private Integer status;//状态：0为进行中，1为已结束
    private String circleId;//所属圈子id
    private Integer popularizeState;//推广状态，0为未推广，1为已推广，2为申请推广中
    private String popularizeThemeId;//推广的主题广场
    private Date startTime;//活动开始时间
    private String address;//活动地点
    private List<String> participantList;//参与者列表，只有用id查询单个Activity才会有该字段


    public static Activity transformToActivity(ActivityEntity activityEntity, TopicEntity topicEntity, List<String> participantIdList){
        Activity activity = new Activity();
        activity.setId(activityEntity.getId());
        activity.setStatus(activityEntity.getStatus());
        activity.setCircleId(activityEntity.getCircle_id());
        activity.setPopularizeState(activityEntity.getPopularize_state());
        activity.setStartTime(activityEntity.getStart_time());
        activity.setAddress(activityEntity.getAddress());
        activity.setPopularizeThemeId(activityEntity.getPopularize_theme_id());

        activity.setTitle(topicEntity.getTitle());
        activity.setContent(topicEntity.getContent());
        activity.setDatetime(topicEntity.getDatetime());
        activity.setUserName(topicEntity.getUser_name());
        activity.setUserId(topicEntity.getUser_id());

        activity.setParticipantList(participantIdList);
        return activity;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime() {
        this.datetime = new Date();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public Integer getPopularizeState() {
        return popularizeState;
    }

    public void setPopularizeState(Integer popularizeState) {
        this.popularizeState = popularizeState;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<String> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<String> participantList) {
        this.participantList = participantList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPopularizeThemeId() {
        return popularizeThemeId;
    }

    public void setPopularizeThemeId(String popularizeThemeId) {
        this.popularizeThemeId = popularizeThemeId;
    }
}
