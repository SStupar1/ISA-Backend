import { TestBed } from '@angular/core/testing';

import { ErService } from './er.service';

describe('ErService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ErService = TestBed.get(ErService);
    expect(service).toBeTruthy();
  });
});
