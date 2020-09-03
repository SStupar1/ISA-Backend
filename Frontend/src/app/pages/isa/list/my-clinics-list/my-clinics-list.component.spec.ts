import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyClinicsListComponent } from './my-clinics-list.component';

describe('MyClinicsListComponent', () => {
  let component: MyClinicsListComponent;
  let fixture: ComponentFixture<MyClinicsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyClinicsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyClinicsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
