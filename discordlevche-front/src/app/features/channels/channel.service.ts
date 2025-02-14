import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Channel } from '../../core/models/channel.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChannelService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.baseUrl}/channels`;

  getAllChannels(): Observable<Channel[]> {
    return this.http.get<Channel[]>(this.baseUrl);
  }

  createChannel(channel: Channel): Observable<Channel> {
    console.log("Изпращам заявка за създаване на канал:", channel);
    return this.http.post<Channel>(this.baseUrl, channel);
  }

  getChannelById(id: number): Observable<Channel> {
    return this.http.get<Channel>(`${this.baseUrl}/${id}`);
  }
  
}
