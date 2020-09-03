import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

@Injectable()
export class AppInterceptor implements HttpInterceptor {

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token: string = localStorage.getItem('token');
        if (token) {
            // Logged in. Add Bearer token.
            return next.handle(
              request.clone({
                headers: request.headers.append('Authorization', token)
              })
            );
          }
          // Not logged in. Continue without modification.
          return next.handle(request);
    }

}