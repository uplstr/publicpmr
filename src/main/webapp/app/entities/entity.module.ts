import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'taxi',
        loadChildren: () => import('./taxi/taxi.module').then(m => m.PublicpmrTaxiModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PublicpmrEntityModule {}
