import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { ExchangeUpdateComponent } from 'app/entities/exchange/exchange-update.component';
import { ExchangeService } from 'app/entities/exchange/exchange.service';
import { Exchange } from 'app/shared/model/exchange.model';

describe('Component Tests', () => {
  describe('Exchange Management Update Component', () => {
    let comp: ExchangeUpdateComponent;
    let fixture: ComponentFixture<ExchangeUpdateComponent>;
    let service: ExchangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [ExchangeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExchangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExchangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExchangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Exchange(123);
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
        const entity = new Exchange();
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
