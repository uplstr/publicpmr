import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaxi } from 'app/shared/model/taxi.model';

type EntityResponseType = HttpResponse<ITaxi>;
type EntityArrayResponseType = HttpResponse<ITaxi[]>;

@Injectable({ providedIn: 'root' })
export class TaxiService {
  public resourceUrl = SERVER_API_URL + 'api/taxis';

  constructor(protected http: HttpClient) {}

  create(taxi: ITaxi): Observable<EntityResponseType> {
    return this.http.post<ITaxi>(this.resourceUrl, taxi, { observe: 'response' });
  }

  update(taxi: ITaxi): Observable<EntityResponseType> {
    return this.http.put<ITaxi>(this.resourceUrl, taxi, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
