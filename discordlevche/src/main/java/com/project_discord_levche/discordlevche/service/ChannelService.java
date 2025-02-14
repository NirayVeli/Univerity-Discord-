package com.project_discord_levche.discordlevche.service;

import com.project_discord_levche.discordlevche.model.ChannelsModel;
import com.project_discord_levche.discordlevche.repository.ChannelRepository;
import com.project_discord_levche.discordlevche.repository.RoleRepository;
import com.project_discord_levche.discordlevche.repository.UserChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserChannelRepository userChannelRepository;

    public List<ChannelsModel> findAll() {
        return channelRepository.findAll();
    }

    public ChannelsModel save(ChannelsModel channel) {
        return channelRepository.save(channel);
    }

    public ChannelsModel findById(Long id){
        return channelRepository.findById(id);
    }

    public ChannelsModel update(Long id, ChannelsModel channelDetails){
        ChannelsModel channel = findById(id);
        if(channel != null){
            channel.setName(channelDetails.getName());
            return channelRepository.save(channel);
        }
        return null;
    }

    public List<ChannelsModel> getUserChannels(Long userId) {
        return userChannelRepository.findChannelsByUserId(userId);
    }
    public void deleteChannel(Long channelId, Long userId){
        if(channelRepository.isOwner(channelId, userId) && channelRepository.hasRole(userId, "OWNER")){
            channelRepository.deleteById(channelId);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to delete the channel");
        }
    }

    public void removeUserFromChannel(Long channelId, Long userId) {
        if(channelRepository.isOwner(channelId, userId) && hasRole(userId, "OWNER")) {
            userChannelRepository.removeUserFromChannel(channelId, userId);
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized to remove users from the channel");
        }
    }

    public boolean hasRole(Long userId, String roleName){
        return roleRepository.hasRole(userId, roleName);
    }

    public void assignRoleToUser(Long userId, String roleName){
        roleRepository.assignRoleToUser(userId, roleName);
    }


}
