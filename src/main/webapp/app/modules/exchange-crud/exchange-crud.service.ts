import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { IExchange } from 'app/shared/model/exchange.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<IExchange>;
type EntityArrayResponseType = HttpResponse<IExchange[]>;

@Injectable({
  providedIn: 'root'
})
export class ExchangeCrudService {
  public resourceUrl = SERVER_API_URL + 'api/exchanges';

  constructor(protected http: HttpClient) {}

  create(exchange: IExchange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exchange);
    return this.http
      .post<IExchange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  protected convertDateFromClient(exchange: IExchange): IExchange {
    const copy: IExchange = Object.assign({}, exchange, {
      date: exchange.date != null && exchange.date.isValid() ? exchange.date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }
}
