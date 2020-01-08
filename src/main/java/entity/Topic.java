package entity;

import daoentity.TopicEntity;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Topic extends UserId implements Serializable {

	// 发帖id
	private String id;
	// 帖子内容
	private String title;
	// 发帖内容
	private String content;
	// 发帖人姓名
	private String userName;//用户名称，冗余到Topic表中

	private Date datetime;

	private Integer belongType = 0;//所属类型：0为圈子帖，1为主题帖
	private String belongId;//所属圈子/主题id
	private Integer commentNum;//评论数，冗余减少查询
	private Integer likeNum;//点赞数，冗余减少查询

	public Topic() {
	}

	public static List<Topic> transformToTopic(List<TopicEntity> topicEntityList){
		List<Topic> topicList = new ArrayList<>();
		if(CollectionUtils.isEmpty(topicEntityList)){
			return topicList;
		}
		for(int i=0; i<topicEntityList.size();i++){
			TopicEntity topicEntity = topicEntityList.get(i);
			Topic topic = new Topic();
			topic.setId(topicEntity.getId());
			topic.setUserId(topicEntity.getUser_id());
			topic.setTitle(topicEntity.getTitle());
			topic.setContent(topicEntity.getContent());
			topic.setDatetime(topicEntity.getDatetime());
			topic.setBelongId(topicEntity.getBelong_id());
			topic.setBelongType(topicEntity.getBelong_type());
			topic.setUserName(topicEntity.getUser_name());
			topic.setCommentNum(topicEntity.getComment_num());
			topic.setLikeNum(topicEntity.getLike_num());
			topicList.add(topic);
		}
		return topicList;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setDatetime(){
		this.datetime = new Date();
	}
	
	public Date getDatetime(){
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Integer getBelongType() {
		return belongType;
	}

	public void setBelongType(Integer belongType) {
		this.belongType = belongType;
	}

	public String getBelongId() {
		return belongId;
	}

	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}
}
