import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { MqttComponent } from './mqtt/mqtt.component';
import { GaugeComponent } from './gauge/gauge.component';
import { jqxGaugeComponent } from '../../node_modules/jqwidgets-framework/jqwidgets-ts/angular_jqxgauge';
import { DatabaseComponent } from './database/database.component';
import {DatabaseService} from "./database/database.service";
import { LoginComponent } from './login/login.component';
import {AuthenticationService} from "./login/authentication.service";
import {AuthGuard} from "./login/auth.guard";
import { HomeComponent } from './home/home.component';



@NgModule({
  declarations: [
    AppComponent,
    MqttComponent,
    GaugeComponent,
    jqxGaugeComponent,
    DatabaseComponent,
    LoginComponent,
    HomeComponent


  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule

  ],
  providers: [DatabaseService,AuthenticationService,AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
