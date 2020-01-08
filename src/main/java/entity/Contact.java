package entity;

import daoentity.ContactEntity;
import daoentity.ContactapplyEntity;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 人脉表，或人脉申请表
 */
public class Contact {

    private String id;
    private String userId;//申请方
    private String groupTag;//userId方的人脉分组标签，逗号隔开
    private String contactId;//被申请方的userId
    private Date datetime;//添加时间，如果是人脉申请表，为申请时间
    private String contactGroupTag;//contact方的人脉分组标签，逗号隔开，如果是人脉申请表，该字段没有其他字段有
    /**
     * 亲密度计算规则：
     * intimacy = 100*参加同一活动 + 10*评论 + 1*点赞
     */
    private Integer intimacy;//亲密度

    public static List<Contact> transformToContact(List<ContactEntity> contactEntityList){
        List<Contact> contactList = new ArrayList<>();
        if (CollectionUtils.isEmpty(contactEntityList)) {
            return contactList;
        }
        for(ContactEntity contactEntity : contactEntityList) {
            Contact contact = new Contact();
            contact.setId(contactEntity.getId());
            contact.setUserId(contactEntity.getUser_id());
            contact.setGroupTag(contactEntity.getGroup_tag());
            contact.setContactId(contactEntity.getContact_id());
            contact.setContactGroupTag(contactEntity.getContact_group_tag());
            contact.setDatetime(contactEntity.getDatetime());
            contact.setIntimacy(contactEntity.getIntimacy());
            contactList.add(contact);
        }
        return contactList;
    }

    public static List<Contact> transformToContactApply(List<ContactapplyEntity> contactapplyEntityList){
        List<Contact> contactList = new ArrayList<>();
        if (CollectionUtils.isEmpty(contactapplyEntityList)) {
            return contactList;
        }
        for(ContactapplyEntity contactapplyEntity : contactapplyEntityList) {
            Contact contact = new Contact();
            contact.setId(contactapplyEntity.getId());
            contact.setUserId(contactapplyEntity.getUser_id());
            contact.setGroupTag(contactapplyEntity.getGroup_tag());
            contact.setContactId(contactapplyEntity.getContact_id());
            contact.setDatetime(contactapplyEntity.getDatetime());
            contactList.add(contact);
        }
        return contactList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupTag() {
        return groupTag;
    }

    public void setGroupTag(String groupTag) {
        this.groupTag = groupTag;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getContactGroupTag() {
        return contactGroupTag;
    }

    public void setContactGroupTag(String contactGroupTag) {
        this.contactGroupTag = contactGroupTag;
    }

    public Integer getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(Integer intimacy) {
        this.intimacy = intimacy;
    }
}
