import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  paymentType?: string;
  createdDate?: Moment;
}

export class Order implements IOrder {
  constructor(public id?: number, public paymentType?: string, public createdDate?: Moment) {}
}
