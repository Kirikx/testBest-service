import { TestBed } from '@angular/core/testing';

import { QuestionService } from './question.service';

describe('QuestionFullService', () => {
  let service: QuestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
