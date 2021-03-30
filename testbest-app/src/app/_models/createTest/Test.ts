import {Chapter} from "./Chapter";

export class Test {
  topicId: string = null;

  id: string = null;
  name: string = null;
  description: string = null;
  duration:	number = null;

  chapters: Array<Chapter> = null;

  isDeleted: boolean = false;
  authorId: string = null;
  created: string = null;
}
