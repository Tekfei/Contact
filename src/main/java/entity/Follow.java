package entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Follow implements Serializable {
    private int userId;
    private int photoId;
    private String userName;
    private Date datetime;
    
    public Follow(){
    	
    }

    public Follow(int userId, String userName, int photoId, String datetime) {
        this.photoId = photoId;
        this.userId = userId;
        this.userName = userName;
        try {
            this.datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Follow(int userId, String userName, int photoId, Date datetime) {
		this.photoId = photoId;
		this.userId = userId;
		this.userName = userName;
        this.datetime = datetime;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime() {
		this.datetime = new Date();
	}

	public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
