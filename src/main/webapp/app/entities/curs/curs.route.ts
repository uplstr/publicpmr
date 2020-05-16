import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Curs } from 'app/shared/model/curs.model';
import { CursService } from './curs.service';
import { CursComponent } from './curs.component';
import { CursDetailComponent } from './curs-detail.component';
import { CursUpdateComponent } from './curs-update.component';
import { ICurs } from 'app/shared/model/curs.model';

@Injectable({ providedIn: 'root' })
export class CursResolve implements Resolve<ICurs> {
  constructor(private service: CursService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICurs> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((curs: HttpResponse<Curs>) => curs.body));
    }
    return of(new Curs());
  }
}

export const cursRoute: Routes = [
  {
    path: '',
    component: CursComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'publicpmrApp.curs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CursDetailComponent,
    resolve: {
      curs: CursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.curs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CursUpdateComponent,
    resolve: {
      curs: CursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.curs.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CursUpdateComponent,
    resolve: {
      curs: CursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.curs.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
