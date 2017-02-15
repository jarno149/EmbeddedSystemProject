import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  private state = true;

  ngOnInit(){


  /*  var user = JSON.parse(localStorage.getItem('currentUser'));
    if (user.token == null)
    {
    this.state = true;

    }
    if (localStorage.getItem('currentUser') == null)
    {
      console.log('storage empty! plz log in')
      return
    } */

  }



}
