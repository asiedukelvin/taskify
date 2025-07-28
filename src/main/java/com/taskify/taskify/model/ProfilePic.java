package com.taskify.taskify.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("profilepics")
public class ProfilePic {
    @Id
    private String id;
    private String userId;
    private String image;

    public ProfilePic() {}

    public ProfilePic(String userId, String image) {
        this.userId = userId;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
