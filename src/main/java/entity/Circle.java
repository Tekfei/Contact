package entity;

import daoentity.CircleEntity;
import java.util.List;

public class Circle {

    private String id;
    private String name;
    private String description;//圈子描述
    private Integer state;//圈子状态，0为未审核，1审核通过，2为审核拒绝，3为没有管理员的圈子状态，4为用户申请成为圈子管理员申请中
    private String userId;//圈子管理员user_id
    private List<User> memberList;//圈子成员id列表
    private List<Activity> activityList;//所含活动id列表
    private List<Topic> topicList;//所含帖子id列表, 后面三个列表只有用id查询单个circle才会有该字段


    public static Circle transformToCircle(CircleEntity circleEntity, List<User> userList, List<Activity> activityList, List<Topic> topicList){
        Circle circle = new Circle();
        circle.setId(circleEntity.getId());
        circle.setName(circleEntity.getName());
        circle.setDescription(circleEntity.getDescription());
        circle.setState(circleEntity.getState());
        circle.setUserId(circleEntity.getUser_id());

        circle.setMemberList(userList);
        circle.setActivityList(activityList);
        circle.setTopicList(topicList);

        return circle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<User> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<User> memberList) {
        this.memberList = memberList;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }
}
