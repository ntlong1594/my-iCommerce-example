import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistorical } from 'app/shared/model/historical/historical.model';
import { HistoricalService } from './historical.service';

@Component({
  templateUrl: './historical-delete-dialog.component.html',
})
export class HistoricalDeleteDialogComponent {
  historical?: IHistorical;

  constructor(
    protected historicalService: HistoricalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historicalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('historicalListModification');
      this.activeModal.close();
    });
  }
}
