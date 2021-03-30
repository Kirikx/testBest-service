import {QuestionFull} from "./QuestionFull";

export class Chapter {
  id: string = null;
  name: string = null;
  description: string = null;
  isDeleted:	boolean = false;
  testId:	string = null;
  questions: Array<QuestionFull> = null;
}
