import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDoctorsListComponent } from './my-doctors-list.component';

describe('MyDoctorsListComponent', () => {
  let component: MyDoctorsListComponent;
  let fixture: ComponentFixture<MyDoctorsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyDoctorsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDoctorsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
