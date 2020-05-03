import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Banks } from 'app/shared/model/banks.model';
import { BanksService } from './banks.service';
import { BanksComponent } from './banks.component';
import { BanksDetailComponent } from './banks-detail.component';
import { BanksUpdateComponent } from './banks-update.component';
import { IBanks } from 'app/shared/model/banks.model';

@Injectable({ providedIn: 'root' })
export class BanksResolve implements Resolve<IBanks> {
  constructor(private service: BanksService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBanks> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((banks: HttpResponse<Banks>) => banks.body));
    }
    return of(new Banks());
  }
}

export const banksRoute: Routes = [
  {
    path: '',
    component: BanksComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.banks.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BanksDetailComponent,
    resolve: {
      banks: BanksResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.banks.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BanksUpdateComponent,
    resolve: {
      banks: BanksResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.banks.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BanksUpdateComponent,
    resolve: {
      banks: BanksResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.banks.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
