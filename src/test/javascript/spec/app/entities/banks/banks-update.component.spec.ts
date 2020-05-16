import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { BanksUpdateComponent } from 'app/entities/banks/banks-update.component';
import { BanksService } from 'app/entities/banks/banks.service';
import { Banks } from 'app/shared/model/banks.model';

describe('Component Tests', () => {
  describe('Banks Management Update Component', () => {
    let comp: BanksUpdateComponent;
    let fixture: ComponentFixture<BanksUpdateComponent>;
    let service: BanksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [BanksUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BanksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BanksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BanksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Banks(123);
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
        const entity = new Banks();
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
