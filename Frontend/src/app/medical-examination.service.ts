import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MedicalExaminationService {

  constructor(private http: HttpClient) { }

  newExamination(patientId: any,
    doctorId: any,
    description: any,
    medicine: any,
    diagnosis: any) {
    return this.http.post(`${environment.baseUrl}medicalExamination/new`, {
      patient: patientId,
      doctor: doctorId,
      diagnosis: diagnosis,
      medicine: medicine,
      description: description
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
  getAllForOnePatient(patientId: any) {
    return this.http.get(`${environment.baseUrl}medicalExamination/getAll/` + patientId).pipe(
      map(
        (response: any) => {
          return response;
        }, catchError((err: any) => {
          return throwError(err);

        })
      )
    )
  }
}
