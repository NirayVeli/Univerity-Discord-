package com.project_discord_levche.discordlevche.controller;

import com.project_discord_levche.discordlevche.model.ChannelsModel;
import com.project_discord_levche.discordlevche.model.FriendModel;
import com.project_discord_levche.discordlevche.model.UserModel;
import com.project_discord_levche.discordlevche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discordlevche/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping("/{userId}/channels")
    public List<ChannelsModel> getUserChannels(@PathVariable Long userId) {
        return userService.getUserChannels(userId);
    }

    @PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public UserModel updateUser(@PathVariable Long id, @RequestBody UserModel userDetails){
        return userService.update(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/search/username")
    public List<UserModel> searchUsersByUsername(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/search/email")
    public List<UserModel> searchUsersByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/{userId}/channels/{channelId}")
    public void addUserToChannel(@PathVariable Long userId, @PathVariable Long channelId) {
        userService.addUserToChannel(userId, channelId);
    }

    @DeleteMapping("/{userId}/channels/{channelId}")
    public void removeUserFromChannel(@PathVariable Long userId, @PathVariable Long channelId) {
        userService.removeUserFromChannel(userId, channelId);
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public FriendModel addFriend(@PathVariable Long userId, @PathVariable Long friendId){
        return  userService.addFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<UserModel> getUserFriends(@PathVariable Long userId) {
        return userService.getUserFriends(userId);
    }

}

