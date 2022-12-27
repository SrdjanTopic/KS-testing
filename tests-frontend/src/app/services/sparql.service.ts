import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class SparqlService {

  constructor(private http: HttpClient) { }
  
  getPreviousConcepts(conceptName: String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${conceptName}`+'/allPreviousConcepts').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  }

  getDirectNextConcepts(conceptName: String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${conceptName}`+'/directNextConcepts').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  } 

  getSolvableTests(conceptName:String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${conceptName}`+'/solvableTests').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  }
  

}
