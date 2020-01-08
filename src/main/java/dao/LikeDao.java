package dao;

import daoentity.ContactEntity;
import daoentity.LikeEntity;
import daoentity.TopicEntity;
import org.apache.commons.collections.CollectionUtils;
import util.RMPUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LikeDao {

	public void insertRecord(String userId, String topicId) {
		LikeEntity likeEntity = new LikeEntity();
		likeEntity.setUser_id(userId);
		likeEntity.setTopic_id(topicId);
		likeEntity.setDatetime(new Date());
		RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Like), likeEntity);

		//修改点赞数
		TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, topicId), TopicEntity.class);
		if(topicEntity == null){
			return;
		}
		topicEntity.setLike_num(topicEntity.getLike_num()+1);
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Topic, topicEntity.getId()), topicEntity);

		//修改亲密度
		String contactId = topicEntity.getUser_id();

		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Contact.user_id", userId);
		queryMap.put("Contact.contact_id", contactId);
		List<ContactEntity> contactEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class);
		if(CollectionUtils.isEmpty(contactEntityList)) {
			queryMap = new HashMap<>();
			queryMap.put("Contact.user_id", contactId);
			queryMap.put("Contact.contact_id", userId);
			contactEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class);
		}
		if(CollectionUtils.isEmpty(contactEntityList)){
			return;
		}
		ContactEntity contactEntity = contactEntityList.get(0);
		contactEntity.setIntimacy(contactEntity.getIntimacy() + ContactDao.calculateIntimacy(0, 0,1));
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Contact, contactEntity.getId()), contactEntity);
	}
	
	public void deleteRecord(String userId, String topicId) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Like.user_id", userId);
		queryMap.put("Like.topic_id", topicId);
		List<LikeEntity> likeEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Like), queryMap, LikeEntity.class);
		if(CollectionUtils.isEmpty(likeEntityList)){
			return;
		}
		RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Like, likeEntityList.get(0).getId()));

		//修改点赞数
		TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, topicId), TopicEntity.class);
		if(topicEntity == null){
			return;
		}
		topicEntity.setLike_num(topicEntity.getLike_num()-1);
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Topic, topicEntity.getId()), topicEntity);
	}

	public List<String> queryLikedTopicIdByUserId(String userId){
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Like.user_id", userId);
		List<LikeEntity> likeEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Like), queryMap, LikeEntity.class);
		List<String> topicIdList = likeEntityList.stream().map(l -> l.getTopic_id()).collect(Collectors.toList());
		return topicIdList;
	}

}
