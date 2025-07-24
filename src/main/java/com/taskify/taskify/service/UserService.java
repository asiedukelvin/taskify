package com.taskify.taskify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.taskify.taskify.utility.JWTUtility;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.taskify.taskify.model.User;
import com.taskify.taskify.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JWTUtility jwtUtil;


    public UserService(UserRepository userRepo, JWTUtility jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public User createUser(User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with email already exists.");
        }
        String rawPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);
        System.out.println("Created user: " + user.getEmail());
        return userRepo.save(user);
    }

    public User loginUser(String email, String rawPassword) {
        User user = userRepo.findByEmail(email);
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getId(), claims);
        return user;
    }

    public User updateProfilePic(String id, String profilePicUrl) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        user.setProfilePic(profilePicUrl);

        return userRepo.save(user);
    }


    public User updateEmail(String userId, String newEmail) {
        if (userRepo.findByEmail(newEmail) != null) {
            throw new RuntimeException("Email already in use");
        }
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(newEmail);
        return userRepo.save(user);
    }

    public User updateName(String userId, String newFirstName, String newLastName) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        return userRepo.save(user);
    }

    public User updateAll(String id, User newUserData) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        // Update fields
        user.setEmail(newUserData.getEmail());
        user.setFirstName(newUserData.getFirstName());
        user.setLastName(newUserData.getLastName());
        user.setProfilePic(newUserData.getProfilePic());

        if (newUserData.getPassword() != null && !newUserData.getPassword().isBlank()) {
            String hashedPassword = passwordEncoder.encode(newUserData.getPassword());
            user.setPassword(hashedPassword);
        }

        return userRepo.save(user);
    }



    public User updatePassword(String userId, String newRawPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newRawPassword));
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
}