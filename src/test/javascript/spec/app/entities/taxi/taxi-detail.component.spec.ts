import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { TaxiDetailComponent } from 'app/entities/taxi/taxi-detail.component';
import { Taxi } from 'app/shared/model/taxi.model';

describe('Component Tests', () => {
  describe('Taxi Management Detail Component', () => {
    let comp: TaxiDetailComponent;
    let fixture: ComponentFixture<TaxiDetailComponent>;
    const route = ({ data: of({ taxi: new Taxi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [TaxiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TaxiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxi).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
