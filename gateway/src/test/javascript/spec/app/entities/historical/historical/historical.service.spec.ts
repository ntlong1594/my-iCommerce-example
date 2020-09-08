import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HistoricalService } from 'app/entities/historical/historical/historical.service';
import { IHistorical, Historical } from 'app/shared/model/historical/historical.model';

describe('Service Tests', () => {
  describe('Historical Service', () => {
    let injector: TestBed;
    let service: HistoricalService;
    let httpMock: HttpTestingController;
    let elemDefault: IHistorical;
    let expectedResult: IHistorical | IHistorical[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HistoricalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Historical(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            actionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Historical', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            actionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            actionDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Historical()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Historical', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            correlationId: 'BBBBBB',
            actionId: 'BBBBBB',
            actionDescription: 'BBBBBB',
            actionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            actionDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Historical', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            correlationId: 'BBBBBB',
            actionId: 'BBBBBB',
            actionDescription: 'BBBBBB',
            actionDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            actionDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Historical', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
