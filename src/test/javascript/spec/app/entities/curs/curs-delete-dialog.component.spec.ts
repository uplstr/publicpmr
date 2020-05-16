import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PublicpmrTestModule } from '../../../test.module';
import { CursDeleteDialogComponent } from 'app/entities/curs/curs-delete-dialog.component';
import { CursService } from 'app/entities/curs/curs.service';

describe('Component Tests', () => {
  describe('Curs Management Delete Component', () => {
    let comp: CursDeleteDialogComponent;
    let fixture: ComponentFixture<CursDeleteDialogComponent>;
    let service: CursService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [CursDeleteDialogComponent]
      })
        .overrideTemplate(CursDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CursDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CursService);
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
