import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExchangeCrudComponent } from './exchange-crud/exchange-crud.component';
import { ExchangeCrudRoutingModule } from 'app/modules/exchange-crud/exchange-crud-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { ExchangeCrudService } from 'app/modules/exchange-crud/exchange-crud.service';

@NgModule({
  declarations: [ExchangeCrudComponent],
  imports: [CommonModule, ExchangeCrudRoutingModule],
  providers: [ExchangeCrudService]
})
export class ExchangeCrudModule {}
