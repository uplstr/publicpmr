import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { ExchangeDetailComponent } from 'app/entities/exchange/exchange-detail.component';
import { Exchange } from 'app/shared/model/exchange.model';

describe('Component Tests', () => {
  describe('Exchange Management Detail Component', () => {
    let comp: ExchangeDetailComponent;
    let fixture: ComponentFixture<ExchangeDetailComponent>;
    const route = ({ data: of({ exchange: new Exchange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [ExchangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExchangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExchangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.exchange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
