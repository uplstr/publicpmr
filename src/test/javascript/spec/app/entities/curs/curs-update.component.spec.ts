import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { CursUpdateComponent } from 'app/entities/curs/curs-update.component';
import { CursService } from 'app/entities/curs/curs.service';
import { Curs } from 'app/shared/model/curs.model';

describe('Component Tests', () => {
  describe('Curs Management Update Component', () => {
    let comp: CursUpdateComponent;
    let fixture: ComponentFixture<CursUpdateComponent>;
    let service: CursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [CursUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Curs(123);
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
        const entity = new Curs();
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
