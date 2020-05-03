import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';

@Component({
  templateUrl: './exchange-delete-dialog.component.html'
})
export class ExchangeDeleteDialogComponent {
  exchange: IExchange;

  constructor(protected exchangeService: ExchangeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.exchangeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'exchangeListModification',
        content: 'Deleted an exchange'
      });
      this.activeModal.dismiss(true);
    });
  }
}
