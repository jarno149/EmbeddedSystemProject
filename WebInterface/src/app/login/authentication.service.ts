import {Injectable} from "@angular/core";
import {Http, Response, Headers} from "@angular/http";
import {Observable} from "rxjs";
import 'rxjs/add/operator/map'



@Injectable()
export class AuthenticationService {

  public token: string;


  constructor(private http: Http) {
// set token if saved in local storage
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
  }








    login(username: string, password: string): Observable<boolean> {
      let headers = new Headers({ 'Content-Type': 'application/json' });
      return this.http.post('http://127.0.0.1:28862/login', JSON.stringify({ username: username, password: password}),{headers: headers})
        .map((response: Response) => {
          // login successful if there's a jwt token in the response
          /*
          let token = response.json() && response.json().token;
          console.log(this.token);
          console.log(token);
          */

          var token = response.json().token;
          console.log(token);

          if (token) {
            // set token property
            this.token = token;
            console.log(token);


            // store username and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('currentUser', JSON.stringify({ username: username, token: token }));

            // return true to indicate successful login
            return true;
          }
          else {
            // return false to indicate failed login
            return false;
          }
        });
  }

  logout()  {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }
}
