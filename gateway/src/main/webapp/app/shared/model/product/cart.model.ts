import { Moment } from 'moment';

export interface ICart {
  id?: number;
  status?: string;
  createdDate?: Moment;
  expiredDate?: Moment;
}

export class Cart implements ICart {
  constructor(public id?: number, public status?: string, public createdDate?: Moment, public expiredDate?: Moment) {}
}
