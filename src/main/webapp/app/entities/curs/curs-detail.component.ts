import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurs } from 'app/shared/model/curs.model';

@Component({
  selector: 'jhi-curs-detail',
  templateUrl: './curs-detail.component.html'
})
export class CursDetailComponent implements OnInit {
  curs: ICurs;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ curs }) => {
      this.curs = curs;
    });
  }

  previousState() {
    window.history.back();
  }
}
