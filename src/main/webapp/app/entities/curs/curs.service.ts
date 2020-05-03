import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICurs } from 'app/shared/model/curs.model';

type EntityResponseType = HttpResponse<ICurs>;
type EntityArrayResponseType = HttpResponse<ICurs[]>;

@Injectable({ providedIn: 'root' })
export class CursService {
  public resourceUrl = SERVER_API_URL + 'api/curs';

  constructor(protected http: HttpClient) {}

  create(curs: ICurs): Observable<EntityResponseType> {
    return this.http.post<ICurs>(this.resourceUrl, curs, { observe: 'response' });
  }

  update(curs: ICurs): Observable<EntityResponseType> {
    return this.http.put<ICurs>(this.resourceUrl, curs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
