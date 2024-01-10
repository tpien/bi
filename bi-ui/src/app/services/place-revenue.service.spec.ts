import { TestBed } from '@angular/core/testing';

import { PlaceRevenueService } from './place-revenue.service';

describe('PlaceRevenueService', () => {
  let service: PlaceRevenueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlaceRevenueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
