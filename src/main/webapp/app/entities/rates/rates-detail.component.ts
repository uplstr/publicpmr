import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRates } from 'app/shared/model/rates.model';

@Component({
  selector: 'jhi-rates-detail',
  templateUrl: './rates-detail.component.html'
})
export class RatesDetailComponent implements OnInit {
  rates: IRates;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rates }) => {
      this.rates = rates;
    });
  }

  previousState() {
    window.history.back();
  }
}
