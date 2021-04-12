import {ChapterFull} from "./ChapterFull";

export class TestFull {
  topicId: string = null;

  id: string = null;
  name: string = null;
  description: string = null;
  duration:	number = null;
  passScore: number = null;

  chapters: Array<ChapterFull> = null;

  isDeleted: boolean = false;
  authorId: string = null;
  created: string = null;
}
