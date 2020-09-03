import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private baseUrl = environment.baseUrl;
  

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  public getAllRequests(): Observable<any> {
    return this.http.get(`${this.baseUrl}registration-request`);
  }

  public approveRequest(id): Observable<any> {
    return this.http.put(`${this.baseUrl}registration-request/${id}/approve`, id);
  }

  public denyRequest(id): Observable<any> {
    return this.http.put(`${this.baseUrl}registration-request/${id}/deny`, id);
  }

}
