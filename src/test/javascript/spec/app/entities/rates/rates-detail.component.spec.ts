import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { RatesDetailComponent } from 'app/entities/rates/rates-detail.component';
import { Rates } from 'app/shared/model/rates.model';

describe('Component Tests', () => {
  describe('Rates Management Detail Component', () => {
    let comp: RatesDetailComponent;
    let fixture: ComponentFixture<RatesDetailComponent>;
    const route = ({ data: of({ rates: new Rates(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [RatesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RatesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RatesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rates).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
