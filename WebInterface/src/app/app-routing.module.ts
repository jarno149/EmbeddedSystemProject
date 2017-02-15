import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {GaugeComponent} from "./gauge/gauge.component";
import {DatabaseComponent} from "./database/database.component";
import {LoginComponent} from "./login/login.component";
import {AppComponent} from "./app.component";
import {AuthGuard} from "./login/auth.guard";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [

 // { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '', component: AppComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'livedata', component: GaugeComponent },
  {path: 'database', component: DatabaseComponent},
  {path: 'home', component: HomeComponent},
  // otherwise redirect to home
  { path: '**', redirectTo: '' }

 //   children: []
 // }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule { }
