import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRates } from 'app/shared/model/rates.model';
import { RatesService } from './rates.service';

@Component({
  templateUrl: './rates-delete-dialog.component.html'
})
export class RatesDeleteDialogComponent {
  rates: IRates;

  constructor(protected ratesService: RatesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ratesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'ratesListModification',
        content: 'Deleted an rates'
      });
      this.activeModal.dismiss(true);
    });
  }
}
