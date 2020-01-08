package dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.ActivityEntity;
import daoentity.ThemeEntity;
import daoentity.TopicEntity;
import daoentity.UserEntity;
import entity.Activity;
import entity.Theme;
import entity.Topic;
import entity.User;
import util.RMPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThemeDao {

    private ActivityDao activityDao;

    public ThemeDao(){
        activityDao = new ActivityDao();
    }

    public void insertTheme(Theme theme){
        ThemeEntity themeEntity = transformToThemeEntity(theme);
        RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Theme), themeEntity);
    }

    private ThemeEntity transformToThemeEntity(Theme theme){
        ThemeEntity themeEntity = new ThemeEntity();
        themeEntity.setId(theme.getId());
        themeEntity.setName(theme.getName());
        themeEntity.setDescription(theme.getDescription());
        return themeEntity;
    }

    public void updateTheme(Theme theme){
        ThemeEntity updateThemeEntity = transformToThemeEntity(theme);
        ThemeEntity themeEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Theme, theme.getId()), ThemeEntity.class);
        BeanUtil.copyProperties(updateThemeEntity, themeEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Theme, theme.getId()), themeEntity);
    }

    public List<Theme> queryAllTheme(){
        List<ThemeEntity> themeEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Theme), null, ThemeEntity.class);
        List<Theme> themeList = new ArrayList<>();
        for(ThemeEntity themeEntity : themeEntityList){
            themeList.add(Theme.transformToTheme(themeEntity, null, null));
        }
        return themeList;
    }

    /**
     * 根据id查询主题广场
     */
    public Theme queryThemeById(String themeId){
        ThemeEntity ThemeEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Theme, themeId), ThemeEntity.class);

        //获得主题的置顶活动列表
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Activity.popularize_theme_id", themeId);
        queryMap.put("Activity.popularize_state", "1");
        List<ActivityEntity> activityEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity), queryMap, ActivityEntity.class);
        List<Activity> activityList = activityDao.getActivityList(activityEntityList, false);

        queryMap = new HashMap<>();
        queryMap.put("Topic.belong_id", themeId);
        queryMap.put("Topic.belong_type", "1");
        List<TopicEntity> topicEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic), queryMap, TopicEntity.class);
        List<Topic> topicList = Topic.transformToTopic(topicEntityList);

        return Theme.transformToTheme(ThemeEntity, activityList, topicList);
    }

}
