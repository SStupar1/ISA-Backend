import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = environment.baseUrl;
  

  constructor(private http: HttpClient) { }

  public getAdminProfileById(id): Observable<any> {
    return this.http.get(`${this.baseUrl}admins/${id}`);
  }

  public getAllAdmins(): Observable<any> {
    return this.http.get(`${this.baseUrl}admins`);
  }

  public updateAdmin(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}admins/${id}`, body);
  }

  public createDoctor(body, id): Observable<any> {
    return this.http.post(`${this.baseUrl}admins/add-doctor`, body);
  }

  public createNurse(body, id): Observable<any> {
    return this.http.post(`${this.baseUrl}admins/clinic/${id}/add-nurse`, body);
  }

  public deleteAdmin(id): Observable<any> {
    return this.http.delete(`${this.baseUrl}admins/delete/${id}`);
  }

  public sendThatRequestIsConfirmed(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}appointment-request/send-request-by-admin/${id}`, body);
  }

  public sendThatRequestIsDenied(id): Observable<any> {
    return this.http.put(`${this.baseUrl}appointment-request/send-negative-request-by-admin/${id}`, {});
  }

  public createAdmin(body): Observable<any> {
    return this.http.post(`${this.baseUrl}admins/create-admin`, body);
  }

  public autoSchedule(body): Observable<any> {
    return this.http.post(`${this.baseUrl}admins/suggest-ers`, body);
  }

}
