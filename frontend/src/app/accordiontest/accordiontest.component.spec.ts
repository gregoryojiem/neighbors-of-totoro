import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccordiontestComponent } from './accordiontest.component';

describe('AccordiontestComponent', () => {
  let component: AccordiontestComponent;
  let fixture: ComponentFixture<AccordiontestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccordiontestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccordiontestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
