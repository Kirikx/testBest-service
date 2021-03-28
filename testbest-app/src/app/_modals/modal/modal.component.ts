import {Component, Output, EventEmitter, ViewChild, ElementRef} from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent {
  @Output() close = new EventEmitter();

  @ViewChild('overlay') overlay: ElementRef<HTMLDivElement>;

  closeModal(event) {
    //TODO Нужно добавить, диалог, что при закрытии, если менялись данные, предупредить, что все будет сброшено
    if (this.overlay.nativeElement === event.target) {
      this.close.emit();
    }
  }
}
