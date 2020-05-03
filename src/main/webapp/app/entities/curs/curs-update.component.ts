import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICurs, Curs } from 'app/shared/model/curs.model';
import { CursService } from './curs.service';
import { IRates } from 'app/shared/model/rates.model';
import { RatesService } from 'app/entities/rates/rates.service';

@Component({
  selector: 'jhi-curs-update',
  templateUrl: './curs-update.component.html'
})
export class CursUpdateComponent implements OnInit {
  isSaving: boolean;

  rates: IRates[];

  editForm = this.fb.group({
    id: [],
    purchase: [null, [Validators.required]],
    sale: [null, [Validators.required]],
    currency: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cursService: CursService,
    protected ratesService: RatesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ curs }) => {
      this.updateForm(curs);
    });
    this.ratesService
      .query()
      .subscribe((res: HttpResponse<IRates[]>) => (this.rates = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(curs: ICurs) {
    this.editForm.patchValue({
      id: curs.id,
      purchase: curs.purchase,
      sale: curs.sale,
      currency: curs.currency
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const curs = this.createFromForm();
    if (curs.id !== undefined) {
      this.subscribeToSaveResponse(this.cursService.update(curs));
    } else {
      this.subscribeToSaveResponse(this.cursService.create(curs));
    }
  }

  private createFromForm(): ICurs {
    return {
      ...new Curs(),
      id: this.editForm.get(['id']).value,
      purchase: this.editForm.get(['purchase']).value,
      sale: this.editForm.get(['sale']).value,
      currency: this.editForm.get(['currency']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurs>>) {
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
