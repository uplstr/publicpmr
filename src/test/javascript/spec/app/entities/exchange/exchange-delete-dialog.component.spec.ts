import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PublicpmrTestModule } from '../../../test.module';
import { ExchangeDeleteDialogComponent } from 'app/entities/exchange/exchange-delete-dialog.component';
import { ExchangeService } from 'app/entities/exchange/exchange.service';

describe('Component Tests', () => {
  describe('Exchange Management Delete Component', () => {
    let comp: ExchangeDeleteDialogComponent;
    let fixture: ComponentFixture<ExchangeDeleteDialogComponent>;
    let service: ExchangeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [ExchangeDeleteDialogComponent]
      })
        .overrideTemplate(ExchangeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExchangeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExchangeService);
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
