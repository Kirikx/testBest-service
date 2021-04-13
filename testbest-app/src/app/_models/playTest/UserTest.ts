import {UserQuestion} from "./UserQuestion";

export class UserTest {
  id: string = null;
  started: string = null;
  finished: string = null;
  isPassed: boolean = false;
  score: number = null;
  testId: string = null;
  userId: string = null;
  userTestQuestions: Array<UserQuestion> = null;
}
