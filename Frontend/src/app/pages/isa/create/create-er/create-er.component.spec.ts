import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateErComponent } from './create-er.component';

describe('CreateErComponent', () => {
  let component: CreateErComponent;
  let fixture: ComponentFixture<CreateErComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateErComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateErComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
