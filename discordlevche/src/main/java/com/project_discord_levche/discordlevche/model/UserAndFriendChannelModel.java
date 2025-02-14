package com.project_discord_levche.discordlevche.model;

import java.util.List;

public class UserAndFriendChannelModel {
    private List<ChannelsModel> channels;
    private List<UserModel> friends;

    public UserAndFriendChannelModel(List<ChannelsModel> channels, List<UserModel> friends) {
        this.channels = channels;
        this.friends = friends;
    }

    public List<ChannelsModel> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelsModel> channels) {
        this.channels = channels;
    }

    public List<UserModel> getFriends() {
        return friends;
    }

    public void setFriends(List<UserModel> friends) {
        this.friends = friends;
    }
}


