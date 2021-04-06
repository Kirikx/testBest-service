import {Component, Inject, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {Test} from "../_models/createTest/Test";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Chapter} from "../_models/createTest/Chapter";
import {ChapterService} from "../_services/chapter.service";
import {TreeviewComponent, TreeviewConfig, TreeviewItem} from "ngx-treeview";
import {QuestionFull} from "../_models/createTest/QuestionFull";
import {QuestionType} from "../_models/createTest/parameters/QuestionType";
import {QuestionService} from "../_services/question.service";
import {QuestionTypeService} from "../_services/question-type.service";
import {ChapterWrap} from "../_models/ChapterWrap";
import {AnswerService} from "../_services/answer.service";
import {AnswerFull} from "../_models/createTest/AnswerFull";

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
  typeQ: QuestionType;
  questionTypes: Array<QuestionType>;
  answer: AnswerFull
  answers: Array<AnswerFull>;

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
    private answerService: AnswerService,
    private route: ActivatedRoute) {
    this.test = new Test();
    this.answer = new AnswerFull();
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

  getChapters() {
    this.chapters = new Array<Chapter>();
    this.test.chapters.forEach(chapter => {
      this.chapters.push(chapter);
    })
  }

  createChapter(): void {
    if (!this.formChapterCreate.valid) {
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

  deleteChapter(): void {
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

//Обработка вопросов
  formQuestionCreate = new FormGroup({
    chaptersSelect: new FormControl('', Validators.required),
    questionTypeId: new FormControl('', Validators.required),
    question: new FormControl('', Validators.required),
    answer: new FormControl('', Validators.required),
    answerTest: new FormControl('', Validators.required),
    //answer: new FormArray([] , Validators.required),
  });

  checkArray = new Array<AnswerFull>();

  setForValidation() {
    if (this.question.questionTypeId != null) {
      this.questionTypes.forEach(value => {
        if (this.question.id != null) {
          if (value.id == this.question.questionTypeId && value.name == 'Вопрос со свободным ответом') {
            this.answer = this.question.answers[0];
            this.formQuestionCreate.setValue({
              chaptersSelect: this.question.chapters,
              questionTypeId: this.question.questionTypeId,
              question: this.question.question,
              answer: this.answer,
              answerTest: this.answer.answer
            })
          } else {
            this.formQuestionCreate.setValue({
              chaptersSelect: this.question.chapters,
              questionTypeId: this.question.questionTypeId,
              question: this.question.question,
              answer: this.question.answers,
              answerTest: this.answer.answer
            })
          }
        }
      });
    } else {
      this.formQuestionCreate.setValue({
        chaptersSelect: this.question.chapters,
        questionTypeId: this.question.questionTypeId,
        question: this.question.question,
        answer: this.question.answers,
        answerTest: this.answer.answer
      });
    }
  }

  @ViewChild(TreeviewComponent, {static: false})
  treeViewComponentSelected: TreeviewComponent;
  selectData: TreeviewItem[];
  select = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: true,
    hasCollapseExpand: false,
    maxHeight: 300
  });
  countSelect: number;

  getSelectData(): void {
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
    this.answers = new Array<AnswerFull>();
    this.answer = new AnswerFull();
    this.typeQ = null;
    this.setForValidation();
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
        this.answers = this.question.answers;
        this.getSelectData();
        this.setForValidation();
        this.checkTypeQuestionUI(false);
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
      this.checkTypeQuestionUI(true);
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
            this.setForValidation()
            this.formQuestionCreate.patchValue({chaptersSelect: this.question.chapters})
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

  changeQuestion(event) {
    this.question.question = event.target.value;
  }

  changeQuestionTypeSelect(event) {
    this.formQuestionCreate.get("questionTypeId").setValue(event.target.value, {
      onlySelf: true
    })
    this.question.questionTypeId = event.target.value.substring(3);
    this.isSubmitted = true;
    this.answer = new AnswerFull();
    this.question.answers = new Array<AnswerFull>();
    this.checkTypeQuestionUI(true);
  }

  createQuestion(): void {
    for (let ans of this.question.answers) {
      if (ans.answer == "" || ans.answer == null) {
        this.formQuestionCreate.patchValue({answer: null})
        break;
      }
    }
    if (!this.formQuestionCreate.valid) {
      this.isSubmitted = false;
    } else {
      let answersLoc = new Array<AnswerFull>();
      if (this.question.id == null) {
        this.question.answers = answersLoc;
        this.questionService.createQuestion(this.question).subscribe(
          data => {
            this.question = data;
            this.questionTypes.forEach(value => {
              if (value.id == this.question.questionTypeId && value.name == 'Вопрос со свободным ответом') {
                this.answer.questionId = this.question.id;
                this.answers.push(this.answer);
                this.question.answers = this.answers;
              } else {
                this.answers.forEach(ans => {
                  ans.id = null;
                  ans.questionId = this.question.id;
                });
                this.question.answers = this.answers;
              }
            });
            this.editQuestion();
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
        for (let type of this.questionTypes) {
          if (type.id == this.question.questionTypeId && type.name == 'Вопрос со свободным ответом') {
            answersLoc.push(this.answer);
            this.question.answers = answersLoc;
            break;
          }
        }
        this.editQuestion();
      }
    }
  }

  editQuestion(): void {
    let editAnswer = new Array<AnswerFull>();
    for (let ans of this.question.answers) {
      if (ans.id != null && ans.id.length < 10) {
        ans.id = null;
        ans.questionId = this.question.id;
        this.answerService.createAnswer(ans).subscribe(
          data => {
            ans = data;
            editAnswer.push(ans);
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
        editAnswer.push(ans);
      }
    }
    this.question.answers = editAnswer;
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

  deleteQuestion(): void {
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

  checkTypeQuestionUI(isNewAnswer: boolean) {
    if (this.question.questionTypeId != null) {
      this.questionTypes.forEach(value => {
        if (value.id == this.question.questionTypeId) {
          this.typeQ = value;
          if (this.typeQ.name == 'Одиночный') {
            if (isNewAnswer) {
              this.answers = new Array<AnswerFull>();
              this.newAnswer();
              this.newAnswer();
            } else {
              for (let answer of this.answers) {
                if (answer.isCorrect) {
                  this.formQuestionCreate.patchValue({answer: answer.answer});
                }
              }
            }
          }
          if (this.typeQ.name == 'Множественный') {
            if (isNewAnswer) {
              this.answers = new Array<AnswerFull>();
              this.newAnswer();
              this.newAnswer();
              this.newAnswer();
            } else {
              for (let answer of this.answers) {
                if (answer.isCorrect) {
                  this.checkArray.push(answer);
                  this.formQuestionCreate.patchValue({answer: this.checkArray});
                }
              }
            }
            console.log(this.checkArray);
          }
        }
      });
    }
  }

//Обработка ответов
  newAnswer() {
    this.answer = new AnswerFull();
    this.answer.id = "" + this.getRandomInt();
    this.answers.push(this.answer);
    this.question.answers = this.answers;
    this.formQuestionCreate.patchValue({answer: this.answer.answer});
  }

  changeFreeAnswer(event) {
    this.answer.answer = event.target.value;
    this.answer.isCorrect = true;
    this.formQuestionCreate.patchValue({answer: this.answer})
  }

  addAnswer(): void {
    this.newAnswer();
    this.checkTypeQuestionUI(false);
  }

  deleteAnswer(id: String): void {
    if (id.length < 10) {
      let buffAnswer = new Array<AnswerFull>();
      this.answers.forEach(value => {
        if (value.id != id) buffAnswer.push(value);
      })
      this.answers = buffAnswer;
      this.question.answers = this.answers;
      this.checkTypeQuestionUI(false);
    } else {
      //TODO В API нет этой возможности
      // for (let ans of this.answers) {
      //   if (ans.id == id) {
      //     this.answerService.deleteAnswer(ans).subscribe(
      //       data => {
      //         let buffAnswer = new Array<AnswerFull>();
      //         this.answers.forEach(value => {
      //           if (value.id != id) buffAnswer.push(value);
      //         })
      //         this.answers = buffAnswer;
      //         this.question.answers = this.answers;
      //         this.checkTypeQuestionUI(false);
      //       },
      //       error => {
      //         if (error.statusText == "Unknown Error") {
      //           this.errorMessage = "Server is not responding";
      //         } else {
      //           this.errorMessage = error.message;
      //         }
      //       }
      //     );
      //   }
      // }
    }
  }

  changeRadioButtonAnswer(event) {
    this.formQuestionCreate.patchValue({answerTest: "Варианты ответа"});
    this.answers.forEach(check => {
      if (check.id == event.target.id) {
        check.isCorrect = true;
        this.formQuestionCreate.patchValue({answer: check.answer})
      } else {
        check.isCorrect = false;
      }
    })
    this.question.answers = this.answers;
  }

  changeInputAnswer(event) {
    this.formQuestionCreate.patchValue({answerTest: "Варианты ответа"});
    let buff = new Array<AnswerFull>();
    this.answers.forEach(check => {
      if (check.id == event.target.content) {
        check.answer = event.target.value;
      }
      buff.push(check)
    })
    this.answers = buff;
    this.question.answers = this.answers
    for (let ans of this.answers) {
      if (ans.answer != "") {
        if (ans.isCorrect) {
          this.isSubmitted = true;
        }
      }
    }
  }

  getAnswer() {
    this.answerService.getFullAnswer(this.answer).subscribe(
      data => {
        // this.question = data;
        // this.setForValidation();
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

  changeCheckboxAnswer(e) {
    if (e.target.checked) {
      for (let answer of this.question.answers) {
        if (answer.id == e.target.content) {
          if (answer.answer != null) {
            answer.isCorrect = true;
            this.checkArray.push(answer);
            break;
          }
        }
      }
    } else {
      let buffArray = Array<AnswerFull>();
      this.checkArray.forEach(element => {
        if (element.id != e.target.content) {
          buffArray.push(element)
          return;
        } else {
          element.isCorrect = false;
        }
      });
      this.checkArray = buffArray;
    }
  }


//Общее закрытие модальных окон
  closeModal() {
    this.showModalQuestion = false;
    this.showModalChapter = false;
    this.isSubmitted = true;
  }

  getRandomInt(): number {
    return Math.floor(Math.random() * 10000);
  }
}
