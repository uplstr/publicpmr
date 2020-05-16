import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublicpmrTestModule } from '../../../test.module';
import { CursDetailComponent } from 'app/entities/curs/curs-detail.component';
import { Curs } from 'app/shared/model/curs.model';

describe('Component Tests', () => {
  describe('Curs Management Detail Component', () => {
    let comp: CursDetailComponent;
    let fixture: ComponentFixture<CursDetailComponent>;
    const route = ({ data: of({ curs: new Curs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PublicpmrTestModule],
        declarations: [CursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.curs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
