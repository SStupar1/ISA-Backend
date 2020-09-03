import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAppointmenttypeComponent } from './create-appointmenttype.component';

describe('CreateAppointmenttypeComponent', () => {
  let component: CreateAppointmenttypeComponent;
  let fixture: ComponentFixture<CreateAppointmenttypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAppointmenttypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAppointmenttypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
