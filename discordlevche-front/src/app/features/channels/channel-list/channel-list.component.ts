import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ChannelService } from '../../../core/services/channel.service';
import { Channel } from '../../../core/models/channel.model';
import { FormsModule } from '@angular/forms'; 
import { CommonModule } from '@angular/common'; 
@Component({
  selector: 'app-channel-list',
  templateUrl: './channel-list.component.html',
  styleUrl: './channel-list.component.css',
  standalone: true, 
  imports: [FormsModule, CommonModule],
})
export class ChannelListComponent {
  public channels: Channel[] = [];
  public newChannelName: string = '';

  private channelService = inject(ChannelService);
  private router = inject(Router);

  constructor() {
    this.fetchChannels();
  }

  fetchChannels() {
    this.channelService.getAllChannels().subscribe((response: Channel[]) => {
      this.channels = response;
    });
  }

  navigateToChannel(channelId: number | undefined) {
    if (channelId !== undefined) {
      this.router.navigate(['/channels', channelId]);
    } else {
      console.error('Грешка: channelId е undefined');
    }
  }
  
  
  createChannel() {
    if (!this.newChannelName.trim()) {
      alert('Моля, въведете име на канала!');
      return;
    }

    const newChannel: Channel = {
      name: this.newChannelName,
      description: 'Описание на канала',
      ownerId: 1, 
    };

    this.channelService.createChannel(newChannel).subscribe(() => {
      this.newChannelName = '';
      this.fetchChannels();
    });
  }
}
