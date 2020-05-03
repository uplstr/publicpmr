import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Rates } from 'app/shared/model/rates.model';
import { RatesService } from './rates.service';
import { RatesComponent } from './rates.component';
import { RatesDetailComponent } from './rates-detail.component';
import { RatesUpdateComponent } from './rates-update.component';
import { IRates } from 'app/shared/model/rates.model';

@Injectable({ providedIn: 'root' })
export class RatesResolve implements Resolve<IRates> {
  constructor(private service: RatesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRates> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((rates: HttpResponse<Rates>) => rates.body));
    }
    return of(new Rates());
  }
}

export const ratesRoute: Routes = [
  {
    path: '',
    component: RatesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'publicpmrApp.rates.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RatesDetailComponent,
    resolve: {
      rates: RatesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.rates.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RatesUpdateComponent,
    resolve: {
      rates: RatesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.rates.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RatesUpdateComponent,
    resolve: {
      rates: RatesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.rates.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
