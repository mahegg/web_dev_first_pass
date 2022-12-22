import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassDisplayComponent } from './pass-display.component';

describe('PassDisplayComponent', () => {
  let component: PassDisplayComponent;
  let fixture: ComponentFixture<PassDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PassDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PassDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
