package com.project_discord_levche.discordlevche.controller;

import com.project_discord_levche.discordlevche.model.MessageModel;
import com.project_discord_levche.discordlevche.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discordlevche/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<MessageModel> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/receiver/{receiverId}")
    public List<MessageModel> getMessagesByReceiverId(@PathVariable Long receiverId) {
        return messageService.getMessagesByReceiverId(receiverId);
    }

    @GetMapping("/between/{userId1}/{userId2}")
    public List<MessageModel> getMessagesBetweenUsers(@PathVariable Long userId1, @PathVariable Long userId2) {
        return messageService.getMessagesBetweenUsers(userId1,userId2);
    }

    @GetMapping("/channel/{channelId}")
    public List<MessageModel> getMessagesByChannelId(@PathVariable Long channelId) {
        return messageService.getMessagesByChannelId(channelId);
    }

    @PostMapping
    public MessageModel createMessage(@RequestBody MessageModel message) {
        return messageService.save(message);
    }

    @PostMapping("/friend")
    public MessageModel sendMessageToFriend(@RequestBody MessageModel message) {
        return messageService.sendMessageToFriend(message.getSenderId(), message.getReceiverId(), message.getContent());
    }

    @PostMapping("/channel")
    public MessageModel sendMessageToChannel(@RequestBody MessageModel message) {
        return messageService.sendMessageToChannel(message.getSenderId(), message.getChannelId(), message.getContent());
    }

    @GetMapping("/{id}")
    public MessageModel getMessageById(@PathVariable Long id){
        return messageService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageModel updateMessage(@PathVariable Long id, @RequestBody MessageModel messageDetails) {
        return messageService.update(id, messageDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.delete(id);
    }
}

