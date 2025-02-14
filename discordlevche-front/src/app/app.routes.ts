import { Routes } from '@angular/router';
import { ChannelListComponent } from './features/channels/channel-list/channel-list.component';
import { ChannelDetailsComponent } from './features/channels/channel-details/channel-details.component';

export const routes: Routes = [
    { path: '', redirectTo: 'channels', pathMatch: 'full' },
    { path: 'channels', component: ChannelListComponent },
    { path: 'channels/:id', component: ChannelDetailsComponent } 
];
