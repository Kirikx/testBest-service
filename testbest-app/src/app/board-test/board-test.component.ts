import {Component, Inject, OnInit, Renderer2, ViewChild} from '@angular/core';
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
import {TreeviewComponent, TreeviewConfig, TreeviewItem} from "ngx-treeview";
import {QuestionFull} from "../_models/createTest/QuestionFull";
import {QuestionType} from "../_models/createTest/parameters/QuestionType";
import {QuestionService} from "../_services/question.service";
import {QuestionTypeService} from "../_services/question-type.service";
import {ChapterWrap} from "../_models/ChapterWrap";
import {isLineBreak} from "codelyzer/angular/sourceMappingVisitor";

@Component({
  selector: 'app-board-test',
  templateUrl: './board-test.component.html',
  styleUrls: ['./board-test.component.css']
})
export class BoardTestComponent implements OnInit {
  test: Test;
  chapter: Chapter;
  chapters: Array<Chapter>;
  topics: Array<Topic>;
  question: QuestionFull;
  questionTypes: Array<QuestionType>;

  isCreateTest = false; //записан ли тест?
  isSubmitted = true; //нужна валидация форм?

  showModalChapter = false;
  showModalQuestion = false;
  errorMessage: string;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService,
    private chapterService: ChapterService,
    private questionService: QuestionService,
    private questionTypeService: QuestionTypeService,
    private route: ActivatedRoute) {
    this.test = new Test();
  }

  ngOnInit(): void {
    if (!this.tokenStorage.getToken()) {
      this.router.navigate(["/home"])
    }
    this.getTest();
    this.getTopics();
    this.getQuestionTypes()
  }

  //Обработка дерева теста
  @ViewChild(TreeviewComponent, {static: false}) treeViewComponent: TreeviewComponent;
  items: TreeviewItem[];
  config = TreeviewConfig.create({
    hasFilter: true,
    hasAllCheckBox: false,
    hasCollapseExpand: true,
    decoupleChildFromParent: false,
    maxHeight: 700
  });
  selectIsChapter = false; //выбрали раздел/вопрос(-ы)
  selectChapters = new Array<string>();
  selectQuestions = new Array<string>();
  countTree: number;

  onSelectedChangeTest(event) {
    const checkedItems = this.treeViewComponent.selection.checkedItems;
    if (checkedItems.length > 0) {
      if (this.countTree > checkedItems.length) {
        this.selectChapters = new Array<string>();
        this.selectQuestions = new Array<string>();
      }
      let id = checkedItems[0].value;
      this.test.chapters.forEach(chapter => {
        chapter.questions.forEach(question => {
          if (question.id === id) {
            this.selectIsChapter = true;
            if (this.selectChapters.find(str => str === chapter.id) == null) {
              this.selectChapters.push(chapter.id);
            }
            // //TODO Добавить проверку на то чтобы был выбран только один раздел!!!
            // if (this.selectChapter.length > 1) {
            // }
          }
        });
      });
      checkedItems.forEach(item => {
        if (this.selectQuestions.find(str => str === item.value) == null) {
          this.selectQuestions.push(item.value);
        }
      })
      this.countTree = checkedItems.length;
    } else {
      this.selectChapters = new Array<string>();
      this.selectQuestions = new Array<string>();
      this.selectIsChapter = false;
      this.countTree = 0;
    }
  }

  onFilterChangeTest(event) {
  }

  getTreeData(): TreeviewItem[] {
    let chapters: TreeviewItem;
    let questions: TreeviewItem;
    // let answers: TreeviewItem;

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
          text: question.question, value: question.id, checked: false
        });
        chapters.children.push(questions);

        // question.answers.forEach(answer => {
        //   answers = new TreeviewItem({text: answer.answer, value: answer.id, checked: false, disabled: true});
        //   questions.children.push(answers);
        // });

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

  //Получения значения кнопок
  getNameCreateButton(): string {
    if (this.selectIsChapter) {
      return "Редактировать";
    } else {
      return "Добавить";
    }
  }

  getNameUpdateButton(): string {
    if (this.selectIsChapter) {
      return "Сохранить";
    } else {
      return "Создать";
    }
  }

  //Обработка теста
  formTestCreate = new FormGroup({
    topicId: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    description: new FormControl(),
    duration: new FormControl('', [Validators.required, Validators.min(10), Validators.max(300)]),
    passScore: new FormControl('', [Validators.required, Validators.min(1)])
  });

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
      if (!this.isCreateTest) {
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
  }

  deleteTest(): void {
    this.testService.deleteTest(this.test).subscribe(
      data => {
        this.router.navigate(["/manager"])
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

  getTest() {
    let id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
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
  formChapterCreate = new FormGroup({
    name: new FormControl('', Validators.required),
    description: new FormControl()
  });

  clearFormCreateChapter() {
    this.chapter = new Chapter();
    this.chapter.testId = this.test.id;
    this.formChapterCreate.setValue({
      name: this.chapter.name,
      description: this.chapter.description,
    })
  }

  openModalChapter() {
    this.showModalChapter = true;
    if (this.selectChapters[0] == null) {
      this.clearFormCreateChapter();
    } else {
      this.getChapter(this.test.chapters.find(chapter => chapter.id === this.selectChapters[0]));
    }
  }

  closeModalCreateChapter() {
    this.isSubmitted = true;
    this.showModalChapter = false;
    this.clearFormCreateChapter();
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

  getChapter(chapter
               :
               Chapter
  ) {
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

  getChapters() {
    this.chapters = new Array<Chapter>();
    this.test.chapters.forEach(chapter => {
      this.chapters.push(chapter);
    })
  }

  createChapter()
    :
    void {
    if (!
      this.formChapterCreate.valid
    ) {
      this.isSubmitted = false;
    } else {
      if (this.chapter.id == null) {
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
      } else {
        this.chapterService.editChapter(this.chapter).subscribe(
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
  }

  deleteChapter()
    :
    void {
    if (!
      this.formChapterCreate.valid
    ) {
      this.isSubmitted = false;
    } else {
      this.chapterService.deleteChapter(this.chapter).subscribe(
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
  formQuestionCreate = new FormGroup({
    chaptersSelect: new FormControl([''], Validators.required),
    questionTypeId: new FormControl('', Validators.required),
    question: new FormControl('', Validators.required)
  });

  @ViewChild(TreeviewComponent, {static: false})
  treeViewComponentSelected
    :
    TreeviewComponent;
  selectData: TreeviewItem[];
  select = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: true,
    hasCollapseExpand: false,
    maxHeight: 500
  });
  countSelect: number;

  getSelectData()
    :
    void {
    let count = 0;
    let item;
    let check = false;
    this.selectData = [];
    this.test.chapters.forEach(chapter => {
      this.question.chapters.forEach(qCh => {
        if (chapter.id == qCh.id && count <= this.question.chapters.length) {
          check = true;
          count = count + 1;
          item = new TreeviewItem({
            text: chapter.name,
            value: chapter.id,
            checked: true
          });
        } else {
          check = false;
        }
      });
      if (!check) {
        item = new TreeviewItem({
          text: chapter.name,
          value: chapter.id,
          checked: false
        });
      }
      this.selectData.push(item);
    })
  }

  clearFormCreateQuestion() {
    this.question = new QuestionFull();
    this.question.topicId = this.test.topicId;
    this.formQuestionCreate.setValue({
      chaptersSelect: this.question.chapters,
      questionTypeId: this.question.questionTypeId,
      question: this.question.question
    })
  }

  getQuestionTypes() {
    this.questionTypeService.getQuestionTypes().subscribe(
      data => {
        this.questionTypes = data;
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

  getQuestion() {
    this.questionService.getFullQuestion(this.question).subscribe(
      data => {
        this.question = data;
        this.getSelectData();
        this.formQuestionCreate.setValue({
          chaptersSelect: this.question.chapters,
          questionTypeId: this.question.questionTypeId,
          question: this.question.question
        })
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

  openModalQuestion() {
    this.showModalQuestion = true;
    this.question = new QuestionFull();
    if (this.selectQuestions[0] == null) {
      this.clearFormCreateQuestion();
      this.getSelectData();
    } else {
      //TODO продумать про множественный выбор
      this.question.id = this.selectQuestions[0];
      this.getQuestion();
    }
  }

  onSelectedChangeQuestion(event) {
    if (this.countSelect > event.length) {
      this.question.chapters = new Array<ChapterWrap>();
    }
    this.test.chapters.forEach(chapter => {
      event.forEach(ev => {
        if (chapter.id == ev) {
          if (!this.question.chapters.find(check => check.id == chapter.id)) {
            this.question.chapters.push(new ChapterWrap(chapter));
            this.formQuestionCreate.setValue({
              chaptersSelect: this.question.chapters,
              questionTypeId: this.question.questionTypeId,
              question: this.question.question
            })
          }
        }
      })
    })
    this.countSelect = event.length;
  }

  closeModalCreateQuestion() {
    this.isSubmitted = true;
    this.showModalQuestion = false;
    this.clearFormCreateQuestion();
  }

  get validationQuestionForm() {
    return this.formQuestionCreate.controls;
  }

// changeQuestionSelectChapter(event) {
//   this.formQuestionCreate.get("chapters").setValue(event.target.value, {
//     onlySelf: true
//   })
//   this.question.chapters.push(this.test.chapters.find(ch => ch.id === event.target.value.substring(3)));
// }

  changeQuestion(event) {
    this.question.question = event.target.value;
  }

  changeQuestionTypeSelect(event) {
    this.formQuestionCreate.get("questionTypeId").setValue(event.target.value, {
      onlySelf: true
    })
    this.question.questionTypeId = event.target.value.substring(3);
  }

  createQuestion()
    :
    void {
    if (!
      this.formQuestionCreate.valid
    ) {
      this.isSubmitted = false;
    } else {
      if (this.question.id == null) {
        this.questionService.createQuestion(this.question).subscribe(
          data => {
            this.closeModal();
            this.getTest();
            this.clearFormCreateQuestion();
          },
          error => {
            if (error.statusText == "Unknown Error") {
              this.errorMessage = "Server is not responding";
            } else {
              this.errorMessage = error.message;
            }
          }
        );
      } else {
        this.questionService.editQuestion(this.question).subscribe(
          data => {
            this.closeModal();
            this.getTest();
            this.clearFormCreateQuestion();
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
  }

  deleteQuestion()
    :
    void {
    if (!
      this.formQuestionCreate.valid
    ) {
      this.isSubmitted = false;
    } else {
      this.questionService.deleteQuestion(this.question).subscribe(
        data => {
          this.closeModal();
          this.getTest();
          this.clearFormCreateQuestion();
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

//Общее закрытие модальных окон
  closeModal() {
    this.showModalQuestion = false;
    this.showModalChapter = false;
    this.isSubmitted = true;
  }
}
