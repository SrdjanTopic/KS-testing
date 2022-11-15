import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { IUser } from '../model/user';
import { AuthService } from './auth.service';
import { catchError, distinctUntilChanged, map , tap} from 'rxjs/operators';
import {
  BehaviorSubject,
  Observable,
  throwError,
} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject = new BehaviorSubject<IUser>({} as IUser);
  public currentUser = this.currentUserSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  constructor(
    private http: HttpClient,
    authenticationService: AuthService
  ) {
    if (authenticationService.isLoggedIn()) {
      this.getCurrentUser().subscribe({
        next: (user) => {
          this.currentUserSubject.next(user);
        },
        error: (error) => {
          console.error('There was an error!', error);
        },
      });
    }
  }

  deleteUser(id: number): Observable<IUser> {
    return this.http
      .delete<IUser>(`${environment.apiUrl}/user/${id}`)
      .pipe(catchError(this.handleError));
  }

  createDeleteRequest(explanation: string, email: string): Observable<any> {
    return this.http
      .post<any>(`${environment.apiUrl}/userDeletion`, {
        deletionExplanation: explanation,
        userEmail: email,
      })
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCurrentUser(): Observable<IUser> {
    return this.http.get<any>(`${environment.apiUrl}/user`).pipe(
      map((user: IUser) => {
        this.currentUserSubject.next(user);
        return user;
      })
    );
  }

  checkUser(id: number): Observable<IUser> {
    return this.http
      .get<any>(`${environment.apiUrl}/user/credentials/${id}`)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  updateUser(user: IUser): Observable<IUser> {
    return this.http.put<any>(`${environment.apiUrl}/user/${user.id}`, user);
  }

  purgeUser() {
    this.currentUserSubject.next({} as IUser);
  }

  changePassword(password: string): Observable<any> {
    let user: IUser = this.currentUserSubject.value;
    console.log(user.id);
    return this.http.put<any>(`${environment.apiUrl}/admin/changePassword`, {
      id: user.id,
      password: password,
    });
  }

  getAllUser(): Observable<IUser[]> {
    return this.http
      .get<IUser[]>(`${environment.apiUrl}/user/users`)
      .pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
