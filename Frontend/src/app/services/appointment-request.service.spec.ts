import { TestBed } from '@angular/core/testing';

import { AppointmentRequestService } from './appointment-request.service';

describe('AppointmentRequestService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppointmentRequestService = TestBed.get(AppointmentRequestService);
    expect(service).toBeTruthy();
  });
});
