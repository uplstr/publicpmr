import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PublicpmrTestModule } from '../../../test.module';
import { BanksComponent } from 'app/entities/banks/banks.component';
import { BanksService } from 'app/entities/banks/banks.service';
import { Banks } from 'app/shared/model/banks.model';

describe('Component Tests', () => {
  describe('Banks Management Component', () => {
    let comp: BanksComponent;
    let fixture: ComponentFixture<BanksComponent>;
    let service: BanksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [BanksComponent],
        providers: []
      })
        .overrideTemplate(BanksComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BanksComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BanksService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Banks(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.banks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
