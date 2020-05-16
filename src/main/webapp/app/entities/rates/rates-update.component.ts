import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IRates, Rates } from 'app/shared/model/rates.model';
import { RatesService } from './rates.service';
import { IBanks } from 'app/shared/model/banks.model';
import { BanksService } from 'app/entities/banks/banks.service';
import { ICurs } from 'app/shared/model/curs.model';
import { CursService } from 'app/entities/curs/curs.service';
import { IExchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from 'app/entities/exchange/exchange.service';

@Component({
  selector: 'jhi-rates-update',
  templateUrl: './rates-update.component.html'
})
export class RatesUpdateComponent implements OnInit {
  isSaving: boolean;

  banks: IBanks[];

  curs: ICurs[];

  exchanges: IExchange[];

  editForm = this.fb.group({
    id: [],
    bankSystemName: [null, [Validators.required]],
    bankId: [],
    curs: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ratesService: RatesService,
    protected banksService: BanksService,
    protected cursService: CursService,
    protected exchangeService: ExchangeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rates }) => {
      this.updateForm(rates);
    });
    this.banksService
      .query()
      .subscribe((res: HttpResponse<IBanks[]>) => (this.banks = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.cursService
      .query()
      .subscribe((res: HttpResponse<ICurs[]>) => (this.curs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.exchangeService
      .query()
      .subscribe((res: HttpResponse<IExchange[]>) => (this.exchanges = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(rates: IRates) {
    this.editForm.patchValue({
      id: rates.id,
      bankSystemName: rates.bankSystemName,
      bankId: rates.bankId,
      curs: rates.curs
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rates = this.createFromForm();
    if (rates.id !== undefined) {
      this.subscribeToSaveResponse(this.ratesService.update(rates));
    } else {
      this.subscribeToSaveResponse(this.ratesService.create(rates));
    }
  }

  private createFromForm(): IRates {
    return {
      ...new Rates(),
      id: this.editForm.get(['id']).value,
      bankSystemName: this.editForm.get(['bankSystemName']).value,
      bankId: this.editForm.get(['bankId']).value,
      curs: this.editForm.get(['curs']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRates>>) {
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

  trackBanksById(index: number, item: IBanks) {
    return item.id;
  }

  trackCursById(index: number, item: ICurs) {
    return item.id;
  }

  trackExchangeById(index: number, item: IExchange) {
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
