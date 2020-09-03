import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingRecipesComponent } from './pending-recipes.component';

describe('PendingRecipesComponent', () => {
  let component: PendingRecipesComponent;
  let fixture: ComponentFixture<PendingRecipesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PendingRecipesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingRecipesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
