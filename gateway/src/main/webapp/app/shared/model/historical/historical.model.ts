import { Moment } from 'moment';

export interface IHistorical {
  id?: number;
  userId?: string;
  correlationId?: string;
  actionId?: string;
  actionDescription?: string;
  actionDate?: Moment;
}

export class Historical implements IHistorical {
  constructor(
    public id?: number,
    public userId?: string,
    public correlationId?: string,
    public actionId?: string,
    public actionDescription?: string,
    public actionDate?: Moment
  ) {}
}
