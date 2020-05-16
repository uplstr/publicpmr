import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExchangeCrudComponent } from 'app/modules/exchange-crud/exchange-crud/exchange-crud.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

// Маршруты
const routes: Routes = [
  {
    path: '',
    component: ExchangeCrudComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ExchangeCrud'
    },
    canActivate: [UserRouteAccessService]
  }
];

// Модуль маршрутизации
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExchangeCrudRoutingModule {}
