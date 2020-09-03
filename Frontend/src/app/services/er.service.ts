import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErService {

  private baseUrl = environment.baseUrl;


  constructor(private http: HttpClient) { }

  public getAllErs(): Observable<any> {
    return this.http.get(`${this.baseUrl}er`);
  }

  public getErsByClinic(id): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/${id}/ers`);
  }

  public getErsByClinicWithFilter(id, filter = {}): Observable<any> {
    return this.http.get(`${this.baseUrl}clinics/${id}/er${this.buildFilterRequest(filter)}`);
  }

  public updateEr(id, body): Observable<any> {
    return this.http.put(`${this.baseUrl}er/${id}`, body);
  }

  public createEr(id, body): Observable<any> {
    return this.http.post(`${this.baseUrl}er/${id}`, body);
  }

  public getEr(id): Observable<any> {
    return this.http.get(`${this.baseUrl}er/${id}`);
  }

  public deleteEr(id): Observable<any> {
    return this.http.delete(`${this.baseUrl}er/delete/${id}`);
  }

  private buildFilterRequest(filterObject: any): String {
    const values = Object.keys(filterObject).filter(filterValue => filterValue !== null || filterValue !== '');
    if (values.length === 0) {
      return '';
    }
    let filterQuery = '?';
    let counter;
    Object.keys(filterObject).forEach(x => {
      if (filterObject[x] !== null || filterObject[x] !== '') {
        let temp = '';
        if (counter === 0) {
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


