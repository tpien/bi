import {Routes} from '@angular/router';
import {BillItemsComponent} from "./components/bill-items/bill-items.component";
import {PlacesComponent} from "./components/places/places.component";

export const routes: Routes = [
  {path: '', component: PlacesComponent},
  {path: 'billItems/:place/:from/:to', component: BillItemsComponent},
];
