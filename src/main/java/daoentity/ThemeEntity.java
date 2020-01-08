package daoentity;

/**
 * 主题广场
 */
public class ThemeEntity {

    private String id;
    private String name;//主题名称
    private String description;//主题描述

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
}
