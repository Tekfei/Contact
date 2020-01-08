package dao;

import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.CommentEntity;
import daoentity.LikeEntity;
import daoentity.TopicEntity;
import org.apache.commons.collections.CollectionUtils;
import entity.Topic;
import util.RMPUtil;

public class TopicDao{

	/**
	 * 获得某个圈子或主题广场的发帖表信息
	 */
	public List<Topic> queryTopicByBelongId(String belongId, Integer belongType){
		Map<String, String> queryMap = new HashMap<>();
		if(belongType!=null) {
			queryMap.put("Topic.belong_type", String.valueOf(belongType));
			queryMap.put("Topic.topic_type", "0");
		}
		queryMap.put("Topic.belong_id", belongId);
		List<TopicEntity> topicEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic), queryMap, TopicEntity.class);
		return Topic.transformToTopic(topicEntityList);
	}

	/**
	 * 通过userId查询用户的发帖表信息
	 */
	public List<Topic> queryTopicByUserId(String userId){
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Topic.user_id", userId);
		queryMap.put("Topic.topic_type", "0");
		List<TopicEntity> topicEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic), queryMap, TopicEntity.class);
		return Topic.transformToTopic(topicEntityList);
	}

	/**
	 * 向发帖表添加信息
	 */
	public String insertTopic(Topic topic){
		TopicEntity topicEntity = transformToTopic(topic);
		topicEntity.setComment_num(topic.getCommentNum()==null? 0:topic.getCommentNum());
		topicEntity.setLike_num(topic.getLikeNum()==null?0:topic.getLikeNum());
		topicEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Topic), topicEntity), TopicEntity.class);
		return topicEntity.getId();
	}

	private TopicEntity transformToTopic(Topic topic){
		TopicEntity topicEntity = new TopicEntity();
		topicEntity.setId(topic.getId());
		topicEntity.setUser_name(topic.getUserName());
		topicEntity.setUser_id(topic.getUserId());
		topicEntity.setDatetime(new Date());
		topicEntity.setBelong_id(topic.getBelongId());
		topicEntity.setBelong_type(topic.getBelongType());
		topicEntity.setTitle(topic.getTitle());
		topicEntity.setContent(topic.getContent());
		topicEntity.setComment_num(topic.getCommentNum());
		topicEntity.setLike_num(topic.getLikeNum());
		topicEntity.setTopic_type(0);
		return topicEntity;
	}

	/**
	 * 通过topicId修改发帖表信息
	 */
	public void updateByTopicId(Topic topic) {
		TopicEntity updateTopicEntity = transformToTopic(topic);
		TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, topic.getId()), TopicEntity.class);
		BeanUtil.copyProperties(updateTopicEntity, topicEntity,
				true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Topic, topic.getId()), topicEntity);
	}

	/**
	 * 通过topicId删除发帖表信息
	 */
	public void deleteByTopicId(String topicId){
		RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Topic, topicId));

		//删除评论
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Comment.topic_id", topicId);
		List<CommentEntity> commentEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment), queryMap, CommentEntity.class);
		for(CommentEntity commentEntity:commentEntityList){
			RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Comment, commentEntity.getId()));
		}

		//删除点赞
		queryMap = new HashMap<>();
		queryMap.put("Like.topic_id", topicId);
		List<LikeEntity> likeEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Like), queryMap, LikeEntity.class);
		for(LikeEntity likeEntity:likeEntityList){
			RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Like, likeEntity.getId()));
		}
	}

	public Topic queryByTopicId(String topicId){
		TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, topicId), TopicEntity.class);
		List<TopicEntity> topicEntityList = new ArrayList<>();
		topicEntityList.add(topicEntity);
		List<Topic> topicList = Topic.transformToTopic(topicEntityList);
		if(CollectionUtils.isEmpty(topicList)){
			return null;
		}
		return topicList.get(0);
	}
}
