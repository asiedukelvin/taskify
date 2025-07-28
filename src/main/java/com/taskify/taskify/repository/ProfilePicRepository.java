package com.taskify.taskify.repository;

import com.taskify.taskify.model.ProfilePic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfilePicRepository extends MongoRepository<ProfilePic, String> {
        Optional<ProfilePic> findByUserId(String userId);
}
