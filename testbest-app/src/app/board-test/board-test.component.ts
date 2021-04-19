import {Component, Inject, OnInit, Renderer2, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";
import {DOCUMENT} from "@angular/common";
import {TestFull} from "../_models/createTest/TestFull";
import {TestService} from "../_services/test.service";
import {TopicService} from "../_services/topic.service";
import {Topic} from "../_models/createTest/parameters/Topic";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ChapterFull} from "../_models/createTest/ChapterFull";
import {ChapterService} from "../_services/chapter.service";
import {TreeviewComponent, TreeviewConfig, TreeviewItem} from "ngx-treeview";
import {QuestionFull} from "../_models/createTest/QuestionFull";
import {QuestionType} from "../_models/createTest/parameters/QuestionType";
import {QuestionService} from "../_services/question.service";
import {QuestionTypeService} from "../_services/question-type.service";
import {ChapterWrap} from "../_models/createTest/ChapterWrap";
import {AnswerService} from "../_services/answer.service";
import {AnswerFull} from "../_models/createTest/AnswerFull";
import {CheckFrom} from "../_helpers/checkFrom";

@Component({
  selector: 'app-board-test',
  templateUrl: './board-test.component.html',
  styleUrls: ['./board-test.component.css']
})
export class BoardTestComponent implements OnInit {
  checkForm = new CheckFrom();

  test: TestFull;
  chapter: ChapterFull;
  chapters: Array<ChapterFull>;
  topics: Array<Topic>;
  question: QuestionFull;
  questionType: QuestionType;
  questionTypes: Array<QuestionType>;
  answer: AnswerFull
  answers: Array<AnswerFull>;

  isCreateTest = false; //записан ли тест?
  isSubmitted = true; //нужна валидация форм?

  showModalChapter = false;
  showModalQuestion = false;
  message: string;

