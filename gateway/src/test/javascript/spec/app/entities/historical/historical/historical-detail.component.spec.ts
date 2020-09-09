import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { HistoricalDetailComponent } from 'app/entities/userActivitiesHistorical/userActivitiesHistorical/userActivitiesHistorical-detail.component';
import { Historical } from 'app/shared/model/userActivitiesHistorical/userActivitiesHistorical.model';

describe('Component Tests', () => {
  describe('Historical Management Detail Component', () => {
    let comp: HistoricalDetailComponent;
    let fixture: ComponentFixture<HistoricalDetailComponent>;
    const route = ({ data: of({ userActivitiesHistorical: new Historical(123) }) } as any) as ActivatedRoute;

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
      it('Should load userActivitiesHistorical on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userActivitiesHistorical).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
