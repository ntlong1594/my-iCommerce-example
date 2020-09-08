import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IHistorical } from 'app/shared/model/historical/historical.model';

type EntityResponseType = HttpResponse<IHistorical>;
type EntityArrayResponseType = HttpResponse<IHistorical[]>;

@Injectable({ providedIn: 'root' })
export class HistoricalService {
  public resourceUrl = SERVER_API_URL + 'services/historical/api/historicals';
  public resourceSearchUrl = SERVER_API_URL + 'services/historical/api/_search/historicals';

  constructor(protected http: HttpClient) {}

  create(historical: IHistorical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historical);
    return this.http
      .post<IHistorical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historical: IHistorical): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historical);
    return this.http
      .put<IHistorical>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistorical>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistorical[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistorical[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(historical: IHistorical): IHistorical {
    const copy: IHistorical = Object.assign({}, historical, {
      actionDate: historical.actionDate && historical.actionDate.isValid() ? historical.actionDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.actionDate = res.body.actionDate ? moment(res.body.actionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historical: IHistorical) => {
        historical.actionDate = historical.actionDate ? moment(historical.actionDate) : undefined;
      });
    }
    return res;
  }
}
