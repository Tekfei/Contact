package dao;

import java.util.*;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.CardEntity;
import daoentity.CircleEntity;
import daoentity.UserEntity;
import org.apache.commons.collections.CollectionUtils;
import entity.User;
import org.apache.commons.lang3.StringUtils;
import util.RMPUtil;

public class UserDao {

	public void activateAccount(String mail) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("User.mail", mail);
		List<UserEntity> userEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User), queryMap, UserEntity.class);
		if(CollectionUtils.isEmpty(userEntityList)){
			return;
		}
		UserEntity userEntity = userEntityList.get(0);
		userEntity.setIs_activated(1);
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.User, userEntity.getId()), userEntity);
	}

	/**
	 * 登录时作验证
	 */
	public User login(String mail) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("User.mail", mail);
		List<UserEntity> userEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User), queryMap, UserEntity.class);
		if(CollectionUtils.isEmpty(userEntityList)){
			return null;
		}
		queryMap = new HashMap<>();
		queryMap.put("Card.user_id", userEntityList.get(0).getId());
		List<CardEntity> cardEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Card), queryMap, CardEntity.class);

		return User.transformToUser(userEntityList, cardEntityList).get(0);
	}

	/**
	 * 注册时插入信息到用户表,返回用户id
	 */
	public void insertUser(User user) {
		UserEntity userEntity = transformToUserEntity(user);
		userEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.User), userEntity), UserEntity.class);

		CardEntity cardEntity = user.getCard();
		if(cardEntity == null || userEntity == null) return;
		cardEntity.setUser_id(userEntity.getId());
		cardEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Card), cardEntity), CardEntity.class);

//		addUserToCircle(cardEntity.getCompany_name(), userEntity);
	}

	private boolean addUserToCircle(String company_name, UserEntity userEntity){
		//加入圈子
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Circle.name", company_name);
		List<CircleEntity> circleEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Circle), queryMap, CircleEntity.class);
		if(CollectionUtils.isEmpty(circleEntityList)){
			return false;
		}
		//更新user表中的circleId
		userEntity.setCircle_id(circleEntityList.get(0).getId());
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.User, userEntity.getId()), userEntity);
		return true;
	}

	private UserEntity transformToUserEntity(User user){
		UserEntity userEntity = new UserEntity();
		userEntity.setId(user.getUserId());
		userEntity.setIs_activated(user.isActivated()? 1:0);
		userEntity.setMail(user.getMail());
		userEntity.setName(user.getName());
		userEntity.setPassword(user.getPassword());
		userEntity.setUser_type(user.getType());
		userEntity.setDatetime(new Date());
		userEntity.setCircle_id(user.getCircleId());
		return userEntity;
	}
	
	/**
	 * 查询用户信息
	 */
	public User queryByUserId(String userId) {
		UserEntity userEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User, userId), UserEntity.class);

		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Card.user_id", userId);
		List<CardEntity> cardEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Card), queryMap, CardEntity.class);

		List<UserEntity> userEntityList =  new ArrayList<>();
		userEntityList.add(userEntity);
		User user = User.transformToUser(userEntityList, cardEntityList).get(0);
		return user;
	}
	
	
	public String queryPassword(String mail) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("User.mail", mail);
		List<UserEntity> userEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User), queryMap, UserEntity.class);
		if(CollectionUtils.isEmpty(userEntityList)){
			return null;
		}
		return userEntityList.get(0).getPassword();
	}
	
	
	/**
	 * 修改用户信息
	 */
	public void updateUser(User user) {
		UserEntity updateUserEntity = transformToUserEntity(user);
		UserEntity userEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User, user.getUserId()), UserEntity.class);
		BeanUtil.copyProperties(updateUserEntity, userEntity,
				true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.User, user.getUserId()), userEntity);

		CardEntity updateCardEntity = user.getCard();
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("Card.user_id", user.getUserId());
		List<CardEntity> cardEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Card), queryMap, CardEntity.class);
		CardEntity cardEntity = CollectionUtils.isEmpty(cardEntityList)? null : cardEntityList.get(0);
		if(cardEntity == null){
			cardEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Card), updateCardEntity), CardEntity.class);
		}
		if(!StringUtils.isEmpty(updateCardEntity.getCompany_name()) &&
				!updateCardEntity.getCompany_name().equals(cardEntity.getCompany_name())){
			//修改公司名，添加到新的圈子
			boolean result = addUserToCircle(updateCardEntity.getCompany_name(), userEntity);
			if(!result){
				updateCardEntity.setCompany_name(null);
			}
		}
		BeanUtil.copyProperties(updateCardEntity, cardEntity,
				true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
		RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Card, cardEntity.getId()), cardEntity);

	}
	
	public void deleteUser(String mail) {
		Map<String, String> queryMap = new HashMap<>();
		queryMap.put("User.mail", mail);
		List<UserEntity> userEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.User), queryMap, UserEntity.class);
		if(CollectionUtils.isEmpty(userEntityList)){
			return;
		}
		RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.User, userEntityList.get(0).getId()));
	}
}

