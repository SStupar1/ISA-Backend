import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public getAllAppointemntTypes(): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-type`);
  }


  public getPotencialClinics(filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment/search-appointments${this.buildFilterRequest(filter)}`);
  }

  public getPotencialDoctors(filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment/search-doctors${this.buildFilterRequest2(filter)}`);
  }

  public createAppointmentRequestAsDoctor(body): Observable<any> {
    return this.http.post(`${this.baseUrl}appointment/create-request-as-doctor`, body);
  }

  public createPotentialAppointmentByAdmin(body): Observable<any> {
    return this.http.post(`${this.baseUrl}appointment-request/create-potential-appointment`, body);
  }

  public getAllPotentialAppointmentsByPatient(): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-request/potential-appointments`);
  }

  public schedulePotentialAppointment(body): Observable<any> {
    return this.http.post(`${this.baseUrl}appointment/schedule-potential-appointment`, body);
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

  private buildFilterRequest2(filterObject: any): String {
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
