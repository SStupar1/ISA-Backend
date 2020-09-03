import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmenttypeViewComponent } from './appointmenttype-view.component';

describe('AppointmenttypeViewComponent', () => {
  let component: AppointmenttypeViewComponent;
  let fixture: ComponentFixture<AppointmenttypeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmenttypeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmenttypeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
