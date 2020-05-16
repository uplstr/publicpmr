import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PublicpmrTestModule } from '../../../test.module';
import { RatesDeleteDialogComponent } from 'app/entities/rates/rates-delete-dialog.component';
import { RatesService } from 'app/entities/rates/rates.service';

describe('Component Tests', () => {
  describe('Rates Management Delete Component', () => {
    let comp: RatesDeleteDialogComponent;
    let fixture: ComponentFixture<RatesDeleteDialogComponent>;
    let service: RatesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [RatesDeleteDialogComponent]
      })
        .overrideTemplate(RatesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RatesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RatesService);
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
