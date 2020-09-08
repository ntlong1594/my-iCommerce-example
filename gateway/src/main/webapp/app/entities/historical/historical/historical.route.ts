import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHistorical, Historical } from 'app/shared/model/historical/historical.model';
import { HistoricalService } from './historical.service';
import { HistoricalComponent } from './historical.component';
import { HistoricalDetailComponent } from './historical-detail.component';
import { HistoricalUpdateComponent } from './historical-update.component';

@Injectable({ providedIn: 'root' })
export class HistoricalResolve implements Resolve<IHistorical> {
  constructor(private service: HistoricalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistorical> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((historical: HttpResponse<Historical>) => {
          if (historical.body) {
            return of(historical.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Historical());
  }
}

export const historicalRoute: Routes = [
  {
    path: '',
    component: HistoricalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.historicalHistorical.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoricalDetailComponent,
    resolve: {
      historical: HistoricalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.historicalHistorical.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoricalUpdateComponent,
    resolve: {
      historical: HistoricalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.historicalHistorical.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoricalUpdateComponent,
    resolve: {
      historical: HistoricalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gatewayApp.historicalHistorical.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
