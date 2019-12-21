import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITaxi, Taxi } from 'app/shared/model/taxi.model';
import { TaxiService } from './taxi.service';

@Component({
  selector: 'jhi-taxi-update',
  templateUrl: './taxi-update.component.html'
})
export class TaxiUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(2)]],
    phone: [null, [Validators.required]],
    active: [null, [Validators.required]]
  });

  constructor(protected taxiService: TaxiService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ taxi }) => {
      this.updateForm(taxi);
    });
  }

  updateForm(taxi: ITaxi) {
    this.editForm.patchValue({
      id: taxi.id,
      title: taxi.title,
      phone: taxi.phone,
      active: taxi.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const taxi = this.createFromForm();
    if (taxi.id !== undefined) {
      this.subscribeToSaveResponse(this.taxiService.update(taxi));
    } else {
      this.subscribeToSaveResponse(this.taxiService.create(taxi));
    }
  }

  private createFromForm(): ITaxi {
    return {
      ...new Taxi(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      phone: this.editForm.get(['phone']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxi>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
