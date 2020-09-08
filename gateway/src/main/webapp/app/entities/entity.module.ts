import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: () => import('./product/product/product.module').then(m => m.ProductProductModule),
      },
      {
        path: 'historical',
        loadChildren: () => import('./historical/historical/historical.module').then(m => m.HistoricalHistoricalModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GatewayEntityModule {}
