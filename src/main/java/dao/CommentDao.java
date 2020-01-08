package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daoentity.CommentEntity;
import daoentity.ContactEntity;
import daoentity.TopicEntity;
import entity.Comment;
import org.apache.commons.collections.CollectionUtils;
import util.RMPUtil;

public class CommentDao{

	/**
	 * 向评论表添加信息
	 */
	public void insertComment(Comment comment) {
		CommentEntity commentEntity = transformToCommentEntity(comment);
		RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Comment), commentEntity);

		//修改评论数
		TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, commentEntity.getTopic_id()), TopicEntity.class);
		if(topicEntity == null){
			return;
		}
		topicEntity.setComment_num(topicEntity.getComment_num()+1);
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Topic, topicEntity.getId()), topicEntity);

		//修改亲密度
		String userId = comment.getUserId();
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
		contactEntity.setIntimacy(contactEntity.getIntimacy() + ContactDao.calculateIntimacy(0, 1,0));
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Contact, contactEntity.getId()), contactEntity);
	}

	private static CommentEntity transformToCommentEntity(Comment comment){
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setId(comment.getId());
		commentEntity.setDatetime(comment.getDatetime());
		commentEntity.setContent(comment.getContent());
		commentEntity.setUser_id(comment.getUserId());
		commentEntity.setTopic_id(comment.getTopicId());
		commentEntity.setUser_name(comment.getUserName());
		return commentEntity;
	}

	/**
	 * 获得评论表信息
	 */
	public List<Comment> queryComment(String topicId) {
		List<Comment> commentList = new ArrayList<>();
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Comment.topic_id", topicId);
		List<CommentEntity> commentEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment), queryMap, CommentEntity.class);
		return Comment.transformToCommentList(commentEntityList);
	}

	/**
	 * 获取评论数量
	 */
	public int countComment(String topicId) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Comment.topic_id", topicId);
		List<CommentEntity> commentEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment), queryMap, CommentEntity.class);
		if(CollectionUtils.isEmpty(commentEntityList)){
			return 0;
		}
		return commentEntityList.size();
	}

	/**
	 * 通过topicId删除发帖表对于的评论的信息
	 */
	public void deleteCommentByTopicId(String topicId) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Comment.topic_id", topicId);
		List<CommentEntity> commentEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment), queryMap, CommentEntity.class);
		if(!CollectionUtils.isEmpty(commentEntityList)) {
			for (CommentEntity commentEntity : commentEntityList) {
				RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Comment, commentEntity.getId()));
			}
		}
	}

	/**
	 * 通过id删除评论信息
	 */
	public void deleteCommentById(String id) {
		CommentEntity commentEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment, id), CommentEntity.class);
		RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Comment, id));

		//修改评论数
		TopicEntity topicEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic, commentEntity.getTopic_id()), TopicEntity.class);
		if(topicEntity == null){
			return;
		}
		topicEntity.setComment_num(topicEntity.getComment_num()-1);
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Topic, topicEntity.getId()), topicEntity);
	}
}
