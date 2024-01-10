import {Component, OnInit} from '@angular/core';
import {PlaceRevenueService} from "../../services/place-revenue.service";
import {BillItem} from "../../model/Repository";
import {DatePipe, DecimalPipe, NgForOf} from "@angular/common";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-bill-items',
  standalone: true,
  imports: [
    DecimalPipe,
    NgForOf,
    DatePipe
  ],
  templateUrl: './bill-items.component.html',
  styleUrl: './bill-items.component.css'
})
export class BillItemsComponent implements OnInit {
  items: BillItem[] = [];
  place: string = '';
  searchFromDate: Date = new Date();
  searchToDate: Date = new Date();

  constructor(private service: PlaceRevenueService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.place = String(this.route.snapshot.paramMap.get('place'));
    const searchFrom = Number(this.route.snapshot.paramMap.get('from'));
    const searchTo = Number(this.route.snapshot.paramMap.get('to'));
    this.searchFromDate = new Date(searchFrom);
    this.searchToDate = new Date(searchTo);

    this.service.geBillItemsByPlaceAndSalePeriod(this.place, searchFrom, searchTo).subscribe(
      places => {
        this.items = places;
      },
    );
  }


}
