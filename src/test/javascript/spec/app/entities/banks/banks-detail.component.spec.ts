import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { BanksDetailComponent } from 'app/entities/banks/banks-detail.component';
import { Banks } from 'app/shared/model/banks.model';

describe('Component Tests', () => {
  describe('Banks Management Detail Component', () => {
    let comp: BanksDetailComponent;
    let fixture: ComponentFixture<BanksDetailComponent>;
    const route = ({ data: of({ banks: new Banks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [BanksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BanksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BanksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.banks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
