package com.project_discord_levche.discordlevche.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_channels")
public class UserChannelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelsModel channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ChannelsModel getChannel() {
        return channel;
    }

    public void setChannel(ChannelsModel channel) {
        this.channel = channel;
    }
}
