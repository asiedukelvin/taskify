package com.taskify.taskify.service;

import com.taskify.taskify.model.ProfilePic;
import com.taskify.taskify.repository.ProfilePicRepository;
import org.springframework.stereotype.Service;


@Service
public class ProfilePicService {
    private final ProfilePicRepository repo;

    public ProfilePicService(ProfilePicRepository repo) {
        this.repo = repo;
    }

    public ProfilePic uploadPic(String userId, String base64) {
        // Fetch existing or create new
        ProfilePic pic = repo.findByUserId(userId).orElse(new ProfilePic());
        pic.setUserId(userId);
        pic.setImage(base64);
        return repo.save(pic); // this will update if exists, insert if not
    }

    public String getProfilePicBase64(String userId) {
        return repo.findByUserId(userId)
                .map(ProfilePic::getImage)
                .orElse(null);
    }


}
