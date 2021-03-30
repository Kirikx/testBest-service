import {AnswerFull} from "./AnswerFull";
import {ChapterWrap} from "../ChapterWrap";

export class QuestionFull {
  id: string = null;
  question: string = null;
  questionTypeId: string = null;
  topicId: string = null;
  isDeleted: boolean = false;
  answers: Array<AnswerFull> = null;
  chapter: Array<ChapterWrap> = null;
}




