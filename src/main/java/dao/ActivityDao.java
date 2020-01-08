package dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.ActivityEntity;
import daoentity.ParticipateEntity;
import daoentity.TopicEntity;
import daoentity.UserEntity;
import entity.Activity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.RMPUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityDao {

    /**
     * 获得某个圈子的活动列表
     */
    public List<Activity> queryActivityByCircleId(String circleId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Activity.circle_id", circleId);
        List<ActivityEntity> activityEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity), queryMap, ActivityEntity.class);
        return getActivityList(activityEntityList, false);
    }

    /**
     * 查询某个用户参与的活动列表
     */
    public List<Activity> queryActivityByUserId(String userId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Participate.user_id", userId);
        List<ParticipateEntity> participantEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);

        List<ActivityEntity> activityEntityList = new ArrayList<>();
        for(ParticipateEntity participantEntity: participantEntityList){
            ActivityEntity activityEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity, participantEntity.getActivity_id()), ActivityEntity.class);
            activityEntityList.add(activityEntity);
        }

        return getActivityList(activityEntityList, false);
    }

    public List<Activity> getActivityList(List<ActivityEntity> activityEntityList, boolean containParticipant){
        List<Activity> activityList = new ArrayList<>();

        for(ActivityEntity activityEntity : activityEntityList){
            TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, activityEntity.getTopic_id()), TopicEntity.class);
            if(topicEntity != null){
                List<String> participantIdList = null;
                if(containParticipant) {
                    Map<String, String> queryMap = new HashMap<>();
                    queryMap.put("Participate.activity_id", activityEntity.getId());
                    List<ParticipateEntity> participantEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);
                    participantIdList = participantEntityList.stream().map(p -> p.getUser_id()).collect(Collectors.toList());
                }

                Activity activity = Activity.transformToActivity(activityEntity, topicEntity, participantIdList);

                activityList.add(activity);
            }
        }
        return activityList;
    }

    /**
     * 创建活动,返回活动id
     */
    public String insertActivity(Activity activity){
        if(StringUtils.isEmpty(activity.getUserName())){
            UserEntity userEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User, activity.getUserId()), UserEntity.class);
            activity.setUserName(userEntity.getName());
        }
        TopicEntity topicEntity = transformToTopicEntity(activity);
        topicEntity.setComment_num(0);
        topicEntity.setLike_num(0);
        topicEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Topic), topicEntity), TopicEntity.class);

        ActivityEntity activityEntity = transformToActivityEntity(activity, topicEntity.getId());
        activityEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Activity), activityEntity), ActivityEntity.class);
        return activityEntity.getId();
    }

    private TopicEntity transformToTopicEntity(Activity activity){
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTitle(activity.getTitle());
        topicEntity.setContent(activity.getContent());
        topicEntity.setBelong_type(0);
        topicEntity.setBelong_id(activity.getCircleId());
        topicEntity.setDatetime(new Date());
        topicEntity.setUser_id(activity.getUserId());
        topicEntity.setUser_name(activity.getUserName());
        topicEntity.setTopic_type(1);
        return topicEntity;
    }

    private ActivityEntity transformToActivityEntity(Activity activity, String topicId){
        ActivityEntity activityEntity = new ActivityEntity();
        if(activity.getId() != null){
            activityEntity.setId(activity.getId());
        }
        activityEntity.setTopic_id(topicId);
        activityEntity.setCircle_id(activity.getCircleId());
        if(activity.getStatus() != null) {
            activityEntity.setStatus(activity.getStatus());
        }else {
            activityEntity.setStatus(0);
        }
        if(activity.getPopularizeState() != null) {
            activityEntity.setPopularize_state(activity.getPopularizeState());
        }else {
            activityEntity.setPopularize_state(0);
        }
        if(activity.getStartTime()== null){
            activityEntity.setStart_time(new Date());
        }else {
            activityEntity.setStart_time(activity.getStartTime());
        }
        activityEntity.setAddress(activity.getAddress());
        activityEntity.setPopularize_theme_id(activity.getPopularizeThemeId());
        return activityEntity;
    }

    /**
     * 通过ActivityId修改信息
     */
    public void updateActivity(Activity activity) {
        ActivityEntity updateActivityEntity = transformToActivityEntity(activity, null);
        ActivityEntity activityEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity, activity.getId()), ActivityEntity.class);

        BeanUtil.copyProperties(updateActivityEntity, activityEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Activity, activity.getId()), activityEntity);

        TopicEntity updateTopicEntity = transformToTopicEntity(activity);
        TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, activityEntity.getTopic_id()), TopicEntity.class);

        BeanUtil.copyProperties(updateTopicEntity, topicEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Topic, topicEntity.getId()), topicEntity);
    }

    /**
     * 通过ActivityId修改活动状态
     */
    public void updateStatus(Integer status, String activityId) {
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setStatus(status);
        updateActivity(activity);
    }

    /**
     * 通过ActivityId修改置顶状态
     */
    public void updatePopularizeState(Integer popularizeState, String activityId, String popularizeThemeId) {
        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setPopularizeState(popularizeState);
        activity.setPopularizeThemeId(popularizeThemeId);
        updateActivity(activity);
    }

    /**
     * 通过ActivityId删除活动
     */
    public void deleteByActivityId(String activityId) {
        ActivityEntity activityEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity, activityId), ActivityEntity.class);
        if(activityEntity != null) {
            String topicId = activityEntity.getTopic_id();
            RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Topic, topicId));
            RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Activity, activityId));
        }

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Participate.activity_id", activityId);
        List<ParticipateEntity> participateEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);
        if(CollectionUtils.isEmpty(participateEntityList)){
            return;
        }
        for(ParticipateEntity participateEntity : participateEntityList){
            RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Participate, participateEntity.getId()));
        }
    }


    public static void main(String[] args){
        Activity activity = new Activity();
        activity.setPopularizeState(0);
        activity.setStatus(0);
        activity.setCircleId("1");
        activity.setDatetime(new Date());
        activity.setStartTime(new Date());
        activity.setContent("content");
        activity.setTitle("title");
        activity.setUserId("1575976105468");
        activity.setUserName("zhs");

        ActivityDao activityDao = new ActivityDao();
        activityDao.insertActivity(activity);
    }

    public Activity queryByActivityId(String activityId) {
        ActivityEntity activityEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity, activityId), ActivityEntity.class);

        List<ActivityEntity> activityEntityList = new ArrayList<>();
        activityEntityList.add(activityEntity);
        List<Activity> activityList = getActivityList(activityEntityList, true);
        if(!CollectionUtils.isEmpty(activityList)){
            return activityList.get(0);
        }
        return null;
    }
}
