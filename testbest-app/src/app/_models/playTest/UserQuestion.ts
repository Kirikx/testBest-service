import {Answer} from "../Answer";

export class UserQuestion {
  id: string = null;
  questionId: string = null;
  userTestId: string = null;
  isCorrect: boolean = false;
  freeAnswer: string = null;
  answered: string = null;
  answers: Array<Answer> = null;
}
