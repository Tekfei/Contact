package daoentity;

public class CircleEntity {

    private String id;
    private String name;//圈子名称
    private String description;//圈子描述
    private Integer state;//圈子状态，0为未审核，1审核通过，2为审核拒绝
    private String user_id;//圈子管理员user_id

    public CircleEntity(){}

    public CircleEntity(String id, String name, String description, Integer state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
