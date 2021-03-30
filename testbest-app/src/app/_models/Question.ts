import {Answer} from "./Answer";

export class Question {
  id: string = null;
  topicId: string = null;
  questionTypeId: string = null;
  question: string = null;
  answers: Array<Answer> = null;
}
