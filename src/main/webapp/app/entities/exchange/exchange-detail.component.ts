import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExchange } from 'app/shared/model/exchange.model';

@Component({
  selector: 'jhi-exchange-detail',
  templateUrl: './exchange-detail.component.html'
})
export class ExchangeDetailComponent implements OnInit {
  exchange: IExchange;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ exchange }) => {
      this.exchange = exchange;
    });
  }

  previousState() {
    window.history.back();
  }
}
