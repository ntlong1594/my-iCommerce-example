import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistorical } from 'app/shared/model/historical/historical.model';

@Component({
  selector: 'jhi-historical-detail',
  templateUrl: './historical-detail.component.html',
})
export class HistoricalDetailComponent implements OnInit {
  historical: IHistorical | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historical }) => (this.historical = historical));
  }

  previousState(): void {
    window.history.back();
  }
}
