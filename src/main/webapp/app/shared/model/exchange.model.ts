import { Moment } from 'moment';
import { IRates } from 'app/shared/model/rates.model';

export interface IExchange {
  id?: number;
  date?: Moment;
  rates?: IRates[];
}

export class Exchange implements IExchange {
  constructor(public id?: number, public date?: Moment, public rates?: IRates[]) {}
}
