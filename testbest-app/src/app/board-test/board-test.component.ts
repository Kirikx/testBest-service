import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {Test} from "../_models/createTest/Test";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Chapter} from "../_models/createTest/Chapter";
import {ChapterService} from "../_services/chapter.service";
import {TreeItem, TreeviewConfig, TreeviewItem} from "ngx-treeview";

@Component({
  selector: 'app-board-test',
  templateUrl: './board-test.component.html',
  styleUrls: ['./board-test.component.css']
})
export class BoardTestComponent implements OnInit {
  test: Test;
  chapter: Chapter;
  topics: Array<Topic>;

  formTestCreate = new FormGroup({
    topicId: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    description: new FormControl(),
    duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(300)]),
    passScore: new FormControl('', [Validators.required, Validators.min(1)])
  });

  formChapterCreate = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl()
  });

  // formQuestionCreate = new FormGroup({
  //   topicId: new FormControl('', Validators.required),
  //   name: new FormControl('', Validators.required),
  //   description: new FormControl(),
  //   duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(300)])
  // });

  isCreateTest = false; //записан ли тест?
  isCreateChapter = false; //записан ли раздел?
  isCreateQuestion = false; //записан ли вопрос?

  isSubmitted = true; //нужна валидация форм?

  showModalChapter = false;
  showModalQuestion = false;
  errorMessage: string;

  item: TreeItem;
  items: TreeviewItem[];
  config = TreeviewConfig.create({
    hasFilter: true,
    hasAllCheckBox: false,
    hasCollapseExpand: true,
    decoupleChildFromParent: true,
    maxHeight: 700
  });

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService,
    private chapterService: ChapterService,
    private route: ActivatedRoute) {
    this.test = new Test();
  }

  ngOnInit(): void {
    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }
    this.getTest();
    this.getTopics();
  }

  //Обработка дерева теста
  onSelectedChangeTest(event) {
  }

  onFilterChangeTest(event) {

  }

  getTreeData(): TreeviewItem[] {
    let chapters: TreeviewItem;
    let questions: TreeviewItem;
    let answers: TreeviewItem;

    const root = new TreeviewItem({
      text: this.test.name, value: 1, checked: false, children: [
        {text: 'Разделы', value: 2, checked: false, disabled: true}
      ]
    });

    this.test.chapters.forEach(chapter => {
      chapters = new TreeviewItem({
        text: chapter.name, value: chapter.id, checked: false, children: [
          {text: 'Вопросы', value: 3, checked: false, disabled: true}
        ]
      });
      chapter.questions.forEach(question => {
        questions = new TreeviewItem({
          text: question.question, value: question.id, checked: false, children: [
            {text: 'Ответы', value: 4, checked: false, disabled: true}
          ]
        });
        chapters.children.push(questions);

        question.answers.forEach(answer => {
          answers = new TreeviewItem({text: answer.answer, value: answer.id, checked: false});
          questions.children.push(answers);
        });

      });
      root.children.push(chapters);
    });
    return [root];
  }


  //Обработка топика
  getTopics() {
    this.topicService.getTopics().subscribe(
      data => {
        this.topics = data;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    )
  }

  //Обработка теста
  get validationTestForm() {
    return this.formTestCreate.controls;
  }

  changeTestSelect(event) {
    this.formTestCreate.get("topicId").setValue(event.target.value, {
      onlySelf: true
    })
    this.test.topicId = event.target.value.substring(3);
  }

  changeTestDescription(event) {
    this.test.description = event.target.value;
  }

  changeTestName(event) {
    this.test.name = event.target.value;
  }

  changeTestDuration(event) {
    this.test.duration = event.target.value;
  }

  changeTestPassScore(event) {
    this.test.passScore = event.target.value;
  }

  createTest(): void {
    if (!this.formTestCreate.valid) {
      this.isSubmitted = false;
    } else {
      this.testService.createTest(this.test).subscribe(
        data => {
          this.test = data;
          this.router.navigate(["/test/" + this.test.id])
        },
        error => {
          if (error.statusText == "Unknown Error") {
            this.errorMessage = "Server is not responding";
          } else {
            this.errorMessage = error.message;
          }
        }
      );
    }
  }

  editTest(): void {
    if (!this.formTestCreate.valid) {
      this.isSubmitted = false;
    } else {
      this.testService.editTest(this.test).subscribe(
        data => {
          this.test = data;
        },
        error => {
          if (error.statusText == "Unknown Error") {
            this.errorMessage = "Server is not responding";
          } else {
            this.errorMessage = error.message;
          }
        }
      );
    }
  }

  getTest() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id != '0') {
      this.test.id = id;
      this.testService.getTest(this.test).subscribe(
        data => {
          this.isCreateTest = true;
          this.test = data;
          this.formTestCreate.setValue({
            topicId: this.test.topicId,
            name: this.test.name,
            description: this.test.description,
            duration: this.test.duration,
            passScore: this.test.passScore
          })
          console.log(this.test);
          this.items = this.getTreeData();
        },
        error => {
          if (error.statusText == "Unknown Error") {
            this.errorMessage = "Server is not responding";
          } else {
            this.errorMessage = error.message;
          }
        }
      );
    }
  }

  //Обработка разделов
  clearFormCreateChapter() {
    this.chapter = new Chapter();
    this.chapter.testId = this.test.id;
    this.formChapterCreate.setValue({
      name: this.chapter.name,
      description: this.chapter.description,
    })
  }

  openModalNewChapter() {
    this.showModalChapter = true;
    this.clearFormCreateChapter();
  }

  closeModalCreateChapter() {
    this.showModalChapter = false;
    this.clearFormCreateChapter();
  }

  openModalEditChapter(id: String) {
    this.showModalChapter = true;
    this.isCreateChapter = true;
    this.getChapter(this.test.chapters.find(chapter => chapter.id === id));
  }

  get validationChapterForm() {
    return this.formChapterCreate.controls;
  }

  changeChapterDescription(event) {
    this.chapter.description = event.target.value;
  }

  changeChapterName(event) {
    this.chapter.name = event.target.value;
  }

  getChapter(chapter: Chapter) {
    this.chapterService.getChapter(chapter).subscribe(
      data => {
        this.chapter = data;
        this.formChapterCreate.setValue({
          name: this.chapter.name,
          description: this.chapter.description,
        })
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.errorMessage = "Server is not responding";
        } else {
          this.errorMessage = error.message;
        }
      }
    )
  }

  createChapter(): void {
    if (!this.formChapterCreate.valid) {
      this.isSubmitted = false;
    } else {
      this.chapterService.createChapter(this.chapter).subscribe(
        data => {
          this.closeModal();
          this.getTest();
          this.clearFormCreateChapter();
        },
        error => {
          if (error.statusText == "Unknown Error") {
            this.errorMessage = "Server is not responding";
          } else {
            this.errorMessage = error.message;
          }
        }
      );
    }
  }

  //Обработка вопросов
  openModalNewQuestion() {
    this.showModalQuestion = true;
  }

  closeModalCreateQuestion() {
    this.showModalChapter = false;
  }

  closeModal() {
    this.showModalQuestion = false;
    this.showModalChapter = false;
  }
}
