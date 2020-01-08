package entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Message implements Serializable {
    private String userName;
    private int type;
	private String content;
    private Date datetime;

    public Message(){
    	
    }
    
    public Message(String content, int type, String userName, String datetime){
    	this.content = content;
        this.type =type;
        this.userName =userName;
        try {
            this.datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    public Message(String content, int type, String userName, Date datetime){
        this.content = content;
        this.type =type;
        this.userName =userName;
    	this.datetime = datetime;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime() {
		this.datetime = new Date();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
