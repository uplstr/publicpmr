import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PublicpmrTestModule } from '../../../test.module';
import { BanksDeleteDialogComponent } from 'app/entities/banks/banks-delete-dialog.component';
import { BanksService } from 'app/entities/banks/banks.service';

describe('Component Tests', () => {
  describe('Banks Management Delete Component', () => {
    let comp: BanksDeleteDialogComponent;
    let fixture: ComponentFixture<BanksDeleteDialogComponent>;
    let service: BanksService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [BanksDeleteDialogComponent]
      })
        .overrideTemplate(BanksDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BanksDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BanksService);
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
