package com.project_discord_levche.discordlevche.service;

import com.project_discord_levche.discordlevche.model.MessageModel;
import com.project_discord_levche.discordlevche.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public MessageModel sendMessageToFriend(Long senderId, Long receiverId, String content) {
        MessageModel message = new MessageModel();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        return messageRepository.save(message);
    }

    public MessageModel sendMessageToChannel(Long senderId, Long channelId, String content) {
        MessageModel message = new MessageModel();
        message.setSenderId(senderId);
        message.setChannelId(channelId);
        message.setContent(content);
        return messageRepository.save(message);
    }

    public List<MessageModel> getMessagesByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    public List<MessageModel> getMessagesBetweenUsers(Long userId1, Long userId2) {
        return messageRepository.findMessagesBetweenUsers(userId1, userId2);
    }

    public List<MessageModel> getMessagesByChannelId(Long channelId) {
        return messageRepository.findMessagesByChannelId(channelId);
    }

    public List<MessageModel> findAll() {
        return messageRepository.findAll();
    }

    public MessageModel save(MessageModel message) {
        return messageRepository.save(message);
    }

    public MessageModel findById(Long id){
        return messageRepository.findById(id);
    }

    public MessageModel update(Long id, MessageModel messageDetails){
        MessageModel message = findById(id);
        if(message != null){
            message.setContent(messageDetails.getContent());
            message.setSenderId(messageDetails.getSenderId());
            message.setChannelId(messageDetails.getChannelId());
            return messageRepository.save(message);
        }
        return null;
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
}

