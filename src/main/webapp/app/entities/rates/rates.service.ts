import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRates } from 'app/shared/model/rates.model';

type EntityResponseType = HttpResponse<IRates>;
type EntityArrayResponseType = HttpResponse<IRates[]>;

@Injectable({ providedIn: 'root' })
export class RatesService {
  public resourceUrl = SERVER_API_URL + 'api/rates';

  constructor(protected http: HttpClient) {}

  create(rates: IRates): Observable<EntityResponseType> {
    console.log(rates);
    return this.http.post<IRates>(this.resourceUrl, rates, { observe: 'response' });
  }

  update(rates: IRates): Observable<EntityResponseType> {
    return this.http.put<IRates>(this.resourceUrl, rates, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRates[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
