import {NgModule, CUSTOM_ELEMENTS_SCHEMA, Directive} from '@angular/core'
import { RouterModule } from '@angular/router';
import { rootRouterConfig } from './app.routes';
import { AppComponent } from './app.component';
import { GithubService } from './github/shared/github.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';

import { AboutComponent } from './about/about.component';
import { HomeComponent } from './home/home.component';
import { RepoBrowserComponent } from './github/repo-browser/repo-browser.component';
import { RepoListComponent } from './github/repo-list/repo-list.component';
import { RepoDetailComponent } from './github/repo-detail/repo-detail.component';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { ContactComponent } from './contact/contact.component';
import {MqttComponent} from "./mqtt/mqtt.component";
import {GoogleChartComponent} from "./google-chart/google-chart.component";
import {Ng2GoogleChartsModule} from "ng2-google-charts";
import {GaugeComponent} from "./gauge/gauge.component";
import { jqxGaugeComponent } from '../../node_modules/jqwidgets-framework/jqwidgets-ts/angular_jqxgauge';
import {MyGaugeChartAngularComponent} from "./vaaliGauge/vaaligauge.component";
import {ServerStatusComponent} from "./serverstatus/serverstatus.component";



@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    RepoBrowserComponent,
    RepoListComponent,
    RepoDetailComponent,
    HomeComponent,
    ContactComponent,
    MqttComponent,
    GoogleChartComponent,
    GaugeComponent,
    jqxGaugeComponent,
    MyGaugeChartAngularComponent,
    ServerStatusComponent


  ],



  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    Ng2GoogleChartsModule,



    RouterModule.forRoot(rootRouterConfig, { useHash: true })
  ],
  providers: [
    GithubService
  ],
  bootstrap: [ AppComponent ]

})
export class AppModule {

}
