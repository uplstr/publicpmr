import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Exchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';
import { ExchangeComponent } from './exchange.component';
import { ExchangeDetailComponent } from './exchange-detail.component';
import { ExchangeUpdateComponent } from './exchange-update.component';
import { IExchange } from 'app/shared/model/exchange.model';

@Injectable({ providedIn: 'root' })
export class ExchangeResolve implements Resolve<IExchange> {
  constructor(private service: ExchangeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExchange> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((exchange: HttpResponse<Exchange>) => exchange.body));
    }
    return of(new Exchange());
  }
}

export const exchangeRoute: Routes = [
  {
    path: '',
    component: ExchangeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'publicpmrApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExchangeDetailComponent,
    resolve: {
      exchange: ExchangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExchangeUpdateComponent,
    resolve: {
      exchange: ExchangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExchangeUpdateComponent,
    resolve: {
      exchange: ExchangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.exchange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
