import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentTypeService {

  private baseUrl = environment.baseUrl;
  
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  public getAllAppointmentType(): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-type`);
  }

  public getAllAppointmentTypeWithFilter(filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-type/with-filter${this.buildFilterRequest(filter)}`);
  }

  public editAppointmentType(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}appointment-type/${id}`, body);
  }

  public getAppointmentType(id): Observable<any> {
    return this.http.get(`${this.baseUrl}appointment-type/${id}`);
  }

  public createAppointmentType(body): Observable<any> {
    return this.http.post(`${this.baseUrl}appointment-type`, body);
  }

  public deleteAppointmentType(id): Observable<any> {
    return this.http.delete(`${this.baseUrl}appointment-type/delete/${id}`);
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

