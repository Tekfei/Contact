package entity;

import daoentity.CommentEntity;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment extends UserId implements Serializable {

	// 评论id
	private String id;
	// 被评论的topic的id
	private String topicId;
	// 评论的内容
	private String content;
	private Date datetime;
	private String userName;//用户名称，冗余到Comment表中
	
	public Comment() {
	}

	public static List<Comment> transformToCommentList(List<CommentEntity> commentEntityList){
		List<Comment> commentList = new ArrayList<>();
		if(CollectionUtils.isEmpty(commentEntityList)){
			return commentList;
		}
		for(CommentEntity commentEntity : commentEntityList) {
			Comment comment = new Comment();
			comment.setId(commentEntity.getId());
			comment.setDatetime(commentEntity.getDatetime());
			comment.setUserId(commentEntity.getUser_id());
			comment.setTopicId(commentEntity.getTopic_id());
			comment.setContent(commentEntity.getContent());
			comment.setUserName(commentEntity.getUser_name());
			commentList.add(comment);
		}
		return commentList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setDatetime(){
		this.datetime = new Date();
	}
	
	public Date getDatetime(){
		return this.datetime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
