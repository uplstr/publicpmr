import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaxi } from 'app/shared/model/taxi.model';
import { TaxiService } from './taxi.service';

@Component({
  templateUrl: './taxi-delete-dialog.component.html'
})
export class TaxiDeleteDialogComponent {
  taxi: ITaxi;

  constructor(protected taxiService: TaxiService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.taxiService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'taxiListModification',
        content: 'Deleted an taxi'
      });
      this.activeModal.dismiss(true);
    });
  }
}
