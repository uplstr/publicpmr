import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxi } from 'app/shared/model/taxi.model';

@Component({
  selector: 'jhi-taxi-detail',
  templateUrl: './taxi-detail.component.html'
})
export class TaxiDetailComponent implements OnInit {
  taxi: ITaxi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taxi }) => {
      this.taxi = taxi;
    });
  }

  previousState() {
    window.history.back();
  }
}
