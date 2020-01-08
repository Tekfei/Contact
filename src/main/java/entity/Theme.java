package entity;

import daoentity.ThemeEntity;

import java.util.List;

/**
 * 主题广场
 */
public class Theme {

    private String id;
    private String name;//主题名称
    private String description;//主题描述
    private List<Activity> activityList;//所含活动id列表
    private List<Topic> topicList;//所含帖子id列表, 后面两个个列表只有用id查询单个circle才会有该字段

    public static Theme transformToTheme(ThemeEntity themeEntity, List<Activity> activityList, List<Topic> topicList){
        Theme theme = new Theme();
        theme.setId(themeEntity.getId());
        theme.setName(themeEntity.getName());
        theme.setDescription(themeEntity.getDescription());
        theme.setActivityList(activityList);
        theme.setTopicList(topicList);
        return theme;
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
