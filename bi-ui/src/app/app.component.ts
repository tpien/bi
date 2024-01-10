import {Component, ElementRef, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';
import {PlaceRevenueService} from "./services/place-revenue.service";
import {PlaceRevenue} from "./model/PlaceRevenue";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'bi-ui';

  from: string = '2023-01-01T10:00';
  to: string = '2024-01-01T10:00';
  places: PlaceRevenue[] = [];

  constructor(private service: PlaceRevenueService) {
  }

  ngOnInit(): void {
    this.search();
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
