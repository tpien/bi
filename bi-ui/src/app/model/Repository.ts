export interface PlaceRevenue {
  name: string;
  totalAmount: number;
  totalAmountGross: number;
  count: number;
}
export interface BillItem {
  name: string;
  place: string;
  amount: number;
  amountGross: number;
  tax: number;
  employee: number;
  saleDate: Date;
}
