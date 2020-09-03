import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseClinicComponent } from './choose-clinic.component';

describe('ChooseClinicComponent', () => {
  let component: ChooseClinicComponent;
  let fixture: ComponentFixture<ChooseClinicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseClinicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseClinicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
