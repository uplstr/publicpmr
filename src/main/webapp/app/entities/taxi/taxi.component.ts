import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaxi } from 'app/shared/model/taxi.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TaxiService } from './taxi.service';
import { TaxiDeleteDialogComponent } from './taxi-delete-dialog.component';

@Component({
  selector: 'jhi-taxi',
  templateUrl: './taxi.component.html'
})
export class TaxiComponent implements OnInit, OnDestroy {
  taxis: ITaxi[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected taxiService: TaxiService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.taxis = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.taxiService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITaxi[]>) => this.paginateTaxis(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.taxis = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTaxis();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITaxi) {
    return item.id;
  }

  registerChangeInTaxis() {
    this.eventSubscriber = this.eventManager.subscribe('taxiListModification', () => this.reset());
  }

  delete(taxi: ITaxi) {
    const modalRef = this.modalService.open(TaxiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxi = taxi;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTaxis(data: ITaxi[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.taxis.push(data[i]);
    }
  }
}
