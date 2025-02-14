import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.baseUrl}/users`;

  getUsersInChannel(channelId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/channel/${channelId}`);
  }
}
