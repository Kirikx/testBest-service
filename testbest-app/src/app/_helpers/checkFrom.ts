import {FormGroup} from "@angular/forms";

export class CheckFrom {

  checkRequired(form: FormGroup, formNamePram: string): boolean {
    if (!form.get(formNamePram).valid) {
      return form.get(formNamePram).value != '' && form.get(formNamePram).value != null;
    } else {
      if (form.get(formNamePram).errors == null) {
        return form.get(formNamePram).value != '' && form.get(formNamePram).value != null;
      } else {
        return form.get(formNamePram).valid
      }
    }
  }
}

