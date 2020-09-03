import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClinicService {

  private baseUrl = environment.baseUrl;
  

  constructor(private http: HttpClient) { }

  public getClinicProfileById(id): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/${id}`);
  }

  public getAllClinics(): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics`);
  }

  public getAllClinicsFilter(filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/with-filter${this.buildFilterRequest(filter)}`);
  }

  public updateClinic(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}clinics/${id}`, body);
  }

  public deleteClinic(id): Observable<any> {
    return this.http.delete(`${this.baseUrl}clinics/delete/${id}`);
  }

  public createClinic(body): Observable<any> {
    return this.http.post(`${this.baseUrl}clinics`, body);
  }

  public gradeAClinic(body): Observable<any> {
    return this.http.post(`${this.baseUrl}clinics/grade-a-clinic`, body);
  }

  public getClinicsAvgGrade(id): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/avg/${id}/clinic`);
  }

  public getMyClinics(id): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/my-clinics/patient/${id}`);
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
