import {Chapter} from "./createTest/Chapter";

export class ChapterWrap {

  constructor(chapter: Chapter) {
    this.id = chapter.id;
    this.name = chapter.name;
    this.description = chapter.description;
  }

  id: string = null;
  name: string = null;
  description: string = null;
}
