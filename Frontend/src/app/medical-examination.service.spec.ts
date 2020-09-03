import { TestBed } from '@angular/core/testing';

import { MedicalExaminationService } from './medical-examination.service';

describe('MedicalExaminationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MedicalExaminationService = TestBed.get(MedicalExaminationService);
    expect(service).toBeTruthy();
  });
});
