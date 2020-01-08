package dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.*;
import entity.Contact;
import entity.Topic;
import org.apache.commons.collections.CollectionUtils;
import util.RMPUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactDao {

    /**
     * 创建好友申请
     */
    public String insertContactApply(Contact contact){
        ContactapplyEntity contactapplyEntity = transformToContactApplyEntity(contact);
        contactapplyEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Contactapply), contactapplyEntity), ContactapplyEntity.class);
        return contactapplyEntity.getId();
    }

    private ContactapplyEntity transformToContactApplyEntity(Contact contact){
        ContactapplyEntity contactapplyEntity = new ContactapplyEntity();
        contactapplyEntity.setId(contact.getId());
        contactapplyEntity.setUser_id(contact.getUserId());
        contactapplyEntity.setContact_id(contact.getContactId());
        contactapplyEntity.setGroup_tag(contact.getGroupTag());
        contactapplyEntity.setDatetime(new Date());
        return contactapplyEntity;
    }

    private ContactEntity transformToContactEntity(ContactapplyEntity contactapplyEntity){
        ContactEntity contactEntity = new ContactEntity();
        BeanUtil.copyProperties(contactapplyEntity, contactEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        return contactEntity;
    }

    private ContactEntity transformToContactEntity(Contact contact){
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contact.getId());
        contactEntity.setUser_id(contact.getUserId());
        contactEntity.setContact_id(contact.getContactId());
        contactEntity.setGroup_tag(contact.getGroupTag());
        contactEntity.setDatetime(contact.getDatetime());
        contactEntity.setContact_group_tag(contact.getContactGroupTag());
        contactEntity.setIntimacy(contact.getIntimacy());
        return contactEntity;
    }

    /**
     * 接收好友申请,删除ContactApply数据，添加Contact数据
     */
    public String acceptContactApply(String contactApplyId){
        ContactapplyEntity contactapplyEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contactapply, contactApplyId), ContactapplyEntity.class);
        if(contactapplyEntity == null){
            return null;
        }
        RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Contactapply, contactapplyEntity.getId()));

        ContactEntity contactEntity = transformToContactEntity(contactapplyEntity);

        contactEntity.setIntimacy(new Integer(0));

        contactEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Contact), contactEntity), ContactEntity.class);

        String contactEntityId = contactEntity.getId();

        Thread calIntimacythread = new Thread(){
            public void run(){
                System.out.println("calculate intimacy Thread Running");
                //TODO 计算亲密度
                int intimacy = 0;
                String userId = contactapplyEntity.getUser_id();
                String contactId = contactapplyEntity.getContact_id();

                Map<String, String> queryMap = new HashMap<>();
                queryMap.put("Topic.user_id", userId);
                List<TopicEntity> userTopicEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic), queryMap, TopicEntity.class);
                queryMap.put("Topic.user_id", contactId);
                List<TopicEntity> contactTopicEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Topic), queryMap, TopicEntity.class);

                intimacy = intimacy + calculateIntimacy(getCommonActivityNum(userId, contactId),
                        getCommentNum(userId, userTopicEntityList, contactId, contactTopicEntityList),
                        getLikeNum(userId, userTopicEntityList, contactId, contactTopicEntityList));
                ContactEntity contactEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact, contactEntityId), ContactEntity.class);
                contactEntity.setIntimacy(intimacy);
                RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Contact, contactEntityId), contactEntity);
            }
        };
        calIntimacythread.start();

        return contactEntityId;
    }

    /**
     * 共同活动数
     */
    private int getCommonActivityNum(String userId, String contactId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Participate.user_id", userId);
        List<ParticipateEntity> participateEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);
        List<String> participateIdList = participateEntityList.stream().map(p->p.getActivity_id()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(participateEntityList)){
            queryMap = new HashMap<>();
            queryMap.put("Participate.user_id", contactId);
            participateEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);
            participateIdList.retainAll(participateEntityList.stream().map(p->p.getActivity_id()).collect(Collectors.toList()));
        }
        return participateEntityList.size();
    }

    private int getCommentNum(String userId, List<TopicEntity> userTopicEntityList, String contactId, List<TopicEntity> contactTopicEntityList){
        int commentNum = 0;
        for(TopicEntity topicEntity : userTopicEntityList){
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("Comment.user_id", contactId);
            queryMap.put("Comment.topic_id", topicEntity.getId());
            List<CommentEntity> commentEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment), queryMap, CommentEntity.class);
            commentNum += commentEntityList.size();
        }
        for(TopicEntity topicEntity : contactTopicEntityList){
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("Comment.user_id", userId);
            queryMap.put("Comment.topic_id", topicEntity.getId());
            List<CommentEntity> commentEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Comment), queryMap, CommentEntity.class);
            commentNum += commentEntityList.size();
        }
        return commentNum;
    }

    private int getLikeNum(String userId, List<TopicEntity> userTopicEntityList, String contactId, List<TopicEntity> contactTopicEntityList){
        int likeNum = 0;
        for(TopicEntity topicEntity : userTopicEntityList){
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("Like.user_id", contactId);
            queryMap.put("Like.topic_id", topicEntity.getId());
            List<LikeEntity> likeEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Like), queryMap, LikeEntity.class);
            likeNum += likeEntityList.size();
        }
        for(TopicEntity topicEntity : contactTopicEntityList){
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("Like.user_id", userId);
            queryMap.put("Like.topic_id", topicEntity.getId());
            List<LikeEntity> likeEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Like), queryMap, LikeEntity.class);
            likeNum += likeEntityList.size();
        }
        return likeNum;
    }

    public static int calculateIntimacy(int activityNum, int commentNum, int likeNum){
        int intimacy = (int)(200/(1+Math.exp(-(activityNum/20 + commentNum/30 + likeNum/40))) - 100);
        return intimacy;
    }

    public static void main(String[] args){
        System.out.println(calculateIntimacy(1, 3, 1));
    }

    /**
     * 更新好友信息，如修改分组
     */
    public void updateContact(Contact contact){
        ContactEntity updatecontactEntity = transformToContactEntity(contact);
        ContactEntity contactEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact, contact.getId()), ContactEntity.class);
        BeanUtil.copyProperties(updatecontactEntity, contactEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Contact, contact.getId()), contactEntity);
    }

    /**
     * 查询用户人脉列表
     */
    public List<Contact> queryContactByUserId(String userId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Contact.user_id", userId);
        List<ContactEntity> contactEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class);
        queryMap = new HashMap<>();
        queryMap.put("Contact.contact_id", userId);
        contactEntityList.addAll(RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class));
        return Contact.transformToContact(contactEntityList);
    }

    /**
     * 查询用户接收到的好友申请列表
     */
    public List<Contact> queryContactApplyByContactId(String contactId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Contactapply.contact_id", contactId);
        List<ContactapplyEntity> contactapplyEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contactapply), queryMap, ContactapplyEntity.class);
        return Contact.transformToContactApply(contactapplyEntityList);
    }

    /**
     * 查询用户的发起申请列表
     */
    public List<Contact> queryContactApplyByUserId(String userId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Contactapply.user_id", userId);
        List<ContactapplyEntity> contactapplyEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contactapply), queryMap, ContactapplyEntity.class);
        return Contact.transformToContactApply(contactapplyEntityList);
    }

    /**
     * 删除好友
     */
    public void deleteContact(String userId, String contactId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Contact.user_id", userId);
        queryMap.put("Contact.contact_id", contactId);
        List<ContactEntity> contactEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class);
        if (CollectionUtils.isEmpty(contactEntityList)) {
            return;
        }
        RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Contact, contactEntityList.get(0).getId()));
    }

    /**
     * 删除发出的好友申请
     */
    public void deleteContactApplyByUserId(String userId, String contactId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Contactapply.user_id", userId);
        queryMap.put("Contactapply.contact_id", contactId);
        List<ContactapplyEntity> contactapplyEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contactapply), queryMap, ContactapplyEntity.class);
        if (CollectionUtils.isEmpty(contactapplyEntityList)) {
            return;
        }
        RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Contactapply, contactapplyEntityList.get(0).getId()));
    }
}
