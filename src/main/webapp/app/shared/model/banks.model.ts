import { BankName } from 'app/shared/model/enumerations/bank-name.model';

export interface IBanks {
  id?: number;
  title?: string;
  systemName?: BankName;
  status?: boolean;
  ratesId?: number;
}

export class Banks implements IBanks {
  constructor(public id?: number, public title?: string, public systemName?: BankName, public status?: boolean, public ratesId?: number) {
    this.status = this.status || false;
  }
}
