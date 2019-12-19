import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateOdometerComponent } from './update-odometer.component';

describe('UpdateOdometerComponent', () => {
  let component: UpdateOdometerComponent;
  let fixture: ComponentFixture<UpdateOdometerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateOdometerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateOdometerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
