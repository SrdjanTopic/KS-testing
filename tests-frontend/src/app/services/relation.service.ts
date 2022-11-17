import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class RelationService {
  constructor(private http: HttpClient) {}

  getRelations() {
    return this.http.get(`${environment.apiUrl}/relations/`, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
    });
  }
  updateRelations(relations: any[]) {
    return this.http
      .post<any>(`${environment.apiUrl}/relations/update`, relations)
      .pipe(map((data) => console.log('All: ', JSON.stringify(data))));
  }
}
