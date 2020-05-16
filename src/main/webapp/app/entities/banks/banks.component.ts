import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBanks } from 'app/shared/model/banks.model';
import { BanksService } from './banks.service';
import { BanksDeleteDialogComponent } from './banks-delete-dialog.component';

@Component({
  selector: 'jhi-banks',
  templateUrl: './banks.component.html'
})
export class BanksComponent implements OnInit, OnDestroy {
  banks: IBanks[];
  eventSubscriber: Subscription;

  constructor(protected banksService: BanksService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.banksService.query().subscribe((res: HttpResponse<IBanks[]>) => {
      this.banks = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBanks();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBanks) {
    return item.id;
  }

  registerChangeInBanks() {
    this.eventSubscriber = this.eventManager.subscribe('banksListModification', () => this.loadAll());
  }

  delete(banks: IBanks) {
    const modalRef = this.modalService.open(BanksDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.banks = banks;
  }
}
