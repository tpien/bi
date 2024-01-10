import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {ApiService} from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class PlaceRevenueService {

  constructor(private apiService: ApiService) { }

  geBillItemsByPlace(place:string): Observable<[]> {
    return this.apiService.getList('company/' + place);
  }
  geBillItemsByPlaceAndSalePeriod(place:string, from: number, to: number): Observable<[]> {
    return this.apiService.getList(`company/findByPlaceAndSalePeriod/${place}/${from}/${to} `);
  }
  getPlaceRevenue(from: number, to: number): Observable<[]> {
    return this.apiService.getList(`company/groupByPlaces/${from}/${to} `);
  }
}
