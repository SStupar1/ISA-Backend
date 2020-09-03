import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentRequestService {

  private baseUrl = environment.baseUrl;
  

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  public getAllRequests(): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-request`);
  }

  public getAllRequestsByClinic(id): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-request/clinic/${id}`);
  }

  public getAllRequestsByPatient(id): Observable<any> {
    return this.http.get(`${this.baseUrl}patients/appointment-requests/${id}`);
  }

  public approveRequest(id): Observable<any> {
    return this.http.put(`${this.baseUrl}appointment-request/${id}/approve`, id);
  }

  public createRequest(body): Observable<any> {
    return this.http.post(`${this.baseUrl}appointment-request/send-request`, body);
  }

  public getRequest(id): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-request/${id}`);
  }

  public getErs(filter): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-request/search-ers${this.buildFilterRequest(filter)}`);
  }

  public approveRequestByPatientAndCreateAnAppointment(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}appointment-request/${id}/approve`, body);
  }

  private buildFilterRequest(filterObject: any): String {
    const values = Object.keys(filterObject).filter(filterValue => filterValue !== null || filterValue !== '');
    if(values.length === 0) {
      return '';
    }
    let filterQuery = '?';
    let counter;
    Object.keys(filterObject).forEach(x => {
      if(filterObject[x] !== null || filterObject[x] !== '') {
        let temp = '';
        if(counter === 0) {
          temp = '';
        } else {
          temp = '&';
        }
        filterQuery = filterQuery + temp + x + '=' + filterObject[x];
        counter = counter + 1;
      }
    })
    return filterQuery;
  }
}
