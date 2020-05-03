import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBanks } from 'app/shared/model/banks.model';

type EntityResponseType = HttpResponse<IBanks>;
type EntityArrayResponseType = HttpResponse<IBanks[]>;

@Injectable({ providedIn: 'root' })
export class BanksService {
  public resourceUrl = SERVER_API_URL + 'api/banks';

  constructor(protected http: HttpClient) {}

  create(banks: IBanks): Observable<EntityResponseType> {
    return this.http.post<IBanks>(this.resourceUrl, banks, { observe: 'response' });
  }

  update(banks: IBanks): Observable<EntityResponseType> {
    return this.http.put<IBanks>(this.resourceUrl, banks, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBanks>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBanks[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
