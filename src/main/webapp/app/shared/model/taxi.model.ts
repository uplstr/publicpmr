export interface ITaxi {
  id?: number;
  title?: string;
  phone?: number;
  active?: number;
}

export class Taxi implements ITaxi {
  constructor(public id?: number, public title?: string, public phone?: number, public active?: number) {}
}
