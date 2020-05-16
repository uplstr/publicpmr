import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublicpmrSharedModule } from 'app/shared/shared.module';
import { BanksComponent } from './banks.component';
import { BanksDetailComponent } from './banks-detail.component';
import { BanksUpdateComponent } from './banks-update.component';
import { BanksDeleteDialogComponent } from './banks-delete-dialog.component';
import { banksRoute } from './banks.route';

@NgModule({
  imports: [PublicpmrSharedModule, RouterModule.forChild(banksRoute)],
  declarations: [BanksComponent, BanksDetailComponent, BanksUpdateComponent, BanksDeleteDialogComponent],
  entryComponents: [BanksDeleteDialogComponent]
})
export class PublicpmrBanksModule {}
