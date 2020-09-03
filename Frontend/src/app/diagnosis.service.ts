import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DiagnosisService {

  baseUrl = `${environment.baseUrl}` + 'diagnosis'

  constructor(private http : HttpClient) { }
  
  addDiagnosis(diagnosis:String) {
    return this.http.post(`${this.baseUrl}/addDiagnosis`,{
      name: diagnosis
    }).pipe(
      map((response: any) => {
        const data = response;
        return data;
      }),
      catchError((err: any) => {
        return throwError(err.text);
      })
    )
  }
  getAll() {
    return this.http.get(`${this.baseUrl}/getAll`).
    pipe(
      map((response : any) => {
        return response;
      }),
      catchError((err : any) => {
        return throwError(err.text);
        
      })
    )
  }
}
