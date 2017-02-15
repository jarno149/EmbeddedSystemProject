import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Http, Headers} from "@angular/http";
import {Temperature} from "./temperature";



@Injectable()

export class DatabaseService{

//  private temperatureUrl ='/php/get.php';  // URL to web API
 // private temperatureUrl ='http://localhost:3000/test';  // URL to web API
  private temperatureUrl ='http://192.168.1.7:28862/temperatures/all';


  constructor (private http: Http) {}

  getTemperature(): Promise<Temperature[]> {

    var user = JSON.parse(localStorage.getItem('currentUser'));
    let headers = new Headers({"Content-Type":"application/json","Authorization":"Bearer " + user.token});
    console.log(user.token);
    return this.http.get(this.temperatureUrl, {headers: headers})
      .toPromise()
      .then(response => response.json() as Temperature[])
      .catch(this.handleError);
  }




  private handleError (error: Response | any)
  {
    // In a real world app, we might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
