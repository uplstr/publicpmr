import { ICurs } from 'app/shared/model/curs.model';
import { IExchange } from 'app/shared/model/exchange.model';
import { BankName } from 'app/shared/model/enumerations/bank-name.model';

export interface IRates {
  id?: number;
  bankSystemName?: BankName;
  bankId?: number;
  curs?: ICurs[];
  exchanges?: IExchange[];
}

export class Rates implements IRates {
  constructor(
    public id?: number,
    public bankSystemName?: BankName,
    public bankId?: number,
    public curs?: ICurs[],
    public exchanges?: IExchange[]
  ) {}
}
