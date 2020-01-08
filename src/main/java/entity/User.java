package entity;

import daoentity.CardEntity;
import daoentity.UserEntity;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User extends UserId implements Serializable {

	private int type;//0为普通用户，1为企业用户，2为管理员

	private boolean isActivated;

	//用户姓名
	private String name;
	//用户密码
	private String password;

	//用户邮箱
	private String mail;
	
	private Date datetime;

	private String circleId;//所属圈子的id

	private String interestedContactList;//推荐人脉列表
	private CardEntity card;//用户名片，只有用id查询单个User才会有该字段
	
	public User(){

	}

	public static List<User> transformToUser(List<UserEntity> userEntityList, List<CardEntity> cardEntityList){
		List<User> userList = new ArrayList<>();
		if(CollectionUtils.isEmpty(userEntityList)){
			return userList;
		}
		for(int i=0; i<userEntityList.size();i++){
			UserEntity userEntity = userEntityList.get(i);
			User user= new User();
			user.setUserId(userEntity.getId());
			user.setType(userEntity.getUser_type());
			if(userEntity.getIs_activated() == 1) {
				user.setActivated(true);
			}
			user.setName(userEntity.getName());
			user.setPassword(userEntity.getPassword());
			user.setMail(userEntity.getMail());
			user.setDatetime(userEntity.getDatetime());
			user.setCircleId(userEntity.getCircle_id());
			user.setInterestedContactList(userEntity.getInterested_contacts());

			if(!CollectionUtils.isEmpty(cardEntityList)) {
				user.setCard(cardEntityList.get(i));
			}
			userList.add(user);
		}
		return userList;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean activated) {
		isActivated = activated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public void setDatetime() {
		this.datetime = new Date();
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public String getInterestedContactList() {
		return interestedContactList;
	}

	public void setInterestedContactList(String interestedContactList) {
		this.interestedContactList = interestedContactList;
	}

	public CardEntity getCard() {
		return card;
	}

	public void setCard(CardEntity card) {
		this.card = card;
	}
}
