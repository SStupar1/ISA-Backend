import { TestBed } from '@angular/core/testing';

import { AppointmentTypeService } from './appointment-type.service';

describe('AppointmentTypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppointmentTypeService = TestBed.get(AppointmentTypeService);
    expect(service).toBeTruthy();
  });
});
