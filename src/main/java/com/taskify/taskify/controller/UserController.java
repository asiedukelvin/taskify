package com.taskify.taskify.controller;

import com.taskify.taskify.model.User;
import com.taskify.taskify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}/profile-pic")
    public ResponseEntity<User> updateProfilePic(@PathVariable String id, @RequestBody String profilePicUrl) {
        User updatedUser = userService.updateProfilePic(id, profilePicUrl);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<User> updateEmail(@PathVariable String id, @RequestBody String newEmail) {
        User updatedUser = userService.updateEmail(id, newEmail);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<User> updateName(@PathVariable String id, @RequestBody Map<String, String> names) {
        String firstName = names.get("firstName");
        String lastName = names.get("lastName");
        User updatedUser = userService.updateName(id, firstName, lastName);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateAll(@PathVariable String id, @RequestBody User newUserData) {
        User updatedUser = userService.updateAll(id, newUserData);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> updatePassword(@PathVariable String id, @RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User updatedUser = userService.updatePassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
