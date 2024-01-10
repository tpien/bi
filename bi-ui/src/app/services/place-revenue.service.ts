import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {ApiService} from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class PlaceRevenueService {

  constructor(private apiService: ApiService) { }

  getAllItems(): Observable<[]> {
    return this.apiService.getList('/company');
  }
  getPlaceRevenue(from: number, to: number): Observable<[]> {
    return this.apiService.getList(`company/groupByPlaces/${from}/${to} `);
  }
}
