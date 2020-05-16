import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IExchange, Exchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';
import { IRates } from 'app/shared/model/rates.model';
import { RatesService } from 'app/entities/rates/rates.service';

@Component({
  selector: 'jhi-exchange-update',
  templateUrl: './exchange-update.component.html'
})
export class ExchangeUpdateComponent implements OnInit {
  isSaving: boolean;

  rates: IRates[];

  editForm = this.fb.group({
    id: [],
    date: [null, [Validators.required]],
    rates: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected exchangeService: ExchangeService,
    protected ratesService: RatesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ exchange }) => {
      this.updateForm(exchange);
    });
    this.ratesService
      .query()
      .subscribe((res: HttpResponse<IRates[]>) => (this.rates = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(exchange: IExchange) {
    this.editForm.patchValue({
      id: exchange.id,
      date: exchange.date != null ? exchange.date.format(DATE_TIME_FORMAT) : null,
      rates: exchange.rates
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const exchange = this.createFromForm();
    if (exchange.id !== undefined) {
      this.subscribeToSaveResponse(this.exchangeService.update(exchange));
    } else {
      this.subscribeToSaveResponse(this.exchangeService.create(exchange));
    }
  }

  private createFromForm(): IExchange {
    return {
      ...new Exchange(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      rates: this.editForm.get(['rates']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExchange>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRatesById(index: number, item: IRates) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
