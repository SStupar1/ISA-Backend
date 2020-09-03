import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMedicineAndDiagnosisComponent } from './add-medicine-and-diagnosis.component';

describe('AddMedicineAndDiagnosisComponent', () => {
  let component: AddMedicineAndDiagnosisComponent;
  let fixture: ComponentFixture<AddMedicineAndDiagnosisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddMedicineAndDiagnosisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMedicineAndDiagnosisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
