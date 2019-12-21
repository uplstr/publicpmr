import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PublicpmrTestModule } from '../../../test.module';
import { TaxiDeleteDialogComponent } from 'app/entities/taxi/taxi-delete-dialog.component';
import { TaxiService } from 'app/entities/taxi/taxi.service';

describe('Component Tests', () => {
  describe('Taxi Management Delete Component', () => {
    let comp: TaxiDeleteDialogComponent;
    let fixture: ComponentFixture<TaxiDeleteDialogComponent>;
    let service: TaxiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [TaxiDeleteDialogComponent]
      })
        .overrideTemplate(TaxiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxiService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
