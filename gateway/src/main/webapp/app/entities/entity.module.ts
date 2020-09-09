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
        path: 'cart',
        loadChildren: () => import('./product/cart/cart.module').then(m => m.ProductCartModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./product/order/order.module').then(m => m.ProductOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GatewayEntityModule {}
