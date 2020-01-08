package dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.ActivityEntity;
import daoentity.CircleEntity;
import daoentity.TopicEntity;
import daoentity.UserEntity;
import entity.Activity;
import entity.Circle;
import entity.Topic;
import entity.User;
import util.RMPUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleDao {

    private ActivityDao activityDao;

    public CircleDao(){
        activityDao = new ActivityDao();
    }

    /**
     * 创建圈子，返回圈子id
     * @param circle
     * @return
     */
    public String insertCircle(Circle circle){
        CircleEntity circleEntity = transformToCircleEntity(circle);
        circleEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Circle), circleEntity), CircleEntity.class);
        return circleEntity.getId();
    }

    private CircleEntity transformToCircleEntity(Circle circle){
        CircleEntity circleEntity = new CircleEntity();
        circleEntity.setId(circle.getId());
        circleEntity.setName(circle.getName());
        circleEntity.setDescription(circle.getDescription());
        if(circle.getState() == null){
            circleEntity.setState(0);
        }else {
            circleEntity.setState(circle.getState());
        }
        circleEntity.setUser_id(circle.getUserId());
        return circleEntity;
    }
    /**
     * 更新圈子状态
     */
    public void updateCircle(Circle circle){
        CircleEntity updateCircleEntity = transformToCircleEntity(circle);
        CircleEntity circleEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Circle, circle.getId()), CircleEntity.class);

        BeanUtil.copyProperties(updateCircleEntity, circleEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Circle, circle.getId()), circleEntity);
    }

    /**
     * 根据id查询圈子
     */
    public Circle queryCircleById(String circleId){
        CircleEntity circleEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Circle, circleId), CircleEntity.class);

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("User.circle_id", circleId);
        List<UserEntity> userEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User), queryMap, UserEntity.class);
        List<User> userList = User.transformToUser(userEntityList, null);

        List<Activity> activityList = activityDao.queryActivityByCircleId(circleId);

        queryMap = new HashMap<>();
        queryMap.put("Topic.belong_id", circleId);
        queryMap.put("Topic.belong_type", "0");
        List<TopicEntity> topicEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic), queryMap, TopicEntity.class);
        List<Topic> topicList = Topic.transformToTopic(topicEntityList);

        return Circle.transformToCircle(circleEntity, userList, activityList, topicList);
    }

}
