import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBanks, Banks } from 'app/shared/model/banks.model';
import { BanksService } from './banks.service';

@Component({
  selector: 'jhi-banks-update',
  templateUrl: './banks-update.component.html'
})
export class BanksUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    systemName: [null, [Validators.required]],
    status: [null, [Validators.required]]
  });

  constructor(protected banksService: BanksService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ banks }) => {
      this.updateForm(banks);
    });
  }

  updateForm(banks: IBanks) {
    this.editForm.patchValue({
      id: banks.id,
      title: banks.title,
      systemName: banks.systemName,
      status: banks.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const banks = this.createFromForm();
    if (banks.id !== undefined) {
      this.subscribeToSaveResponse(this.banksService.update(banks));
    } else {
      this.subscribeToSaveResponse(this.banksService.create(banks));
    }
  }

  private createFromForm(): IBanks {
    return {
      ...new Banks(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      systemName: this.editForm.get(['systemName']).value,
      status: this.editForm.get(['status']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanks>>) {
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
