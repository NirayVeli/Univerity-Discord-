package com.project_discord_levche.discordlevche.controller;

import com.project_discord_levche.discordlevche.model.ChannelsModel;
import com.project_discord_levche.discordlevche.model.UserAndFriendChannelModel;
import com.project_discord_levche.discordlevche.model.UserModel;
import com.project_discord_levche.discordlevche.service.ChannelService;
import com.project_discord_levche.discordlevche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discordlevche/channels")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ChannelsModel createChannel(@RequestBody ChannelsModel channel) {
        ChannelsModel savedChannel = channelService.save(channel);
        channelService.assignRoleToUser(savedChannel.getOwnerId(), "OWNER");
        return savedChannel;
    }

    @GetMapping
    public List<ChannelsModel> getAllChannels() {
        return channelService.findAll();
    }

    @GetMapping("{id}")
    public ChannelsModel getChannelById(@PathVariable Long id){
        return channelService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public UserAndFriendChannelModel getUserChannelsAndFriends(@PathVariable Long userId) {
        List<ChannelsModel> channels = channelService.getUserChannels(userId);
        List<UserModel> friends = userService.getUserFriends(userId);
        return new UserAndFriendChannelModel(channels, friends);
    }

    @PutMapping("/{id}")
    public ChannelsModel updateChannel(@PathVariable Long id, @RequestBody ChannelsModel channelDetails) {
        return channelService.update(id, channelDetails);
    }

    @DeleteMapping("/{channelId}")
    public void deleteChannel(@PathVariable Long channelId, @RequestParam Long userId) {
        channelService.deleteChannel(channelId, userId);
    }

    @DeleteMapping("/{channelId}/remove-user/{userId}")
    public void removeUserFromChannel(@PathVariable Long channelId, @PathVariable Long userId) {
        channelService.removeUserFromChannel(channelId, userId);
    }
}
