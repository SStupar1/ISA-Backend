import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MedicalService {

  private baseUrl = environment.baseUrl;
  

  constructor(private http: HttpClient) { }

  public getMedicalProfileById(id): Observable<any> {
    return this.http.get(`${this.baseUrl}medicalstaff/${id}`);
  }

  public getAllMedical(): Observable<any> {
    return this.http.get(`${this.baseUrl}medicalstaff`);
  }

  public getAllMedicalWithFilter(id, filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}medicalstaff/with-filter${this.buildFilterRequest(filter)}`, id);
  }

  public getA(id, filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/${id}/medical-filter${this.buildFilterRequest(filter)}`, id);
  }

  public updateMedical(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}medicalstaff/${id}`, body);
  }

  public getAllMedicalByClinic(id): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/${id}/medical`);
  }

  public deleteMedical(id): Observable<any> {
    return this.http.delete(`${this.baseUrl}medicalstaff/delete/${id}`);
  }

  public createVacation(body): Observable<any> {
    return this.http.post(`${this.baseUrl}medicalstaff/vacation-request`, body);
  }

  public getMyDoctors(id): Observable<any> {
    return this.http.get(`${this.baseUrl}medicalstaff/my-doctors/patient/${id}`);
  }

  public gradeMyDoctor(body): Observable<any> {
    return this.http.post(`${this.baseUrl}medicalstaff/grade-a-doctor`, body);
  }
  
  public getDoctorsAvgGrade(id): Observable<any> {
    return this.http.get(`${this.baseUrl}medicalstaff/avg/${id}/doctor`);
  }

  public getWorkCalendar(id): Observable<any> {
    return this.http.get(`${this.baseUrl}medicalstaff/calendar/${id}`);
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
