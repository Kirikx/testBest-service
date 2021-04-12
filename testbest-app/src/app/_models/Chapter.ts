import {Question} from "./Question";

export class Chapter {
  id: string = null;
  name: string = null;
  description: string = null;
  testId: string = null;
  questions: Array<Question> = null;
}
