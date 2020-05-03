import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublicpmrSharedModule } from 'app/shared/shared.module';
import { RatesComponent } from './rates.component';
import { RatesDetailComponent } from './rates-detail.component';
import { RatesUpdateComponent } from './rates-update.component';
import { RatesDeleteDialogComponent } from './rates-delete-dialog.component';
import { ratesRoute } from './rates.route';

@NgModule({
  imports: [PublicpmrSharedModule, RouterModule.forChild(ratesRoute)],
  declarations: [RatesComponent, RatesDetailComponent, RatesUpdateComponent, RatesDeleteDialogComponent],
  entryComponents: [RatesDeleteDialogComponent]
})
export class PublicpmrRatesModule {}
