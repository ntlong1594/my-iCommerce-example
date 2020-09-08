import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHistorical, Historical } from 'app/shared/model/historical/historical.model';
import { HistoricalService } from './historical.service';

@Component({
  selector: 'jhi-historical-update',
  templateUrl: './historical-update.component.html',
})
export class HistoricalUpdateComponent implements OnInit {
  isSaving = false;
  actionDateDp: any;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    correlationId: [null, [Validators.required]],
    actionId: [null, [Validators.required]],
    actionDescription: [],
    actionDate: [null, [Validators.required]],
  });

  constructor(protected historicalService: HistoricalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historical }) => {
      this.updateForm(historical);
    });
  }

  updateForm(historical: IHistorical): void {
    this.editForm.patchValue({
      id: historical.id,
      userId: historical.userId,
      correlationId: historical.correlationId,
      actionId: historical.actionId,
      actionDescription: historical.actionDescription,
      actionDate: historical.actionDate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historical = this.createFromForm();
    if (historical.id !== undefined) {
      this.subscribeToSaveResponse(this.historicalService.update(historical));
    } else {
      this.subscribeToSaveResponse(this.historicalService.create(historical));
    }
  }

  private createFromForm(): IHistorical {
    return {
      ...new Historical(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      correlationId: this.editForm.get(['correlationId'])!.value,
      actionId: this.editForm.get(['actionId'])!.value,
      actionDescription: this.editForm.get(['actionDescription'])!.value,
      actionDate: this.editForm.get(['actionDate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistorical>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
