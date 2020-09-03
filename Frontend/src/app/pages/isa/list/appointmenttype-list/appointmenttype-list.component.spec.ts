import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmenttypeListComponent } from './appointmenttype-list.component';

describe('AppointmenttypeListComponent', () => {
  let component: AppointmenttypeListComponent;
  let fixture: ComponentFixture<AppointmenttypeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmenttypeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmenttypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
