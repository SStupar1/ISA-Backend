import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyClinicProfileComponent } from './my-clinic-profile.component';

describe('MyClinicProfileComponent', () => {
  let component: MyClinicProfileComponent;
  let fixture: ComponentFixture<MyClinicProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyClinicProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyClinicProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
