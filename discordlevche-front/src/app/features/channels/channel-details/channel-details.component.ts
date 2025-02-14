import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ChannelService } from '../../../core/services/channel.service';
import { MessageService } from '../../../core/services/message.service';
import { UserService } from '../../../core/services/user.service';
import { Channel } from '../../../core/models/channel.model';
import { Message } from '../../../core/models/message.model';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-channel-details',
  templateUrl: './channel-details.component.html',
  styleUrl: './channel-details.component.css',
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class ChannelDetailsComponent implements OnInit {
  channel: Channel | null = null;
  members: User[] = [];
  messages: Message[] = [];
  newMessage: string = ''; 

  private route = inject(ActivatedRoute);
  private channelService = inject(ChannelService);
  private messageService = inject(MessageService);
  private userService = inject(UserService);

  ngOnInit() {
    const channelId = Number(this.route.snapshot.paramMap.get('id'));
    if (!isNaN(channelId)) {
      this.loadChannel(channelId);
      this.loadMembers(channelId);
      this.loadMessages(channelId);
    }
  }

  loadChannel(channelId: number) {
    this.channelService.getChannelById(channelId).subscribe((data) => {
      this.channel = data;
    });
  }

  loadMembers(channelId: number) {
    this.userService.getUsersInChannel(channelId).subscribe((data) => {
      this.members = data;
    });
  }

  loadMessages(channelId: number) {
    this.messageService.getMessagesByChannel(channelId).subscribe((data) => {
      this.messages = data.map((msg) => ({
        ...msg,
        senderUsername: this.getSenderUsername(msg.senderId)
      }));
    });
  }

  getSenderUsername(senderId: number): string {
    const user = this.members.find(user => user.id === senderId);
    return user ? user.username : 'Неизвестен';
  }

  sendMessage() {
    if (!this.newMessage.trim() || !this.channel?.id) return; 

    const message: Message = {
      id: 0, 
      content: this.newMessage,
      channelId: this.channel.id,
      senderId: 1,
    };

    this.messageService.sendMessageToChannel(message).subscribe(() => {
      this.newMessage = '';
      if (this.channel && this.channel.id !== undefined) {
        this.loadMessages(this.channel.id); 
      }
    });
  }

  goBack() {
    window.history.back();
  }
}
