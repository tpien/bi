import {Component, OnInit} from '@angular/core';
import {PlaceRevenueService} from "../../services/place-revenue.service";
import {BillItem} from "../../model/Repository";
import {DatePipe, DecimalPipe, NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-bill-items',
  standalone: true,
  imports: [
    DecimalPipe,
    NgForOf,
    DatePipe,
    NgIf
  ],
  templateUrl: './bill-items.component.html',
  styleUrl: './bill-items.component.css'
})
export class BillItemsComponent implements OnInit {
  items: BillItem[] = [];
  place: string = '';
  searchFromDate: Date = new Date();
  searchToDate: Date = new Date();
  searchFrom: number = 0;
  searchTo: number = 0;
  page: number = 1;
  basePaginationPath: string = '';

  constructor(private service: PlaceRevenueService,
              private route: ActivatedRoute,
              public router: Router) {
  }

  ngOnInit(): void {
    this.page = Number(this.route.snapshot.paramMap.get('page') ? this.route.snapshot.paramMap.get('page') : 1);
    this.searchFrom = Number(this.route.snapshot.paramMap.get('from'));
    this.searchTo = Number(this.route.snapshot.paramMap.get('to'));
    this.basePaginationPath = 'billItems/'+ this.place + '/'+ this.searchFrom + '/'+ this.searchTo;

    this.searchFromDate = new Date(this.searchFrom);
    this.searchToDate = new Date(this.searchTo);
    this.place = String(this.route.snapshot.paramMap.get('place'));
    this.loadData(this.page);
  }
  loadData(page : number) {
    this.service.geBillItemsByPlaceAndSalePeriod(this.place, this.searchFrom, this.searchTo, page).subscribe(
      places => {
        this.items = places;
        },
    );
  }


  reload(currentPage: number) {
    this.page = currentPage;
    this.router.navigate([this.basePaginationPath +'/' + currentPage]);
    this.loadData(currentPage);
  }
}
