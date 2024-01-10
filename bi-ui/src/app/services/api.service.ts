/* tslint:disable:ban-types */
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { catchError, map } from 'rxjs/operators';
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(
    private http: HttpClient,
  ) {
  }

  get(path: string, params: HttpParams = new HttpParams(), apiURL = environment.api_url): Observable<any> {
    return this.http.get<any>(`${apiURL}${path}`, {params})
      .pipe(catchError(this.formatErrors));
  }

  getList(path: string, params: HttpParams = new HttpParams(), apiURL = environment.api_url): Observable<any> {
    return this.http.get<any[]>(`${apiURL}${path}`, {params})
      .pipe(catchError(this.formatErrors));
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http.put(
      `${environment.api_url}${path}`,
      JSON.stringify(body),
    ).pipe(catchError(this.formatErrors));
  }

  patch(path: string, body: Object = {}): Observable<any> {
    return this.http.patch(
      `${environment.api_url}${path}`,
      JSON.stringify(body),
    ).pipe(catchError(this.formatErrors));
  }

  post(path: string, body: Object = {}): Observable<any> {
    /*let headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });*/

    return this.http.post(
      `${environment.api_url}${path}`,
      JSON.stringify(body),
    ).pipe(catchError(this.formatErrors));
  }

  delete(path: any): Observable<any> {
    return this.http.delete(
      `${environment.api_url}${path}`,
    ).pipe(catchError(this.formatErrors));
  }

  private formatErrors(error: any) {
    console.log(error);
    return throwError(error.error);
  }
}
