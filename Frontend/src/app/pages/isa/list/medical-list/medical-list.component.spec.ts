import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalListComponent } from './medical-list.component';

describe('MedicalListComponent', () => {
  let component: MedicalListComponent;
  let fixture: ComponentFixture<MedicalListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicalListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicalListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
