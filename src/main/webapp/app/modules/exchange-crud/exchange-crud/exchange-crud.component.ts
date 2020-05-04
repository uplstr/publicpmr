import { Component, OnInit } from '@angular/core';
import { ExchangeCrudService } from 'app/modules/exchange-crud/exchange-crud.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IExchange } from 'app/shared/model/exchange.model';
import * as moment from 'moment';
import { BankName } from 'app/shared/model/enumerations/bank-name.model';
import { CurrencyName } from 'app/shared/model/enumerations/currency-name.model';
import { ExchangeService } from 'app/entities/exchange/exchange.service';
import { CursService } from 'app/entities/curs/curs.service';
import { IRates } from 'app/shared/model/rates.model';
import { ICurs } from 'app/shared/model/curs.model';
import { RatesService } from 'app/entities/rates/rates.service';
import { pipe } from 'rxjs';
import { finalize } from 'rxjs/operators';

const ngHtmlParser = require('angular-html-parser');

type EntityResponseType = HttpResponse<IExchange>;

@Component({
  selector: 'jhi-exchange-crud',
  templateUrl: './exchange-crud.component.html',
  styleUrls: ['./exchange-crud.component.scss']
})
export class ExchangeCrudComponent implements OnInit {
  private res: Object;

  private BANKS: BankName[] = [BankName.PRB, BankName.AGR, BankName.EXIM, BankName.SBR];
  private CURRENCY: CurrencyName[] = [CurrencyName.USD, CurrencyName.EUR, CurrencyName.RUB, CurrencyName.MDL, CurrencyName.UAH];
  private exchanges: EntityResponseType;

  constructor(
    private http: HttpClient,
    private exchangeService: ExchangeService,
    private cursService: CursService,
    private ratesService: RatesService
  ) {}

  ngOnInit() {
    this.init();
  }

  private init() {
    fetch('https://thingproxy.freeboard.io/fetch/https://rubpmr.ru')
      .then(response => {
        return response.text();
      })
      .then(data => {
        const { rootNodes, errors } = ngHtmlParser.parse(data.slice(data.search('<tbody>') + 7, -(data.length - data.search('</tbody>'))));
        return rootNodes;
      })
      .then(data => data.map(item => item.children))
      .then(data => {
        data.map((item, index) => (item ? item : data.splice(index, 1)));
        return data;
      })
      .then(data => {
        data.splice(0, 1);
        data.splice(0, 1);
        data.map((item, index) => {
          item.map((child, itemIndex) => {
            if (!(child && child.children && child.children[0] && Number(child.children[0].value) && Number(child.children[0].value) > 0)) {
              data[index].splice(itemIndex, 1);
            }
          });
        });
        return data;
      })
      .then(data => this.fillForm(data))
      .then(data => {
        this.exchangeService.query(moment()).subscribe(query => {
          console.log(query);
          if (query.body.length === 0) {
            this.createExchange(data);
          }
        });
      })
      .then(() => this.getByDate());
  }

  private fillForm(data) {
    const exchange: IExchange = {
      date: moment(),
      rates: []
    };
    const date = moment();

    exchange.date = date;

    this.BANKS.map((bank, index) => {
      exchange.rates.push({
        bankId: index,
        bankSystemName: bank,
        curs: []
      });
      if (data[index].length < 11) {
        data[index].map(curs => {
          if (curs.children[0].value && Number(curs.children[0].value.trim()) > 0) {
            exchange.rates[index].curs.push({
              sale: Number(curs.children[0].value),
              purchase: Number(curs.children[0].value)
            });
          }
        });
        this.CURRENCY.map((curr, currIndex) => {
          exchange.rates[index].curs[currIndex].currency = curr;
        });
      } else {
        let cursI = 1;
        this.CURRENCY.map((curr, currIndex) => {
          if (data[index][cursI].children[0].value && Number(data[index][cursI].children[0].value.trim()) > 0) {
            exchange.rates[index].curs.push({
              currency: curr,
              sale: Number(data[index][cursI].children[0].value),
              purchase: Number(data[index][cursI + 1].children[0].value)
            });
            cursI = cursI + 2;
          }
        });
      }
    });

    this.res = exchange;
    return this.res;
  }

  private createExchange(data) {
    const exchanges: IExchange = {
      date: moment(),
      rates: []
    };

    const rates: IRates[] = [];

    data.rates.map(ratesData => {
      console.log(1);

      const rate: IRates = {
        curs: [],
        bankSystemName: ratesData.bankSystemName,
        bankId: ratesData.bankId + 1
      };

      ratesData.curs.map(cursData => {
        console.log(2);
        this.cursService.create(cursData).subscribe(cursResponse => {
          rate.curs.push(cursResponse.body);
        });
      });

      setTimeout(() => {
        console.log(rate);
        this.ratesService.create(rate).subscribe(rateResponse => rates.push(rateResponse.body));
      }, 0);
    });

    setTimeout(() => {
      console.log(rates);

      exchanges.rates = rates;

      this.exchangeService.create(exchanges).subscribe(() => {});

      console.log(exchanges);
    }, 5000);
  }

  private getByDate() {
    this.exchangeService.query(moment()).subscribe(query => {
      console.log(query);
    });
  }
}
