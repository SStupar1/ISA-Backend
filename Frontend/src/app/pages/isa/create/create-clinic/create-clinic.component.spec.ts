import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateClinicComponent } from './create-clinic.component';

describe('CreateClinicComponent', () => {
  let component: CreateClinicComponent;
  let fixture: ComponentFixture<CreateClinicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateClinicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateClinicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
