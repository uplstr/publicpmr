import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PublicpmrSharedModule } from 'app/shared/shared.module';
import { ExchangeComponent } from './exchange.component';
import { ExchangeDetailComponent } from './exchange-detail.component';
import { ExchangeUpdateComponent } from './exchange-update.component';
import { ExchangeDeleteDialogComponent } from './exchange-delete-dialog.component';
import { exchangeRoute } from './exchange.route';

@NgModule({
  imports: [PublicpmrSharedModule, RouterModule.forChild(exchangeRoute)],
  declarations: [ExchangeComponent, ExchangeDetailComponent, ExchangeUpdateComponent, ExchangeDeleteDialogComponent],
  entryComponents: [ExchangeDeleteDialogComponent]
})
export class PublicpmrExchangeModule {}
