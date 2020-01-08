package dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import daoentity.EncounterEntity;
import entity.Encounter;
import util.RMPUtil;

import java.util.*;

public class EncounterDao {

    /**
     * 创建相遇历史
     */
    public String insertEncounter(Encounter encounter){
        EncounterEntity encounterEntity = transformToEncounterEntity(encounter);
        encounterEntity.setDatetime(new Date());
        encounterEntity = JSON.parseObject(RMPUtil.insert(RMPUtil.tableUrl(RMPUtil.Encounter), encounterEntity), EncounterEntity.class);
        return encounterEntity.getId();
    }

    private EncounterEntity transformToEncounterEntity(Encounter encounter){
        EncounterEntity encounterEntity = new EncounterEntity();
        encounterEntity.setId(encounter.getId());
        encounterEntity.setUser_id(encounter.getUserId());
        encounterEntity.setContact_id(encounter.getContactId());
        encounterEntity.setEncounter_time(encounter.getEncounterTime());
        encounterEntity.setAddress(encounter.getAddress());
        encounterEntity.setNote(encounter.getNote());
        encounterEntity.setActivity_id(encounter.getActivityId());
        encounterEntity.setDatetime(encounter.getDatetime());
        return encounterEntity;
    }
    /**
     * 更新相遇历史信息
     */
    public void updateEncounter(Encounter encounter){
        EncounterEntity updateEncounterEntity = transformToEncounterEntity(encounter);
        EncounterEntity encounterEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Encounter, encounter.getId()), EncounterEntity.class);
        BeanUtil.copyProperties(updateEncounterEntity, encounterEntity,
                true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        RMPUtil.modify(RMPUtil.tableUrl(RMPUtil.Encounter, encounter.getId()), encounterEntity);
    }

    /**
     * 根据id查询相遇历史
     */
    public Encounter queryEncounterById(String encounterId){
        EncounterEntity encounterEntity = RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Encounter, encounterId), EncounterEntity.class);
        if(encounterEntity == null){
            return null;
        }
        List<EncounterEntity> encounterEntityList = new ArrayList<>();
        encounterEntityList.add(encounterEntity);
        return Encounter.transformToEncounterEntity(encounterEntityList).get(0);
    }

    /**
     * 查询跟某个好友的相遇历史列表
     */
    public List<Encounter> queryEncounterByContact(String userId, String contactId){
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("Encounter.user_id", userId);
        queryMap.put("Encounter.contact_id", contactId);
        List<EncounterEntity> encounterEntityList =RMPUtil.get(RMPUtil.tableUrl(RMPUtil.Encounter), queryMap, EncounterEntity.class);
        return Encounter.transformToEncounterEntity(encounterEntityList);
    }

    public void deleteEncounter(String encounterId){
        RMPUtil.delete(RMPUtil.tableUrl(RMPUtil.Encounter, encounterId));
    }

}
