import {TestFull} from "../TestFull";

export class Topic {
  id: string = null;
  name: string = null;
  description: string = null;
  tests: Array<TestFull> = [];
}
