import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { RatesUpdateComponent } from 'app/entities/rates/rates-update.component';
import { RatesService } from 'app/entities/rates/rates.service';
import { Rates } from 'app/shared/model/rates.model';

describe('Component Tests', () => {
  describe('Rates Management Update Component', () => {
    let comp: RatesUpdateComponent;
    let fixture: ComponentFixture<RatesUpdateComponent>;
    let service: RatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [RatesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RatesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RatesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RatesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Rates(123);
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
        const entity = new Rates();
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
