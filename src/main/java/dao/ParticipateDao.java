package dao;

import daoentity.ActivityEntity;
import daoentity.ContactEntity;
import daoentity.EncounterEntity;
import daoentity.ParticipateEntity;
import org.apache.commons.collections.CollectionUtils;
import util.RMPUtil;

import java.util.*;
import java.util.stream.Collectors;

public class ParticipateDao {

    /**
     * 用户参与活动，添加参与记录
     * 修改亲密度
     * 如果活动参与者中有好友，添加相遇记录
     */
    public void insertParticipate(String activityId, String userId){
        ParticipateEntity participateEntity = new ParticipateEntity();
        participateEntity.setUser_id(userId);
        participateEntity.setActivity_id(activityId);
        participateEntity.setDatetime(new Date());
        RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Participate), participateEntity);

        //如果活动参与者中有好友，添加相遇记录
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Participate.activity_id", activityId);
        List<ParticipateEntity> participateEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);
        List<String> participateUserIdList = participateEntityList.stream().map(p -> p.getUser_id()).collect(Collectors.toList());
        participateUserIdList.remove(userId);

        //获取好友列表
        queryMap = new HashMap<>();
        queryMap.put("Contact.user_id", userId);
        List<ContactEntity> contactList1 = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class);
        List<String> contactIdList = contactList1.stream().map(p -> p.getContact_id()).collect(Collectors.toList());

        queryMap = new HashMap<>();
        queryMap.put("Contact.contact_id", userId);
        List<ContactEntity> contactList2 = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Contact), queryMap, ContactEntity.class);
        contactIdList.addAll(contactList2.stream().map(p -> p.getUser_id()).collect(Collectors.toList()));

        //取交集
        contactIdList.retainAll(participateUserIdList);

        //添加相遇历史
        if (CollectionUtils.isEmpty(contactIdList)) {
            return;
        }
        ActivityEntity activityEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Activity, activityId), ActivityEntity.class);
        for(String contactId : contactIdList) {
            //修改亲密度
            List<ContactEntity> contactEntityList = contactList1.stream().filter(contactEntity -> contactEntity.getContact_id().equals(contactId)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(contactEntityList)){
                contactEntityList = contactList2.stream().filter(contactEntity -> contactEntity.getUser_id().equals(contactId)).collect(Collectors.toList());
            }
            ContactEntity contactEntity = contactEntityList.get(0);
            contactEntity.setIntimacy(contactEntity.getIntimacy() + ContactDao.calculateIntimacy(1, 0,0));
            RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Contact, contactEntity.getId()), contactEntity);

            EncounterEntity encounterEntity = new EncounterEntity();
            encounterEntity.setUser_id(userId);
            encounterEntity.setContact_id(contactId);
            encounterEntity.setDatetime(new Date());
            encounterEntity.setActivity_id(activityId);
            encounterEntity.setEncounter_time(activityEntity.getStart_time());
            encounterEntity.setAddress(activityEntity.getAddress());
            RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Encounter), encounterEntity);
            encounterEntity.setUser_id(contactId);
            encounterEntity.setContact_id(userId);
            RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Encounter), encounterEntity);
        }
    }

    /**
     * 用户取消参与活动，删除参与记录
     */
    public void deleteParticipate(String activityId, String userId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Participate.user_id", userId);
        queryMap.put("Participate.activity_id", activityId);
        List<ParticipateEntity> participateEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Participate), queryMap, ParticipateEntity.class);
        if(CollectionUtils.isEmpty(participateEntityList)){
            return;
        }

        RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Participate, participateEntityList.get(0).getId()));

        //删除相遇历史
        queryMap = new HashMap<>();
        queryMap.put("Encounter.activity_id", activityId);
        queryMap.put("Encounter.user_id", userId);
        List<EncounterEntity> encounterEntityList = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Encounter), queryMap, EncounterEntity.class);
        queryMap = new HashMap<>();
        queryMap.put("Encounter.activity_id", activityId);
        queryMap.put("Encounter.contact_id", userId);
        encounterEntityList.addAll(RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Encounter), queryMap, EncounterEntity.class));
        List<String> encounterIdList = encounterEntityList.stream().map(e->e.getId()).collect(Collectors.toList());
        for(String encounterId : encounterIdList){
            RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Encounter, encounterId));
        }
    }

}
