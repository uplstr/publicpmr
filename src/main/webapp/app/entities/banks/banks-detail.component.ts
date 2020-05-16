import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBanks } from 'app/shared/model/banks.model';

@Component({
  selector: 'jhi-banks-detail',
  templateUrl: './banks-detail.component.html'
})
export class BanksDetailComponent implements OnInit {
  banks: IBanks;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ banks }) => {
      this.banks = banks;
    });
  }

  previousState() {
    window.history.back();
  }
}
