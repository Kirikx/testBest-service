import {ChapterFull} from "./ChapterFull";

export class ChapterWrap {

  constructor(chapter: ChapterFull) {
    this.id = chapter.id;
    this.name = chapter.name;
    this.description = chapter.description;
  }

  id: string = null;
  name: string = null;
  description: string = null;
}
