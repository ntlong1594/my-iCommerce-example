import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { HistoricalComponent } from './historical.component';
import { HistoricalDetailComponent } from './historical-detail.component';
import { HistoricalUpdateComponent } from './historical-update.component';
import { HistoricalDeleteDialogComponent } from './historical-delete-dialog.component';
import { historicalRoute } from './historical.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(historicalRoute)],
  declarations: [HistoricalComponent, HistoricalDetailComponent, HistoricalUpdateComponent, HistoricalDeleteDialogComponent],
  entryComponents: [HistoricalDeleteDialogComponent],
})
export class HistoricalHistoricalModule {}