  formCheckbox: FormGroup; //проверка нескольких Checkbox

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer2: Renderer2,
    private router: Router,
    private fb: FormBuilder,
    private tokenStorage: TokenStorageService,
    private testService: TestService,
    private topicService: TopicService,
    private chapterService: ChapterService,
    private questionService: QuestionService,
    private questionTypeService: QuestionTypeService,
    private answerService: AnswerService,
    private route: ActivatedRoute) {
    this.test = new TestFull();
    this.answer = new AnswerFull();
    this.formCheckbox = this.fb.group({
      checkArray: this.fb.array([], [Validators.required])
    })
  }

  ngOnInit(): void {
    this.tokenStorage.checkTokenPrivate(this.router);

    this.getTest();
    this.getTopics();
    this.getQuestionTypes();
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

  chaptersNullQuestion: Array<ChapterFull>;
  selectNullQuestion = false;

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
      let id = checkedItems[0].value + '';
      this.test.chapters.forEach(chapter => {
        if (id.length < 10) {
          this.selectNullQuestion = true;
          for (let buff of this.chaptersNullQuestion) {
            if (buff.id == chapter.id) {
              if (buff.questions[0].id == id) {
                this.selectIsChapter = true;
                this.selectChapters.push(chapter.id);
                break;
              }
            }
          }
        } else {
          chapter.questions.forEach(question => {
            if (question.id === id) {
              this.selectIsChapter = true;
              if (this.selectChapters.find(str => str === chapter.id) == null) {
                this.selectChapters.push(chapter.id);
              }
              //TODO Добавить проверку на то чтобы был выбран только один раздел!!!
              // if (this.selectChapter.length > 1) {}
            }
          });
        }
      });
      checkedItems.forEach(item => {
        if (this.selectQuestions.find(str => str === item.value) == null) {
          if (item.text != 'Добавить вопрос') {
            this.selectQuestions.push(item.value);
          }
        }
      })
      this.countTree = checkedItems.length;
    } else {
      this.selectChapters = new Array<string>();
      this.selectQuestions = new Array<string>();
      this.selectIsChapter = false;
      this.selectNullQuestion = false;
      this.countTree = 0;
    }
  }

  onFilterChangeTest(event) {
  }

  getTreeData(): TreeviewItem[] {
    this.chaptersNullQuestion = new Array<ChapterFull>();
    let chapters: TreeviewItem;
    let questions: TreeviewItem;

    const root = new TreeviewItem({
      text: this.test.name, value: 1, checked: false, children: [
        {text: 'Разделы', value: 2, checked: false, disabled: true}
      ]
    });

    let index: number = 0;
    this.test.chapters.forEach(chapter => {
      index++;
      if (chapter.questions.length > 0) {
        chapters = new TreeviewItem({
          text: chapter.name, value: chapter.id, checked: false, children: [
            {text: 'Вопросы', value: index, checked: false, disabled: true}
          ]
        });
      } else {
        chapters = new TreeviewItem({
          text: chapter.name, value: chapter.id, checked: false, children: [
            {text: 'Добавить вопрос', value: index, checked: false, disabled: false}
          ]
        });
        let questionFull = new QuestionFull();
        questionFull.id = index + '';
        questionFull.question = 'Добавить вопрос';
        let chapterBuff = new ChapterFull();
        chapterBuff.id = chapter.id;
        chapterBuff.name = chapter.name;
        chapterBuff.questions = new Array<QuestionFull>();
        chapterBuff.questions.push(questionFull);
        this.chaptersNullQuestion.push(chapterBuff);
      }
      chapter.questions.forEach(question => {
        questions = new TreeviewItem({
          text: question.question, value: question.id, checked: false
        });
        chapters.children.push(questions);
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
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
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
    duration: new FormControl('', [Validators.required, Validators.min(1), Validators.max(300)]),
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
        this.testService.createTestFull(this.test).subscribe(
          data => {
            this.test = data;
            this.router.navigate(["/test/" + this.test.id])
          },
          error => {
            if (error.statusText == "Unknown Error") {
              this.message = "Server is not responding";
            } else {
              this.message = error.message;
            }
          }
        );
      } else {
        this.testService.editTestFull(this.test).subscribe(
          data => {
            this.test = data;
            this.message = "Изменения внесены"
          },
          error => {
            if (error.statusText == "Unknown Error") {
              this.message = "Server is not responding";
            } else {
              this.message = error.message;
            }
          }
        );
      }
    }
  }

  deleteTest(): void {
    this.testService.deleteTestFull(this.test).subscribe(
      data => {
        this.router.navigate(["/manager"])
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
        }
      }
    );
  }

  getTest() {
    let id = this.route.snapshot.paramMap.get('id');
    if (id != null) {
      this.test.id = id;
      this.testService.getTestFull(this.test).subscribe(
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
            this.message = "Server is not responding";
          } else {
            this.message = error.message;
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
    this.chapter = new ChapterFull();
    this.chapter.testId = this.test.id;
    this.formChapterCreate.setValue({
      name: this.chapter.name,
      description: this.chapter.description,
    })
  }

  openModalChapter() {
    this.message = null;
    this.showModalChapter = true;
    if (this.selectChapters[0] == null) {
      this.clearFormCreateChapter();
    } else {
      this.selectIsChapter = true;
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

  getChapter(chapter: ChapterFull) {
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
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
        }
      }
    )
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
              this.message = "Server is not responding";
            } else {
              this.message = error.message;
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
              this.message = "Server is not responding";
            } else {
              this.message = error.message;
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
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
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
  });

  getChapterId(): string {
    let id: string;
    if (this.question.chapters.length > 0) {
      id = this.question.chapters[0].id;
    } else {
      id = null;
    }
    return id;
  }

  setForValidation() {
    if (this.question.questionTypeId != null) {
      this.questionTypes.forEach(value => {
        if (this.question.id != null) {
          if (value.id == this.question.questionTypeId && value.name == 'Вопрос со свободным ответом') {
            this.answer = this.question.answers[0];
            this.formQuestionCreate.setValue({
              chaptersSelect: this.getChapterId(),
              questionTypeId: this.question.questionTypeId,
              question: this.question.question,
              answer: this.answer,
              answerTest: this.answer.answer
            })
          } else {
            this.formQuestionCreate.setValue({
              chaptersSelect: this.getChapterId(),
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
        chaptersSelect: this.getChapterId(),
        questionTypeId: this.question.questionTypeId,
        question: this.question.question,
        answer: this.question.answers,
        answerTest: this.answer.answer
      });
    }
  }

  getSelectChapter(): void {
    this.chapters = this.test.chapters;
    this.formQuestionCreate.patchValue({chaptersSelect: this.getChapterId()});
  }

  clearFormCreateQuestion() {
    this.question = new QuestionFull();
    this.question.topicId = this.test.topicId;
    this.answers = new Array<AnswerFull>();
    this.answer = new AnswerFull();
    this.questionType = null;
    this.setForValidation();
    let checkArray: FormArray = this.formCheckbox.get('checkArray') as FormArray;
    checkArray.clear();
  }

  getQuestionTypes() {
    this.questionTypeService.getQuestionTypes().subscribe(
      data => {
        this.questionTypes = data;
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
        }
      }
    );
  }

  getQuestion() {
    this.questionService.getFullQuestion(this.question).subscribe(
      data => {
        this.question = data;
        let buff = new Array<AnswerFull>();
        for (let answer of this.question.answers) {
          if (!answer.isDeleted) {
            buff.push(answer);
          }
        }
        this.answers = buff;
        this.question.answers = buff;
        this.getSelectChapter();
        this.setForValidation();
        this.checkTypeQuestionUI(false);
      },
      error => {
        if (error.statusText == "Unknown Error") {
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
        }
      }
    );
  }

  openModalQuestion() {
    this.message = null;
    this.showModalQuestion = true;
    this.question = new QuestionFull();
    if (this.selectQuestions[0] == null) {
      this.selectIsChapter = false;
      this.clearFormCreateQuestion();
      this.getSelectChapter();
      this.checkTypeQuestionUI(true);
    } else {
      //TODO продумать про множественный выбор
      this.question.id = this.selectQuestions[0];
      this.getQuestion();
    }
  }

  onSelectedChapterQuestion(event) {
    console.log(event.target.value);
    let id = event.target.value.substring(3);
    this.question.chapters = new Array<ChapterWrap>();
    for (let chapter of this.chapters) {
      if (chapter.id == id) {
        this.question.chapters.push(new ChapterWrap(chapter));
        this.formQuestionCreate.get("chaptersSelect").setValue(chapter.id, {
          onlySelf: true
        })
        this.setForValidation();
        break;
      }
    }
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
    let newAnswer = true;
    this.formQuestionCreate.get("questionTypeId").setValue(event.target.value, {
      onlySelf: true
    })
    this.question.questionTypeId = event.target.value.substring(3);
    this.isSubmitted = true

    if (this.question.id != null) {
      for (let type of this.questionTypes) {
        if (type.id == this.question.questionTypeId && type.name == 'Вопрос со свободным ответом') {
          for (let ans of this.question.answers) {
            if (ans.isCorrect) {
              this.answer = ans;
              this.formQuestionCreate.patchValue({answerTest: ans.answer});
            }
          }
          break;
        } else {
          newAnswer = false;
          if (type.name == 'Множественный' && this.question.questionTypeId == type.id) {
            let checkArray: FormArray = this.formCheckbox.get('checkArray') as FormArray;
            checkArray.clear();
            for (let ans of this.question.answers) {
              if (ans.isCorrect) checkArray.push(new FormControl(ans));
            }
            break;
          }
        }
      }
    } else {
      for (let type of this.questionTypes) {
        if (type.name == 'Вопрос со свободным ответом' && this.question.questionTypeId == type.id) {
          this.formQuestionCreate.patchValue({answerTest: null});
        }
      }
    }
    this.checkTypeQuestionUI(newAnswer);
  }

  createQuestion(): void {
    let isFreeAnswer = true;
    let isCheckBoxAnswer = false;
    for (let type of this.questionTypes) {
      if (type.name != 'Вопрос со свободным ответом' && this.question.questionTypeId == type.id) {
        this.formQuestionCreate.patchValue({answerTest: "_"});
        for (let ans of this.question.answers) {
          if (ans.answer == "" || ans.answer == null) {
            this.formQuestionCreate.patchValue({answer: null});
            break;
          } else {
            this.formQuestionCreate.patchValue({answer: ans});
          }
        }
        isFreeAnswer = false;
        if (type.name == 'Множественный' && this.question.questionTypeId == type.id) {
          isCheckBoxAnswer = true;
          break;
        }
        if (type.name == 'Одиночный' && this.question.questionTypeId == type.id) {
          let cheekSelect = false;
          for (let ans of this.question.answers) {
            if (ans.isCorrect) cheekSelect = true;
          }
          if (!cheekSelect) this.formQuestionCreate.patchValue({answer: null});
          break;
        }
      }
    }
    if (!this.formQuestionCreate.valid || (isCheckBoxAnswer && !this.formCheckbox.valid)) {
      this.isSubmitted = false;
    } else {
      let answersLoc = new Array<AnswerFull>();
      if (this.question.id == null) {
        this.question.answers = answersLoc;
        this.questionService.createQuestion(this.question).subscribe(
          data => {
            this.question = data;
            if (isFreeAnswer) {
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
            this.editQuestion();
          },
          error => {
            if (error.statusText == "Unknown Error") {
              this.message = "Server is not responding";
            } else {
              this.message = error.message;
            }
          }
        );
      } else {
        if (isFreeAnswer) {
          for (let ans of this.question.answers) {
            if (ans.id == this.answer.id) {
              answersLoc.push(this.answer);
            } else {
              this.deleteAnswer(ans.id);
            }
          }
          this.question.answers = answersLoc;
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
              this.message = "Server is not responding";
            } else {
              this.message = error.message;
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
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
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
          this.message = "Server is not responding";
        } else {
          this.message = error.message;
        }
      }
    );
  }

  checkTypeQuestionUI(isNewAnswer: boolean) {
    if (this.question.questionTypeId != null) {
      this.questionTypes.forEach(value => {
        if (value.id == this.question.questionTypeId) {
          this.questionType = value;
          if (this.questionType.name == 'Одиночный') {
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
          if (this.questionType.name == 'Множественный') {
            if (isNewAnswer) {
              this.answers = new Array<AnswerFull>();
              this.newAnswer();
              this.newAnswer();
              this.newAnswer();
            } else {
              let checkArray: FormArray = this.formCheckbox.get('checkArray') as FormArray;
              checkArray.clear();
              for (let answer of this.answers) {
                if (answer.isCorrect) {
                  checkArray.push(new FormControl(answer));
                }
              }
            }
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
      for (let ans of this.answers) {
        if (ans.id == id) {
          this.answerService.deleteAnswer(ans).subscribe(
            data => {
              let buffAnswer = new Array<AnswerFull>();
              this.answers.forEach(value => {
                if (value.id != id) buffAnswer.push(value);
              })
              this.answers = buffAnswer;
              this.question.answers = this.answers;
              this.checkTypeQuestionUI(false);
            },
            error => {
              if (error.statusText == "Unknown Error") {
                this.message = "Server is not responding";
              } else {
                this.message = error.message;
              }
            }
          );
        }
      }
    }
  }

  changeRadioButtonAnswer(event) {
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

  changeCheckboxAnswer(event) {
    let buffArray = Array<AnswerFull>();
    const checkArray: FormArray = this.formCheckbox.get('checkArray') as FormArray;
    if (event.target.checked) {
      checkArray.push(new FormControl(event.target.value));
      for (let answer of this.question.answers) {
        if (answer.id == event.target.content) {
          answer.isCorrect = true;
          buffArray.push(answer);
          if (answer.answer != null) {
            this.formQuestionCreate.patchValue({answer: answer.answer});
          }
        } else {
          buffArray.push(answer);
        }
      }
    } else {
      let i: number = 0;
      checkArray.controls.forEach((item: FormControl) => {
        if (item.value == event.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
      for (let answer of this.question.answers) {
        if (answer.id == event.target.content) {
          answer.isCorrect = false;
          buffArray.push(answer);
          if (answer.answer != null) {
            this.formQuestionCreate.patchValue({answer: answer.answer});
          } else {
            this.formQuestionCreate.patchValue({answer: ''});
          }
        } else {
          buffArray.push(answer);
        }
      }
    }
    this.answers = buffArray;
    this.question.answers = this.answers;
  }

  changeInputAnswer(event) {
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
