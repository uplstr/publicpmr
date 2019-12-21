import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublicpmrSharedModule } from 'app/shared/shared.module';
import { TaxiComponent } from './taxi.component';
import { TaxiDetailComponent } from './taxi-detail.component';
import { TaxiUpdateComponent } from './taxi-update.component';
import { TaxiDeleteDialogComponent } from './taxi-delete-dialog.component';
import { taxiRoute } from './taxi.route';

@NgModule({
  imports: [PublicpmrSharedModule, RouterModule.forChild(taxiRoute)],
  declarations: [TaxiComponent, TaxiDetailComponent, TaxiUpdateComponent, TaxiDeleteDialogComponent],
  entryComponents: [TaxiDeleteDialogComponent]
})
export class PublicpmrTaxiModule {}
