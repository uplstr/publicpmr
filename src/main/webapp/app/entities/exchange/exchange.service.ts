import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExchange } from 'app/shared/model/exchange.model';

type EntityResponseType = HttpResponse<IExchange>;
type EntityArrayResponseType = HttpResponse<IExchange[]>;

@Injectable({ providedIn: 'root' })
export class ExchangeService {
  public resourceUrl = SERVER_API_URL + 'api/exchanges';

  constructor(protected http: HttpClient) {}

  create(exchange: IExchange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exchange);
    return this.http
      .post<IExchange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(exchange: IExchange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exchange);
    return this.http
      .put<IExchange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExchange>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExchange[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
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

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((exchange: IExchange) => {
        exchange.date = exchange.date != null ? moment(exchange.date) : null;
      });
    }
    return res;
  }
}
