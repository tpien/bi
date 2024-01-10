import {Component, OnInit} from '@angular/core';
import {PlaceRevenue} from "../../model/PlaceRevenue";
import {PlaceRevenueService} from "../../services/place-revenue.service";
import {DecimalPipe, NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-places',
  standalone: true,
  imports: [
    DecimalPipe,
    NgForOf,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './places.component.html',
  styleUrl: './places.component.css'
})
export class PlacesComponent implements OnInit {
  daysAgo: number = 0;
  from: string = '2023-01-01T10:00';
  to: string = '2024-01-01T10:00';
  places: PlaceRevenue[] = [];

  constructor(private service: PlaceRevenueService) {
  }

  ngOnInit(): void {
    this.search();
  }

  changeDate(): void {
    if (this.daysAgo > 0) {

      let sysdate = new Date();
      let dateAgo = new Date();
      dateAgo.setDate(sysdate.getDate() - this.daysAgo);
      this.from = dateAgo.toISOString().slice(0, 16); // "2023-01-01T10:00"
      this.to = sysdate.toISOString().slice(0, 16); // "2023-01-01T10:00
    }
  }

  search(): void {
    const searchFrom = Date.parse(this.from);
    const searchTo = Date.parse(this.to);

    this.service.getPlaceRevenue(searchFrom, searchTo).subscribe(
      places => {
        this.places = places;
      },
    );
  }
}
