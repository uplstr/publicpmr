import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { TaxiUpdateComponent } from 'app/entities/taxi/taxi-update.component';
import { TaxiService } from 'app/entities/taxi/taxi.service';
import { Taxi } from 'app/shared/model/taxi.model';

describe('Component Tests', () => {
  describe('Taxi Management Update Component', () => {
    let comp: TaxiUpdateComponent;
    let fixture: ComponentFixture<TaxiUpdateComponent>;
    let service: TaxiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [TaxiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TaxiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Taxi(123);
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
        const entity = new Taxi();
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
