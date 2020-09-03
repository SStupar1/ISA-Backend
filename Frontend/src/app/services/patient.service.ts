import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ValueTransformer } from '@angular/compiler/src/util';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  private baseUrl = environment.baseUrl;
  

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  public getPatientProfileById(id): Observable<any> {
    return this.http.get(`${this.baseUrl}patients/${id}`);
  }

  public getAllPatients(filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}patients${this.buildFilterRequest(filter)}`);
  }

  public updatePatient(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}patients/${id}`, body);
  }

  public getAllPatientsByClinic(id, filter = {}): Observable<any> {
    console.log(filter)
    return this.http.get(`${this.baseUrl}clinics/${id}/patients${this.buildFilterRequest(filter)}`);
  }

  public deletePatient(id): Observable<any> {
    return this.http.delete(`${this.baseUrl}patients/delete/${id}`);
  }

  public getAllPatientsAppointments(id): Observable<any> {
    return this.http.get(`${this.baseUrl}patients/patient/${id}/appointments`);
  }

  public getAllPatientsByMedicalaAppointments(id): Observable<any> {
    return this.http.get(`${this.baseUrl}patients/medical/${id}/patients-by-appointments`);
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
