import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public login(body): Observable<any> {
    //console.log(body);
    return this.http.post(this.baseUrl + 'auth/login', body);
  }

  public registartion(body): Observable<any> {
    return this.http.post(this.baseUrl + 'auth/patients', body);
  }

  public setPasswordMedical(id: string, body: any): Observable<any> {
    return this.http.post(`${this.baseUrl}auth/medical/${id}/first-password`, body);
  }

  public setPasswordAdmin(id: string, body: any): Observable<any> {
    return this.http.post(`${this.baseUrl}auth/admin/${id}/first-password`, body);
  }

  public updatePasswordMedical(id: string, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}auth/medical/${id}/update-password`, body);
  }

  public updatePasswordAdmin(id: string, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}auth/admin/${id}/update-password`, body);
  }

  public showByRole (roles: string[]) : boolean {
    const userRaw = localStorage.getItem('user');
    const user = JSON.parse(userRaw);

    return roles.some(role => role === user.userType);
  }
}
