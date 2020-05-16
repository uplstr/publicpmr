import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'exchange',
        loadChildren: () => import('./exchange/exchange.module').then(m => m.PublicpmrExchangeModule)
      },
      {
        path: 'rates',
        loadChildren: () => import('./rates/rates.module').then(m => m.PublicpmrRatesModule)
      },
      {
        path: 'banks',
        loadChildren: () => import('./banks/banks.module').then(m => m.PublicpmrBanksModule)
      },
      {
        path: 'curs',
        loadChildren: () => import('./curs/curs.module').then(m => m.PublicpmrCursModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PublicpmrEntityModule {}
