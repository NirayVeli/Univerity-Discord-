import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../models/message.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.baseUrl}/messages`;

  getMessagesByChannel(channelId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.baseUrl}/channel/${channelId}`);
  }

  sendMessageToChannel(message: Message): Observable<any> {
    return this.http.post(this.baseUrl, message);
  }
}
