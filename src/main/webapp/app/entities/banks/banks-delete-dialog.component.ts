import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBanks } from 'app/shared/model/banks.model';
import { BanksService } from './banks.service';

@Component({
  templateUrl: './banks-delete-dialog.component.html'
})
export class BanksDeleteDialogComponent {
  banks: IBanks;

  constructor(protected banksService: BanksService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.banksService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'banksListModification',
        content: 'Deleted an banks'
      });
      this.activeModal.dismiss(true);
    });
  }
}
