import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) { }

  getAllPendingRecipes() {
    return this.http.get(`${environment.baseUrl}recipe/getAllPending`)
    .pipe(
      map((response: any) => {
        const data = response;
        console.log("preuzimam")
        return data;
      }),
      catchError((err: any) => {
        return throwError(err);
      })
    )
  }
  approveRecipe(recipeId : any,
                nurseId  : any){
      return this.http.post(`${environment.baseUrl}recipe/updateRecipe`,{
        nurse : nurseId,
        recipeId : recipeId
      }).pipe(
        map((response: any) => {
          const data = response;
          console.log("preuzimam")
          return data;
        }),
        catchError((err: any) => {
          return throwError(err);
        })
      )
  }
}
