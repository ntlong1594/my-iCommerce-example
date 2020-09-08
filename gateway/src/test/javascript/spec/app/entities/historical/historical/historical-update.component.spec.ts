import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { HistoricalUpdateComponent } from 'app/entities/historical/historical/historical-update.component';
import { HistoricalService } from 'app/entities/historical/historical/historical.service';
import { Historical } from 'app/shared/model/historical/historical.model';

describe('Component Tests', () => {
  describe('Historical Management Update Component', () => {
    let comp: HistoricalUpdateComponent;
    let fixture: ComponentFixture<HistoricalUpdateComponent>;
    let service: HistoricalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [HistoricalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HistoricalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoricalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoricalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Historical(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Historical();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
