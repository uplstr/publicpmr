import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Taxi } from 'app/shared/model/taxi.model';
import { TaxiService } from './taxi.service';
import { TaxiComponent } from './taxi.component';
import { TaxiDetailComponent } from './taxi-detail.component';
import { TaxiUpdateComponent } from './taxi-update.component';
import { ITaxi } from 'app/shared/model/taxi.model';

@Injectable({ providedIn: 'root' })
export class TaxiResolve implements Resolve<ITaxi> {
  constructor(private service: TaxiService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaxi> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((taxi: HttpResponse<Taxi>) => taxi.body));
    }
    return of(new Taxi());
  }
}

export const taxiRoute: Routes = [
  {
    path: '',
    component: TaxiComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.taxi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TaxiDetailComponent,
    resolve: {
      taxi: TaxiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.taxi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TaxiUpdateComponent,
    resolve: {
      taxi: TaxiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.taxi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TaxiUpdateComponent,
    resolve: {
      taxi: TaxiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'publicpmrApp.taxi.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
