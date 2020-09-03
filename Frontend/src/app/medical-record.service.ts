import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MedicalRecordService {

  constructor(private http: HttpClient,
  ) { }

  getMedicalRecordInfo(id: any) {
    return this.http.get(`${environment.baseUrl}medicalRecord/getByPatientId?id=` + id)
      .pipe(
        map((response: any) => {
          console.log(response)
          const data = response
          return data;
        }),
        catchError((err: any) => {
          return throwError(err);
        })
      );
  }
  updateMedicalRecord(
    id : any,
    height : any,
    weight : any,
    bloodType : any,
    diopter : any,
    alergies : any) {
    return this.http.post(`${environment.baseUrl}medicalRecord/update`,{
      id : id,
      height : height,
      weight : weight,
      bloodType : bloodType,
      diopter : diopter,
      alergies : alergies
    }).pipe(
      map((response: any) => {
        const data = response;
        return data;
      }),
      catchError((err: any) => {
        return throwError(err);
      })
    )
  }
}
