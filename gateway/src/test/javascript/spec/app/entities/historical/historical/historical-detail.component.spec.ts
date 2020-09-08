import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { HistoricalDetailComponent } from 'app/entities/historical/historical/historical-detail.component';
import { Historical } from 'app/shared/model/historical/historical.model';

describe('Component Tests', () => {
  describe('Historical Management Detail Component', () => {
    let comp: HistoricalDetailComponent;
    let fixture: ComponentFixture<HistoricalDetailComponent>;
    const route = ({ data: of({ historical: new Historical(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [HistoricalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HistoricalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoricalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load historical on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historical).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
