import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { CartComponent } from 'app/entities/product/cart/cart.component';
import { CartService } from 'app/entities/product/cart/cart.service';
import { Cart } from 'app/shared/model/product/cart.model';

describe('Component Tests', () => {
  describe('Cart Management Component', () => {
    let comp: CartComponent;
    let fixture: ComponentFixture<CartComponent>;
    let service: CartService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [CartComponent],
      })
        .overrideTemplate(CartComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Cart(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.carts && comp.carts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
