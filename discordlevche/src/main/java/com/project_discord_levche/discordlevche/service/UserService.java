package com.project_discord_levche.discordlevche.service;

import com.project_discord_levche.discordlevche.model.ChannelsModel;
import com.project_discord_levche.discordlevche.model.FriendModel;
import com.project_discord_levche.discordlevche.model.UserChannelModel;
import com.project_discord_levche.discordlevche.model.UserModel;
import com.project_discord_levche.discordlevche.repository.ChannelRepository;
import com.project_discord_levche.discordlevche.repository.FriendshipRepository;
import com.project_discord_levche.discordlevche.repository.UserChannelRepository;
import com.project_discord_levche.discordlevche.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserChannelRepository userChannelRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    public UserModel findById(Long id){
        return userRepository.findById(id);
    }

    public UserModel update(Long id, UserModel userDetails){
        UserModel user = findById(id);
        if(user != null){
            user.setUsername(userDetails.getUsername());
            return userRepository.save(user);
        }
        return null;
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public FriendModel addFriend(Long userId, Long friendId){
        FriendModel friendship = new FriendModel();
        friendship.setUserId(userId);
        friendship.setFriendId(friendId);
        return friendshipRepository.save(friendship);
    }

    public List<UserModel> getUserFriends(Long userId) {
        return userRepository.findFriendsByUserId(userId);
    }

    public List<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUserToChannel(Long userId, Long channelId) {
        UserModel user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        ChannelsModel channel = channelRepository.findById(channelId);
        if (channel == null) {
            throw new RuntimeException("Channel not found");
        }

        UserChannelModel userChannel = new UserChannelModel();
        userChannel.setUser(user);
        userChannel.setChannel(channel);
        userChannelRepository.save(userChannel);
    }

    public void removeUserFromChannel(Long userId, Long channelId) {
        UserModel user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        ChannelsModel channel = channelRepository.findById(channelId);
        if (channel == null) {
            throw new RuntimeException("Channel not found");
        }

        List<UserChannelModel> userChannels = userChannelRepository.findAll();
        for (UserChannelModel userChannel : userChannels) {
            if (userChannel.getUser().equals(user) && userChannel.getChannel().equals(channel)) {
                userChannelRepository.deleteById(userChannel.getId());
                break;
            }
        }
    }

    public List<ChannelsModel> getUserChannels(Long userId) {
        List<UserChannelModel> userChannels = userChannelRepository.findAll();
        return userChannels.stream()
                .filter(uc -> uc.getUser().getId().equals(userId))
                .map(UserChannelModel::getChannel)
                .collect(Collectors.toList());
    }

}
