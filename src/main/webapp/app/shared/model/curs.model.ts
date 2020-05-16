import { IRates } from 'app/shared/model/rates.model';
import { CurrencyName } from 'app/shared/model/enumerations/currency-name.model';

export interface ICurs {
  id?: number;
  purchase?: number;
  sale?: number;
  currency?: CurrencyName;
  rates?: IRates[];
}

export class Curs implements ICurs {
  constructor(
    public id?: number,
    public purchase?: number,
    public sale?: number,
    public currency?: CurrencyName,
    public rates?: IRates[]
  ) {}
}
