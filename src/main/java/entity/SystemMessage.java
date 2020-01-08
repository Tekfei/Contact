package entity;

import java.io.Serializable;
import java.util.Date;


public class SystemMessage implements Serializable {
    private Date datetime;
    private String content;
    
    public SystemMessage(){
    	
    }
    
    public SystemMessage(String content, Date datetime){
    	this.datetime = datetime;
    	this.content = content;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime() {
		this.datetime = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
