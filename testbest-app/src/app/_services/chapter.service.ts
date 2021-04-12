import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Global} from "../global";
import {ChapterFull} from "../_models/createTest/ChapterFull";

@Injectable({
  providedIn: 'root'
})
export class ChapterService {
  constructor(private http: HttpClient) {
  }

  getChapters(): Observable<any> {
    return this.http.get(Global.CHAPTER_API, Global.httpOptions);
  }

  getChapter(chapter: ChapterFull): Observable<any> {
    return this.http.get(Global.CHAPTER_API + chapter.id, Global.httpOptions);
  }

  editChapter(chapter: ChapterFull): Observable<any> {
    return this.http.put(Global.CHAPTER_API, chapter, Global.httpOptions);
  }

  deleteChapter(chapter: ChapterFull): Observable<any> {
    return this.http.delete(Global.CHAPTER_API + chapter.id, Global.httpOptions);
  }

  createChapter(chapter: ChapterFull): Observable<any> {
    return this.http.post(Global.CHAPTER_API + 'create', chapter, Global.httpOptions);
  }

}
