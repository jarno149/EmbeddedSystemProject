import {Component, OnInit, Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import {DatabaseService} from "./database.service";
import {Temperature} from "./temperature";
import {Router, CanActivate} from "@angular/router";
import {AuthGuard} from "../login/auth.guard";


Injectable()

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css']
})


export class DatabaseComponent implements OnInit {

temperatures: Temperature[];
mode = 'Observable';
public test=true;


  constructor(private databaseService: DatabaseService,
              private router: Router,) {
  }



  ngOnInit(): void
  {
      this.getTemp();

  }

  getTemp(): void
  {
    this.databaseService
      .getTemperature()
      .then(temperatures => this.temperatures = temperatures);
  }


}
