import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';


interface ProfessionCriteria {
  conceptNames: string[];
  profession: string
}

@Injectable({
  providedIn: 'root'
})

export class SparqlService {

  constructor(private http: HttpClient) { }

  getPreviousConcepts(conceptName: String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${conceptName}` + '/allPreviousConcepts').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  }

  getDirectNextConcepts(conceptName: String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${conceptName}` + '/directNextConcepts').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  }

  getSolvableTests(conceptName: String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${conceptName}` + '/solvableTests').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  }

  getStudentByConcepts(concepts: String[]) {
    return this.http
      .post<any>(`${environment.apiUrl}/sparql/studentsForTeam`, concepts)
      .pipe(
        tap((data) => console.log('All relations: ', JSON.stringify(data)))
      );
  }

  getConceptsByProfessionAndSkills(professionCriteria: ProfessionCriteria) {
    return this.http
      .post<any>(`${environment.apiUrl}/sparql/studentProfessionCriteria`, professionCriteria)
      .pipe(
        tap((data) => console.log('All relations: ', JSON.stringify(data)))
      );
  }

  getUnfinishedTests(studenyId: String): Observable<String> {
    return this.http.get<String>(environment.apiUrl + `/sparql/${studenyId}` + '/unfinishedTests').pipe(
      tap((data) => console.log('All: ', JSON.stringify(data)))
    );
  }

  getUnTestedConceptsByTeacher(teacher: String) {
    return this.http
      .post<any>(`${environment.apiUrl}/sparql/unusedConceptsByTeacher`, teacher)
      .pipe(
        tap((data) => console.log('All relations: ', JSON.stringify(data)))
      );
  }

  getStudentsByTeacherTest(testName: String) {
    return this.http
      .post<any>(`${environment.apiUrl}/sparql/studentsThatSubmittedTest`, testName)
      .pipe(
        tap((data) => console.log('All relations: ', JSON.stringify(data)))
      );
  }




}
