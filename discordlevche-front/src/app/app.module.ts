import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms'; 
import { ChannelListComponent } from './features/channels/channel-list/channel-list.component';
import { ChannelDetailsComponent } from './features/channels/channel-details/channel-details.component';

@NgModule({
  declarations: [
    AppComponent,
    ChannelListComponent,
    ChannelDetailsComponent, 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule, 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
